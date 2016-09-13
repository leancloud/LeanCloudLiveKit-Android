package cn.leancloud.leancloudlivekit;

import com.avos.avoscloud.AVCallback;

import java.util.List;

/**
 * Created by wli on 16/8/4.
 * LeanCloudLiveKit 用户体系的接口
 * 开发者需要实现此接口并调用 LCLiveKit.getInstance().setProfiProvider(LCLiveKitProvider)
 * 来接入 LeanCloudLiveKit
 */
public interface LCLiveKitProvider {

  /**
   * 根据用户 id 获取用户的详细信息
   * @param userIdList
   * @param profilesCallBack
   */
  void fetchProfiles(List<String> userIdList, AVCallback<List<LCLKUser>> profilesCallBack);

  /**
   * 根据 LiveId 拉取推流地址
   * @param liveId
   * @param streamCallback
   */
  void fetchRecordStream(String liveId, AVCallback<String> streamCallback);

  /**
   * 根据 LiveId 拉取播放地址
   * @param liveId
   * @param streamCallback
   */
  void fetchPlayStream(String liveId, AVCallback<String> streamCallback);
}
