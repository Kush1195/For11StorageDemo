package com.kushal.for11storagedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kushal.for11storagedemo.utils.Android11FolderUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    Button btn_create;
    Bitmap bitmap;
    TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        checkPermissions();
        init();
    }

    private void init() {

        btn_create = findViewById(R.id.btn_create);
        tv_show = findViewById(R.id.tv_show);

        bitmap = drawableToBitmap(getResources().getDrawable(R.drawable.mypic));

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    long times = System.currentTimeMillis();
                    tv_show.setText(Environment.DIRECTORY_PICTURES + File.separator + getString(R.string.app_name) + "/" + "MyPic" + times + ".jpg");
                    Android11FolderUtils.saveImageToGallery(mContext, bitmap, "MyPic" + times + ".jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;

    }

    private void checkPermissions() {

        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT > 22) {

            requestPermissions(permissions, 101);

        } else {

            onSuccess();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 101) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {

                        finish();

                        return;
                    }
                }
                onSuccess();
            } else {
                finish();
            }
        }

    }

    public void onSuccess() {

        // Code for onSuccess

    }

}