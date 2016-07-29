package com.daweibayu.livedemo.im.pagerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by wli on 16/7/25.
 */
public class PagerRecyclerView extends RecyclerView {

  private Context mContext = null;

  private PagerAdapter myAdapter = null;

  private int shortestDistance; // 超过此距离的滑动才有效
  private float slideDistance = 0; // 滑动的距离
  private float scrollX = 0; // X轴当前的位置

  private int spanRow = 1; // 行数
  private int spanColumn = 3; // 每页列数
  private int totalPage = 0; // 总页数
  private int currentPage = 1; // 当前页

  private int pageMargin = 0; // 页间距

  private PagerIndicatorView mIndicatorView = null; // 指示器布局

  private int realPosition = 0; // 真正的位置（从上到下从左到右的排列方式变换成从左到右从上到下的排列方式后的位置）

  /*
 * 0: 停止滚动且手指移开; 1: 开始滚动; 2: 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
 */
  private int scrollState = 0; // 滚动状态

  public PagerRecyclerView(Context context) {
    this(context, null);
  }

  public PagerRecyclerView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PagerRecyclerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    defaultInit(context);
  }

  // 默认初始化
  private void defaultInit(Context context) {
    this.mContext = context;
    setLayoutManager(new GridLayoutManager(
      mContext, spanRow, GridLayoutManager.HORIZONTAL, false));
    setOverScrollMode(OVER_SCROLL_NEVER);
  }

  /**
   * 设置行数和每页列数
   *
   * @param spanRow    行数，<=0表示使用默认的行数
   * @param spanColumn 每页列数，<=0表示使用默认每页列数
   */
  public void setPageSize(int spanRow, int spanColumn) {
    this.spanRow = spanRow <= 0 ? this.spanRow : spanRow;
    this.spanColumn = spanColumn <= 0 ? this.spanColumn : spanColumn;
    setLayoutManager(new GridLayoutManager(
      mContext, this.spanRow, GridLayoutManager.HORIZONTAL, false));
  }

  /**
   * 设置页间距
   *
   * @param pageMargin 间距(px)
   */
  public void setPageMargin(int pageMargin) {
    this.pageMargin = pageMargin;
  }

  /**
   * 设置指示器
   *
   * @param indicatorView 指示器布局
   */
  public void setIndicator(PagerIndicatorView indicatorView) {
    this.mIndicatorView = indicatorView;
  }

  @Override
  protected void onMeasure(int widthSpec, int heightSpec) {
    super.onMeasure(widthSpec, heightSpec);
    shortestDistance = getMeasuredWidth() / 3;
  }

  @Override
  public void setAdapter(Adapter adapter) {
    super.setAdapter(adapter);
    this.myAdapter = (PagerAdapter) adapter;
    update();
  }

  // 更新页码指示器和相关数据
  private void update() {
    myAdapter.setSpan(spanRow, spanColumn, pageMargin, realPosition);
    // 计算总页数
    int temp = ((int) Math.ceil(myAdapter.getDataList().size() / (double) (spanRow * spanColumn)));
    if (temp != totalPage) {
      mIndicatorView.initIndicator(temp);
      // 页码减少且当前页为最后一页
      if (temp < totalPage && currentPage == totalPage) {
        currentPage = temp;
        // 执行滚动
        smoothScrollBy(-getWidth(), 0);
      }
      mIndicatorView.setSelectedPage(currentPage - 1);
      totalPage = temp;
    }
  }

  @Override
  public void onScrollStateChanged(int state) {
    switch (state) {
      case 2:
        scrollState = 2;
        break;
      case 1:
        scrollState = 1;
        break;
      case 0:
        if (slideDistance == 0) {
          break;
        }
        scrollState = 0;
        if (slideDistance < 0) { // 上页
          currentPage = (int) Math.ceil(scrollX / getWidth());
          if (currentPage * getWidth() - scrollX < shortestDistance) {
            currentPage += 1;
          }
        } else { // 下页
          currentPage = (int) Math.ceil(scrollX / getWidth()) + 1;
          if (currentPage <= totalPage) {
            if (scrollX - (currentPage - 2) * getWidth() < shortestDistance) {
              // 如果这一页滑出距离不足，则定位到前一页
              currentPage -= 1;
            }
          } else {
            currentPage = totalPage;
          }
        }
        // 执行自动滚动
        smoothScrollBy((int) ((currentPage - 1) * getWidth() - scrollX), 0);
        // 修改指示器选中项
        mIndicatorView.setSelectedPage(currentPage - 1);
        slideDistance = 0;
        break;
    }
    super.onScrollStateChanged(state);
  }

  @Override
  public void onScrolled(int dx, int dy) {
    scrollX += dx;
    if (scrollState == 1) {
      slideDistance += dx;
    }

    super.onScrolled(dx, dy);
  }
}