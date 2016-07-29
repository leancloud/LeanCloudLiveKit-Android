package com.daweibayu.livedemo.im;

import com.avos.avoscloud.im.v2.AVIMMessageCreator;
import com.avos.avoscloud.im.v2.AVIMMessageType;

/**
 * Created by wli on 16/7/26.
 */
@AVIMMessageType(type = LiveBarrageMessage.LIVE_BARRAGE_MESSAGE_TYPE)
public class LiveBarrageMessage extends LCLiveIMMessage {

  public static final int LIVE_BARRAGE_MESSAGE_TYPE = 5001;

  public LiveBarrageMessage() {
    super();
  }

  public static final Creator<LiveBarrageMessage> CREATOR = new AVIMMessageCreator<LiveBarrageMessage>(LiveBarrageMessage.class);
}
