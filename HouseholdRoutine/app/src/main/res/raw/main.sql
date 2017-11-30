DROP TABLE IF EXISTS "checklist";
CREATE TABLE "checklist" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"item_name"  TEXT NOT NULL,
"reminder_id"  INTEGER,
"completed"  INTEGER
);

DROP TABLE IF EXISTS "informations";
CREATE TABLE "informations"(
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT NOT NULL,
"description"  TEXT NOT NULL
);

DROP TABLE IF EXISTS "informations_de";
CREATE TABLE "informations_de" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT NOT NULL,
"description"  TEXT NOT NULL
);

DROP TABLE IF EXISTS "information_sets";
CREATE TABLE "information_sets" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"information_id"  INTEGER NOT NULL,
"url"  TEXT NOT NULL,
"source"  TEXT NOT NULL,
"obtained"  INTEGER NOT NULL,
"obtainable"  INTEGER NOT NULL
);

DROP TABLE IF EXISTS "predefined_checklist";
CREATE TABLE "predefined_checklist" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"item_names"  TEXT
);

DROP TABLE IF EXISTS "predefined_checklist_de";
CREATE TABLE "predefined_checklist_de" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"item_names"  TEXT
);

DROP TABLE IF EXISTS "reminders";
CREATE TABLE "reminders" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT(255) NOT NULL,
"description"  TEXT(5012),
"startDate"  INTEGER NOT NULL,
"endDate"  INTEGER NOT NULL,
"type"  INTEGER NOT NULL
);

DROP TABLE IF EXISTS "predefined_reminders";
CREATE TABLE "predefined_reminders" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT(255),
"description"  TEXT(5012),
"checklist"  INTEGER,
"type"  INTEGER
);

DROP TABLE IF EXISTS "predefined_reminders_de";
CREATE TABLE "predefined_reminders_de" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT(255),
"description"  TEXT(5012),
"checklist"  INTEGER,
"type"  INTEGER
);

DROP TABLE IF EXISTS "user_points";
CREATE TABLE "user_points" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"title"  TEXT,
"description"  TEXT,
"points"  INTEGER,
"date"  INTEGER,
"type"  TEXT NOT NULL
);


INSERT INTO information_sets VALUES (1, 1, 'https://www.google.de/', 'test', 1, 0);
INSERT INTO information_sets VALUES (2, 2, 'https://www.google.de/', 'test2', 1, 1);
INSERT INTO information_sets VALUES (3, 3, 'https://www.google.de/', 'test', 1, 0);
INSERT INTO information_sets VALUES (4, 4, 'https://www.google.de/', 'test2', 1, 1);
INSERT INTO information_sets VALUES (5, 5, 'https://www.google.de/', 'test', 1, 0);
INSERT INTO information_sets VALUES (6, 6, 'https://www.google.de/', 'test2', 1, 1);
INSERT INTO information_sets VALUES (7, 7, 'https://www.google.de/', 'test', 1, 0);
INSERT INTO information_sets VALUES (8, 8, 'https://www.google.de/', 'test2', 1, 1);
INSERT INTO information_sets VALUES (9, 9, 'https://www.google.de/', 'test', 1, 0);
INSERT INTO information_sets VALUES (10, 10, 'https://www.google.de/', 'test2', 1, 1);
INSERT INTO information_sets VALUES (11, 11, 'https://www.google.de/', 'test', 1, 0);
INSERT INTO information_sets VALUES (12, 12, 'https://www.google.de/', 'test2', 1, 1);
INSERT INTO information_sets VALUES (13, 13, 'https://www.google.de/', 'test', 1, 0);
INSERT INTO information_sets VALUES (14, 14, 'https://www.google.de/', 'test2', 1, 1);
INSERT INTO information_sets VALUES (15, 15, 'https://www.google.de/', 'test', 1, 0);
INSERT INTO information_sets VALUES (16, 16, 'https://www.google.de/', 'test2', 1, 1);
INSERT INTO information_sets VALUES (17, 17, 'https://www.google.de/', 'test', 0, 0);
INSERT INTO information_sets VALUES (18, 18, 'https://www.google.de/', 'test2', 0, 1);
INSERT INTO information_sets VALUES (19, 19, 'https://www.google.de/', 'test', 0, 0);
INSERT INTO information_sets VALUES (20, 20, 'https://www.google.de/', 'test2', 0, 1);

INSERT INTO informations_de VALUES (1, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations_de VALUES (2, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations_de VALUES (3, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations_de VALUES (4, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations_de VALUES (5, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations_de VALUES (6, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations_de VALUES (7, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations_de VALUES (8, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations_de VALUES (9, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations_de VALUES (10, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations_de VALUES (11, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations_de VALUES (12, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations_de VALUES (13, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations_de VALUES (14, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations_de VALUES (15, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations_de VALUES (16, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations_de VALUES (17, 'extra 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations_de VALUES (18, 'extra 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations_de VALUES (19, 'extra 3', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations_de VALUES (20, 'extra 4', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations VALUES (1, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations VALUES (2, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations VALUES (3, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations VALUES (4, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations VALUES (5, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations VALUES (6, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations VALUES (7, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations VALUES (8, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations VALUES (9, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations VALUES (10, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations VALUES (11, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations VALUES (12, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations VALUES (13, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations VALUES (14, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations VALUES (15, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations VALUES (16, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations VALUES (17, 'extra 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations VALUES (18, 'extra 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');
INSERT INTO informations VALUES (19, 'extra 3', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO informations VALUES (20, 'extra 4', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');