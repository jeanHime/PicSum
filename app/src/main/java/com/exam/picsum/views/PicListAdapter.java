package com.exam.picsum.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.exam.picsum.R;
import com.exam.picsum.model.PicSumModel;

import java.util.ArrayList;

public class PicListAdapter extends RecyclerView.Adapter<PicListAdapter.ViewHolder>{
    Activity activity;
    private Context context;
    ArrayList<PicSumModel> mData;
    View.OnClickListener listener;

    public PicListAdapter(Activity activity, ArrayList<PicSumModel> data) {
        mData = data;
        this.listener = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PicListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.pic_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicListAdapter.ViewHolder holder, int position) {
        String author = mData.get(position).getAuthor();

        holder.authorName.setText(author);

        Glide.with(context)
                .load(mData.get(position).getDownload_url())
                .centerCrop()
                .placeholder(R.drawable.ic_no_image)
                .into(holder.image);

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("imageUrl", mData.get(position).getDownload_url());
                bundle.putString("author", mData.get(position).getAuthor());
                Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.action_picListFragment_to_picViewFragment,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData!=null ? mData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView authorName;
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imgview_preview);
            authorName = itemView.findViewById(R.id.txt_author);

        }
    }
}
