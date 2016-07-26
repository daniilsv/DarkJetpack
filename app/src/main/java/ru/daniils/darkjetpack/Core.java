package ru.daniils.darkjetpack;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import ru.daniils.darkjetpack.graphics.GameView;
import ru.daniils.darkjetpack.graphics.Scene;

public class Core {
    public static MainThread mainThread = null;
    public static int MPF = 16;
    public static int MPT = 16;
    public static int fadeInColor, fadeOutColor, backgroundColor;
    public static boolean working;
    public static boolean debug = false;
    public static Activity activity = null;
    private static Context context = null;
    private static Core instance = null;
    private static HashMap<String, Bitmap> pictures;

    private Core() {
        fadeInColor = Color.parseColor("#4E729A");
        fadeOutColor = Color.parseColor("#4E729A");
        backgroundColor = Color.parseColor("#4E729A");
        pictures = new HashMap<>();
        working = true;
    }

    static Core getInstance() {
        if (instance == null)
            instance = new Core();
        return instance;
    }

    public static Bitmap getAssetBitmap(String path) {
        try {
            if (pictures.containsKey(path))
                return pictures.get(path);
            InputStream ims = Core.activity.getAssets().open(path);
            Bitmap btm = BitmapFactory.decodeStream(ims);
            if (btm == null)
                return getAssetBitmap("fail.png");
            pictures.put(path, btm);
            return btm;
        } catch (IOException ex) {
            return null;
        }
    }

    public static void replaceScene(Scene s) {
        mainThread.replaceScene(s);
    }

    void setContext(Activity _activity) {
        activity = _activity;
        context = _activity;
    }

    void start(GameView mv) {
        mainThread = new MainThread(mv);
        mainThread.start();
    }
}
