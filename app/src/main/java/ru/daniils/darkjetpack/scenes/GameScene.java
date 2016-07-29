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
import ru.daniils.darkjetpack.objects.BL;
import ru.daniils.darkjetpack.objects.Back;
import ru.daniils.darkjetpack.objects.GUT;
import ru.daniils.darkjetpack.objects.IFMO;
import ru.daniils.darkjetpack.objects.KTW;
import ru.daniils.darkjetpack.objects.Player;

import static java.util.Collections.sort;

public class GameScene extends Scene {
    private final ArrayList<Object> objects = new ArrayList<>();
    public Player player;
    private long lastTick;
    private IFMO ifmo;
    private GUT gut;
    private BL bl;
    private KTW ktw;
    private Back back;

    public GameScene() {
        fadeLayer.setColors(-1, Color.BLUE);
        lastTick = System.currentTimeMillis();
    }

    protected Object addObject(Object object) {
        objects.add(object);
        return object;
    }

    private void loadLayers() {
        player = new Player(new RectF(0, 0, minWH / 6, 2 * minWH / 6), 2);
        addObject(player).moveCenterTo(wd2, hd2);
        ifmo = new IFMO(new RectF(0, 0, wd3, hd3), new V2(0, 1750));
        addObject(ifmo);
        gut = new GUT(new RectF(0, 0, wd2, hd3), new V2(0, -500));
        addObject(gut);
        bl = new BL(new RectF(0, 0, wd3, hd3 / 2), new V2(-750, 0));
        addObject(bl);
        ktw = new KTW(new RectF(0, 0, wd3, hd3), new V2(750, 0));
        addObject(ktw);
        back = new Back(new RectF(0, 0, width, height), new V2(0, 0));
        addObject(back);

        Joystick leftJoystick = new Joystick(new RectF(0, 0, width, height), 4) {

            @Override
            public void down(V2 point) {
                player.speed.set(1, 1);
                player.velocity.set(0, 0);
            }

            @Override
            public void move(V2 point) {
                player.velocity = coord;
            }

            @Override
            public void up() {
                player.speed.div(1.05f);
            }
        };
        addLayer(leftJoystick);
    }

    @Override
    public void load() {
        loadLayers();

        for (int i = 1; i <= 10; i++)
            Core.getAssetBitmap("BAD/" + i + ".png");
        for (int i = 1; i <= 4; i++)
            Core.getAssetBitmap("BAD2/" + i + ".png");
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
                if (o1.type != 3 && (Math.abs(o1.r.centerX()) > width || Math.abs(o1.r.centerY()) > height)) {
                    tmpArr.add(o1);
                    continue;
                }
                if (o1.type < 2)
                    continue;
                for (int j = i + 1; j < objects.size(); ++j) {
                    o2 = objects.get(j);
                    if (o2.type != 3 && (Math.abs(o2.r.centerX()) > width || Math.abs(o2.r.centerY()) > height)) {
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

            for (Object o : objects) {
                if (o.type == 3 || o instanceof Player)
                    continue;
                V2 delta = o.position.dedR(player.position);
                float f = (float) Math.sqrt(delta.x * delta.x + delta.y * delta.y);
                o.additionalSpeed = delta.divR(new V2(-f, -f));
            }

        }
        if (System.currentTimeMillis() - lastTick > 750) {
            Random r = new Random();
            tmpArr = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Object o;
                if (player.position.y >= 750 && r.nextInt(5) > 3) {
                    o = new Rocket(new RectF(0, 0, 270, 140), new V2(player.position.x + (r.nextInt(100) - 50), player.position.y + 40 + r.nextInt(70)), new V2());
                } else {
                    o = new AsteroidEge(new RectF(0, 0, 150, 150), new V2(player.position.x + (r.nextInt(120) - 60), player.position.y + 40 + r.nextInt(70)), new V2());
                }
                o.onLoad();
                o.tick();
                tmpArr.add(o);
            }
            objects.addAll(tmpArr);
            lastTick = System.currentTimeMillis();
        }
        ifmo.position.x = player.position.x;
        gut.position.x = player.position.x;
        bl.position.y = player.position.y;
        ktw.position.y = player.position.y;
        if (player.ege <= 0) {
            Core.replaceScene(new GameOverScene(1, 0));
        }
        if (player.speed.x < 1) {
            player.speed.div(1.05f);
        }
    }

    @Override
    public void background(Canvas c) {
        Paint gradP = Utils.makeLinearGradient(width, height, Color.parseColor("#003388"), Color.parseColor("#00aaff"));
        c.drawRect(0, 0, width, height, gradP);
        back.onDraw(c);
    }

    @Override
    public void render(Canvas c) {
        synchronized (objects) {
            if (objects.size() != 0) {
                try {
                    for (Object o : objects) {
                        if (o instanceof IFMO && o.r.bottom > 0)
                            o.onDraw(c);
                        else if (o instanceof GUT && o.r.top < height)
                            o.onDraw(c);
                        else if (o instanceof Player) {
                            o.onDraw(c);
                        } else if (o.r.left - 10 >= width) {//Далеко справа
                            if (o.r.top - 10 >= height) {//Далеко внизу
                                o.drawDeflect(c, new RectF(width - 50, height - 50, width, height));
                            } else if (o.r.bottom + 10 <= 0) {//Далеко наверху
                                o.drawDeflect(c, new RectF(width - 50, 0, width, 50));
                            } else {//В центре
                                o.drawDeflect(c, new RectF(width - 50, o.r.centerY() - 25, width, o.r.centerY() + 25));
                            }
                        } else if (o.r.right + 10 <= 0) {//Далеко слева
                            if (o.r.top - 10 >= height) {//Далеко внизу
                                o.drawDeflect(c, new RectF(0, height - 50, 50, height));
                            } else if (o.r.bottom + 10 <= 0) {//Далеко наверху
                                o.drawDeflect(c, new RectF(0, 0, 50, 50));
                            } else {//В центре
                                o.drawDeflect(c, new RectF(0, o.r.centerY() - 25, 50, o.r.centerY() + 25));
                            }
                        } else {//В центре
                            if (o.r.top - 10 >= height) {//Далеко внизу
                                o.drawDeflect(c, new RectF(o.r.centerX() - 25, height - 50, o.r.centerX() + 25, height));
                            } else if (o.r.bottom + 10 <= 0) {//Далеко наверху
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
        Core.replaceScene(new GameOverScene(0, 0));
    }
}