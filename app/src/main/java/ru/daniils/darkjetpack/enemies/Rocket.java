package ru.daniils.darkjetpack.enemies;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.V2;
import ru.daniils.darkjetpack.graphics.Object;
import ru.daniils.darkjetpack.objects.Enemy;

public class Rocket extends Enemy {
    private Bitmap mineBitmap;

    public Rocket(RectF _r, V2 position, V2 velocity) {
        super(_r, position, velocity);

    }

    @Override
    public void intersects(Object obj) {

        type = -1;
        obj.type = -1;
    }

    @Override
    public void load() {
        mineBitmap = Core.getAssetBitmap("mine.png");
        if (mineBitmap == null)
            super.load();
    }

    @Override
    public void draw(Canvas c) {
        if (mineBitmap == null) {
            super.draw(c);
        }
        drawBitmap(c, new RectF(0, 0, width, height), mineBitmap, null);
    }
}
