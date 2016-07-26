package ru.daniils.darkjetpack.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.V2;
import ru.daniils.darkjetpack.graphics.Object;

/**
 * Created by GICHA
 */
public abstract class Enemy extends Object {

    protected Bitmap deflectBitmap;
    private Bitmap failBitmap;

    protected Enemy(RectF _r, V2 _position, V2 _velocity) {
        super(_r, 3);
        failBitmap = Core.getAssetBitmap("fail.png");
        deflectBitmap = Core.getAssetBitmap("fail.png");
        position = _position;
        velocity = _velocity;
        type = 2;
        speed = new V2(1, 1);
    }

    public abstract void intersects(Object obj);

    @Override
    public void load() {
    }

    @Override
    public void tick() {
        super.tick();
        moveCenterTo(gs.wd2 - (gs.player.position.x - position.x) * 20, gs.hd2 + (gs.player.position.y - position.y) * 20);//TODO:Fix resolution problem
    }

    @Override
    public void draw(Canvas c) {
        drawBitmap(c, new RectF(0, 0, width, height), failBitmap, null);
    }

    @Override
    public void drawDeflect(Canvas c, RectF rect) {
        c.drawBitmap(deflectBitmap, new Rect(0, 0, deflectBitmap.getWidth(), deflectBitmap.getHeight()), rect, null);
    }

    @Override
    public boolean touch(TouchEvent e) {
        return false;
    }
}
