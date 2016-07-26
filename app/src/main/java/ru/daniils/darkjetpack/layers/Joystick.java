package ru.daniils.darkjetpack.layers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.V2;
import ru.daniils.darkjetpack.graphics.Layer;

public abstract class Joystick extends Layer {
    protected V2 coord;
    private V2 startpoint;
    private V2 jpos;
    private boolean touched = false;
    private int pointerId = -1;
    private Bitmap backBitmap, frontBitmap;

    protected Joystick(RectF r, int zIndex) {
        super(r, zIndex);
        wholeScreenTouch = true;
    }

    @Override
    public void load() {
        backBitmap = Core.getAssetBitmap("joy_back.png");
        frontBitmap = Core.getAssetBitmap("joy_front.png");
    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(Canvas c) {
        if (!touched)
            return;
        drawBitmap(c, new RectF(startpoint.x - minWH / 8, startpoint.y - minWH / 8, startpoint.x + minWH / 8, startpoint.y + minWH / 8), backBitmap, null);
        drawBitmap(c, new RectF(jpos.x - minWH / 16, jpos.y - minWH / 16, jpos.x + minWH / 16, jpos.y + minWH / 16), frontBitmap, null);
    }

    @Override
    public boolean touch(TouchEvent e) {
        switch (e.action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                if (touched)
                    break;
                V2 point = e.getPoint(e.actionIndex);
                if (!r.contains(point.x, point.y))
                    break;
                point.offset(-r.left, -r.top);
                pointerId = e.pointerId;
                touched = true;
                jpos = point;
                startpoint = point;
                down(point);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!touched || pointerId == -1)
                    break;
                int pIndex = e.findPointerIndex(pointerId);
                if (pIndex == -1 || pIndex >= e.pointerCount)
                    break;
                V2 point = e.getPoint(pIndex);
                point.offset(-r.left, -r.top);
                V2 delta = new V2(point.x - startpoint.x, point.y - startpoint.y);
                float d = (float) Math.sqrt(delta.x * delta.x + delta.y * delta.y);
                if (Float.isNaN(d))
                    break;
                if (d >= minWH / 8)
                    jpos = new V2(startpoint.x + delta.x / d * minWH / 8, startpoint.y + delta.y / d * minWH / 8);
                else
                    jpos = point;
                coord = new V2(delta.x / d, -delta.y / d);
                move(point);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                if (!touched || pointerId == -1)
                    break;
                int pIndex = e.findPointerIndex(pointerId);
                if (pIndex != e.actionIndex)
                    break;
                touched = false;
                pointerId = -1;
                up();
                break;
            }
        }
        return false;
    }

    public abstract void down(V2 point);

    public abstract void move(V2 point);

    public abstract void up();
}
