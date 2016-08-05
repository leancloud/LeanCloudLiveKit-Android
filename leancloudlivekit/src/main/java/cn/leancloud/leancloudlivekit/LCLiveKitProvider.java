package cn.leancloud.leancloudlivekit;

import java.util.List;

/**
 * Created by wli on 16/8/4.
 */
public interface LCLiveKitProvider {

  public void fetchProfiles(List<String> userIdList, LCLiveKitProfilesCallBack profilesCallBack);

//  public void getStreamInfo(String conversationId, )
}
