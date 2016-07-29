package com.daweibayu.livedemo.im.pagerview;

import android.view.View;
import android.view.ViewGroup;

import com.daweibayu.livedemo.im.LCIMCommonListAdapter;
import com.daweibayu.livedemo.im.LCIMCommonViewHolder;

import java.util.List;

/**
 * Created by wli on 16/7/25.
 */
public class PagerAdapter<T> extends LCIMCommonListAdapter<T> {

  private int itemWidth = 0;
  private int itemCount = 0;

  private int spanRow;
  private int spanColumn, pageMargin, realPosition;

  /**
   * 实例化适配器
   */
  public PagerAdapter(Class<?> vhClass) {
    super(vhClass);

  }

  @Override
  public void setDataList(List<T> datas) {
    super.setDataList(datas);
    itemCount = dataList.size() + spanRow * spanColumn;
  }

  public void setSpan(int spanRow, int spanColumn, int pageMargin, int realPosition) {
    this.spanColumn = spanColumn;
    this.spanRow = spanRow;
    this.pageMargin = pageMargin;
    this.realPosition = realPosition;
  }


  @Override
  public LCIMCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (itemWidth <= 0) {
      // 计算Item的宽度
      itemWidth = (parent.getWidth() - pageMargin * 2) / spanColumn;
    }

    LCIMCommonViewHolder holder = super.onCreateViewHolder(parent, viewType);

    holder.itemView.measure(0, 0);
    holder.itemView.getLayoutParams().width = itemWidth;
    holder.itemView.getLayoutParams().height = holder.itemView.getMeasuredHeight();

    return holder;
  }

  @Override
  public void onBindViewHolder(LCIMCommonViewHolder holder, int position) {
    if (spanColumn == 1) {
      // 每个Item距离左右两侧各pageMargin
      holder.itemView.getLayoutParams().width = itemWidth + pageMargin * 2;
      holder.itemView.setPadding(pageMargin, 0, pageMargin, 0);
    } else {
      int m = position % (spanRow * spanColumn);
      if (m < spanRow) {
        // 每页左侧的Item距离左边pageMargin
        holder.itemView.getLayoutParams().width = itemWidth + pageMargin;
        holder.itemView.setPadding(pageMargin, 0, 0, 0);
      } else if (m >= spanRow * spanColumn - spanRow) {
        // 每页右侧的Item距离右边pageMargin
        holder.itemView.getLayoutParams().width = itemWidth + pageMargin;
        holder.itemView.setPadding(0, 0, pageMargin, 0);
      } else {
        // 中间的正常显示
        holder.itemView.getLayoutParams().width = itemWidth;
        holder.itemView.setPadding(0, 0, 0, 0);
      }
    }

    countRealPosition(position);

    holder.itemView.setTag(realPosition);

    if (realPosition < dataList.size()) {
      holder.itemView.setVisibility(View.VISIBLE);
      super.onBindViewHolder(holder, realPosition);
    } else {
      holder.itemView.setVisibility(View.INVISIBLE);
    }

  }

  @Override
  public int getItemCount() {
    return itemCount;
  }

  private void countRealPosition(int position) {
    // 为了使Item从左到右从上到下排列，需要position的值
    int m = position % (spanRow * spanColumn);
    switch (m) {
      case 1:
      case 5:
        realPosition = position + 2;
        break;
      case 3:
      case 7:
        realPosition = position - 2;
        break;
      case 2:
        realPosition = position + 4;
        break;
      case 6:
        realPosition = position - 4;
        break;
      case 0:
      case 4:
      case 8:
        realPosition = position;
        break;
    }
  }

//  /**
//   * 删除Item
//   *
//   * @param position 位置
//   */
//  public void remove(int position) {
//    if (position < dataList.size()) {
//      // 删除数据
//      dataList.remove(position);
//      itemCount--;
//      // 删除Item
//      notifyItemRemoved(position);
//      // 更新界面上发生改变的Item
//      notifyItemRangeChanged((currentPage - 1) * spanRow * spanColumn, currentPage * spanRow * spanColumn);
//      // 更新页码指示器
//      update();
//    }
//  }
}