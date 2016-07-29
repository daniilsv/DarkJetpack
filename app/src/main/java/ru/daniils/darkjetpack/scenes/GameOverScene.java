package ru.daniils.darkjetpack.scenes;

import android.graphics.Canvas;
import android.graphics.Color;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.Function;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.graphics.Scene;

public class GameOverScene extends Scene {
    int egeBal;
    int status;

    public GameOverScene(int _status, int _egeBal) {
        super();
        fadeLayer.setColors(-1, Color.GREEN);
        status = _status;
        egeBal = _egeBal;
    }

    private void loadLayers() {
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
        switch (status) {
            case 0://меню
                c.drawText();
                break;
            case 1://0 баллов
                break;
            case 2://ИТМО назад
                break;
            case 3://БОНЧ назад
                break;
            case 4://БЛ назад
                break;
            case 5://КАТАВА назад
                break;
        }
    }

    @Override
    public void touch(TouchEvent event) {
        fadeLayer.outColor = Color.BLACK;
        Core.replaceScene(new GameScene());
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