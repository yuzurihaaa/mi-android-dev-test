package com.miandroidchallenge.ucoppp.miandroidchallenge.util;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

    public static String getImagePath(Application app, String finalDirectory) {
        return ContextCompat.getNoBackupFilesDir(app).getAbsolutePath() + File.separator + finalDirectory;
    }

    public static void saveFile(Application application, Bitmap bitmap, String imagename) {
        final String imageFilePath = getImagePath(application,
                imagename
        );
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(imageFilePath);
            Boolean success = bitmap.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    fileOutputStream
            );

            if (success) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {

                } finally {
                    fileOutputStream = null;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Bitmap getImage(String imagePath) {
        if (TextUtils.isEmpty(imagePath))
            return null;

        File file = new File(imagePath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ignored) {
                }

            }
        }
        return null;
    }
}
