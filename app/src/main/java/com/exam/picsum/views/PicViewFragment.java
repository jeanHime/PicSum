package com.exam.picsum.views;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.exam.picsum.R;

public class PicViewFragment extends Fragment {

    ImageView pic;
    Toolbar toolbar;

    public PicViewFragment() {
        // Required empty public constructor
    }

    public static PicViewFragment newInstance() {
        PicViewFragment fragment = new PicViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_pic_view, container, false);
        initializeViews(view);
        setContents();
        return view;
    }

    private void setContents() {
        Glide.with(getContext())
                .load(getArguments().getString("imageUrl"))
                .placeholder(R.drawable.ic_no_image)
                .into(pic);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getArguments().getString("author"));
    }

    private void initializeViews(View view) {
        pic = view.findViewById(R.id.picture);
    }
}