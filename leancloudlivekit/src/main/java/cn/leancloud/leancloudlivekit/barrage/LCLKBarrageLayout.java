package cn.leancloud.leancloudlivekit.barrage;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import cn.leancloud.leancloudlivekit.R;

/**
 * Created by wli on 16/7/21.
 */
public class LCLKBarrageLayout extends RelativeLayout {

  public LCLKBarrageLayout(Context context) {
    super(context);
  }

  public LCLKBarrageLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public LCLKBarrageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void sendView(final View view) {
    int viewHeight = getResources().getDimensionPixelSize(R.dimen.lclk_im_barrage_item_width);
    int y = (getHeight() - viewHeight) * new Random().nextInt(100) / 100;
    Animation anim = createTranslateAnim(getWidth(), -1 * getWidth(), y);
    anim.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        removeView(view);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {
      }
    });
    view.startAnimation(anim);

    addView(view);
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
  }

  private static Animation createTranslateAnim(int fromX, int toX, int y) {
    TranslateAnimation tlAnim = new TranslateAnimation(fromX, toX, y, y);
    long duration = 3000;
    tlAnim.setDuration(duration);
    tlAnim.setFillEnabled(false);
    tlAnim.setInterpolator(new LinearInterpolator());
    return tlAnim;
  }
}
