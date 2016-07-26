package ru.daniils.darkjetpack.graphics;

import android.graphics.Canvas;
import android.graphics.RectF;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.V2;
import ru.daniils.darkjetpack.scenes.GameScene;


public abstract class Object extends Layer {
    public V2 velocity;//Must be [-1,1]
    public V2 additionalSpeed;
    public V2 position;
    public V2 speed;//velocity modificator
    /**
     * Type of object
     * -1=going to be removed
     * 0=neutral
     * 1=wall
     * 2=enemy
     **/
    public int type;
    protected GameScene gs;

    public Object(RectF r, int zIndex) {
        super(r, zIndex);
        gs = (Core.mainThread.gameView.curScene instanceof GameScene) ? (GameScene) Core.mainThread.gameView.curScene : null;
        if (gs == null)
            gs = (Core.mainThread.newScene instanceof GameScene) ? (GameScene) Core.mainThread.newScene : null;
        position = new V2();
        velocity = new V2();
        additionalSpeed = new V2();
        speed = new V2(1, 1);
    }

    @Override
    public void tick() {
        velocity.add(additionalSpeed);
        if (!Float.isNaN(velocity.x) && !Float.isNaN(velocity.x))
            position.offset(velocity.x * speed.x, velocity.y * speed.y);
        velocity.ded(additionalSpeed);
    }

    public abstract void drawDeflect(Canvas c, RectF rect);

    public abstract void intersects(Object obj);
}