package com.daweibayu.livedemo.im;

import java.util.List;

/**
 * Created by wli on 16/7/22.
 */
public class LCLiveChatAdapter extends LCIMCommonListAdapter<LCLiveIMMessage> {

  int maxMessageCount = 120;
  int removeCountTime = 50;

  public LCLiveChatAdapter() {
    super(LCIMChatItemTextHolder.class);
  }

  @Override
  public void setDataList(List<LCLiveIMMessage> datas) {
    super.setDataList(datas);
  }

  @Override
  public void addDataList(List<LCLiveIMMessage> datas) {
    super.addDataList(datas);
    if (dataList.size() > maxMessageCount) {
      dataList = dataList.subList(removeCountTime, dataList.size());
    }
  }
}
