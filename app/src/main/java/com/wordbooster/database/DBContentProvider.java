package com.wordbooster.database;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Sandeep Tiwari on 12/28/2015.
 */
public class DBContentProvider extends ContentProvider {
    private static final String TAG = DBContentProvider.class.getSimpleName();
    private static final int WORD_BOOSTER                           = 100;

    private static final String PATH_WORD_BOOSTER                   = "word";


    private WordBoosterDatabase mDbHelper;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(WordBoosterContract.CONTENT_AUTHORITY, PATH_WORD_BOOSTER, WORD_BOOSTER);
    }


    @Override
    public boolean onCreate() {
        mDbHelper = new WordBoosterDatabase(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,	String[] selectionArgs, String sortOrder) {

        int uriType = sURIMatcher.match(uri);
        Cursor cursor = null;
        SQLiteDatabase sqlDB = mDbHelper.getReadableDatabase();

        switch (uriType) {

            case WORD_BOOSTER:
                cursor = sqlDB.query(WordBoosterDatabase.TABLE.WORDBOOSTER_WORD_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDbHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case WORD_BOOSTER:
                id = sqlDB.insertWithOnConflict(
                        WordBoosterDatabase.TABLE.WORDBOOSTER_WORD_TABLE, null,
                        values,SQLiteDatabase.CONFLICT_REPLACE);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(uri.getLastPathSegment()+"/"+id);
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int rowsUpdated=0;
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDbHelper.getWritableDatabase();

        switch (uriType) {

            case WORD_BOOSTER:
                rowsUpdated = sqlDB.update(WordBoosterDatabase.TABLE.WORDBOOSTER_WORD_TABLE,
                        values,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqlDB = mDbHelper.getWritableDatabase();
        int uriType = sURIMatcher.match(uri);
        int rowsDeleted = 0;
        switch (uriType) {
            case WORD_BOOSTER:
                rowsDeleted = sqlDB.delete(WordBoosterDatabase.TABLE.WORDBOOSTER_WORD_TABLE,
                        selection,selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }
}
