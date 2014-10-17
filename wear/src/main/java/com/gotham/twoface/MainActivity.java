package com.gotham.twoface;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.animation.ObjectAnimator;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends Activity {
    private static final float ROTATE_FROM = 0.0f;
    private static final float ROTATE_TO = -10.0f * 360.0f;


    BoxInsetLayout stub;
    //View rootLayout = (View) findViewById(R.id.main_activity_root);
    ImageView catCoinFront;
    ImageView catCoinBack;
    final int numFlipsToStartDecay = 10;
    final int minNumFlips = 21;

    boolean isFlipping = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stub = (BoxInsetLayout) findViewById(R.id.watch_view_stub);
        //View rootLayout = (View) findViewById(R.id.main_activity_root);
        catCoinFront = (ImageView) findViewById(R.id.cat_coin_front);
        catCoinBack = (ImageView) findViewById(R.id.cat_coin_back);

        catCoinFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //setting a random speed. Change later
                double duration = 75; //<=== this can be set by the accelerometer

                Random random = new Random();
                int randomizedAdditionalFlips = random.nextInt(10);

                flipCoin(minNumFlips + randomizedAdditionalFlips, duration);

            }
        });
    }
    private void flipCoin(final int numFlips,  final double duration){
        FlipAnimation flipAnimation = new FlipAnimation(catCoinFront, catCoinBack, duration);

        flipAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (numFlips > numFlipsToStartDecay) {
                    flipCoin(numFlips - 1, duration);
                } else if (numFlips > 0) {
                    if(duration < 400) {
                        flipCoin(numFlips - 1, duration * 1.5);
                    } else {
                        flipCoin(numFlips - 1, duration * 1.2);
                    }
                    Log.w("omg", "duration: " + duration);
                }
            }
        });

        if (catCoinFront.getVisibility() == View.GONE)
        {
            flipAnimation.reverse();
        }
        stub.startAnimation(flipAnimation);
    }
}
