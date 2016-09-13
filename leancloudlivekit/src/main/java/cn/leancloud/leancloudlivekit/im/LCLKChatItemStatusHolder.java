package cn.leancloud.leancloudlivekit.im;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.leancloud.leancloudlivekit.R;

/**
 * Created by wli on 16/8/18.
 */
public class LCLKChatItemStatusHolder extends LCLKCommonViewHolder<LCLKIMStatusMessage> {

  private TextView contentView;

  public LCLKChatItemStatusHolder(Context context, ViewGroup root) {
    super(context, root, R.layout.lclk_im_chat_status_item);
    contentView = (TextView) itemView.findViewById(R.id.lclk_im_status_item_view);
  }

  public static ViewHolderCreator HOLDER_CREATOR = new ViewHolderCreator<LCLKChatItemStatusHolder>() {
    @Override
    public LCLKChatItemStatusHolder createByViewGroupAndType(ViewGroup parent, int viewType) {
      return new LCLKChatItemStatusHolder(parent.getContext(), parent);
    }
  };

  @Override
  public void bindData(LCLKIMStatusMessage statusMessage) {
    contentView.setText(statusMessage.getStatusContent());
  }
}