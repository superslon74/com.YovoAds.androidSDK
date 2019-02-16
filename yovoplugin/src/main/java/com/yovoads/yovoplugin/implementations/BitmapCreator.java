package com.yovoads.yovoplugin.implementations;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.yovoads.yovoplugin.YovoSDK;

import java.util.HashMap;
import java.util.LinkedList;

public class BitmapCreator {
    
    static HashMap<String, LinkedList<Bitmap>> map = new HashMap<>(10);

    static public Bitmap createBitmap(int width, int height) {
        try {
            synchronized (map) {
                String key = width + "_" + height;
                LinkedList<Bitmap> list = map.get(key);
                if (list == null) {
                    return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
                } else if (list.isEmpty()) {
                    return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
                } else {
                    Bitmap bitmap = list.pollFirst();
                    if (bitmap == null) {
                        YovoSDK.ShowLog("YOVO_BitmapCreator", "pool has null bitmap");
                    }
//                    Canvas canvas = new Canvas(bitmap);
//                    canvas.drawColor(Color.alpha(Color.TRANSPARENT));
                    makeTransparent(bitmap);
                    return bitmap;
                }
            }
        } catch (OutOfMemoryError e) {
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        }
    }

    static public void putBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        synchronized (map) {
            String key = bitmap.getWidth() + "_" + bitmap.getHeight();
            LinkedList<Bitmap> list = map.get(key);
            if (list == null) {
                list = new LinkedList<>();
            }
            list.push(bitmap);
            map.put(key, list);
        }
    }

    public static Bitmap makeTransparent(Bitmap myBitmap) {
        int width =  myBitmap.getWidth();
        int height = myBitmap.getHeight();
        int [] allpixels = new int [ myBitmap.getHeight()*myBitmap.getWidth()];
        //myBitmap.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(),myBitmap.getHeight());
        myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

        for(int i =0; i<myBitmap.getHeight()*myBitmap.getWidth();i++){
            allpixels[i] = Color.alpha(Color.TRANSPARENT);
        }

        myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        return myBitmap;
    }
}
