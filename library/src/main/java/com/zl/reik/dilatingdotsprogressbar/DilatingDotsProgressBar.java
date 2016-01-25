package com.zl.reik.dilatingdotsprogressbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public class DilatingDotsProgressBar extends View{
    public static final String TAG = DilatingDotsProgressBar.class.getSimpleName();
    private static final float DEFAULT_GROWTH_MULTIPLIER = 1.75f;
    public static final double START_DELAY_FACTOR = 0.35;
    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 0;
    private int mDotColor;
    private int mDotGrowthSpeed;
    private int mWidthBetweenDotCenters;
    private float mDotRadius;
    private float mDotScaleMultiplier;
    private float mDotMaxRadius;
    private int mOrientation;
    private boolean mShouldAnimate;
    private ArrayList<DilatingDotDrawable> drawables = new ArrayList<>();
    private final List<Animator> animations = new ArrayList<>();
    private static final int MIN_SHOW_TIME = 500; // ms
    private static final int MIN_DELAY = 500; // ms
    private long mStartTime = -1;
    private float mHorizontalSpacing;
    private boolean mDismissed = false;
    /** delayed runnable to stop the progress */
    private final Runnable mDelayedHide = new Runnable() {
        @Override
        public void run() {
            mStartTime = -1;
            setVisibility(View.GONE);
            stopAnimations();
        }
    };
    /** delayed runnable to start the progress */
    private final Runnable mDelayedShow = new Runnable() {
        @Override
        public void run() {
            if (!mDismissed) {
                mStartTime = System.currentTimeMillis();
                setVisibility(View.VISIBLE);
                startAnimations();
            }
        }
    };

    public DilatingDotsProgressBar(Context context) {
        this(context, null);
    }

    public DilatingDotsProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DilatingDotsProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    private void removeCallbacks() {
        removeCallbacks(mDelayedHide);
        removeCallbacks(mDelayedShow);
    }

    public void setDotsColor(int color) {
        for (DilatingDotDrawable dot : drawables) {
            dot.setColor(color);
        }
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DilatingDotsProgressBar);
        final int mNumberDots = a.getInt(R.styleable.DilatingDotsProgressBar_dilating_dots_numDots, 3);
        mDotRadius = a.getDimension(R.styleable.DilatingDotsProgressBar_android_radius, 8);
        mDotColor = a.getColor(R.styleable.DilatingDotsProgressBar_android_color, 0xff9c27b0);
        mDotScaleMultiplier = a.getFloat(
            R.styleable.DilatingDotsProgressBar_dilating_dots_scaleMultiplier,
            DEFAULT_GROWTH_MULTIPLIER);
        mDotGrowthSpeed = a.getInt(R.styleable.DilatingDotsProgressBar_dilating_dots_growthSpeed, 300);
        mHorizontalSpacing =
            a.getDimension(R.styleable.DilatingDotsProgressBar_dilating_dots_horizontalSpacing, 12);
        a.recycle();

        mDotMaxRadius = mDotRadius * mDotScaleMultiplier;

        mShouldAnimate = false;
        mWidthBetweenDotCenters = (int) (mDotRadius * 2) + (int) mHorizontalSpacing;

        initDots(mNumberDots);
        updateDots();
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (computeMaxHeight() != h || w != computeMaxWidth()) {
            updateDots();
        }
    }

    private void initDots(final int mNumberDots) {
        for (int i = 1; i <= mNumberDots; i++) {
            final DilatingDotDrawable dot = new DilatingDotDrawable(mDotColor, mDotRadius, mDotMaxRadius);
            dot.setCallback(this);
            drawables.add(dot);

            ValueAnimator growAnimator =
                ObjectAnimator.ofFloat(dot, "radius", mDotRadius, mDotMaxRadius, mDotRadius);
            growAnimator.setDuration(mDotGrowthSpeed);
            growAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

            if (i == mNumberDots) {
                growAnimator.addListener(
                    new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (shouldAnimate()) {
                                startAnimations();
                            }
                        }
                    });
            }

            growAnimator.setStartDelay((i - 1) * (int) (START_DELAY_FACTOR * mDotGrowthSpeed));
            animations.add(growAnimator);
        }
    }

    private void updateDots() {
        if (mDotRadius <= 0) {
            mDotRadius = getHeight() / 2 / mDotScaleMultiplier;
        }

        int left = (int) (mDotMaxRadius - mDotRadius);
        int right = (int) (left + mDotRadius * 2) + 2;
        int top = 0;
        int bottom = (int) (mDotMaxRadius * 2) + 2;

        for (int i = 0; i < drawables.size(); i++) {
            final DilatingDotDrawable dot = drawables.get(i);
            dot.setRadius(mDotRadius);
            dot.setBounds(left, top, right, bottom);
            ValueAnimator growAnimator = (ValueAnimator) animations.get(i);
            growAnimator.setFloatValues(mDotRadius, mDotRadius * mDotScaleMultiplier, mDotRadius);

            left += mWidthBetweenDotCenters;
            right += mWidthBetweenDotCenters;
        }
    }

    protected void startAnimations() {
        mShouldAnimate = true;
        for (Animator anim : animations) {
            anim.start();
        }
    }

    protected void stopAnimations() {
        mShouldAnimate = false;
        removeCallbacks();
        for (Animator anim : animations) {
            anim.cancel();
        }
    }

    protected boolean shouldAnimate() {
        return mShouldAnimate;
    }

    public void reset() {
        hideNow();
    }

    /**
     * Hide the progress view if it is visible. The progress view will not be
     * hidden until it has been shown for at least a minimum show time. If the
     * progress view was not yet visible, cancels showing the progress view.
     */
    @SuppressWarnings ("unused")
    public void hide() {
        hide(MIN_SHOW_TIME);
    }

    public void hide(int delay) {
        mDismissed = true;
        removeCallbacks(mDelayedShow);
        long diff = System.currentTimeMillis() - mStartTime;
        if ((diff >= delay) || (mStartTime == -1)) {
            mDelayedHide.run();
        } else {
            if ((delay - diff) <= 0) {
                mDelayedHide.run();
            } else {
                postDelayed(mDelayedHide, delay - diff);
            }
        }
    }

    /**
     * Show the progress view after waiting for a minimum delay. If
     * during that time, hide() is called, the view is never made visible.
     */
    @SuppressWarnings ("unused")
    public void show() {
        show(MIN_DELAY);
    }

    @SuppressWarnings ("unused")
    public void showNow() {
        show(0);
    }

    @SuppressWarnings ("unused")
    public void hideNow() {
        hide(0);
    }

    public void show(int delay) {
        mStartTime = -1;
        mDismissed = false;
        removeCallbacks(mDelayedHide);

        if (delay == 0) {
            mDelayedShow.run();
        } else {
            postDelayed(mDelayedShow, delay);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (shouldAnimate()) {
            for (DilatingDotDrawable dot : drawables) {
                dot.draw(canvas);
            }
        }
    }

    @Override
    protected boolean verifyDrawable(final Drawable who) {
        if (shouldAnimate()) {
            return drawables.contains(who);
        }
        return super.verifyDrawable(who);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) computeMaxWidth(), (int) computeMaxHeight());
    }

    private float computeMaxHeight() {
        return mDotMaxRadius * 2;
    }

    private float computeMaxWidth() {
        return computeWidth() + ((mDotMaxRadius - mDotRadius) * 2);
    }

    private float computeWidth() {
        return (((mDotRadius * 2) + mHorizontalSpacing) * drawables.size()) - mHorizontalSpacing;
    }
}
