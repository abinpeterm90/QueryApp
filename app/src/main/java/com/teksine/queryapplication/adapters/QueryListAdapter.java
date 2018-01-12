package com.teksine.queryapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teksine.queryapplication.R;
import com.teksine.queryapplication.other.Notification;
import com.teksine.queryapplication.other.RowItem;


import java.net.URL;
import java.util.List;

import static android.net.Uri.parse;

/**
 * Created by abin on 16/12/2017.
 */

public class QueryListAdapter extends BaseAdapter {

    Context context;
    List<Notification> rowItems;

    public QueryListAdapter(Context context, List<Notification> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtName;
        TextView txtDesc;
        TextView infoTextQuery;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        QueryListAdapter.ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.queries_item,null);
            holder = new QueryListAdapter.ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtName = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            holder.infoTextQuery=(TextView) convertView.findViewById(R.id.infoTextQuery);
            convertView.setTag(holder);

        Notification rowItem = (Notification) getItem(position);
        holder.txtDesc.setText((rowItem.getFirstName() +" "+rowItem.getLastName()).toUpperCase());
        holder.infoTextQuery.setText("Tap to Answer/Edit");
        holder.txtName.setText(rowItem.getTopic());
        Uri uri = parse(rowItem.getPhotoUrl());
        Picasso.with(context).load(rowItem.getPhotoUrl()).fit().into(holder.imageView);
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
