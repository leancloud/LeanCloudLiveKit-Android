package cn.leancloud.leancloudlivekit;

import com.avos.avoscloud.AVCallback;

import java.util.List;

/**
 * Created by wli on 16/8/4.
 * LeanCloudLiveKit 用户体系的接口
 * 开发者需要实现此接口并调用 LCLiveKit.getInstance().setProfileProvider(LiveAppProvider)
 * 来接入 LeanCloudLiveKit
 */
public interface LCLiveKitProvider {

  /**
   * 根据用户 id 获取用户的详细信息
   * @param userIdList
   * @param profilesCallBack
   */
  void fetchProfiles(List<String> userIdList, LCLKProfilesCallBack profilesCallBack);

  void fetchRecordStream(String liveId, AVCallback<String> streamCallback);

  void fetchPlayStream(String liveId, AVCallback<String> streamCallback);
}
