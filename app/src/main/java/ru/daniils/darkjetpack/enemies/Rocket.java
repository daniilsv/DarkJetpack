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

public class Rocket extends Enemy {
    private Bitmap btmp;

    public Rocket(RectF _r, V2 position, V2 velocity) {
        super(_r, position, velocity);
        deflectBitmap = Core.getAssetBitmap("rocketDeflect.png");
    }

    @Override
    public void intersects(Object obj) {
        if (obj instanceof Player) {
            ((Player) obj).ege -= 5 + new Random().nextInt(15);
            type = -1;
        }
    }

    @Override
    public void load() {
        int ii = 1 + new Random().nextInt(3);
        btmp = Core.getAssetBitmap("BAD2/" + ii + ".png");
        /*if (mineBitmap == null)
            super.load();*/
    }

    @Override
    public void draw(Canvas c) {/*
        if (mineBitmap == null) {
            super.draw(c);
        }*/
        drawBitmap(c, new RectF(0, 0, width, height), btmp, null);
    }
}
