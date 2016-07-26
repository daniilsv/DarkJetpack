package ru.daniils.darkjetpack.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.V2;
import ru.daniils.darkjetpack.graphics.Layer;
import ru.daniils.darkjetpack.graphics.Scene;

class GameOverScene extends Scene {

    GameOverScene() {
        super();
        fadeLayer.setColors(-1, Color.GREEN);
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
                drawText(c, "Настройки", new V2(wd2, hd2), p);
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
        //FPS
        addLayer(new Layer(new RectF(0, 0, wd2 / 4, hd2 / 3), 0) {

            public void load() {
                p.setTextAlign(Paint.Align.CENTER);
                p.setStyle(Paint.Style.FILL);
                p.setColor(Color.WHITE);
                p.setTextSize(hd2);
            }

            public void tick() {

            }

            public void draw(Canvas c) {
                drawText(c, "FPS", new V2(wd2, hd2), p);
            }

            public boolean touch(TouchEvent e) {
                return false;
            }
        }).moveCenterTo(1 * width / 4, hd3);
        //30
        addLayer(new Layer(new RectF(0, 0, wd2 / 4, hd2 / 3), 0) {

            public void load() {
                p.setTextAlign(Paint.Align.CENTER);
                p.setStyle(Paint.Style.FILL);
                p.setTextSize(hd2);
            }

            public void tick() {

            }

            public void draw(Canvas c) {
                if (Core.MPF == 1000 / 30) {
                    p.setColor(Color.WHITE);
                } else {
                    p.setColor(Color.BLACK);
                }
                drawText(c, "30", new V2(wd2, hd2), p);
            }

            public boolean touch(TouchEvent e) {
                if (Core.MPF != 1000 / 30) {
                    Core.MPF = 1000 / 30;
                    Log.d("FPS set to", "30");
                }
                return true;
            }
        }).moveCenterTo(2 * width / 4, hd3);
        //60
        addLayer(new Layer(new RectF(0, 0, wd2 / 4, hd2 / 3), 0) {

            public void load() {
                p.setTextAlign(Paint.Align.CENTER);
                p.setStyle(Paint.Style.FILL);
                p.setTextSize(hd2);
            }

            public void tick() {

            }

            public void draw(Canvas c) {
                if (Core.MPF == 1000 / 60) {
                    p.setColor(Color.WHITE);
                } else {
                    p.setColor(Color.BLACK);
                }
                drawText(c, "60", new V2(wd2, hd2), p);
            }

            public boolean touch(TouchEvent e) {
                if (Core.MPF != 1000 / 60) {
                    Core.MPF = 1000 / 60;
                    Log.d("FPS set to", "60");
                }
                return true;
            }
        }).moveCenterTo(3 * width / 4, hd3);
        //Назад
        addLayer(new Layer(new RectF(0, 0, wd2 / 2, hd2 / 3), 0) {

            public void load() {
                p.setTextAlign(Paint.Align.CENTER);
                p.setStyle(Paint.Style.FILL);
                p.setColor(Color.WHITE);
                p.setTextSize(hd2);
            }

            public void tick() {

            }

            public void draw(Canvas c) {
                drawText(c, "Назад", new V2(wd2, hd2), p);
            }

            public boolean touch(TouchEvent e) {
                Core.replaceScene(new MainScene());
                return true;
            }
        }).moveCenterTo(5 * width / 6, 7 * height / 8);
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
}