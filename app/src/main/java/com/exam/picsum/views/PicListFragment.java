package com.exam.picsum.views;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.exam.picsum.R;
import com.exam.picsum.model.PicSumModel;
import com.exam.picsum.network.PicSumEndpoint;

import java.util.ArrayList;

public class PicListFragment extends Fragment implements PicSumEndpoint.PicListener {

    RecyclerView recyclerView;
    PicListAdapter adapter;
    ArrayList<PicSumModel> picList;

    public PicListFragment() {
        // Required empty public constructor
    }
    public static PicListFragment newInstance() {
        PicListFragment fragment = new PicListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic_list, container, false);
        initializeViews(view);
        getPicList();
        return view;
    }


    private void initializeViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.picList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        ViewTreeObserver viewTreeObserver = recyclerView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calculateSize();
            }
        });

        picList = new ArrayList<>();
        adapter = new PicListAdapter(getActivity(),picList);
        recyclerView.setAdapter(adapter);

    }

    private static final int sColumnWidth = 180; // assume cell width of 120dp
    private void calculateSize() {
        int spanCount = (int) Math.floor(recyclerView.getWidth() / convertDPToPixels(sColumnWidth));
        ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanCount(spanCount);
    }

    private float convertDPToPixels(int dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return dp * logicalDensity;
    }

    private void updateList(ArrayList<PicSumModel> body) {
        picList.addAll(body);
        adapter.notifyDataSetChanged();
    }

    private void getPicList(){
        if(isNetworkAvailable()) {
            PicSumEndpoint endpoint = new PicSumEndpoint(this);
            endpoint.getList(1);
        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void failed() {

    }

    @Override
    public void success(ArrayList<PicSumModel> body) {
        updateList(body);
    }

}