/*
Navicat SQLite Data Transfer

Source Server         : householdRoutine
Source Server Version : 30808
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30808
File Encoding         : 65001

Date: 2017-11-30 16:07:56
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
INSERT INTO "main"."informations" VALUES (1, 'information 1 ', 'sfhioashnf ansfasn nasf lnaf as');
INSERT INTO "main"."informations" VALUES (2, 'information 2', 'description dasdadada ad adaahjks jasd bdjknasdnkdasndasnjkad');

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
INSERT INTO "main"."informations_de" VALUES (1, 'information 1', 'duiasdb ldnasdas jddasndasnd');
INSERT INTO "main"."informations_de" VALUES (2, 'information 2', 'dilahs daskbd ajhsdbh asjdbad ajlbdawbdil w dibawbdbawd biawd');

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
INSERT INTO "main"."information_sets" VALUES (1, 1, 'https://www.google.de/', 'test', 1, 0);
INSERT INTO "main"."information_sets" VALUES (2, 2, 'https://www.google.de/', 'test2', 1, 1);

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
INSERT INTO "main"."predefined_checklist" VALUES (1, 'Schwämme');
INSERT INTO "main"."predefined_checklist" VALUES (2, 'Mülltüten');
INSERT INTO "main"."predefined_checklist" VALUES (3, 'Kaffee');
INSERT INTO "main"."predefined_checklist" VALUES (4, 'Küche');
INSERT INTO "main"."predefined_checklist" VALUES (5, 'Bad');
INSERT INTO "main"."predefined_checklist" VALUES (6, 'Schlafzimmer');

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
INSERT INTO "main"."predefined_reminders" VALUES (1, 'Einkauf', 'Benötigte Dinge', 1, 1);
INSERT INTO "main"."predefined_reminders" VALUES (2, 'Müllabfuhr', 'Daran denken', null, 0);
INSERT INTO "main"."predefined_reminders" VALUES (3, 'Haus sauber machen', null, 2, 1);

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

-- ----------------------------
-- Table structure for reminders
-- ----------------------------
DROP TABLE IF EXISTS "main"."reminders";
CREATE TABLE "reminders" (
"_id"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT(255) NOT NULL,
"description"  TEXT(5012),
"startDate"  INTEGER NOT NULL,
"endDate"  INTEGER NOT NULL,
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
INSERT INTO "main"."sqlite_sequence" VALUES ('predefined_reminders', 3);
INSERT INTO "main"."sqlite_sequence" VALUES ('predefined_reminders_de', 0);
INSERT INTO "main"."sqlite_sequence" VALUES ('predefined_checklist', 6);
INSERT INTO "main"."sqlite_sequence" VALUES ('predefined_checklist_de', 0);
INSERT INTO "main"."sqlite_sequence" VALUES ('checklist', 0);
INSERT INTO "main"."sqlite_sequence" VALUES ('user_points', 4);
INSERT INTO "main"."sqlite_sequence" VALUES ('reminders', 0);
INSERT INTO "main"."sqlite_sequence" VALUES ('information_sets', 2);
INSERT INTO "main"."sqlite_sequence" VALUES ('informations', 2);
INSERT INTO "main"."sqlite_sequence" VALUES ('informations_de', 2);

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
INSERT INTO "main"."user_points" VALUES (1, 'test1', null, 10, 12316545613, 'daily-bonus');
INSERT INTO "main"."user_points" VALUES (2, 'test2', null, 10, 12316545613, 'daily-bonus');
INSERT INTO "main"."user_points" VALUES (3, 'test3', null, 10, 12316545613, 'daily-bonus');
INSERT INTO "main"."user_points" VALUES (4, 'test4', null, 30, 4564161616, 'daily-bonus');
