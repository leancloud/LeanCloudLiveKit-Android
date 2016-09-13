package cn.leancloud.leancloudlivekit.im;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMClient;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wli on 16/8/3.
 */
public class LCLKGiftPagerAdapter extends PagerAdapter {

  protected List<View> viewList = new ArrayList<View>();

  public LCLKGiftPagerAdapter() {
  }

  public void setViewList(List<View> views) {
    viewList.clear();
    if (null != views && views.size() > 0) {
      viewList.addAll(views);
    }
  }

  public void addView(View view) {
    if (null != view) {
      viewList.add(view);
    }
  }

  @Override
  public int getCount() {
    return viewList.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    if (position >=0 && position < viewList.size()) {
      View view = viewList.get(position);
      container.addView(view);
      return view;
    }
    return null;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView(viewList.get(position));
  }
}
