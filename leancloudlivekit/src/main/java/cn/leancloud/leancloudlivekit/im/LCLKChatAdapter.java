package cn.leancloud.leancloudlivekit.im;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wli on 16/7/22.
 */
public class LCLKChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final int CHAT_ITEM_IM_TYPE = 100;
  private final int CHAT_ITEM_STATUS_TYPE = 200;

  int maxMessageCount = 120;
  int removeCountTime = 50;

  protected List<AVIMTypedMessage> messageList = new ArrayList<AVIMTypedMessage>();

  public LCLKChatAdapter() {
    super();
  }

  /**
   * 添加多条消息记录
   *
   * @param messages
   */
  public void addDataList(List<AVIMTypedMessage> messages) {
    messageList.addAll(messages);
    if (messageList.size() > maxMessageCount) {
      messageList = messageList.subList(removeCountTime, messageList.size());
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case CHAT_ITEM_IM_TYPE:
        return new LCLKChatItemTextHolder(parent.getContext(), parent);
      case CHAT_ITEM_STATUS_TYPE:
        return new LCLKChatItemStatusHolder(parent.getContext(), parent);
    }
    return new LCLKChatItemStatusHolder(parent.getContext(), parent);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ((LCLKCommonViewHolder) holder).bindData(messageList.get(position));
  }

  @Override
  public int getItemViewType(int position) {
    AVIMMessage message = messageList.get(position);
    if (null != message && message instanceof LCLKIMStatusMessage) {
      return CHAT_ITEM_STATUS_TYPE;
    }
    return CHAT_ITEM_IM_TYPE;
  }

  @Override
  public int getItemCount() {
    return messageList.size();
  }
}
