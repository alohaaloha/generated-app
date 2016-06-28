DELIMITER $$

USE pinf_pro$$

DROP PROCEDURE IF EXISTS `klijentInfo`$$
CREATE PROCEDURE `klijentInfo` ( IN racun VARCHAR(30))
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
		WHERE a.racun_duznika = racun);
        
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
		WHERE a.racun_poverioca = racun);
    
	CREATE TEMPORARY TABLE IF NOT EXISTS Klijent AS SELECT * FROM Duznici UNION SELECT * FROM Poverioci;
    
    SELECT * FROM Klijent;
    
    DROP TEMPORARY TABLE IF EXISTS Duznici, Poverioci,Klijent,Rezultat;
END