package com.paveldudka;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.paveldudka.com.paveldudka.fragments.RSBlurFragment;
import com.paveldudka.com.paveldudka.fragments.ScalingBlurFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment1, Fragment.instantiate(this, RSBlurFragment.class.getName())).commit();
            getFragmentManager().beginTransaction().add(R.id.fragment2, Fragment.instantiate(this, ScalingBlurFragment.class.getName())).commit();
        }
    }


}
