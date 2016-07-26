package ru.daniils.darkjetpack;


import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;

public class Utils {
    public static void setRectCenter(RectF rect, float cx, float cy) {
        float wd2 = rect.width() / 2;
        float hd2 = rect.height() / 2;
        rect.set(new RectF(cx - wd2, cy - hd2, cx + wd2, cy + hd2));
    }

    public static Paint makeLinearGradient(float width, float height, int startC, int endC) {
        LinearGradient gradient = new LinearGradient(width / 2, 0, width / 2, height, startC, endC, android.graphics.Shader.TileMode.CLAMP);
        Paint p = new Paint();
        p.setDither(true);
        p.setShader(gradient);
        return p;
    }

    public static Paint makeRadialGradient(float width, float height, float radius, int centerC, int edgeC) {
        RadialGradient gradient = new RadialGradient(width / 2, height / 2, radius, centerC, edgeC, android.graphics.Shader.TileMode.CLAMP);
        Paint p = new Paint();
        p.setDither(true);
        p.setShader(gradient);
        return p;
    }

    //Устанавливаем размер текста, чтобы получить нужную ширину
    public static void setTextSizeForWidth(Paint paint, float desiredWidth, String text) {
        final float testTextSize = 48f;
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();
        paint.setTextSize(desiredTextSize);
    }
}
