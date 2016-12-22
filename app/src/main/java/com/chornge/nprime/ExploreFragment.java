package com.chornge.nprime;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExploreFragment extends Fragment {

    boolean isTabLoaded = false;
    TextView explore_text_view;
    SearchView searchView;
    CharSequence query;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    //mandatory
    public ExploreFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ReadRss readRss = new ReadRss(getContext());
        readRss.execute();
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        Typeface robotoBold = Typeface.createFromAsset(getActivity().getAssets(), "font/Roboto-Bold.ttf");
        explore_text_view = (TextView) view.findViewById(R.id.explore_text_view);
        searchView = (SearchView) view.findViewById(R.id.search_view);
        query = searchView.getQuery();
        searchView.setQueryHint("Search View");
        explore_text_view.setTypeface(robotoBold);

        String Restaurant = "restaurant";
        String url;

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && !isTabLoaded) {
            Log.e("Third Tab Fragment", "loaded");
            isTabLoaded = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}