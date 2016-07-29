package ru.daniils.darkjetpack;

import android.graphics.PointF;

/**
 * Created by DVS
 */
public class V2 {
    public float x, y;

    public V2() {
        x = 0;
        y = 0;
    }

    public V2(double _x, double _y) {
        x = (float) _x;
        y = (float) _y;
    }

    public V2(float _x, float _y) {
        x = _x;
        y = _y;
    }

    public V2(V2 o) {
        x = o.x;
        y = o.y;
    }

    public V2(PointF o) {
        x = o.x;
        y = o.y;
    }

    public V2 set(float _x, float _y) {
        x = _x;
        y = _y;
        return this;
    }

    public V2 add(float i) {
        x += i;
        y += i;
        return this;
    }

    public V2 add(V2 o) {
        x += o.x;
        y += o.y;
        return this;
    }

    public V2 addR(V2 o) {
        return new V2(x + o.x, y + o.y);
    }

    public V2 ded(float i) {
        x -= i;
        y -= i;
        return this;
    }

    public V2 ded(V2 o) {
        x -= o.x;
        y -= o.y;
        return this;
    }

    public V2 dedR(V2 o) {
        return new V2(x - o.x, y - o.y);
    }

    public V2 mul(float i) {
        x *= i;
        y *= i;
        return this;
    }

    public V2 mul(V2 o) {
        x *= o.x;
        y *= o.y;
        return this;
    }

    public V2 mulR(V2 o) {
        return new V2(x * o.x, y * o.y);
    }

    public V2 mul(float dx, float dy) {
        x *= dx;
        y *= dy;
        return this;
    }

    public V2 div(float i) {
        x /= i;
        y /= i;
        return this;
    }

    public V2 div(V2 o) {
        x /= o.x;
        y /= o.y;
        return this;
    }

    public V2 divR(V2 o) {
        return new V2(x / o.x, y / o.y);
    }

    public V2 offset(float dx, float dy) {
        x += dx;
        y += dy;
        return this;
    }
}