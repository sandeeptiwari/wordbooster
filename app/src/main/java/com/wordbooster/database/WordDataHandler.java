package com.wordbooster.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.wordbooster.model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sandeep Tiwari on 1/3/2016.
 */
public class WordDataHandler {

    private static final String TAG = WordDataHandler.class.getSimpleName();

    public static void saveWord(Context ctx, Word word) {
        ContentValues values = new ContentValues();
        int id = word.getId();
        values.put(WordBoosterContract.WordBoosterColumns.ID, id);
        values.put(WordBoosterContract.WordBoosterColumns.WORD, word.getWord());
        values.put(WordBoosterContract.WordBoosterColumns.VARIANT, word.getVariant());
        values.put(WordBoosterContract.WordBoosterColumns.MEANING, word.getMeaning());
        values.put(WordBoosterContract.WordBoosterColumns.RATIO, word.getRatio());
        ctx.getContentResolver().insert(WordBoosterContract.WordBooster.CONTENT_URI, values);
        Log.i(TAG, "post data insertion is complete");
    }



    public static Word getWordById(Context ctx, String wordId) {

        Word word = new Word();
        String where = WordBoosterContract.WordBoosterColumns.ID + "==" + wordId;
        Log.i(TAG, "check where clause in WordDataHelper :->" + where);
        Cursor cursor = ctx.getContentResolver().query(WordBoosterContract.WordBooster.CONTENT_URI,
                null, where, null, null);

        int count = cursor.getCount();
        Log.i(TAG, "cursor count :->" + count);

        if (count == 0)
            return null;

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(WordBoosterContract.WordBoosterColumns.ID));
            word.setId(id);

            String wordName = cursor.getString(
                    cursor.getColumnIndex(WordBoosterContract.WordBoosterColumns.WORD));
            word.setWord(wordName);

            int varient = cursor.getInt(
                    cursor.getColumnIndex(WordBoosterContract.WordBoosterColumns.VARIANT));
            word.setVariant(varient);

            String meaning =
                    cursor.getString(cursor.getColumnIndex(WordBoosterContract.WordBoosterColumns.MEANING));
            word.setMeaning(meaning);

            float ratio = cursor.getFloat(
                    cursor.getColumnIndex(WordBoosterContract.WordBoosterColumns.RATIO));
            word.setRatio(ratio);

        }
        cursor.close();
        return word;
    }


    public static List<Word> getAllWordList(Context ctx) {

        if(ctx == null)
            return null;

        List<Word> wordList = new ArrayList<Word>();
        Cursor cursor = ctx.getContentResolver().query(WordBoosterContract.WordBooster.CONTENT_URI,
                null, null, null, null);
        int count = cursor.getCount();
        Log.i(TAG, "cursor count :->" + count);

        if (count == 0)
            return null;

        while (cursor.moveToNext()) {
            Word word = new Word();
            int id = cursor.getInt(cursor.getColumnIndex(WordBoosterContract.WordBoosterColumns.ID));
            word.setId(id);

            String wordName = cursor.getString(
                    cursor.getColumnIndex(WordBoosterContract.WordBoosterColumns.WORD));
            word.setWord(wordName);

            int varient = cursor.getInt(
                    cursor.getColumnIndex(WordBoosterContract.WordBoosterColumns.VARIANT));
            word.setVariant(varient);

            String meaning =
                    cursor.getString(cursor.getColumnIndex(WordBoosterContract.WordBoosterColumns.MEANING));
            word.setMeaning(meaning);

            float ratio = cursor.getFloat(
                    cursor.getColumnIndex(WordBoosterContract.WordBoosterColumns.RATIO));
            word.setRatio(ratio);
            wordList.add(word);
        }
        cursor.close();
        return wordList;
    }

}
