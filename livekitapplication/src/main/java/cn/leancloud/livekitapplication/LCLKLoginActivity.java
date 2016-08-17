package cn.leancloud.livekitapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.leancloudlivekit.LCLiveKit;

/**
 * Created by wli on 16/8/5.
 * 登陆页面
 */
public class LCLKLoginActivity extends AppCompatActivity {

  @Bind(R.id.activity_login_et_username)
  EditText nameView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lclk_login_activity);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.activity_login_btn_login)
  public void onLoginClick(View view) {
    String clientId = nameView.getText().toString();
    if (TextUtils.isEmpty(clientId.trim())) {
      Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
      return;
    }

    // 打开 LeanCloud 实时通讯
    LCLiveKit.getInstance().open(clientId, new AVIMClientCallback() {
      @Override
      public void done(AVIMClient avimClient, AVIMException e) {
        if (null == e) {
          finish();
          Intent intent = new Intent(LCLKLoginActivity.this, LCLKLiveRoomListActivity.class);
          startActivity(intent);
        } else {
          Toast.makeText(LCLKLoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
}
