DELIMITER $$

USE pinf_pro$$

DROP PROCEDURE IF EXISTS `racuniBanka`$$
CREATE PROCEDURE `racuniBanka` ( IN sifraBanke VARCHAR(3))
BEGIN
	

	CREATE TEMPORARY TABLE IF NOT EXISTS Racuni AS
	SELECT k.ime,k.prezime,r.broj_racuna, d.novo_stanje FROM pinf_pro.racun_pravnog_lica r
    INNER JOIN pinf_pro.banka b
    ON r.banka_id = b.id
    INNER JOIN pinf_pro.klijent k
    ON r.vlasnik_id = k.id
    INNER JOIN pinf_pro.dnevno_stanje_racuna d
    on d.dnevni_izvod_banke_id = r.id
    WHERE b.sifra_banke = sifraBanke
    AND (SELECT max(datum) FROM pinf_pro.dnevno_stanje_racuna) = d.datum;
    
    SELECT * FROM RACUNI;
    
    DROP TEMPORARY TABLE IF EXISTS Racuni;

END