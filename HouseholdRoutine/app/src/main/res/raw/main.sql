DROP TABLE IF EXISTS "checklist";
CREATE TABLE "checklist" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"item_name"  TEXT(64) NOT NULL,
"quantity"  INTEGER NOT NULL,
"reminder_id"  INTEGER NOT NULL,
"completed"  INTEGER NOT NULL
);

DROP TABLE IF EXISTS "predefined_checklist";
CREATE TABLE "predefined_checklist" (
"_id"  INTEGER,
"item_name"  TEXT(64),
"quantity"  INTEGER,
"predefined_reminder_id"  INTEGER
);

DROP TABLE IF EXISTS "predefined_checklist_de";
CREATE TABLE "predefined_checklist_de" (
"_id"  INTEGER,
"item_name"  TEXT(64),
"quantity"  INTEGER,
"predefined_reminder_id"  INTEGER
);

DROP TABLE IF EXISTS "reminders";
CREATE TABLE "reminders" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT(255) NOT NULL,
"description"  TEXT(5012),
"checklist_id"  INTEGER NOT NULL,
"startDate"  INTEGER NOT NULL,
"endDate"  INTEGER NOT NULL,
"outdated"  INTEGER NOT NULL,
"type"  INTEGER NOT NULL
);

DROP TABLE IF EXISTS "predefined_reminders";
CREATE TABLE "predefined_reminders" (
"_id"  INTEGER,
"name"  TEXT(255),
"description"  TEXT(5012),
"checklist"  INTEGER,
"type"  INTEGER
);

DROP TABLE IF EXISTS "predefined_reminders_de";
CREATE TABLE "predefined_reminders_de" (
"_id"  INTEGER,
"name"  TEXT(255),
"description"  TEXT(5012),
"checklist"  INTEGER,
"type"  INTEGER
);