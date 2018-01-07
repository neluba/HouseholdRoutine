/*
Navicat SQLite Data Transfer

Source Server         : householdRoutine
Source Server Version : 30808
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30808
File Encoding         : 65001

Date: 2018-01-07 17:19:04
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for checklist
-- ----------------------------
DROP TABLE IF EXISTS "main"."checklist";
CREATE TABLE "checklist" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"item_name"  TEXT NOT NULL,
"reminder_id"  INTEGER,
"completed"  INTEGER
);

-- ----------------------------
-- Records of checklist
-- ----------------------------

-- ----------------------------
-- Table structure for informations
-- ----------------------------
DROP TABLE IF EXISTS "main"."informations";
CREATE TABLE "informations" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT NOT NULL,
"description"  TEXT NOT NULL
);

-- ----------------------------
-- Records of informations
-- ----------------------------
INSERT INTO "main"."informations" VALUES (1, 'German recycling rate', 'Germany has the highest recycling rate with 64%.');
INSERT INTO "main"."informations" VALUES (2, 'Waste hierachy', 'Europe has a fixed order how waste should be handled. \n1. Prevent \n2. Prepare for reuse \n3. Recycling \n4. Other utilization \n5. Dispose');
INSERT INTO "main"."informations" VALUES (3, 'Own waste generation', 'Unlike water or electricity where we pay for what we use, do we pay a fixed price for the garbage collection. That has the consequence that many people are oblivious to how much waste they actually generate. ');
INSERT INTO "main"."informations" VALUES (4, 'Plastic waste in rivers', 'The ten largest rivers transport more than 90% of the plasic waste into the oceans. Eight out ten rivers are from Asia and on rank one is the Yangtze.');
INSERT INTO "main"."informations" VALUES (5, 'Consequences of plastic waste in oceans for animals', 'Over ten million tons of waste gets disposed into the oceans every year. And 75% of this is plasic waste. This high potion of plastic waste costs every year up to 100.000 sea mammals and one million sea birds their lives.');
INSERT INTO "main"."informations" VALUES (6, 'Illegal landfills for electric waste', 'A huge amount of electric waste gets exported illegally to African landfills every year from all over the world. Those landfills are extremely harmful to the environment and contain toxic elements such as lead or mercury.');
INSERT INTO "main"."informations" VALUES (7, 'Annual waste generation of a German', 'A German citizen generates every year on average 618 kilograms of waste. ');
INSERT INTO "main"."informations" VALUES (8, 'It''s important to seperate the waste', 'Waste that wasn''t seperated correctly will eventually get sorted out, but it won''t get recycelt that way.');

-- ----------------------------
-- Table structure for informations_de
-- ----------------------------
DROP TABLE IF EXISTS "main"."informations_de";
CREATE TABLE "informations_de" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT NOT NULL,
"description"  TEXT NOT NULL
);

-- ----------------------------
-- Records of informations_de
-- ----------------------------
INSERT INTO "main"."informations_de" VALUES (1, 'Deutsche Recyclingquote', 'Deutschland hat die höchste Recyclingsquote in Europa mit 64%.');
INSERT INTO "main"."informations_de" VALUES (2, 'Abfallhierachie', 'In der EU ist eine Reihenfolge festgelegt, wie mit dem Abfall umgegangen werden muss. \n1. Vermeidung \n2. Vorbereitung zur Wiederverwendung \n3. Recycling \n4. Sonstige Verwertung \n5. Beseitigung');
INSERT INTO "main"."informations_de" VALUES (3, 'Eigene Müllgenerierung', 'Anders als bei Strom und Wasser, bei dem man so viel bezahlt wie man verbraucht, bezahlt man für die Müllabfuhr einen festen Preis. Deswegen bekommen die meisten Menschen gar nicht mit, wie viel Müll sie generieren.');
INSERT INTO "main"."informations_de" VALUES (4, 'Plastikmüll in den Flüssen', 'Die Zehn größten Flüsse auf der Welt befördern über 90% des Plastikmülls in die Weltmeere. Davon sind acht aus Asien und auf dem ersten Platz liegt der Jangtse.');
INSERT INTO "main"."informations_de" VALUES (5, 'Folgen des Plastikmülls in den Meeren für Tiere', 'Jährlich werden mehr als Zehn Millionen Tonnen Abfall in die Ozeane geschwemmt und circa 75% davon sind Kunstoff. Dieser hohe Plastikanteil kostet damit jahrlich bis zu 100.000 Meeressäugern und eine Millionen Meeresvögeln das leben.');
INSERT INTO "main"."informations_de" VALUES (6, 'Illegale Deponien für Elektroschrott', 'Ein großer Teil des Elektroschrotts wird von der ganzen Welt aus illegal auf Deponien nach Afrika exportiert. Diese Deponien sind extrem Umweltschädigend und enthalten giftige Materialien wie Blei oder Quecksilber.');
INSERT INTO "main"."informations_de" VALUES (7, 'Jährliches Müllaufkommen des Deutschen', 'Jeder Deutsche verursacht pro Jahr im Durchschnitt circa 618 Kilogramm Müll.');
INSERT INTO "main"."informations_de" VALUES (8, 'Es ist wichtig den Müll richtig zu trennen', 'In den Großstädten gibt es bis zu 50% Fehlwürfe in der Gelben Tonne. Diese werden dann zwar aussortiert, können aber nicht mehr recycelt werden.');

-- ----------------------------
-- Table structure for information_sets
-- ----------------------------
DROP TABLE IF EXISTS "main"."information_sets";
CREATE TABLE "information_sets" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"information_id"  INTEGER NOT NULL,
"url"  TEXT NOT NULL,
"source"  TEXT NOT NULL,
"obtained"  INTEGER NOT NULL,
"obtainable"  INTEGER NOT NULL
);

-- ----------------------------
-- Records of information_sets
-- ----------------------------
INSERT INTO "main"."information_sets" VALUES (1, 1, 'https://www.focus.de/finanzen/experten/iwkoeln/abfall-nur-ein-land-in-der-eu-produziert-noch-mehr-muell-als-deutschland_id_6239128.html', 'Focus', 0, 1);
INSERT INTO "main"."information_sets" VALUES (2, 2, 'https://www.alba.info/unternehmen/service/glossar/abfallhierarchie.html', 'Alba', 0, 1);
INSERT INTO "main"."information_sets" VALUES (3, 3, 'http://www.latimes.com/world/global-development/la-fg-global-trash-20160422-20160421-snap-htmlstory.html', 'Los Angeles Times', 0, 1);
INSERT INTO "main"."information_sets" VALUES (4, 4, 'https://www.wissenschaftsjahr.de/2016-17/aktuelles/alle-aktuellen-meldungen/oktober-2017/globale-studie-bestimmt-mengen-an-plastikfracht-in-flusssystemen.html', 'Bundesministerium für Bildung und Forschung', 0, 1);
INSERT INTO "main"."information_sets" VALUES (5, 5, 'https://www.nabu.de/natur-und-landschaft/meere/muellkippe-meer/muellkippemeer.html', 'NABU', 0, 1);
INSERT INTO "main"."information_sets" VALUES (6, 6, 'http://www.dailymail.co.uk/news/article-3049457/Where-computer-goes-die-Shocking-pictures-toxic-electronic-graveyards-Africa-West-dumps-old-PCs-laptops-microwaves-fridges-phones.html', 'MailOnline', 0, 1);
INSERT INTO "main"."information_sets" VALUES (7, 7, 'https://www.welt.de/newsticker/dpa_nt/infoline_nt/brennpunkte_nt/article146138404/Deutschland-auf-Platz-sechs.html', 'Welt', 0, 1);
INSERT INTO "main"."information_sets" VALUES (8, 8, 'http://www.sueddeutsche.de/wissen/muell-kreislauf-das-deutsche-recycling-maerchen-1.3491734', 'Süddeutsche Zeitung', 0, 1);

-- ----------------------------
-- Table structure for predefined_checklist
-- ----------------------------
DROP TABLE IF EXISTS "main"."predefined_checklist";
CREATE TABLE "predefined_checklist" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"item_names"  TEXT
);

-- ----------------------------
-- Records of predefined_checklist
-- ----------------------------
INSERT INTO "main"."predefined_checklist" VALUES (1, '[ "Household waste", "Paper", "Glass", "Batteries" ]');
INSERT INTO "main"."predefined_checklist" VALUES (2, '[ "Kitchen", "Living room", "Bathroom", "Bedroom" ]');
INSERT INTO "main"."predefined_checklist" VALUES (3, '[ "Window cleaner", "Garbage bags", "Sponges", "Dish soap", "Dust bags", "Laundry detergent" ]');

-- ----------------------------
-- Table structure for predefined_checklist_de
-- ----------------------------
DROP TABLE IF EXISTS "main"."predefined_checklist_de";
CREATE TABLE "predefined_checklist_de" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"item_names"  TEXT
);

-- ----------------------------
-- Records of predefined_checklist_de
-- ----------------------------
INSERT INTO "main"."predefined_checklist_de" VALUES (1, '[ "Hausmüll", "Papier", "Glas", "Batterien" ]');
INSERT INTO "main"."predefined_checklist_de" VALUES (2, '[ "Küche", "Wohnzimmer", "Badezimmer", "Schlafzimmer" ]');
INSERT INTO "main"."predefined_checklist_de" VALUES (3, '[ "Fenster reiniger", "Müllbeutel", "Putzschwämme", "Spülmittel", "Staubsaugerbeutel", "Waschmittel" ]');

-- ----------------------------
-- Table structure for predefined_reminders
-- ----------------------------
DROP TABLE IF EXISTS "main"."predefined_reminders";
CREATE TABLE "predefined_reminders" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT(255),
"description"  TEXT(5012),
"checklist"  INTEGER,
"type"  INTEGER
);

-- ----------------------------
-- Records of predefined_reminders
-- ----------------------------
INSERT INTO "main"."predefined_reminders" VALUES (1, 'Take out the trash', '', 0, 0);
INSERT INTO "main"."predefined_reminders" VALUES (2, 'Garbage collection', 'Set up the trash for the garbage collection. ', 0, 0);
INSERT INTO "main"."predefined_reminders" VALUES (3, 'Throw away the paper waste', null, 0, 0);
INSERT INTO "main"."predefined_reminders" VALUES (4, 'Dispose old batteries', 'Remember to dispose them in special containers', 0, 0);
INSERT INTO "main"."predefined_reminders" VALUES (5, 'Dispose old glass bottles', null, 0, 0);
INSERT INTO "main"."predefined_reminders" VALUES (6, 'Clean the barthroom', '', 0, 0);
INSERT INTO "main"."predefined_reminders" VALUES (7, 'Wash the dishes', null, 0, 0);
INSERT INTO "main"."predefined_reminders" VALUES (8, 'Vacuum the apartment', null, 0, 0);
INSERT INTO "main"."predefined_reminders" VALUES (9, 'Clean the cat litter box', null, 0, 0);
INSERT INTO "main"."predefined_reminders" VALUES (10, 'Take medicine', null, 0, 0);
INSERT INTO "main"."predefined_reminders" VALUES (11, 'Dispose of garbage', null, 1, 1);
INSERT INTO "main"."predefined_reminders" VALUES (12, 'Clean the apartment', null, 2, 1);
INSERT INTO "main"."predefined_reminders" VALUES (13, 'Buy cleaning accessories', 'Things to buy, to keep the household clean', 3, 1);

-- ----------------------------
-- Table structure for predefined_reminders_de
-- ----------------------------
DROP TABLE IF EXISTS "main"."predefined_reminders_de";
CREATE TABLE "predefined_reminders_de" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT(255),
"description"  TEXT(5012),
"checklist"  INTEGER,
"type"  INTEGER
);

-- ----------------------------
-- Records of predefined_reminders_de
-- ----------------------------
INSERT INTO "main"."predefined_reminders_de" VALUES (1, 'Müll raus bringen', null, 0, 0);
INSERT INTO "main"."predefined_reminders_de" VALUES (2, 'Müllabfuhr', 'Den Müll für die Müllabfahr nach draußen stellen', 0, 0);
INSERT INTO "main"."predefined_reminders_de" VALUES (3, 'Altpapier entsorgen', null, 0, 0);
INSERT INTO "main"."predefined_reminders_de" VALUES (4, 'Alte Batterien entsorgen', 'Daran denken, die alten Batterien in bestimmten Sammelbehältern zu entsorgen', 0, 0);
INSERT INTO "main"."predefined_reminders_de" VALUES (5, 'Altglas entsorgen', null, 0, 0);
INSERT INTO "main"."predefined_reminders_de" VALUES (6, 'Das Badezimmer sauber machen', null, 0, 0);
INSERT INTO "main"."predefined_reminders_de" VALUES (7, 'Abwasch machen', null, 0, 0);
INSERT INTO "main"."predefined_reminders_de" VALUES (8, 'Die Wohnung saugen', null, 0, 0);
INSERT INTO "main"."predefined_reminders_de" VALUES (9, 'Das Katzenklo reinigen', null, 0, 0);
INSERT INTO "main"."predefined_reminders_de" VALUES (10, 'Medikamente nehmen', null, 0, 0);
INSERT INTO "main"."predefined_reminders_de" VALUES (11, 'Müll entsorgen', null, 1, 1);
INSERT INTO "main"."predefined_reminders_de" VALUES (12, 'Wohnung putzen', null, 2, 1);
INSERT INTO "main"."predefined_reminders_de" VALUES (13, 'Putzutensilien besorgen', 'Dinge kaufen, um die Wohnung sauber zu halten', 3, 1);

-- ----------------------------
-- Table structure for reminders
-- ----------------------------
DROP TABLE IF EXISTS "main"."reminders";
CREATE TABLE "reminders" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT(255) NOT NULL,
"description"  TEXT(5012),
"start_date"  INTEGER NOT NULL,
"end_date"  INTEGER NOT NULL,
"type"  INTEGER NOT NULL
);

-- ----------------------------
-- Records of reminders
-- ----------------------------

-- ----------------------------
-- Table structure for sqlite_sequence
-- ----------------------------
DROP TABLE IF EXISTS "main"."sqlite_sequence";
CREATE TABLE sqlite_sequence(name,seq);

-- ----------------------------
-- Records of sqlite_sequence
-- ----------------------------
INSERT INTO "main"."sqlite_sequence" VALUES ('predefined_reminders', 13);
INSERT INTO "main"."sqlite_sequence" VALUES ('predefined_reminders_de', 13);
INSERT INTO "main"."sqlite_sequence" VALUES ('predefined_checklist', 6);
INSERT INTO "main"."sqlite_sequence" VALUES ('predefined_checklist_de', 3);
INSERT INTO "main"."sqlite_sequence" VALUES ('checklist', 0);
INSERT INTO "main"."sqlite_sequence" VALUES ('user_points', 4);
INSERT INTO "main"."sqlite_sequence" VALUES ('information_sets', 8);
INSERT INTO "main"."sqlite_sequence" VALUES ('informations', 8);
INSERT INTO "main"."sqlite_sequence" VALUES ('informations_de', 8);
INSERT INTO "main"."sqlite_sequence" VALUES ('reminders', 2);

-- ----------------------------
-- Table structure for user_points
-- ----------------------------
DROP TABLE IF EXISTS "main"."user_points";
CREATE TABLE "user_points" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"title"  TEXT,
"description"  TEXT,
"points"  INTEGER,
"date"  INTEGER,
"type"  TEXT NOT NULL
);

-- ----------------------------
-- Records of user_points
-- ----------------------------
