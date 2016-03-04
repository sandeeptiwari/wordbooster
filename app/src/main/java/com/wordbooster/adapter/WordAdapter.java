package com.wordbooster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wordbooster.Utils.AppConstant;
import com.wordbooster.activity.R;
import com.wordbooster.model.Word;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sandeep Tiwari on 3/4/2016.
 * Create the basic adapter extending from RecyclerView.Adapter
 * Note that we specify the custom ViewHolder which gives us access to our views
 */
public class WordAdapter extends
        RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private static final String TAG = WordAdapter.class.getSimpleName();
    String WORL_THUMB_URL = "http://appsculture.com/vocab/images/";

    private List<Word> mItems;
    private Context mContext;

    public WordAdapter(Context context, List<Word> items) {

        mItems = items;
        mContext = context;
    }

    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_word, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WordAdapter.ViewHolder holder, int position) {
        Word currentItem = mItems.get(position);

        String wordNameWithHeading = "<body>" +
                "<strong>WORD :</strong>&nbsp;" + currentItem.getWord() +
                "</blockquote></body>";

        String meaningWithHeading = "<body>" +
                "<strong>MEANING :</strong>&nbsp;" + currentItem.getMeaning() +
                "</blockquote></body>";

        holder.wordName.setText(Html.fromHtml(wordNameWithHeading));
        holder.wordMeaning.setText(Html.fromHtml(meaningWithHeading));
        int id = currentItem.getId();
        float ratio = currentItem.getRatio();
        String url = WORL_THUMB_URL + id + ".png";
        if(ratio >= 0) {
            Log.i(TAG, "IMAGE URL : " + url);
            Glide.with(mContext)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.mipmap.default_thumb)
                    .crossFade()
                    .into(holder.wordPic);
        }else{
            Log.i(TAG, "No Image Url "+ url);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    /**
     * Provide a direct reference to each of the views within a data item
     * Used to cache the views within the item layout for fast access
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * holder should contain a member variable
         * for any view that will be set as you render a row
         */
        public TextView wordName, wordMeaning;
        public CircleImageView wordPic;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);


            wordName = (TextView) itemView.findViewById(R.id.word_name);
            wordMeaning = (TextView) itemView.findViewById(R.id.word_meaning);
            wordPic = (CircleImageView) itemView.findViewById(R.id.word_pic);

        }
    }
}
