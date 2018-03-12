package com.example.devendra.firebaseauthui;

/**
 * Created by Devendra on 3/12/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class RequestAdapter extends ArrayAdapter<Request> {
    public RequestAdapter(Context context, int resource, List<Request> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_request, parent, false);
        }

        ImageView imgCrop = (ImageView) convertView.findViewById(R.id.imgCrop);
        TextView tvCropTitle = (TextView) convertView.findViewById(R.id.tvCropTitle);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
        Request status = getItem(position);

        Request r  = getItem(position);

        tvCropTitle.setText(r.getCrop_name());
        tvStatus.setText(r.getStatus());


        return convertView;
    }
}

