# android-demo-animation
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190814100740160.gif)

# 用法
1. 透明度  
~~~ java
ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "alpha", 0f, 1f);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
~~~
2. 缩放
~~~ java
ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(iv, "scaleX", 0f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(iv, "scaleY", 0f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleXAnimator).with(scaleYAnimator);
        set.setDuration(1000);
        set.start();
~~~
3. 位移
~~~ java
ObjectAnimator objectAnimatorTranslate = ObjectAnimator.ofFloat(iv, "translationX", 0f, 500f);
        objectAnimatorTranslate.setDuration(1000);
        objectAnimatorTranslate.start();
~~~
4. 旋转
~~~ java
ObjectAnimator objectAnimatorScale = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);
        objectAnimatorScale.setDuration(1000);
        objectAnimatorScale.start();
~~~
5. 组合
~~~ java
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
~~~
6. ValueAnimator
~~~ java
ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>()
        {

            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue)
            {
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                PointF point = (PointF) animation.getAnimatedValue();
                iv.setX(point.x);
                iv.setY(point.y);

            }
        });
        valueAnimator.start();
~~~
