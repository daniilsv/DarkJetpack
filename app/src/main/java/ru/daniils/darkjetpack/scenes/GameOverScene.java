package ru.daniils.darkjetpack.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.Function;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.V2;
import ru.daniils.darkjetpack.graphics.Layer;
import ru.daniils.darkjetpack.graphics.Scene;

public class GameOverScene extends Scene {
    int egeBal;

    public GameOverScene(int _egeBal) {
        super();
        fadeLayer.setColors(-1, Color.GREEN);
        egeBal = _egeBal;
    }

    private void loadLayers() {
        //НАСТРОЙКИ
        addLayer(new Layer(new RectF(0, 0, wd2, hd2 / 3), 0) {
            int count = 0;

            public void load() {
                p.setTextAlign(Paint.Align.CENTER);
                p.setStyle(Paint.Style.FILL);
                p.setColor(Color.WHITE);
                p.setTextSize(hd2);
            }

            public void tick() {

            }

            public void draw(Canvas c) {
                drawText(c, "Жаль, что это только игра...", new V2(wd2, hd2), p);
            }

            public boolean touch(TouchEvent e) {
                count++;
                if (count == 5) {
                    Core.debug = !Core.debug;
                    count = 0;
                }
                return false;
            }
        }).moveCenterTo(wd2, 1 * height / 8);
        addLayer(new Layer(new RectF(0, 0, wd2, hd2 / 3), 0) {
            int count = 0;

            public void load() {
                p.setTextAlign(Paint.Align.CENTER);
                p.setStyle(Paint.Style.FILL);
                p.setColor(Color.WHITE);
                p.setTextSize(hd2);
            }

            public void tick() {

            }

            public void draw(Canvas c) {
                drawText(c, "Хотя.. Имея " + egeBal + " по ЕГЭ", new V2(wd2, hd2), p);
            }

            public boolean touch(TouchEvent e) {
                count++;
                if (count == 5) {
                    Core.debug = !Core.debug;
                    count = 0;
                }
                return false;
            }
        }).moveCenterTo(wd2, 4 * height / 8);
        addLayer(new Layer(new RectF(0, 0, wd2, hd2 / 3), 0) {
            int count = 0;

            public void load() {
                p.setTextAlign(Paint.Align.CENTER);
                p.setStyle(Paint.Style.FILL);
                p.setColor(Color.WHITE);
                p.setTextSize(hd2);
            }

            public void tick() {

            }

            public void draw(Canvas c) {
                drawText(c, " ты всё равно бы не поступил", new V2(wd2, hd2), p);
            }

            public boolean touch(TouchEvent e) {
                count++;
                if (count == 5) {
                    Core.debug = !Core.debug;
                    count = 0;
                }
                return false;
            }
        }).moveCenterTo(wd2, 6 * height / 8);
    }

    @Override
    public void load() {
        loadLayers();
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
                if (fadeLayer.state <= 10)
                    Core.working = false;
            }
        });
        fadeLayer.outColor = Color.BLACK;
        fadeLayer.out();
    }
}