package com.paveldudka.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.paveldudka.R;
import com.paveldudka.util.FastBlur;

/**
 * Created by paveldudka on 3/4/14.
 */
public class ScalingBlurFragment extends Fragment {
    private final String UPSCALE_FILTER = "upscale_filter";
    private final String FAST_BLUR = "fast_blur";

    private ImageView image;
    private TextView text;
    private CheckBox upScaleFilter;
    private CheckBox fastBlur;
    private TextView statusText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        image = (ImageView) view.findViewById(R.id.picture);
        text = (TextView) view.findViewById(R.id.text);
        image.setImageResource(R.drawable.picture);
        statusText = addStatusText((ViewGroup) view.findViewById(R.id.controls));
        addCheckBoxes((ViewGroup) view.findViewById(R.id.controls));

        if (savedInstanceState != null) {
            upScaleFilter.setChecked(savedInstanceState.getBoolean(UPSCALE_FILTER));
            fastBlur.setChecked(savedInstanceState.getBoolean(FAST_BLUR));
        }
        applyBlur();
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
        long startMs = System.currentTimeMillis();
        float scaleFactor = 14;

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

        if (fastBlur.isChecked()) {
            overlay = FastBlur.doBlur(overlay, 1, true);
        }
        view.setBackground(new BitmapDrawable(
                getResources(), overlay));
        statusText.setText(System.currentTimeMillis() - startMs + "ms");

    }

    @Override
    public String toString() {
        return "Scaling";
    }

    private void addCheckBoxes(ViewGroup container) {

        upScaleFilter = new CheckBox(getActivity());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        upScaleFilter.setLayoutParams(lp);
        upScaleFilter.setText("Filter up scale");
        upScaleFilter.setVisibility(View.VISIBLE);
        upScaleFilter.setTextColor(0xFFFFFFFF);
        upScaleFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                applyBlur();
            }
        });
        container.addView(upScaleFilter);

        fastBlur = new CheckBox(getActivity());
        lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        fastBlur.setLayoutParams(lp);
        fastBlur.setText("Apply fastblur to downscaled image");
        fastBlur.setVisibility(View.VISIBLE);
        fastBlur.setTextColor(0xFFFFFFFF);
        fastBlur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                applyBlur();
            }
        });
        container.addView(fastBlur);
    }

    private TextView addStatusText(ViewGroup container) {
        TextView result = new TextView(getActivity());
        result.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        result.setTextColor(0xFFFFFFFF);
        container.addView(result);
        return result;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(UPSCALE_FILTER, upScaleFilter.isChecked());
        outState.putBoolean(FAST_BLUR, fastBlur.isChecked());
        super.onSaveInstanceState(outState);
    }
}
