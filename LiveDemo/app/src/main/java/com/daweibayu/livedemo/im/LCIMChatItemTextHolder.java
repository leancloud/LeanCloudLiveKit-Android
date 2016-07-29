package com.daweibayu.livedemo.im;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.daweibayu.livedemo.R;
import com.squareup.picasso.Picasso;


/**
 * Created by wli on 15/9/17.
 * 聊天页面中的文本 item 对应的 holder
 */
public class LCIMChatItemTextHolder extends LCIMCommonViewHolder<LCLiveIMMessage> {

  private ImageView avatarView;
  private TextView nameView;
  private TextView contentView;

  public LCIMChatItemTextHolder(Context context, ViewGroup root) {
    super(context, root, R.layout.im_chat_item_text_layout);
    initView();
  }

  public void initView() {
    avatarView = (ImageView) itemView.findViewById(R.id.im_chat_item_text_avatar_view);
    contentView = (TextView) itemView.findViewById(R.id.im_chat_item_text_content_view);
    nameView = (TextView) itemView.findViewById(R.id.im_chat_item_text_name_view);
  }

  public static ViewHolderCreator HOLDER_CREATOR = new ViewHolderCreator<LCIMChatItemTextHolder>() {
    @Override
    public LCIMChatItemTextHolder createByViewGroupAndType(ViewGroup parent, int viewType) {
      return new LCIMChatItemTextHolder(parent.getContext(), parent);
    }
  };

  @Override
  public void bindData(LCLiveIMMessage lcLiveIMMessage) {
    if (null != lcLiveIMMessage) {
      if (!TextUtils.isEmpty(lcLiveIMMessage.getName())) {
        nameView.setText(lcLiveIMMessage.getName());
      }
      if (!TextUtils.isEmpty(lcLiveIMMessage.getAvatar())) {
        Picasso.with(getContext()).load(lcLiveIMMessage.getAvatar()).placeholder(R.mipmap.lcim_default_avatar_icon).into(avatarView);
      }
      contentView.setText(lcLiveIMMessage.getMessageContent());
    } else {
      nameView.setText("");
      avatarView.setImageResource(R.mipmap.lcim_default_avatar_icon);
      contentView.setText("");
    }
  }
}
