DELIMITER $$

USE pinf_pro$$

DROP PROCEDURE IF EXISTS `izvestajIzvoda`$$
CREATE PROCEDURE `izvestajIzvoda` ( IN racun VARCHAR(30), IN pocetak date, IN kraj date)
BEGIN

	
    
	CREATE TEMPORARY  TABLE IF NOT EXISTS Duznici 
	AS  (
		SELECT k.ime,k.prezime,k.adresa,k.telefon,r.broj_racuna,nm.nm_naziv,dr.dr_naziv FROM pinf_pro.analitika_izvoda a 
		INNER JOIN pinf_pro.racun_pravnog_lica r
		ON  r.broj_racuna = a.racun_duznika 
		INNER JOIN pinf_pro.klijent k 
		ON r.vlasnik_id = k.id
		INNER JOIN pinf_pro.naseljeno_mesto nm
		on k.naseljeno_mesto_id = nm.id
		INNER JOIN pinf_pro.drzava dr
		ON nm.drzava_id = dr.id
		WHERE a.racun_duznika = racun
        AND DATE(a.datum_prijema) BETWEEN pocetak AND KRAJ);
        
	CREATE TEMPORARY  TABLE IF NOT EXISTS Poverioci
	AS  (
		SELECT k.ime,k.prezime,k.adresa,k.telefon,r.broj_racuna,nm.nm_naziv,dr.dr_naziv FROM pinf_pro.analitika_izvoda a 
		INNER JOIN pinf_pro.racun_pravnog_lica r
		ON  r.broj_racuna = a.racun_poverioca 
		INNER JOIN pinf_pro.klijent k 
		ON r.vlasnik_id = k.id
		INNER JOIN pinf_pro.naseljeno_mesto nm
		on k.naseljeno_mesto_id = nm.id
		INNER JOIN pinf_pro.drzava dr
		ON nm.drzava_id = dr.id
		WHERE a.racun_poverioca = racun
        AND DATE(a.datum_prijema) BETWEEN pocetak AND KRAJ);
    
	CREATE TEMPORARY TABLE IF NOT EXISTS Klijent AS SELECT * FROM Duznici UNION SELECT * FROM Poverioci;
        
    
    CREATE TEMPORARY TABLE IF NOT EXISTS Rezultat (datum date, svrha VARCHAR(256), duguje double, potrazuje double);
    
    INSERT INTO Rezultat 
    (datum, svrha,duguje)
    SELECT a.datum_prijema,a.svrha,a.iznos FROM pinf_pro.analitika_izvoda a
    INNER JOIN Klijent k
	ON k.broj_racuna = a.racun_duznika
    WHERE DATE(a.datum_prijema) BETWEEN pocetak AND KRAJ;
    
    INSERT INTO Rezultat 
    (datum, svrha,potrazuje)
    SELECT a.datum_prijema,a.svrha,a.iznos FROM pinf_pro.analitika_izvoda a
    INNER JOIN Klijent k
	ON k.broj_racuna = a.racun_poverioca
    WHERE DATE(a.datum_prijema) BETWEEN pocetak AND KRAJ;
    
    #SELECT * FROM Klijent;
    #SELECT pocetnoStanje, krajnjeStanje;
    SELECT datum, svrha,duguje,potrazuje FROM Rezultat;
    
    
	DROP TEMPORARY TABLE IF EXISTS Duznici, Poverioci,Klijent,Rezultat;
END