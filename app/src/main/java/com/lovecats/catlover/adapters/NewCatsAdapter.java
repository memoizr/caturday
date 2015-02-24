package com.lovecats.catlover.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lovecats.catlover.CatDetailActivity;
import com.lovecats.catlover.DashboardActivity;
import com.lovecats.catlover.FavoriteCatsFragment;
import com.lovecats.catlover.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import greendao.CatImage;

public class NewCatsAdapter extends RecyclerView.Adapter<NewCatsAdapter.CatsViewHolder> {
    private Context mContext;
    public List<CatImage> mCatImages;

    class CatsViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.cardCat_IV) ImageView cat_IV;
        @InjectView(R.id.catContainer) View catContainer;

        public CatsViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }

    public NewCatsAdapter(Context context, List<CatImage> catImages) {
        mContext = context;
        mCatImages = catImages;
    }

    @Override
    public CatsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View card = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.v_cats_card, viewGroup, false);
        return new CatsViewHolder(card);
    }

    @Override
    public void onBindViewHolder(final CatsViewHolder myViewHolder, int i) {
        final int j = i;

        final String transitionName = "catTransition" + i;
        final String containerTransitionName = "containerTransition" + i;
        ViewCompat.setTransitionName(myViewHolder.cat_IV, transitionName);

        // save the transition name in the tag of the imageview
        Picasso.with(mContext).load(mCatImages.get(i).getUrl()).into(myViewHolder.cat_IV);
        myViewHolder.catContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CatDetailActivity.class);
                ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) mContext,
                            Pair.create((View) myViewHolder.cat_IV, transitionName)
                    );
                // put more extras in the intent if you want, like the object clicked
                intent.putExtra("transition", transitionName);
                intent.putExtra("url", mCatImages.get(j).getUrl());
                intent.putExtra("id", mCatImages.get(j).getId());
                ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCatImages.size();
    }

    public void change() {
    }

    public void doSomething() {

        System.out.println("done");
    }
}
