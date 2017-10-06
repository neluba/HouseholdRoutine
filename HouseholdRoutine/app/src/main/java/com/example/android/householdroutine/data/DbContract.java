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

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_CHECKLIST = "checklist_id";
        public static final String COLUMN_START_DATE = "startDate";
        public static final String COLUMN_END_DATE = "endDate";
        public static final String COLUMN_OUTDATED = "outdated";

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
