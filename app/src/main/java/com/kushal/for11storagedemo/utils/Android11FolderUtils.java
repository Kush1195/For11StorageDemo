package com.kushal.for11storagedemo.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.kushal.for11storagedemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Android11FolderUtils {

    public static void saveImageToGallery(Context context, Bitmap bitmap, String name) {

        OutputStream fos;
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                ContentResolver resolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + context.getString(R.string.app_name));
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);

                Toast.makeText(context, "Image Saved", Toast.LENGTH_SHORT).show();

            } else {

                saveBitmap(context, bitmap, name);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String saveBitmap(Context context, Bitmap bitmap, String str) {

        try {

            String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File file2 = new File(file, context.getString(R.string.app_name));
            if (!file2.exists()) {
                file2.mkdirs();
            }

            File file3 = new File(file, context.getString(R.string.app_name) + "/" + str);
            FileOutputStream fileOutputStream = new FileOutputStream(file3);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast(context, "Saved Successfully");
            String absolutePath = file3.getAbsolutePath();
            MediaScannerConnection.scanFile(context, new String[]{file3.getPath()}, new String[]{"image/jpeg"}, null);
            return absolutePath;

        } catch (Exception unused) {
            Toast(context, "Failed to Save");
            return null;
        }
    }

    public static void Toast(Context context, String str) {
        Toast(context, str, 0);
    }

    public static void Toast(Context context, String str, int i) {
        Toast.makeText(context, str, i).show();
    }

}
