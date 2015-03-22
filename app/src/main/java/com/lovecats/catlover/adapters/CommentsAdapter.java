package com.lovecats.catlover.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lovecats.catlover.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 14/03/15.
 */
public class CommentsAdapter extends ArrayAdapter<JsonObject> {
    ArrayList<JsonObject> mList;
    Context mContext;

    public CommentsAdapter(Context context, ArrayList<JsonObject> array) {
        super(context, 0, array);
        mContext = context;
        mList = array;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JsonObject comment = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.v_comment, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.user_name_TV);
        TextView tvComment = (TextView) convertView.findViewById(R.id.comment_TV);
        ImageView profileImage = (ImageView) convertView.findViewById(R.id.user_image_IV);
        tvComment.setText(comment.get("content").getAsString());
        JsonParser parser = new JsonParser();
        JsonObject o = (JsonObject)parser.parse(comment.get("user").toString());
        JsonElement image_url = o.get("image_url");

        if (image_url != null && !image_url.isJsonNull()) {
            Picasso.with(mContext).load(image_url.getAsString()).into(profileImage);
        }
        tvName.setText(o.get("username").getAsString());
        return convertView;
    }
}
