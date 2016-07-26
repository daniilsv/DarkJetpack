package ru.daniils.darkjetpack.objects;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.V2;
import ru.daniils.darkjetpack.enemies.AsteroidEge;
import ru.daniils.darkjetpack.enemies.Rocket;
import ru.daniils.darkjetpack.graphics.Object;
import ru.daniils.darkjetpack.scenes.GameOverScene;

public class Player extends Object {
    public int ege = 310;
    private Bitmap playerBitmap;

    public Player(RectF _r, int zIndex) {
        super(_r, zIndex);
        type = 2;
        speed = new V2(2, 2);
    }

    @Override
    public void intersects(Object obj) {
        if (obj instanceof AsteroidEge || obj instanceof Rocket) {
            ege -= 2 + new Random().nextInt(7);
        } else if (obj instanceof IFMO) {
            Core.replaceScene(new GameOverScene(ege));
        } else
            type = -1;
        obj.type = -1;
    }

    @Override
    public void load() {
        playerBitmap = Core.getAssetBitmap("player.png");
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
        drawBitmap(c, new RectF(0, 0, width, height), playerBitmap, null);
    }

    @Override
    public boolean touch(TouchEvent e) {

        return false;
    }
}
