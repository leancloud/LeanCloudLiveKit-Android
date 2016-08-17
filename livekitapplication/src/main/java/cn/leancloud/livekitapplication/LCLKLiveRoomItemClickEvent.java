package cn.leancloud.livekitapplication;

/**
 * Created by wli on 16/8/10.
 * 直播间 item 的点击事件
 */
public class LCLKLiveRoomItemClickEvent {
  public LCLiveRoom liveRoom;

  LCLKLiveRoomItemClickEvent(LCLiveRoom liveRoom) {
    this.liveRoom = liveRoom;
  }
}
