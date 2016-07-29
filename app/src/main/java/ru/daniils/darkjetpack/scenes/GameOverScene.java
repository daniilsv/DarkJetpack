package ru.daniils.darkjetpack.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.Function;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.graphics.Scene;

public class GameOverScene extends Scene {
    int egeBal;
    int status;
    Paint p = new Paint();

    public GameOverScene(int _status, int _egeBal) {
        super();
        fadeLayer.setColors(-1, Color.GREEN);
        status = _status;
        egeBal = _egeBal;
        p.setColor(Color.WHITE);
        p.setTextSize(80);
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
                break;
            case 1://0 баллов
                c.drawText("Чот мда..."+egeBal+"баллов.",wd2,hd3+80,p);
                break;
            case 2://ИТМО назад
                c.drawText("Поздравляем, вы набрали "+egeBal+"баллов.",wd2,hd3,p);
                c.drawText("Но вы всё равно не поступили в ИТМО.",wd2,hd3+80,p);
                break;
            case 3://БОНЧ назад
                c.drawText("Вы поступили в БОНЧ.",wd2,hd3,p);
                c.drawText("            WASTED",wd2,hd3+80,p); //Да здравствует адаптивная вёрстка! (нет), ЧОТ Я ВЩИ ОТКУДА ОНО ШИРИНУ И ВЫСОТУ БЕРЁТ :ССС Исправь хд
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