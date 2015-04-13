package com.lovecats.catlover.capsules.profile.following;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.view.HeaderAdapter;
import com.lovecats.catlover.models.user.UserEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FollowingAdapter extends HeaderAdapter<RecyclerView.ViewHolder> {

    private int headerHeight;
    private List<UserEntity> userEntitities;
    private Context context;

    public FollowingAdapter(int headerHeight) {
        this.headerHeight = headerHeight;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        if (viewType == TYPE_ITEM) {
            View comment = LayoutInflater.from(context)
                    .inflate(R.layout.v_comment, parent, false);

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
//                UserEntity userEntity = userEntitities.get(position);
//
//                ((UsersViewHolder) holder).tvComment.setText(commentEntity.getContent());
//                ((UsersViewHolder) holder).tvName.setText(commentEntity.getUser().getUsername());
//                String url = commentEntity.getUser().getImage_url();
//                Picasso.with(context).load(url).into(((UsersViewHolder)holder).profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return userEntitities.size();
    }

    public void setCommentEntities(List<UserEntity> userEntitities) {
        this.userEntitities = userEntitities;
        notifyDataSetChanged();
    }


    public void addCommentEntity(UserEntity userEntity) {
        this.userEntitities.add(userEntity);
        notifyDataSetChanged();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.user_name_TV) TextView tvName;
        @InjectView(R.id.comment_TV) TextView tvComment;
        @InjectView(R.id.user_image_IV) ImageView profileImage;

        public UsersViewHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }
}
