package cn.leancloud.leancloudlivekit.im;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cn.leancloud.leancloudlivekit.R;

/**
 * Created by wli on 16/8/4.
 */
public class LCLKChatItemTextHolder extends LCLKCommonViewHolder<LCLKIMMessage> {

  private ImageView avatarView;
  private TextView nameView;
  private TextView contentView;

  public LCLKChatItemTextHolder(Context context, ViewGroup root) {
    super(context, root, R.layout.lclk_im_chat_item_text_layout);
    initView();
  }

  public void initView() {
    avatarView = (ImageView) itemView.findViewById(R.id.im_chat_item_text_avatar_view);
    contentView = (TextView) itemView.findViewById(R.id.im_chat_item_text_content_view);
    nameView = (TextView) itemView.findViewById(R.id.im_chat_item_text_name_view);
  }

  public static ViewHolderCreator HOLDER_CREATOR = new ViewHolderCreator<LCLKChatItemTextHolder>() {
    @Override
    public LCLKChatItemTextHolder createByViewGroupAndType(ViewGroup parent, int viewType) {
      return new LCLKChatItemTextHolder(parent.getContext(), parent);
    }
  };

  @Override
  public void bindData(LCLKIMMessage LCLKIMMessage) {
    if (null != LCLKIMMessage) {
      if (!TextUtils.isEmpty(LCLKIMMessage.getName())) {
        nameView.setText(LCLKIMMessage.getName());
      }
      if (!TextUtils.isEmpty(LCLKIMMessage.getAvatar())) {
        Picasso.with(getContext()).load(LCLKIMMessage.getAvatar()).placeholder(R.mipmap.lclk_default_avatar_icon).into(avatarView);
      }
      contentView.setText(LCLKIMMessage.getMessageContent());
    } else {
      nameView.setText("");
      avatarView.setImageResource(R.mipmap.lclk_default_avatar_icon);
      contentView.setText("");
    }
  }
}