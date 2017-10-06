DROP TABLE IF EXISTS "checklist";
CREATE TABLE "checklist" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"item_name"  TEXT(64) NOT NULL,
"predefined_item_id"  INTEGER NOT NULL,
"quantity"  INTEGER NOT NULL,
"completed"  INTEGER NOT NULL
);
DROP TABLE IF EXISTS "reminders";
CREATE TABLE "reminders" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT(255) NOT NULL,
"description"  TEXT(5012),
"checklist_id"  INTEGER NOT NULL,
"startDate"  INTEGER NOT NULL,
"endDate"  INTEGER NOT NULL,
"outdated"  INTEGER NOT NULL
);