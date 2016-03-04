package com.wordbooster.api;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordbooster.Utils.AppConstant;
import com.wordbooster.database.WordDataHandler;
import com.wordbooster.model.Word;
import com.wordbooster.model.WordData;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sandeep Tiwari on 3/4/2016.
 */
public class WordBoosterNetworkService extends IntentService{

    private static final String TAG = WordBoosterNetworkService.class.getSimpleName();
    OkHttpClient client = new OkHttpClient();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *  name Used to name the worker thread, important only for debugging.
     */
    public WordBoosterNetworkService() {
        super("WordBoosterNetworkService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle responseBundle = null;
        try {
            String response = getWord();
            WordData wordData = getWordData(response);
            responseBundle = new Bundle();
            //responseBundle.putParcelable(AppConstant.RESPONSE_KEY, wordData);
            responseBundle.putInt(AppConstant.RESPONSE_KEY, 200);
        } catch (IOException e) {
            responseBundle = new Bundle();
            responseBundle.putString(AppConstant.ERROR_KEY, e.getMessage());
        } catch (JSONException e) {
            responseBundle = new Bundle();
            responseBundle.putString(AppConstant.ERROR_KEY, e.getMessage());
        }
        Intent responseIntent = new Intent();
        responseIntent.setAction(AppConstant.REQUEST_WORD_ACTION);
        responseIntent.putExtras(responseBundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(responseIntent);
    }



    private String getWord() throws IOException {
        Request request = new Request.Builder()
                .url(AppConstant.REQUEST_WORD_URL)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private WordData getWordData(String strWord) throws JSONException, IOException {
        Log.i(TAG, "Word's Json : "+strWord);
        ObjectMapper mapper = new ObjectMapper();
        WordData wordData = mapper.readValue(strWord, WordData.class);
        //insert into db
        List<Word> words = wordData.getWords();
        for (Word word: words) {
            WordDataHandler.saveWord(this, word);
        }
        Log.i(TAG, "Word's data insertion done");
        return  wordData;
    }
}
