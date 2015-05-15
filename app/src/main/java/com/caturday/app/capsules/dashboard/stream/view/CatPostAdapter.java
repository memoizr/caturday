package com.caturday.app.capsules.dashboard.stream.view;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.caturday.app.capsules.common.view.HeaderAdapter;
import com.caturday.app.capsules.main.view.MainActivity;
import com.caturday.app.R;
import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.user.UserEntity;
import com.caturday.app.util.helper.ShareHelper;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CatPostAdapter extends HeaderAdapter<RecyclerView.ViewHolder> {
    private final CatStreamPresenter presenter;
    private Context context;
    private List<CatPostEntity> mCatPosts = new ArrayList<>();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public CatPostAdapter(Context context, CatStreamPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    public void setItems(List<CatPostEntity> catPostEntities) {
        this.mCatPosts = catPostEntities;
        notifyDataSetChanged();
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

    void updateItem(int position, CatPostEntity catPostEntity) {
        mCatPosts.set(position, catPostEntity);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        int index = position - 1;
        if (viewHolder instanceof CatsCardViewHolder) {
            final CatsCardViewHolder myViewHolder = (CatsCardViewHolder) viewHolder;

            final CatPostEntity catPostEntity = mCatPosts.get(index);

            ((CatsCardViewHolder) viewHolder).caption_TV.setText(catPostEntity.getCaption());

            String commentsNumber = Integer.toString(catPostEntity.getComments().size());
            ((CatsCardViewHolder) viewHolder).total_comments_count.setText(commentsNumber);
            ((CatsCardViewHolder) viewHolder).total_comments_count.setOnClickListener(view ->
                presenter.openDetails(index, myViewHolder.cat_IV, catPostEntity, true)
            );
            String dtStart = catPostEntity.getCreatedAt();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            try {
                Date date = format.parse(dtStart);
                String longAgo = new PrettyTime().format(date);

                ((CatsCardViewHolder) viewHolder).date_TV.setText(longAgo);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            int votesCount = catPostEntity.getVotesCount();
            String votesNumber = votesCount > 0 ? Integer.toString(votesCount) : "";
            myViewHolder.vote_B.setText(votesNumber);
            myViewHolder.vote_B.setOnClickListener(view ->
                            presenter.plusOneClicked(catPostEntity.getServerId(), index)
            );

            Glide.with(context)
                    .load(catPostEntity.getImageUrl())
                    .centerCrop()
                    .into(myViewHolder.cat_IV);

            UserEntity user = catPostEntity.getUser();
            myViewHolder.username_TV.setText(user.getUsername());
            Glide.with(context).load(user.getImageUrl()).into(myViewHolder.user_image_IV);

            myViewHolder.options_B.setOnClickListener(this::showPopup);

            myViewHolder.share_B.setOnClickListener(view -> {
                ShareHelper.shareLinkAction("Check out this cat!",
                        mCatPosts.get(index).getImageUrl(),
                        context);
            });

            myViewHolder.catContainer.setOnClickListener(view -> {
                ((MainActivity) context).toggleArrow(true);
                presenter.openDetails(index, myViewHolder.cat_IV, catPostEntity, false);
            });
        }
    }

    public void addItems(Collection<CatPostEntity> catPostCollection) {
        mCatPosts.addAll(catPostCollection);
        notifyDataSetChanged();
    }

    public void showPopup(View v) {
        Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenuStyle);
        PopupMenu popupMenu = new PopupMenu(wrapper, v);
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Report abuse");
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return mCatPosts.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    public void addItem(CatPostEntity catPostEntity) {
        List<CatPostEntity> list = new ArrayList<>();
        list.add(catPostEntity);
        list.addAll(mCatPosts);
        mCatPosts = list;
        notifyDataSetChanged();
    }

    class CatsCardViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.cardCat_IV) ImageView cat_IV;
        @InjectView(R.id.catContainer) View catContainer;
        @InjectView(R.id.caption_TV) TextView caption_TV;
        @InjectView(R.id.total_comments_count_TV) TextView total_comments_count;
        @InjectView(R.id.votes_B) Button vote_B;
        @InjectView(R.id.share_B) View share_B;
        @InjectView(R.id.options_B) ImageButton options_B;
        @InjectView(R.id.username_TV) TextView username_TV;
        @InjectView(R.id.date_TV) TextView date_TV;
        @InjectView(R.id.user_image_IV) ImageView user_image_IV;

        public CatsCardViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }
}
