INSERT INTO `template` (`template_id`, `template`) VALUES ('1', 'Test'), ('2', 'Test 2');
INSERT INTO `mandant` (`mandant_id`, `email`, `firmenname`, `hausnummer`, `land`, `lieferkosten`, `mindestbestellwert`, `ort`, `plz`, `strasse`, `telefon`, `template_id`) VALUES (NULL, 'michael@gmx.at', 'FirmenETC', '12', 'Österreich', '10.0', '5.0', 'Salzburg', '5020', 'Musterstrasse', '065012312312', '1'), (NULL, 'meilinger07@gmail.com', 'FirmaX', '13', 'Österreich', '20.0', '10.0', 'Zehnkirchen', '1234', 'Maxmusterstrasse', '0623523252523', '2');
INSERT INTO `kunde` (`kunde_id`, `benutzername`, `email`, `hausnummer`, `land`, `nachname`, `newsletter`, `ort`, `passwort`, `plz`, `strasse`, `telefonnummer`, `vorname`) VALUES (NULL, 'alimohamed', 'alimohamed@gmx.at', '12', 'Österreich', 'Mohamed', b'0', 'Mühlbach', '12345678', '6720', 'Landstrasse', '02604968309683', 'Mohamed'), (NULL, 'muhewinger', 'muhewinger@gmail.com', '14', 'Österreich', 'Muhewinger', b'0', 'Zehnkirchen', '12345678', '1422', 'Lagestrasse', '020984239042342', 'Julian');
INSERT INTO `kategorie` (`kategorie_id`, `name`) VALUES (NULL, 'Türkisch'), (NULL, 'Indisch');
INSERT INTO `bestellart` (`bestellart_id`, `bestellart`) VALUES ('1', 'Abholung'), ('2', 'Lieferrung');
INSERT INTO `angestellter` (`angestellter_id`, `benutzername`, `nachname`, `passwort`, `vorname`, `mandant_id`) VALUES ('1', 'niklasheim', 'Heim', '12345678', 'Niklas', '3'), ('2', 'buu01', 'Eraslan', '12345678', 'Burak', '4');
INSERT INTO `bestellstatus` (`bestellstatus_id`, `bestellstatus`) VALUES ('1', 'Fertig'), ('2', 'In Bearbeitung');
INSERT INTO `bestellung` (`bestellung_id`, `bestelldatum`, `dauer`, `gesamtpreis`, `trinkgeld`, `bestellart_id`, `kunde_id`, `mandant_id`) VALUES ('1', '2022-02-09 14:02:05', '50', '50', '0', '1', '2', '4'), ('2', '2022-02-03 14:02:05', '60', '30', '10', '2', '1', '3');
INSERT INTO `bestellung_bestellstatus` (`bestellung_id`, `bestellstatus_id`) VALUES ('1', '1'), ('2', '2');
INSERT INTO `gericht` (`gericht_id`, `beschreibung`, `name`, `preis`, `preisangebot`, `mandant_id`) VALUES ('1', 'Fleischig', 'Fleischkas', '10', '0', '4'), ('2', 'Wässrig', 'Wasser', '20', '0', '3');
INSERT INTO `gerichtstatus` (`gerichtstatus_id`, `status`) VALUES ('1', 'Fertig'), ('2', 'In Bearbeitung');
INSERT INTO `gericht_bestellung` (`gericht_betsellung_id`, `anmerkung`, `bestellung_id`, `gericht_id`) VALUES ('1', 'Scharf', '1', '1'), ('2', 'Kalt', '2', '2');
INSERT INTO `gericht_gerichtstatus` (`gericht_id`, `gerichtstatus_id`) VALUES ('1', '1'), ('2', '2');
INSERT INTO `rolle` (`rolle_id`, `rolle`) VALUES ('1', 'Koch'), ('2', 'Kellner');
INSERT INTO `angestellter_rolle` (`angestellter_id`, `rolle_id`) VALUES ('2', '1'), ('1', '2');
INSERT INTO `mandant_bestellart` (`mandant_id`, `bestellart_id`) VALUES ('4', '1'), ('3', '2');
INSERT INTO `mandant_kategorie` (`mandant_id`, `kategorie_id`) VALUES ('4', '2'), ('4', '1');
INSERT INTO `oeffnungszeit` (`oeffnungszeit_id`, `endepause`, `oeffnungszeit`, `schliessungszeit`, `startpause`, `tag`, `mandant_id`) VALUES ('1', '14:11:02', '10:11:02', '18:11:02', '13:30:02', '1', '4'), ('2', '14:11:02', '06:11:02', '23:11:02', '13:11:02', '2', '3');