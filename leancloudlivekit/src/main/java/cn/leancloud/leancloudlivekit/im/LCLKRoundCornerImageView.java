package cn.leancloud.leancloudlivekit.im;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by wli on 16/7/26.
 */
public class LCLKRoundCornerImageView extends ImageView {
  public LCLKRoundCornerImageView(Context context) {
    super(context);
  }
  public LCLKRoundCornerImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
  public LCLKRoundCornerImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }
  @Override
  protected void onDraw(Canvas canvas) {
    Path clipPath = new Path();
    int w = this.getWidth();
    int h = this.getHeight();
    clipPath.addRoundRect(new RectF(0, 0, w, h), w/2, h/2, Path.Direction.CW);
    canvas.clipPath(clipPath);
    super.onDraw(canvas);
  }
}