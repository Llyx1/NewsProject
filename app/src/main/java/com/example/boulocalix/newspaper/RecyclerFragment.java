package com.example.boulocalix.newspaper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link RecyclerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerFragment extends Fragment implements AsyncResponse,constant, NewsAdapter.newsAdapterOnClickHandler{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    HomePage main ;
    Context context = null ;
    NewsAdapter adapter ;
    RecyclerView recyclerView ;

    private static final String URL = "url";

    // TODO: Rename and change types of parameters
    private String mUrl;


//    private OnFragmentInteractionListener mListener;

    public RecyclerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RecyclerFragment newInstance(String url) {
        RecyclerFragment fragment = new RecyclerFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(URL);
        }
        try {
            main = (HomePage) getActivity() ;
            context = getActivity() ;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
        if (!mUrl.equals(QUERY_URL + HOME_PAGE)){
        AsyncDownloader downloader = new AsyncDownloader(main, this, mUrl );
        downloader.execute();
        }
        else {
            AsyncDownloaderSpecial downloaderSpecial = new AsyncDownloaderSpecial(main, this, mUrl);
            downloaderSpecial.execute();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout fragmentRecycler = (FrameLayout) inflater.inflate(R.layout.fragment_recycler, null);
        recyclerView = fragmentRecycler.findViewById(R.id.recyclerview) ;
        return fragmentRecycler ;

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(FeedItem item);
//    }

    @Override
    public void processFinish(ArrayList<FeedItem> output) {
        adapter = new NewsAdapter(main, output);
        recyclerView.setLayoutManager(new LinearLayoutManager(main));
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    public void onClick(FeedItem item) {

        Intent detailIntent = new Intent(main, DetailActivity.class) ;
        detailIntent.putExtra("Info" , item) ;
        startActivity(detailIntent);

    }
}
