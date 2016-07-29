package ru.daniils.darkjetpack.objects;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.V2;
import ru.daniils.darkjetpack.enemies.AsteroidEge;
import ru.daniils.darkjetpack.enemies.Rocket;
import ru.daniils.darkjetpack.graphics.Object;

public class BL extends Object {
    private Bitmap ifmoBitmap;

    public BL(RectF _r, V2 pos) {
        super(_r, 10);
        type = 3;
        speed = new V2(0, 0);
        position = pos;
    }

    @Override
    public void intersects(Object obj) {
        if (obj instanceof AsteroidEge || obj instanceof Rocket) {
            obj.type = -1;
        }
    }

    @Override
    public void load() {
        ifmoBitmap = Core.getAssetBitmap("bl.png");
    }

    @Override
    public void tick() {
        super.tick();
        moveCenterTo(gs.wd2 - (gs.player.position.x - position.x) * 20, gs.hd2 + (gs.player.position.y - position.y) * 20);//TODO:Fix resolution problem

    }

    @Override
    public void drawDeflect(Canvas c, RectF rect) {

    }


    @Override
    public void draw(Canvas c) {
        drawBitmap(c, new RectF(0, 0, width, height), ifmoBitmap, null);
    }

    @Override
    public boolean touch(TouchEvent e) {

        return false;
    }
}
