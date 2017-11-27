package com.example.android.householdroutine.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by oliver on 06.10.2017.
 */

public class DbContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.householdroutine";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public  static final String PATH_REMINDERS = "reminders";
    public static final String PATH_CHECKLIST = "checklist";
    public static final String PATH_SINGLE_CHECKLIST = "single_checklist";
    public static final String PATH_PREDEFINED_REMINDERS = "predefined_reminders";
    public static final String PATH_PREDEFINED_CHECKLIST = "predefined_checklist";
    public static final String PATH_FULL_PREDEFINED_CHECKLIST = "full_predefined_checklist";


    public static final class RemindersEntry implements BaseColumns {
        /** Base content URI for the reminders table  **/
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_REMINDERS)
                .build();

        public static final String TABLE_NAME = "reminders";

        // reminder name -- string
        public static final String COLUMN_NAME = "name";
        // reminder description -- string
        public static final String COLUMN_DESCRIPTION = "description";
        // id of the checklist. -1 if it doesn't have a checklist. It needs to be set, when type is 1
        // start time in millis -- integer
        public static final String COLUMN_START_DATE = "startDate";
        // end time in millis -- integer
        public static final String COLUMN_END_DATE = "endDate";
        // 0 if the reminder is currently activ, otherwise 1 -- integer
        public static final String COLUMN_OUTDATED = "outdated";
        // type = 0 for reminder and type = 1 for checklist -- integer
        public static final String COLUMN_TYPE = "type";

        public static final int TYPE_REMINDER = 0;
        public static final int TYPE_CHECKLIST = 1;
        public static final int OUTDATED_FALSE = 0;
        public static final int OUTDATED_TRUE = 1;

        /**
         * Returns the content uri for a specific row using the id of a reminder
         * @param id reminder id
         */
        public static Uri buildRemindersUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }
    }



    public static final class ChecklistEntry implements BaseColumns {
        // Base content URI for the checklist table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CHECKLIST)
                .build();

        public static final String TABLE_NAME = "checklist";
        // item names are saved as an json array
        public static final String COLUMN_ITEM_NAME = "item_name";
        public static final String COLUMN_REMINDER_ID = "reminder_id";
        public static final String COLUMN_COMPLETED = "completed";

        public static final int COMPLETED_TRUE = 1;
        public static final int COMPLETED_FALSE = 0;

        /**
         * Returns the content uri for a specific row using the id of a checklist
         * @param id checklist id
         */
        public static Uri buildChecklistUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }

        /**
         * Returns the content uri for a single checklist using the reminder id
         * @param id reminder id
         */
        public static Uri buildChecklistUriWithReminderId(long id) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_SINGLE_CHECKLIST)
                    .appendPath(Long.toString(id))
                    .build();
        }
    }



    public static final class PredefinedRemindersEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PREDEFINED_REMINDERS)
                .build();

        public static final String TABLE_NAME = "predefined_reminders";
        public static final String TABLE_NAME_DE = "predefined_reminders_de";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_CHECKLIST = "checklist";
        public static final String COLUMN_TYPE = "type";

        /**
         * Returns the content uri for a specific row using the id of a reminder
         * @param id reminder id
         */
        public static Uri buildUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }
    }

    public static final class PredefinedChecklistEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PREDEFINED_CHECKLIST)
                .build();

        // Inner joins the predefined reminders table with the predefined checklist table
        // Returns _id, name, description and item_names from the database
        public static final Uri FULL_PREDEFINED_CHECKLIST_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FULL_PREDEFINED_CHECKLIST)
                .build();

        public static final String TABLE_NAME = "predefined_checklist";
        public static final String TABLE_NAME_DE = "predefined_checklist_de";
        // item names are saved as an json array
        public static final String COLUMN_ITEM_NAMES = "item_names";

        /**
         * Returns the content uri for a specific row using the id of a checklist
         * @param id checklist id
         */
        public static Uri buildUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }
    }



}
