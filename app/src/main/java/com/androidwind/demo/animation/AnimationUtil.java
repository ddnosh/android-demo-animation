package com.androidwind.demo.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class AnimationUtil {

    private ViewGroup mAnimMaskLayout; //动画层
    private Activity mActivity;

    public void playAnimation(Activity activity, View startView, View finishView) {
        mActivity = activity;
        final ImageView ivFlower = (ImageView) activity.getLayoutInflater().inflate(R.layout.flower, null);
        mAnimMaskLayout = createAnimLayout();
        mAnimMaskLayout.addView(ivFlower); //把动画图片添加到动画层
        //获取起点坐标
        int[] startLocation = new int[2];
        startView.getLocationInWindow(startLocation);
        final int x1 = startLocation[0];
        final int y1 = startLocation[1];
        //设置动画图片的起始位置
        addViewToAnimLayout(ivFlower, startLocation);
        //获取终点坐标，最近拍摄的坐标
        int[] location = new int[2];
        finishView.getLocationInWindow(location);
        final int x2 = location[0];
        final int y2 = location[1];
        //此处控制偏移量
        int offsetX;
        int offsetY;
        offsetX = 0; //未做偏移，需要的自己计算
        offsetY = 0;
        //抛物线动画，原理：两个位移动画，一个横向匀速移动，一个纵向变速移动，两个动画同时执行，就有了抛物线的效果。
        ObjectAnimator translateAnimationX = ObjectAnimator.ofFloat(ivFlower, "translationX", 0, -(x1 - x2) - offsetX);
        ObjectAnimator translateAnimationY = ObjectAnimator.ofFloat(ivFlower, "translationY", 0, y2 - y1 + offsetY);
        //到终点后的缩小动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivFlower, "scaleX", 0.3f, 1f, 2f, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivFlower, "scaleY", 0.3f, 1f, 2f, 0f);
        scaleY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimMaskLayout.removeView(ivFlower); //动画结束后移除动画图片
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        //旋转
        ObjectAnimator rotation = ObjectAnimator.ofFloat(ivFlower, "rotation", 0, 360);
        translateAnimationX.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setInterpolator(new DecelerateInterpolator());
        translateAnimationX.setDuration(1500);
        translateAnimationY.setDuration(1500);
        scaleX.setDuration(1500);
        scaleY.setDuration(1500);
        rotation.setDuration(1500);

        AnimatorSet animatorSet = new AnimatorSet(); //设置动画播放顺序
        animatorSet.play(translateAnimationX).with(translateAnimationY);
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.play(rotation);
        animatorSet.start();
    }

    /**
     * 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) mActivity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mActivity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * ImageView加到动画层
     */
    private void addViewToAnimLayout(final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
    }
}
