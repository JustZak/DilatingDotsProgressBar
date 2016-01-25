package com.zl.reik.dilatingdotsprogressbar;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class DilatingDotDrawable extends Drawable {
    private static final String TAG = DilatingDotDrawable.class.getSimpleName();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float radius;
    private float maxRadius;
    final Rect mDirtyBounds = new Rect(0, 0, 0, 0);

    public DilatingDotDrawable(final int color, final float radius, final float maxRadius) {
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        this.radius = radius;
        setMaxRadius(maxRadius);
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect bounds = getBounds();
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), radius - 1, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        if (alpha != mPaint.getAlpha()) {
            mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidateSelf();
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidateSelf();
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (maxRadius * 2) + 2;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (maxRadius * 2) + 2;
    }

    public void setMaxRadius(final float maxRadius) {
        this.maxRadius = maxRadius;
        mDirtyBounds.bottom = (int) (maxRadius * 2) + 2;
        mDirtyBounds.right = (int) (maxRadius * 2) + 2;
    }

    @Override
    public Rect getDirtyBounds() {
        return mDirtyBounds;
    }

    @Override
    protected void onBoundsChange(final Rect bounds) {
        super.onBoundsChange(bounds);
        mDirtyBounds.offsetTo(bounds.left, bounds.top);
    }
}
