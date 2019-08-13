package com.androidwind.demo.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MainActivity extends AppCompatActivity {

    private ImageView iv, iv_from, iv_to;
    private AnimatorSet showAnimatorSet;
    private AnimatorSet hideAnimatorSet;
    private int counter = 0;
    private List<String> tags = new ArrayList<>();
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.iv);
        iv_from = findViewById(R.id.iv_from);
        iv_to = findViewById(R.id.iv_to);
        //click6
        tv = findViewById(R.id.tv);
        tags.clear();
        tags.add("敕勒川");
        tags.add("阴山下");
        tags.add("天似穹庐");
        tags.add("笼盖四野");
        tags.add("天苍苍");
        tags.add("野茫茫");
        tags.add("风吹草低见牛羊");
        startAnimation();
    }

    public void click1(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "alpha", 0f, 1f);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

    public void click2(View view) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(iv, "scaleX", 0f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(iv, "scaleY", 0f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleXAnimator).with(scaleYAnimator);
        set.setDuration(1000);
        set.start();
        //另一种实现
        // PropertyValuesHolder objectAnimatorScaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        // PropertyValuesHolder objectAnimatorScaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        // ObjectAnimator.ofPropertyValuesHolder(iv, objectAnimatorScaleX, objectAnimatorScaleY).setDuration(1000).start();
    }

    public void click3(View view) {
        ObjectAnimator objectAnimatorTranslate = ObjectAnimator.ofFloat(iv, "translationX", 0f, 500f);
        objectAnimatorTranslate.setDuration(1000);
        objectAnimatorTranslate.start();
    }

    public void click4(View view) {
        ObjectAnimator objectAnimatorScale = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);
        objectAnimatorScale.setDuration(1000);
        objectAnimatorScale.start();
    }

    public void click5(View view) {
        AnimatorSet group = new AnimatorSet();
        ObjectAnimator objectAnimatorScaleX = ObjectAnimator.ofFloat(iv, "scaleX", 0f, 1f);
        ObjectAnimator objectAnimatorScaleY = ObjectAnimator.ofFloat(iv, "scaleY", 0f, 1f);
        ObjectAnimator objectAnimatorRotateX = ObjectAnimator.ofFloat(iv, "rotationX", 0f, 360f);
        ObjectAnimator objectAnimatorRotateY = ObjectAnimator.ofFloat(iv, "rotationY", 0f, 360f);
        ObjectAnimator objectAnimatorTranslate = ObjectAnimator.ofFloat(iv, "translationX", 0f, 500f);
        group.setDuration(2000);
        group.play(objectAnimatorScaleX).with(objectAnimatorScaleY)
                .before(objectAnimatorRotateX).before(objectAnimatorRotateY).after(objectAnimatorTranslate);
        group.start();
    }

    public void click6(View view) {
        if (counter == 0) {
            showAnimatorSet.start();
        }
    }

    public void click7(View view) {
        AnimationUtil animationUtil = new AnimationUtil();
        animationUtil.playAnimation(this, iv_from, iv_to);
    }

    private void startAnimation() {
        initShowAnimation();
        initHideAnimation();
    }

    public void initShowAnimation() {
        counter = 0;
        android.animation.ObjectAnimator animatorT =
                android.animation.ObjectAnimator.ofFloat(tv, "translationY", 30f, 0f);
        android.animation.ObjectAnimator animatorA =
                android.animation.ObjectAnimator.ofFloat(tv, "alpha", 0f, 1f);
        if (showAnimatorSet == null) {
            showAnimatorSet = new AnimatorSet();
        }
        showAnimatorSet.playTogether(animatorT, animatorA);
        showAnimatorSet.setDuration(1000);
        showAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                tv.setText(tags.get(counter));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                counter++;
                if (counter < tags.size()) {
                    if (hideAnimatorSet != null) {
                        hideAnimatorSet.start();
                    }
                } else {
                    counter = 0;
                }
            }
        });
    }

    private void initHideAnimation() {
        android.animation.ObjectAnimator animatorT =
                android.animation.ObjectAnimator.ofFloat(tv, "translationY", 0f, -30f);
        android.animation.ObjectAnimator animatorA =
                android.animation.ObjectAnimator.ofFloat(tv, "alpha", 1f, 0f);
        if (hideAnimatorSet == null) {
            hideAnimatorSet = new AnimatorSet();
        }
        hideAnimatorSet.playTogether(animatorT, animatorA);
        hideAnimatorSet.setDuration(1000);
        hideAnimatorSet.setStartDelay(1000);
        hideAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (counter < tags.size()) {
                    if (showAnimatorSet != null) {
                        showAnimatorSet.start();
                    }
                }
            }
        });
    }
}
