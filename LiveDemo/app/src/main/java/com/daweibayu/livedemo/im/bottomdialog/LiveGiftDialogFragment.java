package com.daweibayu.livedemo.im.bottomdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.daweibayu.livedemo.play.LiveGiftMessage;
import com.daweibayu.livedemo.R;
import com.daweibayu.livedemo.im.pagerview.PagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by wli on 16/7/22.
 */
public class LiveGiftDialogFragment extends DialogFragment {

  private PagerAdapter<LiveGift> giftAdapter;

  @Bind(R.id.live_gift_dialog_gift_1)
  TextView gift1View;

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
    Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);

    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
    dialog.setContentView(R.layout.live_gift_dialog);
    dialog.setCanceledOnTouchOutside(true); // 外部点击取消

    ButterKnife.bind(this, dialog);

    // 设置宽度为屏宽, 靠近屏幕底部。
    Window window = dialog.getWindow();
    WindowManager.LayoutParams lp = window.getAttributes();
    lp.gravity = Gravity.BOTTOM; // 紧贴底部
    lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
    window.setAttributes(lp);

    initPagerRecyclerView();

    return dialog;
  }

  @OnClick(R.id.live_gift_dialog_gift_1)
  public void onGift1Click(View view) {
    LiveGiftMessage giftMessage = new LiveGiftMessage();
    giftMessage.setMessageContent("gift 1");
    EventBus.getDefault().post(new LiveGiftClickEvent(giftMessage));
  }

  private void initPagerRecyclerView() {

  }

//  @Nullable
//  @Override
//  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//    View view = inflater.inflate(R.layout.live_gift_dialog, container);
//    return view;
//  }
}
