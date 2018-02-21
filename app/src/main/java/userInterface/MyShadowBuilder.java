package userInterface;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.view.View.DragShadowBuilder;

public class MyShadowBuilder extends View.DragShadowBuilder {

    private ShapeDrawable shadow;
    private int shadowColor;

    public MyShadowBuilder(View v, int color) {
        super(v);
        shadowColor = color;
        shadow = new ShapeDrawable(new OvalShape());
    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        shadow.draw(canvas);
    }

    @Override
    public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
        int radius = 96;

        shadow.setBounds(0, 0, radius, radius);

        shadowSize.set(radius, radius);
        shadowTouchPoint.set(radius / 2, 65);
        shadow.getPaint().setColor(shadowColor);
    }

}





