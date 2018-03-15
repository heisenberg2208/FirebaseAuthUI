package com.example.devendra.firebaseauthui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Devendra on 3/15/2018.
 */

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.user_adapter, parent, false);
        }
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvLatLon = (TextView) convertView.findViewById(R.id.tvLatLon);
        User user = getItem(position);

        tvUserName.setText(user.getUname());
        tvLatLon.setText(user.getUlat() + " "+ user.getUlon());

        return convertView;
    }
}
