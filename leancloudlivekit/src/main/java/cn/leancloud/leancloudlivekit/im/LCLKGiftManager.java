package cn.leancloud.leancloudlivekit.im;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wli on 16/9/12.
 */
public class LCLKGiftManager {

  private static LCLKGiftManager giftManager;

  private HashMap<Integer, LCLKGiftItem> giftItemHashMap = new HashMap<>();

  public synchronized static LCLKGiftManager getInstance() {
    if (null == giftManager) {
      giftManager = new LCLKGiftManager();
    }
    return giftManager;
  }

  private LCLKGiftManager() {
  }

  public List<LCLKGiftItem> getGiftList() {
    ArrayList<LCLKGiftItem> giftItemList = new ArrayList<>();
    giftItemList.addAll(giftItemHashMap.values());
    return giftItemList;
  }

  public void addGiftItem(LCLKGiftItem giftItem) {
    giftItemHashMap.put(giftItem.giftMessageIndex, giftItem);
  }

  public LCLKGiftItem getGiftItem(int messageIdex) {
    if (giftItemHashMap.containsKey(messageIdex)) {
      return giftItemHashMap.get(messageIdex);
    }
    return null;
  }

  public void clearGiftList() {
    giftItemHashMap.clear();
  }
}
