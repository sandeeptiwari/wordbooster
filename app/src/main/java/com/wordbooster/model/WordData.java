package com.wordbooster.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Sandeep Tiwari on 3/4/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WordData implements Parcelable {
    private String version;
    private List<Word> words;

    public WordData(){

    }

    @Override
    public String toString() {
        return String.format("{version:%s,[words:%s]}", version, words);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.version);
        dest.writeTypedList(words);
    }


    private WordData(Parcel in) {
        this.version = in.readString();
        in.readTypedList(words, Word.CREATOR);
    }

    public static final Parcelable.Creator<WordData> CREATOR = new Parcelable.Creator<WordData>() {
        public WordData createFromParcel(Parcel source) {
            return new WordData(source);
        }

        public WordData[] newArray(int size) {
            return new WordData[size];
        }
    };

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
