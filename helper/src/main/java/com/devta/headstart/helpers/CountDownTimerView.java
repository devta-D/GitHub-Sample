package com.devta.headstart.helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import com.devta.headstart.BuildConfig;
import com.devta.headstart.R;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author Divyanshu Tayal
 */

public class CountDownTimerView extends AppCompatTextView {

    private int maxTime, interval; //in seconds
    private Callbacks timerCallBacks;
    private CountDownTimer countDownTimer;

    public void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.start();
            if (timerCallBacks != null) timerCallBacks.onCountDownStart();
        }else {
            initTimer();
        }
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public CountDownTimerView(Context context) {
        this(context, null);
    }

    public CountDownTimerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CountDownTimerView);
        maxTime = array.getInt(R.styleable.CountDownTimerView_maxTimeInSeconds, 60);
        interval = array.getInt(R.styleable.CountDownTimerView_intervalInSeconds, 1);

        array.recycle();

        if (context instanceof Callbacks) {
            timerCallBacks = (Callbacks) context;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void initTimer() {

        countDownTimer = new CountDownTimer(maxTime * 1000, interval * 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    String remainingTime = String.format(Locale.getDefault(),"%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
                    setText(remainingTime);
                } catch (Exception e) {
                    if(BuildConfig.DEBUG) e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                setText("00:00");
                if (timerCallBacks != null) timerCallBacks.onCountDownFinish();
            }
        };
        countDownTimer.start();
    }

    public interface Callbacks {

        void onCountDownFinish();

        void onCountDownStart();
    }
}
