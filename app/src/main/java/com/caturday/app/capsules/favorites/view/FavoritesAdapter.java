package com.caturday.app.capsules.favorites.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.caturday.app.R;
import com.caturday.app.capsules.common.view.HeaderAdapter;
import com.caturday.app.models.catpost.CatPostEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FavoritesAdapter extends HeaderAdapter {
    private int headerHeight;
    private List<CatPostEntity> catPostEntities = new ArrayList<>();
    private Context context;

    public FavoritesAdapter(int headerHeight) {
        this.headerHeight = headerHeight;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        if (viewType == TYPE_ITEM) {
            View comment = LayoutInflater.from(context)
                    .inflate(R.layout.v_favorite_card, parent, false);

            return new FavoritesViewHolder(comment);

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

        if (holder instanceof FavoritesViewHolder) {
            CatPostEntity catPostEntity = catPostEntities.get(position);
            String url = catPostEntity.getImageUrl();

            Glide.with(context).load(url).into(((FavoritesViewHolder) holder).favoriteImage);
        } else {
            StaggeredGridLayoutManager.LayoutParams layoutParams =
                    (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);

        }
    }

    @Override
    public int getItemCount() {
        return catPostEntities.size();
    }

    public void setCatPostEntities(List<CatPostEntity> catPostEntities) {
        this.catPostEntities = new ArrayList<>();
        catPostEntities.add(new CatPostEntity());
        this.catPostEntities.addAll(catPostEntities);
        notifyDataSetChanged();
    }
//
//
//    public void addCommentEntity(CommentEntity commentEntity) {
//        this.commentEntities.add(commentEntity);
//        notifyDataSetChanged();
//    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.favorite_IV) ImageView favoriteImage;

        public FavoritesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }
}
