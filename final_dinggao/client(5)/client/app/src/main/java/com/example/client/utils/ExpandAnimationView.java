package com.example.client.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

import com.example.client.R;
import com.example.client.activity.AudienceMainActivity;
import com.example.client.activity.NewGame1Activity;
import com.example.client.activity.NewGameActivity;

public class ExpandAnimationView extends LinearLayout {

    private ImageView mImageView;

    private final AnimatorSet mAnimatorSet = new AnimatorSet();

    private int mExpandWidth = 0;

    private final LinearLayout mExpandContainer = new LinearLayout(getContext());

    private final List<ExpandItem> mExpandItems = new ArrayList<>();

    private boolean mIsExpanding = false;

    private OnExpandViewClickListener mClickListener;

    public ExpandAnimationView(Context context) {
        this(context, null);
    }

    public ExpandAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mExpandItems.add(0,new ExpandItem(R.drawable.ic_next,"收起"));
        mExpandItems.add(1,new ExpandItem(R.drawable.ic_new,"新建赛事"));
        initViews();
    }

    private void initViews() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        //setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bg_expand_view, null));
        mImageView = new ImageView(getContext());
        addView(mImageView);
        mImageView.setLayoutParams(new LayoutParams(dip2px(25F), dip2px(25F)));
        LayoutParams p = (LayoutParams) mImageView.getLayoutParams();
        p.setMargins(dip2px(13F), dip2px(13F), dip2px(13F), dip2px(13F));
        mImageView.setLayoutParams(p);
        mImageView.setImageResource(R.drawable.ic_add);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageView.performClick();
            }
        });

        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });

        initExpandView();
    }

    private void initExpandView() {
        if (mExpandItems == null || mExpandItems.isEmpty()) return;

        addView(mExpandContainer, 0);
        mExpandContainer.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
        ViewGroup.LayoutParams lp = mExpandContainer.getLayoutParams();
        mExpandContainer.setGravity(Gravity.CENTER_VERTICAL);

        for (int i = 0; i < mExpandItems.size(); i++) {
            final TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextSize(13F);
            textView.setTextColor(Color.GRAY);
            textView.setText(mExpandItems.get(i).text);
            textView.setMaxLines(1);
            Drawable drawable = ResourcesCompat.getDrawable(getContext().getResources(), mExpandItems.get(i).resourceId, null);
            if (drawable != null) {
                drawable.setBounds(0, 0, dip2px(15F), dip2px(15F));
            }
            textView.setCompoundDrawables(drawable, null, null, null);
            textView.setCompoundDrawablePadding(dip2px(3F));

            mExpandContainer.addView(textView);
            int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override

                public void onClick(View view) {

                    if (mExpandItems.get(finalI).text=="收起") {
                        collapse();
                    }
                    if (mExpandItems.get(finalI).text=="新建赛事") {
                        //添加相应事件
                        Intent i = new Intent(getContext(), NewGame1Activity.class);
                        Bundle b = new Bundle();
                        b.putString("key", "manager");
                        getContext().startActivity(i, b);
                    }

                }
            });

            LayoutParams p = (LayoutParams) textView.getLayoutParams();
            p.setMargins(i == 0 ? dip2px(23F) : dip2px(13F), 0, dip2px(13F), 0);
            textView.setLayoutParams(p);
            final View divider = new View(getContext());
            divider.setBackgroundColor(Color.parseColor("#fafafa"));
            divider.setLayoutParams(new LayoutParams(dip2px(1F), dip2px(20F)));
            mExpandContainer.addView(divider);
            mExpandWidth += (textView.getTextSize() * mExpandItems.get(i).text.length()) + dip2px(15F + 20F + 1F + 3F);
        }

        mExpandWidth += dip2px(10F + 13F);

        mExpandContainer.setAlpha(0);
    }

    // 属性动画原理 与下面实现动画时 属性"expandWidth"相对应
    public void setExpandWidth(float width) {
        LayoutParams p = (LayoutParams) mExpandContainer.getLayoutParams();
        p.width = (int) width;
        mExpandContainer.setLayoutParams(p);
    }

    // 开始动画
    public void startAnimation() {
        if (mIsExpanding) {
            collapse();
        } else {
            expand();
        }
    }

    private void collapse() {
        if (!mIsExpanding) return;
        mIsExpanding = false;
        final ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(mImageView, "rotation", -45F, 0F);
        final ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mImageView, "alpha", 0.5F, 1F);
        final ObjectAnimator collapseAnim = ObjectAnimator.ofFloat(this, "expandWidth", mExpandWidth, 0F);
        final ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mExpandContainer, "alpha", 1F, 0F);
        mAnimatorSet.playTogether(rotateAnimation, alphaAnimation, collapseAnim, alphaAnim);
        mAnimatorSet.start();
        if (mClickListener != null) {
            mClickListener.onCollapse();
        }
    }

    private void expand() {
        if (mIsExpanding) return;
        mIsExpanding = true;
        final ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(mImageView, "rotation", 0F, -45F);
        final ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mImageView, "alpha", 1F, 0.5F);
        final ObjectAnimator expandAnim = ObjectAnimator.ofFloat(this, "expandWidth", 0F, mExpandWidth);
        final ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mExpandContainer, "alpha", 0F, 1F);
        mAnimatorSet.playTogether(rotateAnimation, alphaAnimation, expandAnim, alphaAnim);
        mAnimatorSet.start();
        if (mClickListener != null) {
            mClickListener.onExpand();
        }
    }

    /*public void onItemClick(String tag){
        if(tag=="收起"){
            mClickListener.onCollapse();
        }
        if(tag=="新建赛事"){

        }

    }*/
    private int dip2px(float floatVal) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (floatVal * density + 0.5F);
    }

    public void setOnExpandViewClickListener(OnExpandViewClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), dip2px(25F + 26F));
    }

    public void destroy() {
        mAnimatorSet.removeAllListeners();
        mAnimatorSet.cancel();
    }

    class ExpandItem {
        public int resourceId;

        public String text;
        public ExpandItem(int resourceId,String text){
            this.resourceId=resourceId;
            this.text=text;
        }

    }

    interface OnExpandViewClickListener {
        void onExpand();

        void onCollapse();


        void onItemClick(String tag);
    }

}