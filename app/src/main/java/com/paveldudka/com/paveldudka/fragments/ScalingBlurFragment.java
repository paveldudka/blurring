package com.paveldudka.com.paveldudka.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paveldudka.R;

/**
 * Created by paveldudka on 3/4/14.
 */
public class ScalingBlurFragment extends Fragment {
    private ImageView image;
    private TextView text;
    private CheckBox upScaleFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        image = (ImageView) view.findViewById(R.id.picture);
        text = (TextView) view.findViewById(R.id.text);
        image.setImageResource(R.drawable.picture);
        applyBlur();
        addCheckBox((ViewGroup)view.findViewById(R.id.controls));
        return view;
    }

    private void applyBlur() {
        image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                image.getViewTreeObserver().removeOnPreDrawListener(this);
                image.buildDrawingCache();

                Bitmap bmp = image.getDrawingCache();
                blur(bmp, text);
                return true;
            }
        });
    }

    private void blur(Bitmap bkg, View view) {
        float scaleFactor = 16;

        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        if (upScaleFilter.isChecked()) {
            paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        }
        canvas.drawBitmap(bkg, 0, 0, paint);
        view.setBackground(new BitmapDrawable(
                getResources(), overlay));

    }

    @Override
    public String toString() {
        return "Scaling";
    }

    private void addCheckBox(ViewGroup container) {

        upScaleFilter = new CheckBox(getActivity());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        upScaleFilter.setLayoutParams(lp);
        upScaleFilter.setText("Filter up scale");
        upScaleFilter.setVisibility(View.VISIBLE);
        upScaleFilter.setBackgroundColor(0x7f000000);
        upScaleFilter.setTextColor(0xFFFFFFFF);
        upScaleFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                applyBlur();
            }
        });


        container.addView(upScaleFilter);
    }
}
