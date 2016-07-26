package ru.daniils.darkjetpack;

import android.view.MotionEvent;

public class TouchEvent {
    public V2 point;
    public int action;
    public int actionIndex;
    public int pointerId;
    public int pointerCount;
    private MotionEvent me;

    public TouchEvent(MotionEvent _me) {
        me = _me;
        point = new V2(me.getX(), me.getY());
        pointerCount = me.getPointerCount();
        action = me.getActionMasked();
        actionIndex = me.getActionIndex();
        pointerId = me.getPointerId(actionIndex);
    }

    public int findPointerIndex(int pointerId) {
        return me.findPointerIndex(pointerId);
    }

    public int getPointerId(int actionIndex) {
        return me.getPointerId(actionIndex);
    }

    public V2 getPoint(int pointerIndex) {
        return new V2(me.getX(pointerIndex), me.getY(pointerIndex));
    }
}
