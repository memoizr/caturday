package com.lovecats.catlover.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lovecats.catlover.R;

import java.util.ArrayList;

/**
 * Created by user on 14/03/15.
 */
public class CommentsAdapter extends ArrayAdapter<String> {
    ArrayList<String> mList;

    public CommentsAdapter(Context context, ArrayList<String> objects) {
        super(context, 0, objects);
        mList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String comment = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.v_comment, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.comment_TV);
        tvName.setText(comment);
        return convertView;
    }
}
