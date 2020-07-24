package com.example.wallpaper.Utils;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import com.example.wallpaper.R;

import java.io.IOException;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainer;

public class functions {
    public static void changeFragment(FragmentActivity activity, Fragment fragment)
    {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.myFrame,fragment).commit();
    }
    public static final String apiKey="";
    public static boolean setWallpaper(Activity activity, Bitmap bitmap){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        Bitmap tempBitmap =Bitmap.createScaledBitmap(bitmap, height, width,false);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(activity);
        try {
            wallpaperManager.setBitmap(bitmap);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
