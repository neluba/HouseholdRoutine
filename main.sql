/*
Navicat SQLite Data Transfer

Source Server         : householdRoutine
Source Server Version : 30808
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30808
File Encoding         : 65001

Date: 2017-11-23 13:24:51
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
"outdated"  INTEGER NOT NULL,
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
INSERT INTO "main"."sqlite_sequence" VALUES ('reminders', 0);
INSERT INTO "main"."sqlite_sequence" VALUES ('checklist', 0);
