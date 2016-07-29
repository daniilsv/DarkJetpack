package ru.daniils.darkjetpack.enemies;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.V2;
import ru.daniils.darkjetpack.graphics.Object;
import ru.daniils.darkjetpack.objects.Enemy;

public class Mine extends Enemy {

    public Mine(RectF _r, V2 position, V2 velocity) {
        super(_r, position, velocity);
        deflectBitmap = Core.getAssetBitmap("mineDeflect.png");
    }

    @Override
    public void intersects(Object obj) {

        type = -1;
        obj.type = -1;
    }

    @Override
    public void load() {
        for (int i = 1; i <= 10; i++)
            Core.getAssetBitmap("BAD/" + i + ".png");
        /*if (mineBitmap == null)
            super.load();*/
    }

    @Override
    public void draw(Canvas c) {
       /*if (mineBitmap == null) {
            super.draw(c);
        }*/
        drawBitmap(c, new RectF(0, 0, width, height), Core.getAssetBitmap("BAD/" + new Random().nextInt(9) + 1 + ".png"), null);
    }
}
