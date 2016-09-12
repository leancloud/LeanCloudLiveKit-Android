package cn.leancloud.leancloudlivekit.record;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.pili.pldroid.streaming.CameraStreamingManager;
import com.pili.pldroid.streaming.CameraStreamingSetting;
import com.pili.pldroid.streaming.StreamingProfile;
import com.pili.pldroid.streaming.widget.AspectFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import cn.leancloud.leancloudlivekit.R;

/**
 * Created by wli on 16/8/4.
 * 负责录制直播的 Fragment
 */
public class LCLKRecordFragment extends Fragment implements CameraStreamingManager.StreamingStateListener {

  private CameraStreamingManager mediaStreamingManager;
  private CameraStreamingSetting cameraStreamingSetting;
  private View mView;

  private String recordStream;

  @Override
  public void onResume() {
    super.onResume();
    if (null != mediaStreamingManager) {
      mediaStreamingManager.resume();
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (null != mediaStreamingManager) {
      mediaStreamingManager.pause();
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (null != mediaStreamingManager) {
      mediaStreamingManager.destroy();
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    mView = inflater.inflate(R.layout.lclk_record_fragment, parent, false);
    return mView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    AspectFrameLayout aspectFrameLayout = (AspectFrameLayout) mView.findViewById(R.id.live_recore_aspect_layout);
    GLSurfaceView glSurfaceView = (GLSurfaceView) mView.findViewById(R.id.live_recore_camera_preview_view);
    aspectFrameLayout.setShowMode(AspectFrameLayout.SHOW_MODE.REAL);

    mediaStreamingManager = new CameraStreamingManager(getActivity(), aspectFrameLayout, glSurfaceView,
      CameraStreamingManager.EncodingType.SW_VIDEO_WITH_SW_AUDIO_CODEC);
    mediaStreamingManager.setStreamingStateListener(this);
    cameraStreamingSetting = getCameraSetting();
    setStream(recordStream);
  }

  @Override
  public void onStateChanged(int state, Object info) {
    switch (state) {
      case CameraStreamingManager.STATE.PREPARING:
        break;
      case CameraStreamingManager.STATE.READY:
        new Thread(new Runnable() {
          @Override
          public void run() {
            if (mediaStreamingManager != null) {
              mediaStreamingManager.startStreaming();
            }
          }
        }).start();
        break;
      case CameraStreamingManager.STATE.CONNECTING:
        break;
      case CameraStreamingManager.STATE.STREAMING:
        // The av packet had been sent.
        break;
      case CameraStreamingManager.STATE.SHUTDOWN:
        // The streaming had been finished.
        break;
      case CameraStreamingManager.STATE.IOERROR:
        // Network connect error.
        break;
      case CameraStreamingManager.STATE.SENDING_BUFFER_EMPTY:
        break;
      case CameraStreamingManager.STATE.SENDING_BUFFER_FULL:
        break;
      case CameraStreamingManager.STATE.AUDIO_RECORDING_FAIL:
        // Failed to record audio.
        break;
      case CameraStreamingManager.STATE.OPEN_CAMERA_FAIL:
        // Failed to open camera.
        break;
      case CameraStreamingManager.STATE.DISCONNECTED:
        // The socket is broken while streaming
        break;
    }
  }

  public void setStream(String recordStream) {
    this.recordStream = recordStream;
    if (!TextUtils.isEmpty(recordStream) && null != mediaStreamingManager) {
      initStream(recordStream);
      startRecord();
    }
  }

  private void initStream(String recordStream) {
    StreamingProfile streamingProfile = getSreamingProfile(recordStream);
    mediaStreamingManager.prepare(cameraStreamingSetting, null, null, streamingProfile);
  }

  private CameraStreamingSetting getCameraSetting() {
    CameraStreamingSetting cameraStreamingSetting = new CameraStreamingSetting();
    cameraStreamingSetting.setContinuousFocusModeEnabled(true)
      .setCameraFacingId(CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_BACK)
      .setBuiltInFaceBeautyEnabled(true)
      .setResetTouchFocusDelayInMs(3000)
      .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.SMALL)
      .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9)
      .setFaceBeautySetting(new CameraStreamingSetting.FaceBeautySetting(1.0f, 1.0f, 0.8f))
      .setVideoFilter(CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY);
    return cameraStreamingSetting;
  }

  private StreamingProfile getSreamingProfile(String recordStream) {
    StreamingProfile streamingProfile = new StreamingProfile();
    try {
      streamingProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_HIGH3);
      streamingProfile.setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2);
      streamingProfile.setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_480);
      streamingProfile.setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY);
      streamingProfile.setStreamStatusConfig(new StreamingProfile.StreamStatusConfig(3));
      streamingProfile.setSendingBufferProfile(new StreamingProfile.SendingBufferProfile(0.2f, 0.8f, 3.0f, 20 * 1000));
      streamingProfile.setStream(new StreamingProfile.Stream(new JSONObject(recordStream)));
      return streamingProfile;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void startRecord() {
    mediaStreamingManager.startStreaming();
  }

  public void stopRecord() {
    mediaStreamingManager.stopStreaming();
  }

  @Override
  public boolean onStateHandled(int i, Object o) {
    return false;
  }
}
