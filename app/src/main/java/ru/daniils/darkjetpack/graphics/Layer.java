package ru.daniils.darkjetpack.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.ArrayList;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.Utils;
import ru.daniils.darkjetpack.V2;

import static java.util.Collections.sort;

public abstract class Layer implements Comparable<Layer> {
    public RectF r;
    public float wd2, hd2;
    public float wd3, hd3;
    public float minWH;
    protected boolean wholeScreenTouch = false;
    protected Paint p;
    protected float width, height;
    private ArrayList<Layer> layers;
    private int zIndex;

    protected Layer(RectF _r, int _zIndex) {
        r = _r;
        wd2 = (width = r.width()) / 2;
        hd2 = (height = r.height()) / 2;
        wd3 = width / 3;
        hd3 = height / 3;
        minWH = Math.min(width, height);
        zIndex = _zIndex;
        layers = new ArrayList<>();
        p = new Paint();
        p.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    protected Layer(Point tlPoint, int _width, int _height, int _zIndex) {
        r = new RectF(tlPoint.x, tlPoint.y, tlPoint.x + _width, tlPoint.y + _height);
        this.width = _width;
        this.height = _height;
        zIndex = _zIndex;
        layers = new ArrayList<>();
        p = new Paint();
        p.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    protected Layer addLayer(Layer layer) {
        layer.r.offset(r.left, r.top);
        layers.add(layer);
        return layer;
    }

    public abstract void load();

    public void onLoad() {
        load();
        if (layers.size() != 0) {
            sort(layers);
            for (Layer l : layers)
                l.load();
        }
    }

    public abstract void tick();

    public void onTick() {
        tick();
        if (layers.size() != 0)
            for (Layer l : layers)
                l.tick();
    }

    public abstract void draw(Canvas c);

    public void onDraw(Canvas c) {
        c.save(Canvas.MATRIX_SAVE_FLAG);
        if (Core.debug) {
            Paint pa = new Paint();
            pa.setColor(Color.MAGENTA);
            pa.setAlpha(255 - 200);
            drawRect(c, new RectF(0, 0, width, height), pa);
        }
        draw(c);
        c.restore();
        if (layers.size() != 0)
            for (Layer l : layers) {
                c.save(Canvas.MATRIX_SAVE_FLAG);
                l.onDraw(c);
                c.restore();
            }

    }

    public abstract boolean touch(TouchEvent e);

    boolean onTouch(TouchEvent e) {
        if (touch(e)) return true;
        if (layers.size() != 0)
            for (Layer l : layers)
                if (l.wholeScreenTouch || l.r.contains(e.point.x, e.point.y))
                    if (l.touch(e)) return true;
        return false;
    }

    public void moveCenterTo(Float x, Float y) {
        if (x == null) {
            Utils.setRectCenter(r, r.centerX(), y);
            if (layers.size() != 0)
                for (Layer l : layers)
                    Utils.setRectCenter(l.r, l.r.centerX(), y);
        } else if (y == null) {
            Utils.setRectCenter(r, x, r.centerY());
            if (layers.size() != 0)
                for (Layer l : layers)
                    Utils.setRectCenter(l.r, x, l.r.centerY());
        } else {
            Utils.setRectCenter(r, x, y);
            if (layers.size() != 0)
                for (Layer l : layers)
                    Utils.setRectCenter(l.r, x, y);
        }
    }

    @Override
    public int compareTo(Layer another) {
        return zIndex - another.zIndex;
    }

    protected void rotate(Canvas c, float degrees, float px, float py) {
        c.rotate(degrees, r.centerX() + px, r.centerY() + py);
    }

    protected void drawBitmap(Canvas c, RectF rect, Bitmap b, Paint p) {
        if (b == null)
            return;
        RectF tmpr = new RectF(rect);
        tmpr.offset(r.left, r.top);
        c.drawBitmap(b, null, tmpr, p);
    }

    protected void drawCircle(Canvas c, float x, float y, float rad, Paint p) {
        c.drawCircle(r.left + x, r.top + y, rad, p);
    }

    protected void drawRect(Canvas c, RectF rect, Paint p) {
        RectF tmpr = new RectF(rect);
        tmpr.offset(r.left, r.top);
        c.drawRect(tmpr, p);
    }

    protected void drawLine(Canvas c, V2 start, V2 end, Paint p) {
        start.offset(r.left, r.top);
        end.offset(r.left, r.top);
        c.drawLine(start.x, start.y, end.x, end.y, p);
    }

    protected void drawText(Canvas c, String text, V2 center, Paint p) {
        center.offset(r.left, r.top);
        c.drawText(text, center.x, center.y, p);
    }
}
