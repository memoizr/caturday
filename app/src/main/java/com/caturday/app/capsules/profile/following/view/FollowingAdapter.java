package com.caturday.app.capsules.profile.following.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.caturday.app.R;
import com.caturday.app.capsules.common.view.HeaderAdapter;
import com.caturday.app.capsules.profile.view.ProfileActivity;
import com.caturday.app.models.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FollowingAdapter extends HeaderAdapter<RecyclerView.ViewHolder> {

    private int headerHeight;
    private List<UserEntity> userEntitities = new ArrayList();
    private Context context;

    public FollowingAdapter(int headerHeight) {
        this.headerHeight = headerHeight;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        if (viewType == TYPE_ITEM) {
            View comment = LayoutInflater.from(context)
                    .inflate(R.layout.v_user, parent, false);

            return new UsersViewHolder(comment);

        } else if (viewType == TYPE_HEADER) {
            View header = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.v_header, parent, false);

            header.getLayoutParams().height = headerHeight;
            return new EmptyHeader(header);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType +
                " + make sure your using types correctly");
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof UsersViewHolder) {
                UserEntity userEntity = userEntitities.get(position);

                ((UsersViewHolder) holder).tvName.setText(userEntity.getUsername());
                String url = userEntity.getImageUrl();
                Glide.with(context).load(url).into(((UsersViewHolder)holder).profileImage);

            ((UsersViewHolder) holder).containerV.setOnClickListener(v -> {
                        Intent intent = new Intent(context, ProfileActivity.class);
                        intent.putExtra(ProfileActivity.EXTRA_ID, userEntity.getServerId());
                        ((Activity) context).startActivity(intent);
                    }
            );

        }
    }

    @Override
    public int getItemCount() {
        return userEntitities.size();
    }

    public void setUserEntities(List<UserEntity> userEntitities) {
        this.userEntitities = userEntitities;
        notifyDataSetChanged();
    }


    public void addUserEntitities(UserEntity userEntity) {
        this.userEntitities.add(userEntity);
        notifyDataSetChanged();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.container_V) View containerV;
        @InjectView(R.id.username_TV) TextView tvName;
        @InjectView(R.id.user_image_IV) ImageView profileImage;

        public UsersViewHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }
}
