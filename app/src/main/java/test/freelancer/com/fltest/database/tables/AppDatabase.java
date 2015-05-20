package test.freelancer.com.fltest.database.tables;

import android.content.Context;

import test.freelancer.com.fltest.database.DatabaseHelper;
import test.freelancer.com.fltest.database.EngineDatabase;

/**
 * Created by Android 17 on 5/20/2015.
 */
public class AppDatabase extends EngineDatabase {
    /**
     * @param context  - application context
     * @param database - database
     */

    public final static String DATABASE_NAME = "whatsbeef.db";
    public final static int DATABASE_VERSION = 1;

    /**
     * @param dbname    - database name
     * @param dbversion - database version
     */
    public AppDatabase() {
        super(DATABASE_NAME, DATABASE_VERSION);
        addTable(new TvProgramsTable());
    }
}
