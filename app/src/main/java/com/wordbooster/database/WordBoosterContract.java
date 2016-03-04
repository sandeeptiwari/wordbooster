package com.wordbooster.database;

import android.net.Uri;

/**
 * Created by Sandeep Tiwari on 12/28/2015.
 */
public class WordBoosterContract {

    public static final String 	CONTENT_AUTHORITY 	= "com.wordbooster.activity.content";
    private static final Uri BASE_CONTENT_URI 	= Uri.parse("content://" + CONTENT_AUTHORITY);

    public interface WordBoosterColumns{
        String ID 					= "id";
        String WORD      			= "word";
        String VARIANT 			    = "variant";
        String MEANING 		    	= "meaning";
        String RATIO 				= "ratio";
    }



    private static final String PATH_WORD_BOOSTER               = "word";


    public static class WordBooster implements WordBoosterColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WORD_BOOSTER).build();
    }
}
