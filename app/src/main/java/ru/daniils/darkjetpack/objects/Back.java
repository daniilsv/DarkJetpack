package ru.daniils.darkjetpack.objects;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.V2;
import ru.daniils.darkjetpack.graphics.Object;

public class Back extends Object {
    private Bitmap backbtmp;

    public Back(RectF _r, V2 pos) {
        super(_r, 1);
        type = 3;
        speed = new V2(0, -1);
        velocity = new V2(0, 0.15);
        position = pos;
    }

    @Override
    public void intersects(Object obj) {

    }

    @Override
    public void load() {
        backbtmp = Core.getAssetBitmap("back.png");
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void drawDeflect(Canvas c, RectF rect) {

    }


    @Override
    public void draw(Canvas c) {
        drawBitmap(c, new RectF(0, 0, width, height), backbtmp, null);
    }

    @Override
    public boolean touch(TouchEvent e) {

        return false;
    }
}
