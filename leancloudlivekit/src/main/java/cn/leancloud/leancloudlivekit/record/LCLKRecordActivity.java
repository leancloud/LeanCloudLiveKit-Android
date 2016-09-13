package cn.leancloud.leancloudlivekit.record;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.leancloudlivekit.LCLiveKit;
import cn.leancloud.leancloudlivekit.LCLiveKitProvider;
import cn.leancloud.leancloudlivekit.R;
import cn.leancloud.leancloudlivekit.im.LCLKIMFragment;
import cn.leancloud.leancloudlivekit.utils.LCLKConstants;

/**
 * Created by wli on 16/8/2.
 * 负责录制直播的页面
 * 只需要开发者传入 LCLKConstants.LIVE_ID 就可以
 */
public class LCLKRecordActivity extends AppCompatActivity {

  LCLKIMFragment lclkimFragment;
  LCLKRecordFragment lclkRecordFragment;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lclk_record_activity);
    lclkimFragment = (LCLKIMFragment) getSupportFragmentManager().findFragmentById(R.id.lclk_im_fragment);
    lclkRecordFragment = (LCLKRecordFragment) getSupportFragmentManager().findFragmentById(R.id.lclk_record_fragment);

    final String liveId = getIntent().getStringExtra(LCLKConstants.LIVE_ID);

    initConversation(liveId);
    initRecordFragment(liveId);
  }

  /**
   * 初始化录制直播的 Fragment
   * @param liveId
   */
  private void initRecordFragment(String liveId) {
    LCLiveKitProvider provider = LCLiveKit.getInstance().getProfileProvider();
    if (null != provider) {
      provider.fetchRecordStream(liveId, new AVCallback<String>() {
        @Override
        protected void internalDone0(String s, AVException e) {
          if (null == e && !TextUtils.isEmpty(s)) {
            lclkRecordFragment.setStream(s);
          }
        }
      });
    }
  }

  /**
   * 初始化实时通讯的 Fragment
   * @param liveId
   */
  private void initConversation(final String liveId) {
    final AVIMClient client = LCLiveKit.getInstance().getClient();
    AVIMConversationQuery conversationQuery = client.getQuery();
    conversationQuery.whereEqualTo("name", liveId);
    conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
      @Override
      public void done(List<AVIMConversation> list, AVIMException e) {
        if (null != list && list.size() > 0) {
          lclkimFragment.setConversation(list.get(0));
        } else {
          client.createConversation(new ArrayList<String>(), liveId, null, false, true, new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation avimConversation, AVIMException e) {
              lclkimFragment.setConversation(avimConversation);
            }
          });
        }
      }
    });
  }
}
