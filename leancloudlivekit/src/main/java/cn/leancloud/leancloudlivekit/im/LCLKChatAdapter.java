package cn.leancloud.leancloudlivekit.im;

import java.util.List;

/**
 * Created by wli on 16/7/22.
 */
public class LCLKChatAdapter extends LCLKCommonListAdapter<LCLKIMMessage> {

  int maxMessageCount = 120;
  int removeCountTime = 50;

  public LCLKChatAdapter() {
    super(LCLKChatItemTextHolder.class);
  }

  @Override
  public void setDataList(List<LCLKIMMessage> datas) {
    super.setDataList(datas);
  }

  @Override
  public void addDataList(List<LCLKIMMessage> datas) {
    super.addDataList(datas);
    if (dataList.size() > maxMessageCount) {
      dataList = dataList.subList(removeCountTime, dataList.size());
    }
  }
}
