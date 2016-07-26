package ru.daniils.darkjetpack.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.TouchEvent;


public class GameView extends View {

    public Scene curScene;
    private Handler h;
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    public GameView(Context context) {
        super(context);
        h = new Handler();
    }

    @Override
    protected void onDraw(Canvas c) {
        if (c != null && curScene != null)
            curScene.onDraw(c);
        if (Core.working)
            h.postDelayed(r, Core.MPF);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (curScene == null || !curScene.loaded || curScene.fadeLayer.state != 255)
            return false;
        curScene.onTouchEvent(new TouchEvent(event));
        return true;
    }
}
