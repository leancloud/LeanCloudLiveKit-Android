package cn.leancloud.livekitapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.leancloudlivekit.LCLiveKit;
import cn.leancloud.leancloudlivekit.record.LCLKRecordActivity;
import cn.leancloud.leancloudlivekit.utils.LCLKConstants;

/**
 * Created by wli on 16/8/8.
 * 创建直播间页面
 */
public class LCLKLiveRoomCreateActivity extends AppCompatActivity {

  @Bind(R.id.live_channle_start_et_title)
  EditText titleView;

  @Bind(R.id.live_channle_start_et_topic)
  EditText topicView;

  @Bind(R.id.live_channle_start_iv_conver)
  ImageView converView;

  @Bind(R.id.live_channle_start_tv_start)
  TextView startView;

  private String localCameraPath;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lclk_live_room_create_activity);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.live_channle_start_iv_conver)
  public void onConverClick(View view) {
    final String[] Items = {"手机相册", "拍照"};
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setItems(Items, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
          case 0:
            dispatchPickPictureIntent();
            break;
          case 1:
            dispatchTakePictureIntent();
            break;
        }
      }
    });
    builder.setCancelable(true);
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  @OnClick(R.id.live_channle_start_tv_start)
  public void onStartClick(View view) {
    final String title = titleView.getText().toString();
    final String topic = topicView.getText().toString();
    final String clientId = LCLiveKit.getInstance().getCurrentUserId();
    final String liveId = clientId;
    final String anchorId = clientId;

    if (TextUtils.isEmpty(title.trim())) {
      Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
      return;
    }

    if (TextUtils.isEmpty(topic.trim())) {
      Toast.makeText(this, "话题不能为空", Toast.LENGTH_SHORT).show();
      return;
    }

    if (TextUtils.isEmpty(localCameraPath)) {
      Toast.makeText(this, "封面不能为空", Toast.LENGTH_SHORT).show();
      return;
    }

    if (TextUtils.isEmpty(clientId.trim())) {
      Toast.makeText(this, "请重新登陆！", Toast.LENGTH_SHORT).show();
    }

    LCLiveRoom liveRoom = new LCLiveRoom();

    try {
      liveRoom.setConver(AVFile.withAbsoluteLocalPath("cover", localCameraPath));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    liveRoom.setTopic(topic);
    liveRoom.setTitle(title);
    liveRoom.setLiveId(liveId);
    liveRoom.setAnchorId(anchorId);
    liveRoom.saveInBackground(new SaveCallback() {
      @Override
      public void done(AVException e) {
        finish();
        Intent intent = new Intent(LCLKLiveRoomCreateActivity.this, LCLKRecordActivity.class);
        intent.putExtra(LCLKConstants.LIVE_ID, liveId);
        startActivity(intent);
      }
    });
  }

  private void dispatchTakePictureIntent() {
    localCameraPath = getPicturePathByCurrentTime(this);
    Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
    Uri imageUri = Uri.fromFile(new File(localCameraPath));
    takePictureIntent.putExtra("return-data", false);
    takePictureIntent.putExtra("output", imageUri);
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
      this.startActivityForResult(takePictureIntent, 1);
    }
  }

  private void dispatchPickPictureIntent() {
    Intent photoPickerIntent = new Intent("android.intent.action.PICK", (Uri) null);
    photoPickerIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
    this.startActivityForResult(photoPickerIntent, 2);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (-1 == resultCode) {
      switch (requestCode) {
        case 1:
          Picasso.with(this).load(new File(localCameraPath)).into(converView);
          break;
        case 2:
          localCameraPath = getRealPathFromURI(this, data.getData());
          Picasso.with(this).load(new File(localCameraPath)).into(converView);
      }
    }
  }

  private String getRealPathFromURI(Context context, Uri contentUri) {
    Cursor cursor = null;
    String var6;
    try {
      String[] proj = new String[]{"_data"};
      cursor = context.getContentResolver().query(contentUri, proj, (String) null, (String[]) null, (String) null);
      int column_index = cursor.getColumnIndexOrThrow("_data");
      cursor.moveToFirst();
      var6 = cursor.getString(column_index);
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
    return var6;
  }

  private static String getPicturePathByCurrentTime(Context context) {
    String state = Environment.getExternalStorageState();
    boolean isExternalStorageWritable = "mounted".equals(state);
    File fille = isExternalStorageWritable ? context.getExternalCacheDir() : context.getCacheDir();
    return (new File(fille, "picture_" + System.currentTimeMillis())).getAbsolutePath();
  }
}
