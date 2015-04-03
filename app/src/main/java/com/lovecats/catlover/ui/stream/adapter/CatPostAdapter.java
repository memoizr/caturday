package com.lovecats.catlover.ui.stream.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovecats.catlover.ui.detail.CatDetailActivity;
import com.lovecats.catlover.ui.main.MainActivity;
import com.lovecats.catlover.R;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import greendao.CatPost;
import lombok.Setter;

public class CatPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    @Setter private List<CatPost> mCatPosts;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public CatPostAdapter(Context context, List<CatPost> catPosts) {
        mContext = context;
        mCatPosts= catPosts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == TYPE_ITEM) {
            View card = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.v_cats_card, viewGroup, false);

            return new CatsCardViewHolder(card);

        } else if (viewType == TYPE_HEADER) {
            View header = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.v_header, viewGroup, false);
            return new EmptyHeader(header);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType +
                " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof EmptyHeader) {

//            StaggeredGridLayoutManager.LayoutParams layoutParams =
//                    (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
//            layoutParams.setFullSpan(true);
        } else {
            final CatsCardViewHolder myViewHolder = (CatsCardViewHolder) viewHolder;
            final int j = i;

            final String transitionName = "catTransition" + i;
            ViewCompat.setTransitionName(myViewHolder.cat_IV, transitionName);

            ((CatsCardViewHolder) viewHolder).caption_TV.setText(mCatPosts.get(i).getCaption());
            ((CatsCardViewHolder) viewHolder).total_votes_count.setText(
                    mCatPosts.get(i).getTotalVotesCount().toString());

            Picasso.with(mContext).load(mCatPosts.get(i).getImage_url()).into(myViewHolder.cat_IV);
            myViewHolder.catContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)mContext).toggleArrow(true);
                    Intent intent = new Intent(mContext, CatDetailActivity.class);
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    (Activity) mContext,
                                    Pair.create((View) myViewHolder.cat_IV, transitionName)
                            );
                    intent.putExtra("transition", transitionName);
                    intent.putExtra("url", mCatPosts.get(j).getImage_url());
                    intent.putExtra("id", mCatPosts.get(j).getId());
                    intent.putExtra("serverId", mCatPosts.get(j).getServerId());
                    ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
                }
            });
        }
    }

    public void addItems(Collection<CatPost> catPostCollection) {
        mCatPosts.addAll(catPostCollection);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCatPosts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    class EmptyHeader extends RecyclerView.ViewHolder {

        public EmptyHeader(View itemView) {
            super(itemView);
        }
    }

    class CatsCardViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.cardCat_IV) ImageView cat_IV;
        @InjectView(R.id.catContainer) View catContainer;
        @InjectView(R.id.caption_TV) TextView caption_TV;
        @InjectView(R.id.total_votes_count_TV) TextView total_votes_count;

        public CatsCardViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }

}
