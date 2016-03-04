package com.wordbooster.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Sandeep Tiwari on 3/4/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Word implements Parcelable {

    private int id;
    private String word;
    private int variant;
    private String meaning;
    private float ratio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getVariant() {
        return variant;
    }

    public void setVariant(int variant) {
        this.variant = variant;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeInt(this.variant);
        dest.writeString(this.meaning);
        dest.writeFloat(this.ratio);
    }

    public Word() {
    }

    private Word(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.variant = in.readInt();
        this.meaning = in.readString();
        this.ratio = in.readFloat();
    }

    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    @Override
    public String toString() {
        return String.format("{id:%s,word:%s,variant:%s,meaning:%s,ratio:%s}",
                id, word, variant, meaning, ratio);
    }
}
