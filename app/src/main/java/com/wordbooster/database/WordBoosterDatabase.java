package com.wordbooster.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.wordbooster.database.WordBoosterContract;

/**
 * Created by Sandeep Tiwari on 12/28/2015.
 */
public class WordBoosterDatabase extends SQLiteOpenHelper {

    private static final String TAG = WordBoosterDatabase.class.getSimpleName();
    public static final String DB_NAME = "wordbooster.db";
    public static final int DB_VERSION = 1;
    private Context mContext;

    private static WordBoosterDatabase instance;

    public interface TABLE {
        String WORDBOOSTER_WORD_TABLE = "word";
    }

    public static WordBoosterDatabase getInstance(Context ctx) {
        if (instance == null || !ctx.equals(instance.mContext)) {
            instance = new WordBoosterDatabase(ctx);
        }
        return instance;
    }

    public WordBoosterDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userTableCreateQuery =
                "CREATE TABLE " + TABLE.WORDBOOSTER_WORD_TABLE + " ("
                        + WordBoosterContract.WordBoosterColumns.ID + " INTEGER PRIMARY KEY, "
                        + WordBoosterContract.WordBoosterColumns.WORD + " TEXT, "
                        + WordBoosterContract.WordBoosterColumns.VARIANT + " INTEGER, "
                        + WordBoosterContract.WordBoosterColumns.MEANING + " TEXT, "
                        + WordBoosterContract.WordBoosterColumns.RATIO + " REAL, "
                        + " INTEGER,  UNIQUE(" + WordBoosterContract.WordBoosterColumns.ID + ") ON CONFLICT REPLACE);";




        Log.i(TAG, "Create query for user table : " + userTableCreateQuery);
        db.execSQL(userTableCreateQuery);


        //Log.i(TAG, "Create query for post attachment table : " + postAttachObjectTableCreateQuery);
        //db.execSQL(postAttachObjectTableCreateQuery);

    }

    /**
     * alter your table here
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if(oldVersion<=3){
        //db.execSQL("ALTER TABLE " + TABLE.NAME + " ADD " + AppAddressColumns.PHONE_NO + " TEXT");
        //}
    }
}
