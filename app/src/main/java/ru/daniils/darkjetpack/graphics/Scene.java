package ru.daniils.darkjetpack.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.layers.Fade;

import static java.util.Collections.sort;


public abstract class Scene {
    /* 0 = invisible
    * 255 = visible*/
    public boolean loaded = false;
    public float width, height;
    public float wd2, hd2;
    public float wd3, hd3;
    public float minWH;
    public Paint p;
    public Fade fadeLayer;
    protected ArrayList<Layer> layers;
    private GameView gv;
    private int backgroundColor;

    public Scene() {
        gv = Core.mainThread.gameView;
        wd2 = (width = gv.getWidth()) / 2;
        hd2 = (height = gv.getHeight()) / 2;
        wd3 = width / 3;
        hd3 = height / 3;
        minWH = Math.min(width, height);
        backgroundColor = Core.backgroundColor;
        layers = new ArrayList<>();
        p = new Paint();
        p.setFlags(Paint.ANTI_ALIAS_FLAG);
        fadeLayer = new Fade(new RectF(0, 0, width, height), 100, Core.fadeInColor, Core.fadeOutColor);
        addLayer(fadeLayer);
    }

    protected Layer addLayer(Layer layer) {
        layers.add(layer);
        return layer;
    }

    public abstract void load();

    public void onLoad() {
        load();
        if (layers.size() != 0) {
            sort(layers);
            for (Layer l : layers)
                l.onLoad();
        }
        loaded = true;
    }

    public abstract void tick();

    public void onTick() {
        tick();
        if (layers.size() != 0)
            for (Layer l : layers)
                l.onTick();
    }

    public abstract void render(Canvas c);

    public void background(Canvas c) {
        p.setColor(backgroundColor);
        c.drawRect(0, 0, width, height, p);
    }

    void onDraw(Canvas c) {
//Draw background
        background(c);
//Draw main canvas
        render(c);
//Draw layers
        if (layers.size() != 0)
            for (Layer l : layers)
                l.onDraw(c);
    }

    public abstract void touch(TouchEvent e);

    void onTouchEvent(TouchEvent e) {
        if (layers.size() != 0)
            for (Layer l : layers) {
                if (l.wholeScreenTouch || l.r.contains(e.point.x, e.point.y))
                    if (l.onTouch(e)) return;
            }
        touch(e);
    }

    public void onBackPressed() {

    }
}
