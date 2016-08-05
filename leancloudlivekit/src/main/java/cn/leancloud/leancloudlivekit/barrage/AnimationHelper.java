package cn.leancloud.leancloudlivekit.barrage;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by wli on 16/7/21.
 */
public class AnimationHelper {
  /**
   * 创建平移动画
   */
  public static Animation createTranslateAnim(Context context, int fromX, int toX) {
    TranslateAnimation tlAnim = new TranslateAnimation(fromX, toX, 0, 0);
    //自动计算时间
//    long duration = (long) (Math.abs(toX - fromX) * 1.0f / ScreenUtils.getScreenW(context) * 4000);
    long duration = 10000;
    tlAnim.setDuration(duration);
    tlAnim.setFillEnabled(false);
    tlAnim.setInterpolator(new LinearInterpolator());
    return tlAnim;
  }
}