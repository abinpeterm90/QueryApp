package com.teksine.queryapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teksine.queryapplication.R;
import com.teksine.queryapplication.model.EndUser;

import java.util.List;

import static android.net.Uri.parse;

/**
 * Created by abin on 24/12/2017.
 */

public class QueryHistoryAdapter extends BaseAdapter {
    Context context;
    List<EndUser> rowItems;

    public QueryHistoryAdapter(Context context, List<EndUser> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView topicMessageText;
        TextView queryTextView;
        ImageView statusImage;
        TextView infoText;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        QueryHistoryAdapter.ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.previous_question_item, null);
            holder = new QueryHistoryAdapter.ViewHolder();
            holder.queryTextView = (TextView) convertView.findViewById(R.id.queryTextView);
            holder.topicMessageText = (TextView) convertView.findViewById(R.id.topicMessageText);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            holder.statusImage = (ImageView) convertView.findViewById(R.id.statusImage);
            holder.infoText=(TextView) convertView.findViewById(R.id.infoText);
            convertView.setTag(holder);
        }
        else {
            holder = (QueryHistoryAdapter.ViewHolder) convertView.getTag();
        }

        EndUser rowItem = (EndUser) getItem(position);
        holder.queryTextView.setText(rowItem.getQuery());
        holder.topicMessageText.setText((rowItem.getTopic()));
        Uri uri = parse(rowItem.getPhotoUrl());
        Picasso.with(context).load(rowItem.getPhotoUrl()).fit().into(holder.imageView);

        if(rowItem.getAnswerStatus()==0){
            holder.statusImage.setImageResource(R.drawable.cross);
            holder.infoText.setText("Not Answered");
        }
        else{
            holder.statusImage.setImageResource(R.drawable.check);
            holder.infoText.setText("Tap to see Answer");
        }

        Animation animation= AnimationUtils.loadAnimation(context, R.anim.push_left_in);
        convertView.startAnimation(animation);
        animation = null;
        //Uri uri = parse(rowItem.getPhotoUrl());
        //Picasso.with(context).load(rowItem.getPhotoUrl()).fit().into(holder.imageView);
        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}
