package com.daweibayu.livedemo.im.pagerview;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daweibayu.livedemo.R;
import com.daweibayu.livedemo.im.bottomdialog.LiveGift;
import com.daweibayu.livedemo.im.LCIMCommonViewHolder;

/**
 * Created by wli on 16/7/25.
 */
public class GiftItemHolder extends LCIMCommonViewHolder<LiveGift> {

  private ImageView iconView;
  private TextView nameView;

  public GiftItemHolder(ViewGroup root) {
    super(root.getContext(), root, R.layout.live_gift_item);
    iconView = (ImageView) itemView.findViewById(R.id.live_gift_item_icon_view);
    nameView = (TextView) itemView.findViewById(R.id.live_gift_item_name_view);
  }

  @Override
  public void bindData(LiveGift liveGift) {
    nameView.setText(liveGift.name);
  }

  public static ViewHolderCreator HOLDER_CREATOR = new ViewHolderCreator<GiftItemHolder>() {
    @Override
    public GiftItemHolder createByViewGroupAndType(ViewGroup parent, int viewType) {
      return new GiftItemHolder(parent);
    }
  };
}
