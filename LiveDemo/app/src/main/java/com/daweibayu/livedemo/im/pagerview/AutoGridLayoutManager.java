//package com.daweibayu.livedemo.im.bottomdialog;
//
//import android.content.Context;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.view.View;
//
///**
// * Created by wli on 16/7/25.
// */
//public class AutoGridLayoutManager extends GridLayoutManager {
//
//  private int measuredWidth = 0;
//  private int measuredHeight = 0;
//
//  public AutoGridLayoutManager(Context context, AttributeSet attrs,
//                               int defStyleAttr, int defStyleRes) {
//    super(context, attrs, defStyleAttr, defStyleRes);
//  }
//
//  public AutoGridLayoutManager(Context context, int spanCount) {
//    super(context, spanCount);
//  }
//
//  public AutoGridLayoutManager(Context context, int spanCount,
//                               int orientation, boolean reverseLayout) {
//    super(context, spanCount, orientation, reverseLayout);
//  }
//
//  @Override
//  public void onMeasure(RecyclerView.Recycler recycler,
//                        RecyclerView.State state, int widthSpec, int heightSpec) {
//    if (measuredHeight <= 0) {
//      View view = recycler.getViewForPosition(0);
//      if (view != null) {
//        measureChild(view, widthSpec, heightSpec);
//        measuredWidth = View.MeasureSpec.getSize(widthSpec);
//        measuredHeight = view.getMeasuredHeight() * getSpanCount();
//      }
//    }
//    setMeasuredDimension(measuredWidth, measuredHeight);
//  }
//
//}