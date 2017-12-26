package com.teksine.queryapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teksine.queryapplication.R;
import com.teksine.queryapplication.model.EndUser;

import java.util.List;

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
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        QueryHistoryAdapter.ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.previous_question_item,null);
        holder = new QueryHistoryAdapter.ViewHolder();
        holder.queryTextView = (TextView) convertView.findViewById(R.id.queryTextView);
        holder.topicMessageText = (TextView) convertView.findViewById(R.id.topicMessageText);
        holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
        holder.statusImage=(ImageView) convertView.findViewById(R.id.statusImage);
        convertView.setTag(holder);

        EndUser rowItem = (EndUser) getItem(position);
        holder.queryTextView.setText(rowItem.getQuery());
        holder.topicMessageText.setText((rowItem.getAnswer()));
        if(rowItem.getAnswerStatus()==0){
            holder.statusImage.setImageResource(R.drawable.cross);
        }
        else{
            holder.statusImage.setImageResource(R.drawable.check);
        }


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
