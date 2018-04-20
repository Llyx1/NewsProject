package com.example.boulocalix.newspaper;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>
{

    ArrayList<FeedItem> feedItems;
    Context context;
    newsAdapterOnClickHandler clickHandler ;

    public void changeData(ArrayList<FeedItem> output) {
        this.feedItems.clear();
        this.feedItems.addAll(output);
        notifyDataSetChanged();
    }

    public interface newsAdapterOnClickHandler {
        void onClick(FeedItem item);
    }

    public void setListener(newsAdapterOnClickHandler clickHandler){
        this.clickHandler = clickHandler;
    }

    public NewsAdapter(Context context, ArrayList<FeedItem>feedItems){
        this.feedItems=feedItems;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.activity_news_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        FeedItem postItem=feedItems.get(position);
        holder.title.setText(postItem.getTitle());
        holder.date.setText(postItem.getPubDate());

        if (postItem.getImage() != null && !postItem.getImage().equals("")) {
            Picasso.with(context).load(postItem.getImage())
                    .resize(Resources.getSystem().getDisplayMetrics().widthPixels/3,0).into(holder.imageView);
        }else{
            Picasso.with(context).load("https://d50m6q67g4bn3.cloudfront.net/teams_avatars/51f79653-c4c9-497a-a27d-39f5f1067b38_1450369795410")
                    .resize(Resources.getSystem().getDisplayMetrics().widthPixels/3,0).into(holder.imageView);
        }
    }



    @Override
    public int getItemCount() {
        if (feedItems != null ) {
        return feedItems.size();}
        else {return -1;}
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,date;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title_text);
            date= (TextView) itemView.findViewById(R.id.date_text);
            imageView = (ImageView) itemView.findViewById(R.id.image) ;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            FeedItem item = feedItems.get(adapterPosition);
            clickHandler.onClick(item);
        }
    }
}
