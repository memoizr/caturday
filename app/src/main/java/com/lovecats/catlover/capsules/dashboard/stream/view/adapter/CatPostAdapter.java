package com.lovecats.catlover.capsules.dashboard.stream.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lovecats.catlover.capsules.detail.view.CatDetailActivity;
import com.lovecats.catlover.capsules.main.view.MainActivity;
import com.lovecats.catlover.R;
import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.util.helper.ShareHelper;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Setter;

public class CatPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    @Setter private List<CatPostEntity> mCatPosts;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public CatPostAdapter(Context context, List<CatPostEntity> catPosts) {
        mContext = context;
        mCatPosts = catPosts;
    }

    //TODO optimize this class. Shame on you!

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

            //TODO make my mind up whether to use staggered grid or not
//            StaggeredGridLayoutManager.LayoutParams layoutParams =
//                    (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
//            layoutParams.setFullSpan(true);
        } else {
            final CatsCardViewHolder myViewHolder = (CatsCardViewHolder) viewHolder;

            final CatPostEntity catPostEntity = mCatPosts.get(i);

            final String transitionName = "catTransition" + i;

            ViewCompat.setTransitionName(myViewHolder.cat_IV, transitionName);

            ((CatsCardViewHolder) viewHolder).caption_TV.setText(catPostEntity.getCaption());

            String commentsNumber = Integer.toString(catPostEntity.getComments().size());
            ((CatsCardViewHolder) viewHolder).total_comments_count.setText(commentsNumber);
            int votesCount = catPostEntity.getVotesCount();


            String votesNumber = "";

            if (votesCount != 0) {
                votesNumber = Integer.toString(votesCount);
            }

            myViewHolder.vote_B.setText(votesNumber);

            Picasso.with(mContext).load(catPostEntity.getImageUrl()).into(myViewHolder.cat_IV);

            myViewHolder.options_B.setOnClickListener(this::showPopup);

            myViewHolder.share_B.setOnClickListener(view -> {
                ShareHelper.shareLinkAction("Check out this cat!",
                        mCatPosts.get(i).getImageUrl(),
                        mContext);
            });

            myViewHolder.vote_B.setOnClickListener(view -> {
                System.out.println("Clicked!");
            });

            myViewHolder.catContainer.setOnClickListener(view -> {
                ((MainActivity) mContext).toggleArrow(true);
                Intent intent = new Intent(mContext, CatDetailActivity.class);
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                (Activity) mContext,
                                Pair.create((View) myViewHolder.cat_IV, transitionName)
                        );
                intent.putExtra("transition", transitionName);
                intent.putExtra("url", catPostEntity.getImageUrl());
                intent.putExtra("serverId", catPostEntity.getServerId());
                ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
            });
        }
    }

    public void addItems(Collection<CatPostEntity> catPostCollection) {
        mCatPosts.addAll(catPostCollection);
        notifyDataSetChanged();
    }
    public void showPopupWindow(View v) {
        PopupWindow popupWindow = new PopupWindow(mContext);

        LayoutInflater inflater = (LayoutInflater)   mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.menu_card, null);


        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.showAsDropDown(v);
    }

    public void showPopup(View v) {
        Context wrapper = new ContextThemeWrapper(mContext, R.style.PopupMenuStyle);
        PopupMenu popupMenu = new PopupMenu(wrapper, v);
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Report abuse");
        popupMenu.getMenu().add(Menu.NONE, 2, Menu.NONE, "Copy image URL");
        popupMenu.show();
    }


    @Override
    public int getItemCount() {
        return mCatPosts.size();
    }

    // TODO extend header abstract class

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
        @InjectView(R.id.total_comments_count_TV) TextView total_comments_count;
        @InjectView(R.id.votes_B) Button vote_B;
        @InjectView(R.id.share_B) View share_B;
        @InjectView(R.id.options_B) ImageButton options_B;

        public CatsCardViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }
}
