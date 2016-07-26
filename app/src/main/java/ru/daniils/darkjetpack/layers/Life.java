package ru.daniils.darkjetpack.layers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import ru.daniils.darkjetpack.TouchEvent;
import ru.daniils.darkjetpack.graphics.Layer;

public class Life extends Layer {
    Paint pAlive, pDied;
    int[][] a;
    int[][] b;

    int NeibX[] = {-1, -1, -1, 0, 0, 1, 1, 1};
    int NeibY[] = {1, 0, -1, 1, -1, -1, 0, 1};

    int test[][] = {{1, 7}, {1, 8}, {2, 8}, {3, 3}, {3, 4},
            {3, 5}, {3, 8}, {3, 9}, {4, 1}, {4, 2}, {4, 3},
            {4, 5}, {4, 9}, {5, 1}, {5, 6}, {5, 8}, {5, 9},
            {6, 5}, {6, 7}, {7, 3}, {7, 4}, {7, 6}, {7, 11},
            {8, 3}, {8, 7}, {8, 9}, {8, 10}, {8, 11}, {9, 3},
            {9, 4}, {9, 7}, {9, 8}, {9, 9}, {10, 4}, {11, 4},
            {11, 5},

            {45, 14}, {46, 14}, {47, 14}, {48, 14}, {49, 14}, {50, 14}, {51, 14}, {52, 14},
            {45, 15},/*********/{47, 15}, {48, 15}, {49, 15}, {50, 15},/*********/{52, 15},
            {45, 16}, {46, 16}, {47, 16}, {48, 16}, {49, 16}, {50, 16}, {51, 16}, {52, 16}};

    public Life(RectF _r, int _zIndex) {
        super(_r, _zIndex);
        a = new int[80][40];
        b = new int[80][40];
        pAlive = new Paint();
        pAlive.setColor(Color.rgb(236, 236, 36));
        pDied = new Paint();
        pDied.setColor(Color.rgb(236, 0, 36));

        for (int i = 0; i < 80; i++)
            for (int j = 0; j < 40; j++) {
                a[i][j] = 0;
                b[i][j] = 0;
            }
        for (int i = 0; i < 58; i++)
            a[test[i][0]][test[i][1]] = 1;
    }

    @Override
    public void load() {

    }

    void SetOne(int x, int y, int state) {
        b[x][y] = state;
    }

    void CopyFrame() {
        int i, j;
        for (i = 0; i < 80; i++)
            for (j = 0; j < 40; j++) {
                a[i][j] = b[i][j];
                b[i][j] = 0;
            }
    }

    int IsOne(int x, int y) {
        if (x >= 0 && x < 80 && y >= 0 && y < 40)
            return a[x][y];
        return 0;
    }

    int FindNeib(int x, int y) {
        int i, ret = 0;
        for (i = 0; i < 8; i++)
            if (IsOne(x + NeibX[i], y + NeibY[i]) == 1)
                ret++;
        return ret;
    }

    @Override
    public void tick() {
        int i, j, neibC;
        for (i = 0; i < 80; i++)
            for (j = 0; j < 40; j++) {

                neibC = FindNeib(i, j);

                if (IsOne(i, j) == 1) {

                    if (neibC < 2 || neibC > 3) {
                        SetOne(i, j, 2);
                    } else {
                        SetOne(i, j, 1);
                    }

                } else {
                    if (neibC == 3) {
                        SetOne(i, j, 1);
                    }
                }
            }
        CopyFrame();
    }

    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < 80; i++)
            for (int j = 0; j < 40; j++)
                if (IsOne(i, j) != 0)
                    drawRect(c, new RectF(i * width / 80, j * height / 40, (i + 1) * width / 80, (j + 1) * height / 40), IsOne(i, j) == 1 ? pAlive : (IsOne(i, j) == 2 ? pDied : p));
    }

    @Override
    public boolean touch(TouchEvent e) {
        return false;
    }
}