package ru.daniils.darkjetpack.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

import ru.daniils.darkjetpack.Core;
import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.Utils;
import ru.daniils.darkjetpack.V2;
import ru.daniils.darkjetpack.enemies.AsteroidEge;
import ru.daniils.darkjetpack.enemies.Rocket;
import ru.daniils.darkjetpack.graphics.Object;
import ru.daniils.darkjetpack.graphics.Scene;
import ru.daniils.darkjetpack.layers.Joystick;
import ru.daniils.darkjetpack.objects.IFMO;
import ru.daniils.darkjetpack.objects.Player;

import static java.util.Collections.sort;

public class GameScene extends Scene {
    private final ArrayList<Object> objects = new ArrayList<>();
    public Player player;
    private long lastTick;
    private IFMO ifmo;

    GameScene() {
        fadeLayer.setColors(-1, Color.BLUE);
        lastTick = System.currentTimeMillis();
    }

    protected Object addObject(Object object) {
        objects.add(object);
        return object;
    }

    private void loadLayers() {
        player = new Player(new RectF(0, 0, wd3 / 3, hd3), 2);
        addObject(player).moveCenterTo(wd2, hd2);
        ifmo = new IFMO(new RectF(0, 0, wd2, hd2), new V2(0, 2000));
        addObject(ifmo);

        Joystick leftJoystick = new Joystick(new RectF(0, 0, width, height), 4) {

            @Override
            public void down(V2 point) {

            }

            @Override
            public void move(V2 point) {
                player.velocity = coord;
            }

            @Override
            public void up() {
                player.velocity = new V2(0, 0);
            }
        };
        addLayer(leftJoystick);
        /*Joystick rightJoystick = new Joystick(new RectF(wd2, 0, width, height), 4) {
            @Override
            public void down(V2 point) {

            }

            @Override
            public void move(V2 point) {
            }

            @Override
            public void up() {
            }
        };
        addLayer(rightJoystick);*/
        //addLayer(new Life(new RectF(width - wd3, height - hd3, width, height), 0));
    }

    @Override
    public void load() {
        loadLayers();
        if (objects.size() != 0) {
            sort(objects);
            for (Object o : objects)
                o.onLoad();
        }
    }


    @Override
    public void tick() {
        Object o1, o2;
        List<Object> tmpArr = new ArrayList<>();
        if (objects.size() != 0) {
            for (Object o : objects)
                o.tick();

            for (int i = 0; i < objects.size(); ++i) {
                o1 = objects.get(i);
                if (o1.type != 3 && (Math.abs(o1.r.centerX()) > width * 2 || Math.abs(o1.r.centerY()) > height * 2)) {
                    tmpArr.add(o1);
                    continue;
                }
                if (o1.type < 2)
                    continue;
                for (int j = i + 1; j < objects.size(); ++j) {
                    o2 = objects.get(j);
                    if (o2.type != 3 && (Math.abs(o2.r.centerX()) > width * 2 || Math.abs(o2.r.centerY()) > height * 2)) {
                        tmpArr.add(o2);
                        continue;
                    }
                    if (o2.type < 2)
                        continue;
                    if (RectF.intersects(o1.r, o2.r))
                        o1.intersects(o2);
                    if (o2.type == -1)
                        tmpArr.add(o2);
                }
                if (o1.type == -1)
                    tmpArr.add(o1);
            }
            objects.removeAll(tmpArr);
/*
            for (Object o : objects) {
                if (o.type == 3 || o instanceof Player)
                    continue;
                V2 delta = o.position.dedR(player.position);
                float f = (float) Math.sqrt(delta.x * delta.x + delta.y * delta.y);
                o.additionalSpeed = delta.divR(new V2(-f, -f));
            }
*/
        }
        if (System.currentTimeMillis() - lastTick > 1500) {
            Random r = new Random();
            tmpArr = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Object o;
                if (player.position.y >= 750 && r.nextInt(5) > 3) {
                    o = new Rocket(new RectF(0, 0, 300, 200), new V2(player.position.x + (r.nextInt(80) - 40), player.position.y + 40 + r.nextInt(50)), new V2());
                } else {
                    o = new AsteroidEge(new RectF(0, 0, 200, 200), new V2(player.position.x + (r.nextInt(80) - 40), player.position.y + 40 + r.nextInt(50)), new V2());
                }
                o.onLoad();
                tmpArr.add(o);
            }
            objects.addAll(tmpArr);
            lastTick = System.currentTimeMillis();
        }
        ifmo.position.x = player.position.x;
        if (player.ege <= 0) {
            Core.replaceScene(new GameOverScene(0));
        }
    }

    @Override
    public void background(Canvas c) {
        Paint gradP = Utils.makeRadialGradient(width, height, wd2, Color.parseColor("#411541"), Color.parseColor("#860470"));
        c.drawRect(0, 0, width, height, gradP);
    }

    @Override
    public void render(Canvas c) {
        synchronized (objects) {
            if (objects.size() != 0) {
                try {
                    for (Object o : objects) {
                        if (o instanceof IFMO) {
                            if (o.r.bottom > 0)
                                o.onDraw(c);
                        } else if (o instanceof Player) {
                            o.onDraw(c);
                        } else if (o.r.left >= width) {//Далеко справа
                            if (o.r.top >= height) {//Далеко внизу
                                o.drawDeflect(c, new RectF(width - 50, height - 50, width, height));
                            } else if (o.r.bottom <= 0) {//Далеко наверху
                                o.drawDeflect(c, new RectF(width - 50, 0, width, 50));
                            } else {//В центре
                                o.drawDeflect(c, new RectF(width - 50, o.r.centerY() - 25, width, o.r.centerY() + 25));
                            }
                        } else if (o.r.right <= 0) {//Далеко слева
                            if (o.r.top >= height) {//Далеко внизу
                                o.drawDeflect(c, new RectF(0, height - 50, 50, height));
                            } else if (o.r.bottom <= 0) {//Далеко наверху
                                o.drawDeflect(c, new RectF(0, 0, 50, 50));
                            } else {//В центре
                                o.drawDeflect(c, new RectF(0, o.r.centerY() - 25, 50, o.r.centerY() + 25));
                            }
                        } else {//В центре
                            if (o.r.top >= height) {//Далеко внизу
                                o.drawDeflect(c, new RectF(o.r.centerX() - 25, height - 50, o.r.centerX() + 25, height));
                            } else if (o.r.bottom <= 0) {//Далеко наверху
                                o.drawDeflect(c, new RectF(o.r.centerX() - 25, 0, o.r.centerX() + 25, 50));
                            } else {//В центре
                                o.onDraw(c);
                            }
                        }
                    }
                } catch (ConcurrentModificationException ignored) {
                }
            }
        }
        p.setColor(Color.WHITE);
        Utils.setTextSizeForWidth(p, width / 6, "################");
        c.drawText(String.format("%fx%f", player.position.x, player.position.y), 0, 50, p);

        Utils.setTextSizeForWidth(p, width / 12, "###");
        c.drawText(String.format("%d", player.ege), width - width / 10, 75, p);

    }

    @Override
    public void touch(TouchEvent event) {

    }

    @Override
    public void onBackPressed() {
        Core.replaceScene(new MainScene());
    }
}