INSERT INTO `layout` (`layout_id`, `name`) VALUES (NULL, 'Testlayout'), (NULL, 'Testlayout 2');
INSERT INTO `mandant` (`mandant_id`, `email`, `firmenname`, `hausnummer`, `land`, `lieferkosten`, `mindestbestellwert`, `ort`, `plz`, `seitenaufrufe_summe`, `strasse`, `telefon`, `umsatz_summe`, `layout_id`) VALUES (NULL, 'michael@gmx.at', 'FirmenETC', '12', 'Österreich', '10.0', '5.0', 'Salzburg', '5020', '5', 'Musterstrasse', '065012312312', '10', '1'), (NULL, 'meilinger07@gmail.com', 'FirmaX', '13', 'Österreich', '20', '10', 'Zehnkirchen', '1234', '50000', 'Maxmusterstrasse', '0623523252523', '50000', '2');
INSERT INTO `kunde` (`kunde_id`, `email`, `hausnummer`, `land`, `nachname`, `newsletter`, `ort`, `passwort`, `plz`, `strasse`, `telefonnummer`, `vorname`) VALUES (NULL, 'alimohamed@gmx.at', '12', 'Österreich', 'Mohamed', b'0', 'Mühlbach', '12345678', '6720', 'Landstrasse', '02604968309683', 'Mohamed'), (NULL, 'muhewinger@gmail.com', '14', 'Österreich', 'Muhewinger', b'0', 'Zehnkirchen', '12345678', '1422', 'Lagestrasse', '020984239042342', 'Julian');
INSERT INTO `kategorie` (`kategorie_id`, `name`) VALUES (NULL, 'Türkisch'), (NULL, 'Indisch');
INSERT INTO `bestellart` (`bestellart_id`, `bestellart`) VALUES ('1', 'Abholung'), ('2', 'Lieferrung');
INSERT INTO `angestellter` (`angestellter_id`, `email`, `nachname`, `passwort`, `vorname`, `mandant_id`) VALUES ('1', 'niklasheim@gmx.at', 'Heim', '12345678', 'Niklas', '1'), ('2', 'burak@gmx.at', 'Eraslan', '12345678', 'Burak', '2');
INSERT INTO `bestellstatus` (`bestellstatus_id`, `bestellstatus`) VALUES ('1', 'Fertig'), ('2', 'In Bearbeitung');
INSERT INTO `bestellung` (`bestellung_id`, `bestelldatum`, `dauer`, `gesamtpreis`, `trinkgeld`, `bestellart_id`, `kunde_id`, `mandant_id`) VALUES ('1', '2022-02-09 14:02:05', '50', '50', '0', '1', '2', '2'), ('2', '2022-02-03 14:02:05', '60', '30', '10', '2', '1', '1');
INSERT INTO `bestellung_bestellstatus` (`bestellung_id`, `bestellstatus_id`) VALUES ('1', '1'), ('2', '2');
INSERT INTO `gericht` (`gericht_id`, `anzahl_gekauft`, `beschreibung`, `name`, `preis`, `preisangebot`, `status`, `mandant_id`) VALUES (NULL, '15', 'Wässrig', 'Wasser', '5.0', '0', '1', '1'), (NULL, '100', 'Fleischig', 'Fleischkas', '10', '0', '0', '2'), (NULL, '1502', 'Ein super leckerer Döner mit spezial Soße', 'Döner Kebab', '5.0', '1.0', '2', '2');
INSERT INTO `gericht_bestellung` (`gericht_betsellung_id`, `anmerkung`, `bestellung_id`, `gericht_id`) VALUES ('1', 'Scharf', '1', '1'), ('2', 'Kalt', '2', '2');
INSERT INTO `rolle` (`rolle_id`, `rolle`) VALUES ('1', 'Koch'), ('2', 'Kellner');
INSERT INTO `angestellter_rolle` (`angestellter_id`, `rolle_id`) VALUES ('2', '1'), ('1', '2');
INSERT INTO `mandant_bestellart` (`mandant_id`, `bestellart_id`) VALUES ('1', '1'), ('1', '2');
INSERT INTO `mandant_kategorie` (`mandant_id`, `kategorie_id`) VALUES ('2', '2'), ('2', '1');
INSERT INTO `oeffnungszeit` (`oeffnungszeit_id`, `endepause`, `oeffnungszeit`, `schliessungszeit`, `startpause`, `tag`, `mandant_id`) VALUES ('1', '14:11:02', '10:11:02', '18:11:02', '13:30:02', '1', '2'), ('2', '14:11:02', '06:11:02', '23:11:02', '13:11:02', '2', '1');
INSERT INTO `umsatz` (`umsatz_id`, `jahr`, `monat`, `umsatz`, `mandant_id`) VALUES ('1', '2021', '1', '5.00', '1'), ('2', '2021', '1', '50000.00', '2');
INSERT INTO `seitenaufrufe` (`seitenaufrufe_id`, `aufrufe`, `jahr`, `monat`, `mandant_id`) VALUES ('1', '1500', '2021', '4', '2'), ('2', '3', '2021', '4', '1');