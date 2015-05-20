package test.freelancer.com.fltest.database.tables;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import test.freelancer.com.fltest.database.Table;
import test.freelancer.com.fltest.handlers.TvProgramHandler;

/**
 * Created by Android 17 on 5/20/2015.
 */
public class TvProgramsTable extends Table{

    public final static String TABLE_NAME       = "program_tbl";
    public final static String ID               = "id";
    public final static String NAME             = "outlet_id";
    public final static String START_TIME       = "name";
    public final static String END_TIME         = "address";
    public final static String CHANNEL          = "action";
    public final static String RATING           = "status";


    public final static String TABLE_STRUCTURE = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT, "
            + START_TIME + " TEXT, "
            + END_TIME + " TEXT, "
            + CHANNEL + " TEXT, "
            + RATING + " TEXT "
            + ");";
    private static final String TAG = TvProgramsTable.class.getSimpleName();

    @Override
    public String getTableStructure() {
        return TABLE_STRUCTURE;
    }

    @Override
    public String getName() {
        return TABLE_NAME;
    }


    public long insertProgram(TvProgramHandler data){
        ContentValues cv = new ContentValues();
        cv.put(NAME, data.getName());
        cv.put(START_TIME, data.getStartTime());
        cv.put(END_TIME, data.getEndTime());
        cv.put(CHANNEL, data.getChannel());
        cv.put(RATING, data.getRating());
        return insertOrUpdate(cv, NAME + " = " + "\""+data.getName() +"\"");
    }

    public TvProgramHandler getChannel(int id){
        String query = "SELECT * FROM " + getName() + " WHERE " + ID + " = " + id;
        Cursor cursor = rawQuery(query, null);
        TvProgramHandler data = null;
        if(cursor != null){
            if(cursor.moveToFirst()){
                data = new TvProgramHandler();
                data.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                data.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                data.setStartTime(cursor.getString(cursor.getColumnIndex(START_TIME)));
                data.setEndTime(cursor.getString(cursor.getColumnIndex(END_TIME)));
                data.setChannel(cursor.getString(cursor.getColumnIndex(CHANNEL)));
                data.setRating(cursor.getString(cursor.getColumnIndex(RATING)));
            }
        }
        return  data;
    }
    public TvProgramHandler getLastProgram(){
        String query = "SELECT * FROM " + getName() + " ORDER BY " + ID + " DESC " + " LIMIT 1";
        Cursor cursor = rawQuery(query, null);
        TvProgramHandler data = null;
        if(cursor != null){
            if(cursor.moveToFirst()){
                data = new TvProgramHandler();
                data.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                data.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                data.setStartTime(cursor.getString(cursor.getColumnIndex(START_TIME)));
                data.setEndTime(cursor.getString(cursor.getColumnIndex(END_TIME)));
                data.setChannel(cursor.getString(cursor.getColumnIndex(CHANNEL)));
                data.setRating(cursor.getString(cursor.getColumnIndex(RATING)));
            }
        }
        return  data;
    }

    public ArrayList<TvProgramHandler> getAllTvPrograms(){
        String query = "SELECT * FROM " + getName();
        Cursor cursor = rawQuery(query, null);
        ArrayList<TvProgramHandler> data = new ArrayList<TvProgramHandler>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    TvProgramHandler item = new TvProgramHandler();
                    item.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                    item.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                    item.setStartTime(cursor.getString(cursor.getColumnIndex(START_TIME)));
                    item.setEndTime(cursor.getString(cursor.getColumnIndex(END_TIME)));
                    item.setChannel(cursor.getString(cursor.getColumnIndex(CHANNEL)));
                    item.setRating(cursor.getString(cursor.getColumnIndex(RATING)));
                    data.add(item);
                } while (cursor.moveToNext());
            }
        }
        return data;
    }
}
