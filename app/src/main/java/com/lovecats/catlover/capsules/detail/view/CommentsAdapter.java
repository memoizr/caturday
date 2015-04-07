package com.lovecats.catlover.capsules.detail.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.view.HeaderAdapter;
import com.lovecats.catlover.models.comment.CommentEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommentsAdapter extends HeaderAdapter<RecyclerView.ViewHolder> {

    private int headerHeight;
    private List<CommentEntity> commentEntities;
    private Context context;

    public CommentsAdapter(int headerHeight) {
       this.headerHeight = headerHeight;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        if (viewType == TYPE_ITEM) {
            View comment = LayoutInflater.from(context)
                    .inflate(R.layout.v_comment, parent, false);

            return new CommentsViewHolder(comment);

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

        if (holder instanceof CommentsViewHolder) {
            CommentEntity commentEntity = commentEntities.get(position);

            ((CommentsViewHolder) holder).tvComment.setText(commentEntity.getContent());
            ((CommentsViewHolder) holder).tvName.setText(commentEntity.getUser().getUsername());
            String url = commentEntity.getUser().getImage_url();
            Picasso.with(context).load(url).into(((CommentsViewHolder)holder).profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return commentEntities.size();
    }

    public void setCommentEntities(List<CommentEntity> commentEntities) {
        this.commentEntities = commentEntities;
        notifyDataSetChanged();
    }


    public void addCommentEntity(CommentEntity commentEntity) {
        this.commentEntities.add(commentEntity);
        notifyDataSetChanged();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.user_name_TV) TextView tvName;
        @InjectView(R.id.comment_TV) TextView tvComment;
        @InjectView(R.id.user_image_IV) ImageView profileImage;

        public CommentsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }
}
