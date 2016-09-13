package cn.leancloud.leancloudlivekit.im;

/**
 * Created by wli on 16/9/12.
 */
class LCLKGiftItem {
  int giftResource;
  String giftName;
  int giftMessageIndex;

  public LCLKGiftItem(int giftMessageIndex, String giftName, int giftResource) {
    this.giftMessageIndex = giftMessageIndex;
    this.giftName = giftName;
    this.giftResource = giftResource;
  }
}
