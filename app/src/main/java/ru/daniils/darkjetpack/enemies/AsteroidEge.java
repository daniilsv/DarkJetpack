package ru.daniils.darkjetpack.enemies;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.V2;
import ru.daniils.darkjetpack.graphics.Object;
import ru.daniils.darkjetpack.objects.Enemy;
import ru.daniils.darkjetpack.objects.Player;

public class AsteroidEge extends Enemy {
    private Bitmap btmp;

    public AsteroidEge(RectF _r, V2 position, V2 velocity) {
        super(_r, position, velocity);
        deflectBitmap = Core.getAssetBitmap("mineDeflect.png");
    }

    @Override
    public void intersects(Object obj) {
        if (obj instanceof Player) {
            ((Player) obj).ege -= 2 + new Random().nextInt(10);
            type = -1;
        }
    }

    @Override
    public void load() {
        int ii = 1 + new Random().nextInt(9);
        btmp = Core.getAssetBitmap("BAD/" + ii + ".png");
        /*if (mineBitmap == null)
            super.load();*/
    }

    @Override
    public void draw(Canvas c) {
       /*if (mineBitmap == null) {
            super.draw(c);
        }*/
        drawBitmap(c, new RectF(0, 0, width, height), btmp, null);
    }
}
