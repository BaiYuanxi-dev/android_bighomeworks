package com.example.client.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;
import com.example.client.R;


public class ProcessBar extends LinearLayout {
    private Context context;
    private CharSequence[] titles;
    private ViewPager mViewPager;
    private OnPageChangeListener mPageChangeListener;
    private int color;
    private Drawable curr;
    private Drawable background;
    private int textSizeSelected;
    private int textSizeUnSelected;
    private List<ProcessElement> processElementList = new ArrayList();

    public ProcessBar(Context context) {
        super(context);
    }

    public ProcessBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public ProcessBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    @TargetApi(21)
    public ProcessBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ProcessBar);
        this.initAttributes(typedArray);
        typedArray.recycle();
        this.setContent();
    }

    private void initAttributes(TypedArray typedArray) {
        this.textSizeSelected = this.context.getResources().getDimensionPixelSize(R.dimen.text_size_selected);
        this.textSizeUnSelected = this.context.getResources().getDimensionPixelSize(R.dimen.text_size_unselected);
        this.color = this.context.getResources().getColor(R.color.processColor);
        this.titles = typedArray.getTextArray(R.styleable.ProcessBar_processbar_titles);
        this.color = typedArray.getColor(R.styleable.ProcessBar_processbar_selected_color, this.color);
        this.curr = typedArray.getDrawable(R.styleable.ProcessBar_processbar_current_drawable);
        this.background = typedArray.getDrawable(R.styleable.ProcessBar_processbar_background);
        this.textSizeSelected = typedArray.getDimensionPixelSize(R.styleable.ProcessBar_processbar_selected_text_size, this.textSizeSelected);
        this.textSizeUnSelected = typedArray.getDimensionPixelSize(R.styleable.ProcessBar_processbar_unselected_text_size, this.textSizeUnSelected);
        if (this.titles == null) {
            this.titles = this.context.getResources().getTextArray(R.array.process_bar_elements);
        }

        if (this.curr == null) {
            this.curr = this.context.getResources().getDrawable(R.drawable.plan_arrow);
        }

        if (this.background == null) {
            this.background = this.context.getResources().getDrawable(R.drawable.shape_plan_tab_background);
        }

    }

    private void setContent() {
        this.setOrientation(LinearLayout.HORIZONTAL);

        for(int i = 0; i < this.titles.length; ++i) {
            ProcessElement processElement = new ProcessElement(this.context, this.titles[i]);
            processElement.setSelectedColor(this.color);
            processElement.setSelectedDrawable(this.curr);
            processElement.setTextSize(this.textSizeSelected, this.textSizeUnSelected);
            processElement.setLayoutParams(new LayoutParams(-1, -1, 1.0F));
            this.addView(processElement);
            this.processElementList.add(processElement);
        }

        this.setBackground(this.background);
        ((ProcessElement)this.processElementList.get(0)).setStatus(2);
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        if (this.mViewPager != null && this.mPageChangeListener != null) {
            this.mViewPager.removeOnPageChangeListener(this.mPageChangeListener);
        }

        if (viewPager != null) {
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter == null) {
                throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
            }

            this.mViewPager = viewPager;
            if (this.mPageChangeListener == null) {
                this.mPageChangeListener = new OnPageChangeListener() {
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    public void onPageSelected(int position) {
                        if (ProcessBar.this.processElementList.size() > position) {
                            ((ProcessElement) ProcessBar.this.processElementList.get(position)).setStatus(2);
                            if (position > 0) {
                                ((ProcessElement) ProcessBar.this.processElementList.get(position - 1)).setStatus(3);
                            }

                            if (position < ProcessBar.this.processElementList.size() - 1) {
                                ((ProcessElement) ProcessBar.this.processElementList.get(position + 1)).setStatus(1);
                            }
                        }

                    }

                    public void onPageScrollStateChanged(int state) {
                    }
                };
            }

            viewPager.addOnPageChangeListener(this.mPageChangeListener);
        } else {
            this.mViewPager = null;
        }

    }

    public void setPosition(int position) {
        if (this.processElementList.size() > position && position >= 0) {
            ((ProcessElement)this.processElementList.get(position)).setStatus(2);
            int i;
            if (position > 0) {
                for(i = 0; i < position; ++i) {
                    ((ProcessElement)this.processElementList.get(i)).setStatus(3);
                }
            }

            if (position < this.processElementList.size() - 1) {
                for(i = position + 1; i < this.processElementList.size(); ++i) {
                    ((ProcessElement)this.processElementList.get(i)).setStatus(1);
                }
            }
        }

    }
}
