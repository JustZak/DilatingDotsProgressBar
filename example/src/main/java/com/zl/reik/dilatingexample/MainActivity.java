package com.zl.reik.dilatingexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    public final static String TAG = MainActivity.class.getSimpleName();
    private AppCompatSeekBar mRadiusSeekbar;
    private TextView mRadiusTextView;
    private AppCompatSeekBar mSpacingSeekbar;
    private TextView mSpacingTextView;
    private AppCompatSeekBar mGrowthSpeedSeekbar;
    private TextView mGrowthSpeedTextView;
    private AppCompatSeekBar mNumberDotsSeekbar;
    private TextView mNumberDotsTextView;
    private AppCompatSeekBar mColorSeekbar;
    private TextView mColorTextView;
    private AppCompatSeekBar mScaleMultiplierSeekbar;
    private TextView mScaleMultiplierTextView;
    private DilatingDotsProgressBar mDilatingDotsProgressBar;

    private String numberDotsText;
    private String radiusText;
    private String spacingText;
    private String growthSpeedText;
    private String scaleMultiplierText;
    private String colorText;
    private final String spaces = "  ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(DilatingDotsProgressBar.class.getSimpleName());
        setSupportActionBar(toolbar);

        mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
        mDilatingDotsProgressBar.show(500);

        mNumberDotsSeekbar = (AppCompatSeekBar) findViewById(R.id.seekbar_number_dots);
        mRadiusSeekbar = (AppCompatSeekBar) findViewById(R.id.seekbar_radius);
        mSpacingSeekbar = (AppCompatSeekBar) findViewById(R.id.seekbar_spacing);
        mGrowthSpeedSeekbar = (AppCompatSeekBar) findViewById(R.id.seekbar_growth_speed);
        mColorSeekbar = (AppCompatSeekBar) findViewById(R.id.seekbar_color);
        mScaleMultiplierSeekbar = (AppCompatSeekBar) findViewById(R.id.seekbar_scale_multiplier);

        mNumberDotsTextView = (TextView) findViewById(R.id.textview_number_dots);
        mRadiusTextView = (TextView) findViewById(R.id.textview_radius);
        mSpacingTextView = (TextView) findViewById(R.id.textview_spacing);
        mGrowthSpeedTextView = (TextView) findViewById(R.id.textview_growth_speed);
        mColorTextView = (TextView) findViewById(R.id.textview_color);
        mScaleMultiplierTextView = (TextView) findViewById(R.id.textview_scale_multiplier);

        numberDotsText = getResources().getString(R.string.number_of_dots);
        radiusText = getResources().getString(R.string.radius);
        spacingText = getResources().getString(R.string.spacing);
        growthSpeedText = getResources().getString(R.string.growth_speed);
        scaleMultiplierText = getResources().getString(R.string.scale_multiplier);
        colorText = getResources().getString(R.string.color);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupSeekbars();
    }

    private void setupSeekbars(){
        mNumberDotsSeekbar.setProgress(mDilatingDotsProgressBar.getNumberOfDots());
        mNumberDotsSeekbar.setOnSeekBarChangeListener(this);

        mRadiusSeekbar.setProgress((int)mDilatingDotsProgressBar.getDotRadius());
        mRadiusSeekbar.setOnSeekBarChangeListener(this);

        mSpacingSeekbar.setProgress((int)mDilatingDotsProgressBar.getHorizontalSpacing());
        mSpacingSeekbar.setOnSeekBarChangeListener(this);

        mGrowthSpeedSeekbar.setProgress(mDilatingDotsProgressBar.getDotGrowthSpeed());
        mGrowthSpeedSeekbar.setOnSeekBarChangeListener(this);

        mScaleMultiplierSeekbar.setProgress(10);
        mScaleMultiplierSeekbar.setOnSeekBarChangeListener(this);

        mColorSeekbar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
        if (seekBar == mRadiusSeekbar) {
            mDilatingDotsProgressBar.setDotRadius(progress);
            mRadiusTextView.setText(radiusText + spaces + progress);
        } else if (seekBar == mSpacingSeekbar) {
            mDilatingDotsProgressBar.setDotSpacing(progress);
            mSpacingTextView.setText(spacingText + spaces + progress);
        } else if (seekBar == mGrowthSpeedSeekbar) {
            mDilatingDotsProgressBar.setGrowthSpeed(progress);
            mGrowthSpeedTextView.setText(growthSpeedText + spaces + progress);
        } else if (seekBar == mNumberDotsSeekbar) {
            mDilatingDotsProgressBar.setNumberOfDots(progress);
            mNumberDotsTextView.setText(numberDotsText + spaces + progress);
        } else if (seekBar == mColorSeekbar) {
            mDilatingDotsProgressBar.setDotColor(progress);
            mColorTextView.setText(colorText + spaces + progress);
        } else if (seekBar == mScaleMultiplierSeekbar) {
            float scale = lerp(1.2f, 4, progress/100f);
            mDilatingDotsProgressBar.setDotScaleMultpiplier(scale);
            mScaleMultiplierTextView.setText(scaleMultiplierText + spaces + scale);
        }
    }


    private float lerp(float min, float max, float progress) {
        float result = (min * (1.0f - progress)) + (max * progress);
        Log.d(TAG, "lerp: " + result);
        return result;
    }

    @Override
    public void onStartTrackingTouch(final SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar) {

    }
}
