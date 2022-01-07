package com.example.client.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.client.R;


public class ProcessElement extends RelativeLayout {
    private Context context;
    private TextView textView;
    private ImageView background;
    private int color;
    private Bitmap currBmp;
    private int textSizeSelected = 14;
    private int textSizeUnSelected = 14;
    public static final int STATUS_NEW = 1;
    public static final int STATUS_SELECTED = 2;
    public static final int STATUS_OLD = 3;

    public ProcessElement(Context context, CharSequence title) {
        super(context);
        this.init(context, title);
    }

    private void init(Context context, CharSequence title) {
        this.context = context;
        this.textView = new TextView(context);
        this.background = new ImageView(context);
        this.textView.setLayoutParams(new LayoutParams(-1, -1));
        this.textView.setGravity(17);
        this.textView.setText(title);
        this.textView.setTextColor(context.getResources().getColor(R.color.white));
        this.background.setLayoutParams(new LayoutParams(-1, -1));
        this.background.setScaleType(ScaleType.FIT_XY);
        this.addView(this.background);
        this.addView(this.textView);
        this.setGravity(17);
    }

    public void setSelectedColor(int color) {
        this.color = color;
    }

    public void setSelectedDrawable(Drawable drawable) {
        this.currBmp = this.drawable2Bitmap(drawable);
    }

    public void setTextSize(int textSizeSelected, int textSizeUnSelected) {
        this.textSizeSelected = textSizeSelected;
        this.textSizeUnSelected = textSizeUnSelected;
        this.textView.setTextSize(0, (float)textSizeUnSelected);
    }

    public void setStatus(int status) {
        if (status == 1) {
            this.background.setImageAlpha(0);
            this.background.setBackgroundColor(0);
            this.textView.setTextSize(0, (float)this.textSizeUnSelected);
        } else if (status == 2) {
            if (this.currBmp == null) {
                this.background.setImageResource(R.drawable.plan_arrow);
            } else {
                this.background.setImageBitmap(this.currBmp);
            }

            this.background.setImageAlpha(255);
            this.background.setBackgroundColor(0);
            this.textView.setTextSize(0, (float)this.textSizeSelected);
        } else if (status == 3) {
            this.background.setImageAlpha(0);
            this.background.setBackgroundColor(this.color);
            this.textView.setTextSize(0, (float)this.textSizeUnSelected);
        }

    }

    Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }
}
