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