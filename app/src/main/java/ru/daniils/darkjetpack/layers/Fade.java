package ru.daniils.darkjetpack.layers;

import android.graphics.Canvas;
import android.graphics.RectF;

import ru.daniils.darkjetpack.Function;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.graphics.Layer;

public class Fade extends Layer {

    public int inColor;
    public int outColor;

    /* 0 = normal
    * 1 = going visible
    * 2 = going invisible*/
    public byte status = 1;
    public int state = 0;
    private byte step = 10;
    private Function inFunction = null, outFunction = null;

    public Fade(RectF _r, int _zIndex, int _fadeInColor, int _fadeOutColor) {
        super(_r, _zIndex);
        inColor = _fadeInColor;
        outColor = _fadeOutColor;
    }

    @Override
    public void load() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(Canvas c) {
        if (status == 1) {
            if (inFunction != null && inFunction.takeArgs)
                inFunction.doThis(c);
            state += step;
            if (state >= 255) {
                state = 255;
                status = 0;
            }
            p.setColor(inColor);
        } else if (status == 2) {
            if (outFunction != null && outFunction.takeArgs)
                outFunction.doThis(c);
            state -= step;
            if (state <= 0) {
                state = 0;
                status = 0;
            }
            p.setColor(outColor);
        }
        p.setAlpha(255 - state);
        drawRect(c, new RectF(0, 0, width, height), p);
    }

    @Override
    public boolean touch(TouchEvent e) {
        return false;
    }

    public void in() {
        status = 1;
    }

    public void out() {
        status = 2;
    }

    public void setColors(int _inColor, int _outColor) {
        if (_inColor != -1)
            inColor = _inColor;
        if (_outColor != -1)
            outColor = _outColor;
    }

    public void setInFunction(Function _inFunction) {
        inFunction = _inFunction;
    }

    public void setOutFunction(Function _outFunction) {
        outFunction = _outFunction;
    }
}
