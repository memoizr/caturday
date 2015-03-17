package com.lovecats.catlover.adapters;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovecats.catlover.CatDetailActivity;
import com.lovecats.catlover.MainActivity;
import com.lovecats.catlover.R;
import com.lovecats.catlover.data.CatPostModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import greendao.CatPost;

public class CatPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    public List<CatPost> mCatPosts;
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

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof EmptyHeader) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        } else {
            final CatsCardViewHolder myViewHolder = (CatsCardViewHolder) viewHolder;
            final int j = i;

//            setAnimation(myViewHolder.catContainer, i);
            final String transitionName = "catTransition" + i;
            ViewCompat.setTransitionName(myViewHolder.cat_IV, transitionName);

            ((CatsCardViewHolder) viewHolder).caption_TV.setText(mCatPosts.get(i).getCaption());

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
                    ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
                }
            });
        }
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

        public CatsCardViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }

    int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
