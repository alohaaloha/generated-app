DELETE FROM `pinf_pro`.`kliring`
WHERE `kliring`.id > -1;

DELETE FROM `pinf_pro`.`stavka_kliringa`
WHERE `stavka_kliringa`.id > -1;

DELETE FROM `pinf_pro`.`rtgs`
WHERE `rtgs`.id > -1;

DELETE FROM `pinf_pro`.`analitika_izvoda`
WHERE `analitika_izvoda`.id > -1;

DELETE FROM `pinf_pro`.`dnevno_stanje_racuna`
WHERE `dnevno_stanje_racuna`.id > -1;