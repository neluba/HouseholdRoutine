package com.example.android.householdroutine.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by oliver on 06.10.2017.
 */

public class DbContract {
    static final String CONTENT_AUTHORITY = "com.example.android.householdroutine";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String PATH_REMINDERS = "reminders";
    static final String PATH_CHECKLIST = "checklist";
    static final String PATH_SINGLE_CHECKLIST = "single_checklist";
    static final String PATH_PREDEFINED_REMINDERS = "predefined_reminders";
    static final String PATH_PREDEFINED_CHECKLIST = "predefined_checklist";
    static final String PATH_FULL_PREDEFINED_CHECKLIST = "full_predefined_checklist";
    static final String PATH_USER_POINTS = "user_points";
    static final String PATH_USER_POINTS_SUM = "user_points_sum";
    static final String PATH_USER_POINTS_REMINDERS_COUNT = "user_points_reminders_count";
    static final String PATH_INFORMATION_SETS = "information_sets";
    static final String PATH_INFORMATIONS = "informations";
    static final String PATH_FULL_INFORMATION = "full_information";

    /**
     * Reminders table
     */
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
        // type = 0 for reminder and type = 1 for checklist -- integer
        public static final String COLUMN_TYPE = "type";

        public static final int TYPE_REMINDER = 0;
        public static final int TYPE_CHECKLIST = 1;

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


    /**
     * Checklist table
     */
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


    /**
     * predefined_reminders and predefined_reminders_de table
     */
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

    /**
     * predefined_checklist and predefined_checklist_de table
     */
    public static final class PredefinedChecklistEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PREDEFINED_CHECKLIST)
                .build();

        // Inner joins the predefined reminders table with the predefined checklist table
        // Returns checklistId, name, description and item_names from the database
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

    /**
     * user_points table
     */
    public static final class UserPointsEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_USER_POINTS)
                .build();
        /**
         * returns as single row and column with the accumulated rewarded points. The column name
         * is set in the variable REWARDED_POINTS
         */
        public static final Uri USER_POINTS_SUM_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_USER_POINTS_SUM)
                .build();
        /**
         *  returns a single row and column with the reminders count. The column name is set in the
         *  variable REMINDERS_COUNT
         */
        public static final Uri USER_POINTS_REMINDERS_COUNT_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_USER_POINTS_REMINDERS_COUNT)
                .build();

        public static final String TABLE_NAME = "user_points";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_POINTS = "points";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TYPE = "type";

        // reminder as a type for the type column
        public static final String TYPE_REMINDER = "reminder";
        // points rewarded for different types
        public static final int POINTS_REMINDER = 10;

        // column name for retrieving the reminders count
        public static final String REMINDERS_COUNT = "reminders";
        // column name for retrieving the rewarded points
        public static final String REWARDED_POINTS = "points";


        /**
         * Returns the content uri for a specific row using the id
         * @param id rewarded points id
         */
        public static Uri buildUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }
    }

    /**
     * informations_sets table
     */
    public static final class InformationSetsEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_INFORMATION_SETS)
                .build();

        // Inner joins the information_sets table with the informations table
        // Returns _id, name, description, url and source
        public static final Uri FULL_INFORMATION_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FULL_INFORMATION)
                .build();



        public static final String TABLE_NAME = "information_sets";
        public static final String COLUMN_INFORMATION_ID = "information_id";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_SOURCE = "source";
        public static final String COLUMN_OBTAINED = "obtained";
        public static final String COLUMN_OBTAINABLE = "obtainable";

        public static final int OBTAINABLE_TRUE = 1;
        public static final int OBTAINABLE_FALSE = 0;
        public static final int OBTAINED_TRUE = 1;
        public static final int OBTAINED_FALSE = 0;

        /**
         * Returns the content uri for a specific row using the id
         * @param id information set id
         */
        public static Uri buildUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }
    }

    /**
     * informations and informations_de table
     */
    public static final class InformationsEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_INFORMATIONS)
                .build();

        public static final String TABLE_NAME = "informations";
        public static final String TABLE_NAME_DE = "informations_de";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";


        /**
         * Returns the content uri for a specific row using the id
         * @param id information id
         */
        public static Uri buildUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }
    }
}
