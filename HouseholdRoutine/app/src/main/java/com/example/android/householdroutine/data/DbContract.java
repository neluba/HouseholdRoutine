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
        // integer
        public static final String COLUMN_CHECKLIST = "checklist_id";
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

        /**
         * Returns the content uri for a specific row using the id of a reminder
         * @param id reminder id
         */
        public static Uri buildRemindersUriWithId(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();
        }
        public static Uri buildRemindersUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }
    }

    public static final String PATH_CHECKLIST = "checklist";

    public static final class ChecklistEntry implements BaseColumns {
        // Base content URI for the checklist table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CHECKLIST)
                .build();

        public static final String TABLE_NAME = "checklist";

        public static final String COLUMN_ITEM_NAME = "item_name";
        public static final String COLUMN_PREDEFINED_ITEM_ID = "predefined_item_id";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_COMPLETED = "completed";

        /**
         * Returns the content uri for a specific row using the id of a checklist
         * @param id reminder id
         */
        public static Uri buildChecklistUriWithId(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();
        }
        public static Uri buildChecklistUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }
    }


}
