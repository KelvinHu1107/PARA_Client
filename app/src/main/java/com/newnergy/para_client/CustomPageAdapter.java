package com.newnergy.para_client;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Kelvin on 2016/6/13.
 */
public class CustomPageAdapter extends PagerAdapter {


    Context ctx;
    private LayoutInflater layoutInflater;
    private TextView textView;
    private String[]imageUrls;
    private String getImageUrl, lastPage;

    private ImageView imageView;
    private Animator mCurrentAnimator;
    private int mLongAnimationDuration;


    public CustomPageAdapter(Context ctx, String getImageUrl, String lastPage) {
        this.ctx = ctx;
        this.getImageUrl = getImageUrl;
        this.lastPage = lastPage;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return ValueMessagerFurtherInfo.ServicePhotoUrl.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    public void getImageData(String profilePhotoUrl, final ImageView imageView) {

        Picasso.with(ctx).load(getImageUrl + profilePhotoUrl).into(imageView);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.client_furtherinfo_sublayout,container,false);

        imageView = (ImageView) item_view.findViewById(R.id.imageView_furtherInfo_imageViewer);
        //pinchZoomImageView = (PinchZoomImageView) item_view.findViewById(R.id.imageView_furtherInfo_imageViewer);
        textView = (TextView) item_view.findViewById(R.id.textView_furtherInfo_pagerView_counter);
        //imageView.setImageResource(image_resources[position]);
        //pinchZoomImageView.setImageResource(image_resources[position]);
        textView.setText((position+1)+" of "+ ValueMessagerFurtherInfo.ServicePhotoUrl.length);
        getImageData(ValueMessagerFurtherInfo.ServicePhotoUrl[position],imageView);
        mLongAnimationDuration = item_view.getResources().getInteger(android.R.integer.config_longAnimTime);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ValueMessager.lastPageViewPic = lastPage;
                ValueMessager.bitmapUrlBuffer = getImageUrl + ValueMessagerFurtherInfo.ServicePhotoUrl[position];

                Intent nextPage_Display = new Intent(ctx, Client_FurtherInfo_PictureDisplay.class);
                ctx.startActivity(nextPage_Display);
            }
        });

        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout)object);

//        super.destroyItem(container, position, object);
    }

    private void zoomImageFromThumb() {
        if(mCurrentAnimator != null)
            mCurrentAnimator.cancel();

        //load image res---empty

        Rect startBounds = new Rect();
        Rect finalBounds = new Rect();
        Point globalOffset = new Point();
        imageView.getGlobalVisibleRect(startBounds);

        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if((float) finalBounds.width() / finalBounds.height() >
                (float) startBounds.width() / startBounds.height()){
            startScale = (float) startBounds.height() / finalBounds.height();

            float startWidth = (float) startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = (float) startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= startHeight;
            startBounds.bottom += startHeight;
        }

        imageView.setVisibility(View.VISIBLE);

        imageView.setPivotX(0f);
        imageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(imageView, View.X, startBounds.left, finalBounds.left ))
                .with(ObjectAnimator.ofFloat(imageView, View.Y, startBounds.top, finalBounds.top ))
                .with(ObjectAnimator.ofFloat(imageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(imageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mLongAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        set.start();
        mCurrentAnimator = set;
    }

}
