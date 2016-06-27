DELIMITER $$

USE pinf_pro$$

DROP PROCEDURE IF EXISTS `placanje`$$

CREATE PROCEDURE `placanje` (	IN duznik VARCHAR(256),
								IN svrha VARCHAR(256),
                                IN poverilac VARCHAR(256),
                                IN datum_prijema TIMESTAMP,
                                IN datum_valute TIMESTAMP,
                                IN racun_duznika VARCHAR(18),
                                IN model_zaduzenja INT(11),
                                IN poziv_na_broj_zaduzenja VARCHAR(20),
                                IN racun_poverioca VARCHAR(18),
                                IN model_odobrenja INT(11),
                                IN poziv_na_broj_odobrenja VARCHAR(20),
                                IN is_hitno BIT(1),
                                IN iznos DOUBLE,
                                IN tip_greske INT(11),
                                IN status_naloga VARCHAR(1),
                                IN naziv_mesta VARCHAR(60),
                                IN oznaka_vrste_placanja INT(11),
                                IN oznaka_valute_placanja VARCHAR(3),
                                OUT rtgs_id BIGINT(20),
                                OUT debug VARCHAR(50))
BEGIN
	DECLARE racunDuznikaId BIGINT(20) DEFAULT -1;
	DECLARE racunPoveriocaId BIGINT(20) DEFAULT -1;
	DECLARE dsrId BIGINT(20) DEFAULT -1;
	DECLARE naseljenoMestoId BIGINT(20) DEFAULT -1;
	DECLARE vrstaPlacanjaId BIGINT(20) DEFAULT -1;
	DECLARE valutaId BIGINT(20) DEFAULT -1;

	SET rtgs_id = -1;

	# Gledamo da li dužnikov račun postoji u našoj banci ili je iz druge banke
	SELECT `id`
	INTO racunDuznikaId
	FROM `racun_pravnog_lica` AS rpl
	WHERE rpl.broj_racuna = racun_duznika;

	# Gledamo da li poveriočev račun postoji u našoj banci ili je iz druge banke
	SELECT `id`
	INTO racunPoveriocaId
	FROM `racun_pravnog_lica` AS rpl
	WHERE rpl.broj_racuna = racun_poverioca;

	# Nadjemo ID od naseljeno mesta
	SELECT `id`
	INTO naseljenoMestoId
	FROM `naseljeno_mesto` AS nm
	WHERE nm.`nm_naziv` = naziv_mesta;

    # Nadjemo ID od vrste placanja
	SELECT `id`
	INTO vrstaPlacanjaId
	FROM `vrsta_placanja` AS vp
	WHERE vp.`oznaka_vrste_placanja` = oznaka_vrste_placanja;

    # Nadjemo ID od valute
	SELECT `id`
	INTO valutaId
	FROM `valuta` AS val
	WHERE val.`zvanicna_sifra` = oznaka_valute_placanja;

	IF ((racunDuznikaId != -1 AND racunPoveriocaId != -1)
		OR (racunDuznikaId = -1 AND racunPoveriocaId != -1)
        OR (racunDuznikaId != -1 AND racunPoveriocaId = -1)) THEN
		BEGIN
			DECLARE idBankeDuznika BIGINT(20) DEFAULT -1;
			DECLARE swiftKodDuznika VARCHAR(8);
			DECLARE obracunskiRacunDuznika VARCHAR(18);
			DECLARE idBankePoverioca BIGINT(20) DEFAULT -1;
			DECLARE swiftKodPoverioca VARCHAR(8);
			DECLARE obracunskiRacunPoverioca VARCHAR(18);

			SET debug = "USO U PRVI IF";

			SELECT `id`, `swift_kod`, `obracunski_racun`
			INTO idBankeDuznika, swiftKodDuznika, obracunskiRacunDuznika
			FROM `banka` AS bnk
			WHERE bnk.`sifra_banke` = SUBSTRING(racun_duznika, 1, 3);

			SELECT `id`, `swift_kod`, `obracunski_racun`
			INTO idBankePoverioca, swiftKodPoverioca, obracunskiRacunPoverioca
			FROM `banka` AS bnk
			WHERE bnk.`sifra_banke` = SUBSTRING(racun_poverioca, 1, 3);

            # UKOLIKO SU KLIJENTI IZ ISTE BANKE (NALOG ZA PRENOS IZMEDJU KLIJENATA ISTE BANKE)
            # ILI UKOLIKO POSTOJI SAMO JEDAN KLIJENT (UPLATNICA ILI ISPLATNICA)
            IF ((idBankeDuznika = idBankePoverioca) OR idBankeDuznika = -1 OR idBankePoverioca = -1) THEN
				SET debug = "USO AKO SU ISTE BANKE ILI JE SAMO JEDAN RACUN";
				BEGIN
					# UKOLIKO JE NAVEDEN DUZNIK
					IF(idBankeDuznika != -1) THEN
						# Gledamo da li za dužnika postoji uopšte dnevno stanje računa
						SELECT `id`
						INTO dsrId
						FROM `dnevno_stanje_racuna` AS dsr
						WHERE dsr.`datum` = DATE(datum_prijema) AND dsr.`dnevni_izvod_banke_id` = racunDuznikaId;

						# Ako ne postoji dnevno stanje računa za traženi dan moramo kreirati novo.
						IF dsrId = -1
						THEN
							BEGIN
								DECLARE novoPrethodnoStanje double DEFAULT 0;

								SELECT `novo_stanje`
								INTO novoPrethodnoStanje
								FROM `dnevno_stanje_racuna` AS dsr
								WHERE dsr.`dnevni_izvod_banke_id` = racunDuznikaId
								ORDER BY dsr.datum DESC
								LIMIT 1; # TREBALO BI DA VRACA SAMO JEDAN RED (SORITIRALI SMO PO DATUMU - OPADAJUCI REDOSLED, ZNACI PRVI JE NAJNOVIJI)

								IF novoPrethodnoStanje IS NULL
								THEN SET novoPrethodnoStanje = 0;
								END IF;

								INSERT INTO `dnevno_stanje_racuna` (`broj_izvoda`,
									`datum`,
									`prethodno_stanje`,
									`promet_u_korist`,
									`promet_na_teret`,
									`novo_stanje`,
									`dnevni_izvod_banke_id`)
								VALUES (0, TIMESTAMP(DATE(datum_prijema)), novoPrethodnoStanje, 0, 0, novoPrethodnoStanje, racunDuznikaId);

								SET dsrId = LAST_INSERT_ID();
							END;
						END IF;

						# Dodamo analitiku izvoda ...
						INSERT INTO `analitika_izvoda`
							( `duznik`, `svrha`, `poverilac`,
							`datum_prijema`, `datum_valute`,
							`racun_duznika`, `model_zaduzenja`, `poziv_na_broj_zaduzenja`,
							`racun_poverioca`, `model_odobrenja`, `poziv_na_broj_odobrenja`,
							`is_hitno`, `iznos`, `tip_greske`, `status`,
							`dnevno_stanje_racuna_id`, `naseljeno_mesto_id`, `vrsta_placanja_id`, `valuta_placanja_id`)
						VALUES
							( duznik, svrha, poverilac,
							datum_prijema, datum_valute,
							racun_duznika, model_zaduzenja, poziv_na_broj_zaduzenja,
							racun_poverioca, model_odobrenja, poziv_na_broj_odobrenja,
							is_hitno, iznos, tip_greske, status_naloga,
							dsrId, naseljenoMestoId, vrstaPlacanjaId, valutaId);

						# ... i azuriramo dnevno stanje povecavsi promet na teret i smanjivsi novo stanje za taj dan duzniku
						BEGIN
							DECLARE prometNaTeret double;
							DECLARE novoStanje double;

							SELECT `promet_na_teret`, `novo_stanje`
							INTO prometNaTeret, novoStanje
							FROM `dnevno_stanje_racuna` as dsr
							WHERE dsr.`datum` = DATE(datum_prijema) AND dsr.`dnevni_izvod_banke_id` = racunDuznikaId;

							IF prometNaTeret IS NULL
							THEN SET prometNaTeret = 0, novoStanje = 0;
							END IF;

							UPDATE `dnevno_stanje_racuna` as dsr
							SET dsr.`promet_na_teret` = prometNaTeret + iznos,
								dsr.`novo_stanje` = novoStanje - iznos
							WHERE dsr.`id` = dsrId;
						END;
					END IF;

					# UKOLIKO JE NAVEDEN POVERIOC
                    IF(idBankePoverioca != -1) THEN
						# Gledamo da li za poverioca postoji uopšte dnevno stanje računa
						SELECT `id`
						INTO dsrId
						FROM `dnevno_stanje_racuna` AS dsr
						WHERE dsr.`datum` = DATE(datum_prijema) AND dsr.`dnevni_izvod_banke_id` = racunPoveriocaId;

						# Ako ne postoji dnevno stanje računa za traženi dan moramo kreirati novo.
						IF dsrId = -1
						THEN
							BEGIN
								DECLARE novoPrethodnoStanje double DEFAULT 0;

								SELECT `novo_stanje`
								INTO novoPrethodnoStanje
								FROM `dnevno_stanje_racuna` AS dsr
								WHERE dsr.`dnevni_izvod_banke_id` = racunPoveriocaId
								ORDER BY dsr.datum DESC
								LIMIT 1; # TREBALO BI DA VRACA SAMO JEDAN RED (SORITIRALI SMO PO DATUMU - OPADAJUCI REDOSLED, ZNACI PRVI JE NAJNOVIJI)

								IF novoPrethodnoStanje IS NULL
								THEN SET novoPrethodnoStanje = 0;
								END IF;

								INSERT INTO `dnevno_stanje_racuna` (`broj_izvoda`,
									`datum`,
									`prethodno_stanje`,
									`promet_u_korist`,
									`promet_na_teret`,
									`novo_stanje`,
									`dnevni_izvod_banke_id`)
								VALUES (0, TIMESTAMP(DATE(datum_prijema)), novoPrethodnoStanje, 0, 0, novoPrethodnoStanje, racunPoveriocaId);

								SET dsrId = LAST_INSERT_ID();
							END;
						END IF;

						# Dodamo analitiku izvoda ...
						INSERT INTO `analitika_izvoda`
							( `duznik`, `svrha`, `poverilac`,
							`datum_prijema`, `datum_valute`,
							`racun_duznika`, `model_zaduzenja`, `poziv_na_broj_zaduzenja`,
							`racun_poverioca`, `model_odobrenja`, `poziv_na_broj_odobrenja`,
							`is_hitno`, `iznos`, `tip_greske`, `status`,
							`dnevno_stanje_racuna_id`, `naseljeno_mesto_id`, `vrsta_placanja_id`, `valuta_placanja_id`)
						VALUES
							( duznik, svrha, poverilac,
							datum_prijema, datum_valute,
							racun_duznika, model_zaduzenja, poziv_na_broj_zaduzenja,
							racun_poverioca, model_odobrenja, poziv_na_broj_odobrenja,
							is_hitno, iznos, tip_greske, status_naloga,
							dsrId, naseljenoMestoId, vrstaPlacanjaId, valutaId);

						# ... i azuriramo dnevno stanje povecavsi promet u korist i novo stanje za taj dan poveriocu
						BEGIN
							DECLARE prometUKorist double;
							DECLARE novoStanje double;

							SELECT `promet_u_korist`, `novo_stanje`
							INTO prometUKorist, novoStanje
							FROM `dnevno_stanje_racuna` as dsr
							WHERE dsr.`datum` = DATE(datum_prijema) AND dsr.`dnevni_izvod_banke_id` = racunPoveriocaId;

							IF prometUKorist IS NULL
							THEN SET prometUKorist = 0, novoStanje = 0;
							END IF;

							UPDATE `dnevno_stanje_racuna` as dsr
							SET dsr.`promet_u_korist` = prometUKorist + iznos,
								dsr.`novo_stanje` = novoStanje + iznos
							WHERE dsr.`id` = dsrId;
						END;
                    END IF;
                END;
			ELSE
				#SET debug = "USO AKO SU RAZLICITE BANKE";
                SET debug = is_hitno;
				BEGIN
					IF (is_hitno OR iznos >=250000) THEN
						SET debug = "USO U RTGS IF";
						BEGIN
							DECLARE analitikaId BIGINT(20) DEFAULT -1;

							# Ako je hitno i dužnik nije iz naše banke šaljemo RTGS nalog centralnoj banci
							INSERT INTO `analitika_izvoda`
								( `duznik`, `svrha`, `poverilac`,
								`datum_prijema`, `datum_valute`,
								`racun_duznika`, `model_zaduzenja`, `poziv_na_broj_zaduzenja`,
								`racun_poverioca`, `model_odobrenja`, `poziv_na_broj_odobrenja`,
								`is_hitno`, `iznos`, `tip_greske`, `status`,
								`dnevno_stanje_racuna_id`, `naseljeno_mesto_id`, `vrsta_placanja_id`, `valuta_placanja_id`)
							VALUES
								( duznik, svrha, poverilac,
								datum_prijema, datum_valute,
								racun_duznika, model_zaduzenja, poziv_na_broj_zaduzenja,
								racun_poverioca, model_odobrenja, poziv_na_broj_odobrenja,
								is_hitno, iznos, tip_greske, status_naloga,
								NULL, naseljenoMestoId, vrstaPlacanjaId, valutaId);

							SET analitikaId = LAST_INSERT_ID();
							SET debug = "USAO U KURAC";

							INSERT INTO `rtgs`
								( `id_poruke`,
								`swift_kod_banke_duznika`, `obracunski_racun_banke_duznika`,
								`swift_kod_banke_poverioca`, `obracunski_racun_banke_poverioca`,
								`broj_stavke_id`)
							VALUES
								( 'STA OVDE TREBA U ID PORUKE???',
								swiftKodDuznika, obracunskiRacunDuznika,
								swiftKodPoverioca, obracunskiRacunPoverioca,
								analitikaId);

							SET rtgs_id = LAST_INSERT_ID();		# Šaljemo na out parametar ID novo unetog RTGS-a radi instant exporta u .xml datoteku
                        END;
					ELSE
						BEGIN
							DECLARE analitikaId BIGINT(20) DEFAULT -1;
							DECLARE kliringId BIGINT(18) DEFAULT -1;
                            DECLARE duznikUPostojecemKliringu VARCHAR(18);
                            DECLARE poverilacUPostojecemKliringu VARCHAR(18);

							INSERT INTO `analitika_izvoda`
								( `duznik`, `svrha`, `poverilac`,
								`datum_prijema`, `datum_valute`,
								`racun_duznika`, `model_zaduzenja`, `poziv_na_broj_zaduzenja`,
								`racun_poverioca`, `model_odobrenja`, `poziv_na_broj_odobrenja`,
								`is_hitno`, `iznos`, `tip_greske`, `status`,
								`dnevno_stanje_racuna_id`, `naseljeno_mesto_id`, `vrsta_placanja_id`, `valuta_placanja_id`)
							VALUES
								( duznik, svrha, poverilac,
								datum_prijema, datum_valute,
								racun_duznika, model_zaduzenja, poziv_na_broj_zaduzenja,
								racun_poverioca, model_odobrenja, poziv_na_broj_odobrenja,
								is_hitno, iznos, tip_greske, status_naloga,
								NULL, naseljenoMestoId, vrstaPlacanjaId, valutaId);

							SET analitikaId = LAST_INSERT_ID();

                            # Tražimo kliring između ove dve banke za baš ovaj dan i koji još nije poslat
							SELECT `id`
							INTO kliringId
							FROM `kliring` AS klr
							WHERE (klr.`obracunski_racun_duznika` = obracunskiRacunDuznika OR klr.`obracunski_racun_duznika` = obracunskiRacunPoverioca)
								AND (klr.`obracunski_racun_poverioca` = obracunskiRacunPoverioca OR klr.`obracunski_racun_poverioca` = obracunskiRacunDuznika)
								AND klr.`datum` = DATE(datum_prijema)
								AND klr.`poslat` = 0;

							IF kliringId != -1
							THEN	# Ako kliring postoji već u bazi dodajemo samo analitiku u stavke kliringa
								BEGIN
									INSERT INTO `stavka_kliringa`
										(`kliring_id`, `analitika_izvoda_id`)
									VALUES
										(kliringId, analitikaId);
								END;
							ELSE 	# Ako kliring još ne postoji pravimo ga i dodajemo analitiku kao njegovu stavke
								BEGIN
									INSERT INTO `kliring`
										(`id_poruke`, `swwift_duznika`, `obracunski_racun_duznika`,
										`swift_poverioca`, `obracunski_racun_poverioca`,
										`ukupan_iznos`, `datum_valute`, `datum` ,`poslat`)
									VALUES
										('STA OVDE TREBA U ID PORUKE???', swiftKodDuznika, obracunskiRacunDuznika,
										swiftKodPoverioca, obracunskiRacunPoverioca,
										0, datum_valute, DATE(datum_prijema), 0);

									SET kliringId = LAST_INSERT_ID();

									INSERT INTO `stavka_kliringa`
										(`kliring_id`, `analitika_izvoda_id`)
									VALUES
										(kliringId, analitikaId);
								END;
							END IF;

							SELECT klr.`obracunski_racun_duznika`, klr.`obracunski_racun_poverioca`
                            INTO duznikUPostojecemKliringu, poverilacUPostojecemKliringu
                            FROM `kliring` as klr
                            WHERE klr.`id` = kliringId;

							# Na kraju ažuriramo ukupan iznos za kliring
                            IF duznikUPostojecemKliringu = obracunskiRacunDuznika THEN
								UPDATE `kliring` as klr
								SET klr.`ukupan_iznos` = klr.`ukupan_iznos` + iznos
								WHERE klr.`id` = kliringId;
							ELSE
								UPDATE `kliring` as klr
								SET klr.`ukupan_iznos` = klr.`ukupan_iznos` - iznos
								WHERE klr.`id` = kliringId;
                            END IF;

                        END;
                    END IF;
                END;
            END IF;
        END;
    END IF;

END;
