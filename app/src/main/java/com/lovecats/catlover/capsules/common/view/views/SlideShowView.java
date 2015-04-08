package com.lovecats.catlover.capsules.common.view.views;

import android.animation.Animator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lovecats.catlover.R;
import com.lovecats.catlover.util.interpolators.HyperTanAccelerateInterpolator;
import com.lovecats.catlover.util.interpolators.HyperTanDecelerateInterpolator;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Getter;

public class SlideShowView extends FrameLayout {
    @InjectView(R.id.slide_show_image_view) ImageView imageView;
    @InjectView(R.id.reveal_V) View reveal;
    @InjectView(R.id.slide_show_view_pager) ViewPager slide_show_view_pager;

    private Context context;

    private AnimationSet zoomIn;
    private ScaleAnimation mScale;

    public SlideShowView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.v_slide_show, this, true);

        String[] urls = {
        "http://24.media.tumblr.com/tumblr_lokav7QGyk1qij6yko1_500.jpg",
        "http://25.media.tumblr.com/tumblr_lm5pnr3Ema1qij6yko1_1280.jpg",
        "http://28.media.tumblr.com/tumblr_lyen4oNDur1qgn88ho1_1280.jpg",
        "http://24.media.tumblr.com/tumblr_lo704ppQDt1qagn8eo1_1280.jpg",
        "http://26.media.tumblr.com/tumblr_lydgh7MEEH1r286rvo1_1280.jpg"};

        this.context = context;

        ButterKnife.inject(this);

        SlideShowPagerAdapter adapter = new SlideShowPagerAdapter();
        adapter.setUrls(Arrays.asList(urls));
        slide_show_view_pager.setAdapter(adapter);
        cycleViewPager();
    }

    private void setRandomImage() {
//        if (CatPostEntity.getCount() > 0) {
//            String url = CatPostEntity.getRandomCatPost().getImage_url();
//            Picasso.with(context).load(url).into(slide_0);
//        }
    }

    private void cycleViewPager() {
//        int viewPagerItems = slide_show_view_pager.getAdapter().getCount();

        slide_show_view_pager.postDelayed(() -> {
            slide_show_view_pager.setCurrentItem(cycleInts(), true);
        }, 2000);
        slide_show_view_pager.postDelayed(() -> {
            slide_show_view_pager.setCurrentItem(cycleInts(), true);
        }, 2000);
        slide_show_view_pager.postDelayed(() -> {
            slide_show_view_pager.setCurrentItem(cycleInts(), true);
        }, 2000);
        slide_show_view_pager.postDelayed(() -> {
            slide_show_view_pager.setCurrentItem(cycleInts(), true);
        }, 2000);
    }

    int foo = 0;

    private int cycleInts() {
//        int viewPagerItems = slide_show_view_pager.getAdapter().getCount();
        return foo++;
    }

//    private void animateBackgroundImage(ImageView view) {
//        zoomIn = new AnimationSet(true);
//        view.setPivotY(1);
//        view.setPivotX(0);
//        float rand = (float) (Math.random() / 1.3) + 1;
//        float randAfter = (float) (Math.random() / 1.3) + 1;
//
//        mScale = new ScaleAnimation(rand, randAfter, rand, randAfter,
//                ScaleAnimation.RELATIVE_TO_PARENT, (float) Math.random(), ScaleAnimation.RELATIVE_TO_PARENT,
//                (float) Math.random());
//        mScale.setDuration(20000);
//        zoomIn.addAnimation(mScale);
//        zoomIn.setFillBefore(true);
//        zoomIn.setFillAfter(true);
//        zoomIn.setFillEnabled(true);
//
//        mScale.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                cycleBackgroundImage();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//
//        view.startAnimation(zoomIn);
//    }

    private void cycleBackgroundImage() {
//        setRandomImage();
//        animateBackgroundImage(slide_0);
    }

    public void animationStart() {
//        slide_0.startAnimation(zoomIn);
    }

    public void animationPause() {
//        slide_0.clearAnimation();
    }

    public void animationResume() {
    }

    public void flash() {
        reveal();
        fade();
    }

    private void reveal() {
        // previously invisible view

        // get the center for the clipping circle
        int cx = reveal.getWidth() / 2;
        int cy = reveal.getTop() + 96;

        // get the final radius for the clipping circle
        int finalRadius = (int) Math.sqrt(Math.pow(reveal.getWidth(), 2) + Math.pow(reveal.getHeight(), 2));

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(reveal, cx, cy, 0, finalRadius);
        anim.setDuration(400);
        anim.setInterpolator(new HyperTanAccelerateInterpolator());

        // make the view visible and start the animation
        reveal.setVisibility(View.VISIBLE);
        anim.start();
    }

    private void fade() {
        reveal.animate()
                .alpha(0f).setStartDelay(400).setDuration(600)
                .setInterpolator(new HyperTanDecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        reveal.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                })
                .start();
    }

    class SlideShowPagerAdapter extends PagerAdapter {

        @Getter private List<String> urls;
        private View view_0;
        private View view_1;


        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("This is it! " + position);
            ViewPager pager = (ViewPager) container;
            view_0 = findViewById(R.id.slide_show_page_0);
            view_1 = findViewById(R.id.slide_show_page_0);
            ImageView imageView = (ImageView) pager.findViewById(R.id.slide_show_image_view);

            Picasso.with(context).load(urls.get(position)).into(imageView);

            if ((position % 2) == 0) {
                pager.addView(view_0);
            return view_0;
            }else{
                pager.addView(view_1);
                return view_1;
            }
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((LinearLayout) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        void setUrls(List<String> urls) {
            this.urls = urls;
            notifyDataSetChanged();
        }
    }
}
