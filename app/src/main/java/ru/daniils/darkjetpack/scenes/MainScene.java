package ru.daniils.darkjetpack.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.Function;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.graphics.Layer;
import ru.daniils.darkjetpack.graphics.Scene;

public class MainScene extends Scene {
    public MainScene() {
        super();
        fadeLayer.setColors(-1, Color.RED);
    }

    private void loadLayers() {
        addLayer(new Layer(new RectF(0, 0, 200, 200), 0) {

            public void load() {

            }

            public void tick() {
            }

            public void draw(Canvas c) {
                p.setColor(Color.RED);
                drawCircle(c, wd2, hd2, 100, p);
            }

            public boolean touch(TouchEvent e) {
                Core.replaceScene(new GameScene());
                return true;
            }
        }).moveCenterTo(wd2, hd2);

        addLayer(new Layer(new RectF(0, 0, 100, 100), 0) {

            public void load() {

            }

            public void tick() {
            }

            public void draw(Canvas c) {
                p.setColor(Color.RED);
                drawCircle(c, wd2, hd2, 50, p);
            }

            public boolean touch(TouchEvent e) {
                Core.replaceScene(new SettingsScene());
                return true;
            }
        }).moveCenterTo(width - wd2 / 2, hd2);
    }

    @Override
    public void load() {
        loadLayers();
        fadeLayer.setInFunction(new Function(true) {
            @Override
            public void doThis(Object... args) {
                layers.get(0).moveCenterTo(null, height - fadeLayer.state * hd2 / 255);
            }
        });
        fadeLayer.setOutFunction(new Function(true) {
            @Override
            public void doThis(Object... args) {
                layers.get(0).moveCenterTo(null, fadeLayer.state * hd2 / 255);
            }
        });
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Canvas c) {

    }

    @Override
    public void touch(TouchEvent event) {

    }

    @Override
    public void onBackPressed() {

        fadeLayer.setOutFunction(new Function(true) {
            @Override
            public void doThis(Object... args) {
                layers.get(0).moveCenterTo(null, fadeLayer.state * hd2 / 255);
                if (fadeLayer.state <= 10)
                    Core.working = false;
            }
        });
        fadeLayer.outColor = Color.BLACK;
        fadeLayer.out();
    }
}
