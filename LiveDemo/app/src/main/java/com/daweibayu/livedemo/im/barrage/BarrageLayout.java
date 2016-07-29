package com.daweibayu.livedemo.im.barrage;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by wli on 16/7/21.
 */
public class BarrageLayout extends RelativeLayout {

  public BarrageLayout(Context context) {
    super(context);
  }

  public BarrageLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public BarrageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void sendText(String content) {
    final TextView textView = new TextView(getContext());

    textView.setText(content);

    Animation anim = AnimationHelper.createTranslateAnim(getContext(), getWidth(), 0);
    anim.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        removeView(textView);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {}
    });
    textView.startAnimation(anim);

    addView(textView);
  }

  public void sendText(String content, Color color) {

  }

  public void sendText(String content, Color color, int speed) {

  }

  public void sendText(String content, Color color, int speed, int size) {

  }

  public void sendView(final View view) {
    Animation anim = AnimationHelper.createTranslateAnim(getContext(), getWidth(), 0);
    anim.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        removeView(view);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {}
    });
    view.startAnimation(anim);

    addView(view);
  }

  public void sendViewGroup(ViewGroup viewGroup) {

  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
  }
}
