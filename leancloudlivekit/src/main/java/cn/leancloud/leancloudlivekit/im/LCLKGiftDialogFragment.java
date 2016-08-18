package cn.leancloud.leancloudlivekit.im;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.leancloudlivekit.R;

/**
 * Created by wli on 16/7/22.
 */
public class LCLKGiftDialogFragment extends DialogFragment {

  GridLayout currentLayout = null;

  ViewPager viewPager;

  LCLKGiftPagerAdapter pagerAdapter;

  List<View> giftViewList = new ArrayList<>();

  DialogInterface.OnDismissListener onDismissListener;


  int pageWidth = 4;
  int pagerHeight = 2;

  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    if (null != onDismissListener) {
      onDismissListener.onDismiss(dialog);
    }
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
    Dialog dialog = new Dialog(getActivity(), R.style.LCLKBottomDialog);

    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
    dialog.setContentView(R.layout.lclk_gift_dialog);
    dialog.setCanceledOnTouchOutside(true); // 外部点击取消
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    viewPager = (ViewPager) dialog.findViewById(R.id.lift_gift_dialog_pager);

    pagerAdapter = new LCLKGiftPagerAdapter();
    viewPager.setAdapter(pagerAdapter);

    // 设置宽度为屏宽, 靠近屏幕底部。
    Window window = dialog.getWindow();
    window.getWindowManager().getDefaultDisplay().getWidth();
    WindowManager.LayoutParams lp = window.getAttributes();
    lp.gravity = Gravity.BOTTOM; // 紧贴底部
    lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
    window.setAttributes(lp);

    initGridView();
    return dialog;
  }

  public void setViews(List<View> viewList) {
    giftViewList.clear();
    if (null != viewList) {
      giftViewList.addAll(viewList);
    }
    initGridView();
  }

  public void setOnDismissListener(final DialogInterface.OnDismissListener listener) {
    onDismissListener = listener;
  }

  private void initGridView() {
    if (null != pagerAdapter) {
      final int screenWidth = getResources().getDisplayMetrics().widthPixels;
      final int dialogHeight = getResources().getDimensionPixelOffset(R.dimen.lclk_live_gift_dialog_height);
      for (View view : giftViewList) {
        if (null == currentLayout || currentLayout.getChildCount() >= pageWidth * pagerHeight) {
          currentLayout = new GridLayout(view.getContext());
          currentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
          currentLayout.setColumnCount(pageWidth);
          currentLayout.setRowCount(pagerHeight);
          currentLayout.setUseDefaultMargins(true);
          pagerAdapter.addView(currentLayout);
        }
        view.setLayoutParams(new ViewGroup.LayoutParams(screenWidth / pageWidth, dialogHeight / pagerHeight));
        currentLayout.addView(view);
      }
      pagerAdapter.notifyDataSetChanged();
    }
  }
}
