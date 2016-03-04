package com.wordbooster.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.wordbooster.Utils.AppConstant;
import com.wordbooster.Utils.ItemClickSupport;
import com.wordbooster.activity.R;
import com.wordbooster.adapter.WordAdapter;
import com.wordbooster.api.WordBoosterNetworkService;
import com.wordbooster.database.WordDataHandler;
import com.wordbooster.model.Word;
import com.wordbooster.model.WordData;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandeep Tiwari on 3/4/2016.
 */
public class WordListFragment extends BaseFragment {
    private static final String TAG = WordListFragment.class.getSimpleName();
    private Context mContext;
    private RecyclerView mRvWord;
    private ViewGroup mLoadingView;
    private boolean isBroadCastRegistered;
    private IntentFilter mIntentFilter;
    private CircularProgressView progressView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_word_list, container, false);
    }

    /**
     * All initialization and ui controlls initialization
     * would be in this method.
     */
    @Override
    protected void initUI() {
        super.initUI();
        View view = getView();
        mRvWord = (RecyclerView)view.findViewById(R.id.word_recycler_view);
        mLoadingView = (ViewGroup)view.findViewById(R.id.loadingContainer);
        progressView = (CircularProgressView)view.findViewById(R.id.progress_view);

        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.DKGRAY);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        mRvWord.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity()).paint(paint).build());
        mRvWord.setHasFixedSize(true);
        mRvWord.setItemAnimator(new DefaultItemAnimator());

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(AppConstant.REQUEST_WORD_ACTION);
        registerBroadCast();
    }

    /**
     * All flow related stuffs would be in this method
     */
    @Override
    protected void onStartActivity() {
        super.onStartActivity();
        progressView.startAnimation();
        List<Word> words = WordDataHandler.getAllWordList(getActivity());
        if(words == null){
            requestForWord();
        }else{
            setAdapter();
        }

        ItemClickSupport.addTo(mRvWord).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.i(TAG, "clicked");
                    }
                }
        );
    }

    private void requestForWord() {
        Intent netWorkIntent = new Intent(getActivity(), WordBoosterNetworkService.class);
        getActivity().startService(netWorkIntent);
    }

    private void registerBroadCast(){
        if(!isBroadCastRegistered) {
            LocalBroadcastManager.getInstance(getActivity()).
                    registerReceiver(receiver, mIntentFilter);
            isBroadCastRegistered = true;
        }
    }

    private void unRegisterBroadCast(){
        if(isBroadCastRegistered) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
            isBroadCastRegistered = false;
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            String error = bundle.getString(AppConstant.ERROR_KEY);
            if(error == null){
                int response = bundle.getInt(AppConstant.RESPONSE_KEY);
                if(response == 200){
                    setAdapter();
                }
            }else{
                //ToDo all error handling would be here
                Log.i(TAG, "Error String"+error);
            }

        }
    };

    private void setAdapter(){
        Log.i(TAG, "Adapert set");
        List<Word> words = WordDataHandler.getAllWordList(getActivity());
        mLoadingView.setVisibility(View.GONE);
        mRvWord.setVisibility(View.VISIBLE);
        WordAdapter wordAdapter = new WordAdapter(getActivity(), words);
        mRvWord.setAdapter(wordAdapter);
        mRvWord.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onStop() {
        super.onStop();
        unRegisterBroadCast();
    }

    @Override
    public void onDetach() {
        unRegisterBroadCast();
        super.onDetach();
    }
}
