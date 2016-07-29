package com.daweibayu.livedemo.record;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daweibayu.livedemo.R;
import com.pili.pldroid.streaming.CameraStreamingManager;
import com.pili.pldroid.streaming.CameraStreamingSetting;
import com.pili.pldroid.streaming.StreamingProfile;
import com.pili.pldroid.streaming.widget.AspectFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wli on 16/7/28.
 */
public class LiveRecordFragment extends Fragment implements CameraStreamingManager.StreamingStateListener {

  public static String LIVE_RECORD_STREAM_KEY = "live_record_stream";

  private String recordStream;
  private CameraStreamingManager mCameraStreamingManager;
  private CameraStreamingSetting cameraStreamingSetting;
  private StreamingProfile streamingProfile;

  @Bind(R.id.live_recore_aspect_layout)
  AspectFrameLayout aspectFrameLayout;

//  @Bind(R.id.live_recore_camera_preview_view)
//  GLSurfaceView cameraPreviewView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.live_record_fragment, container, false);
    ButterKnife.bind(this, view);


    aspectFrameLayout.setShowMode(AspectFrameLayout.SHOW_MODE.REAL);
    mCameraStreamingManager = new CameraStreamingManager(getContext(), aspectFrameLayout, null, CameraStreamingManager.EncodingType.SW_VIDEO_WITH_SW_AUDIO_CODEC);

    cameraStreamingSetting = new CameraStreamingSetting();
    cameraStreamingSetting.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
      .setContinuousFocusModeEnabled(true)
      .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
      .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9);
    streamingProfile = new StreamingProfile();
    streamingProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_HIGH1)
      .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
      .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_480)
      .setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY);

    if (null != getArguments() && getArguments().containsKey(LIVE_RECORD_STREAM_KEY)) {
      recordStream = getArguments().getString(LIVE_RECORD_STREAM_KEY);
    }
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (!TextUtils.isEmpty(recordStream)) {
      initRecord();
    }
  }

  public void setRecordStream(String stream) {
    recordStream = stream;
    initRecord();
  }

  private void initRecord() {
    try {
      streamingProfile.setStream(new StreamingProfile.Stream(new JSONObject(recordStream)));
      mCameraStreamingManager.prepare(cameraStreamingSetting, streamingProfile);
      mCameraStreamingManager.setStreamingStateListener(this);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (null != mCameraStreamingManager) {
      mCameraStreamingManager.resume();
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (null != mCameraStreamingManager) {
      mCameraStreamingManager.pause();
    }
  }

  @Override
  public void onStateChanged(int state, Object info) {
    Log.e("onStateChanged", state + "");
    if (null != info) {
      Log.e("onStateChanged", state + "    " + info.toString());
    }
    switch (state) {
      case CameraStreamingManager.STATE.PREPARING:
        break;
      case CameraStreamingManager.STATE.READY:
        // start streaming when READY
        new Thread(new Runnable() {
          @Override
          public void run() {
            if (mCameraStreamingManager != null) {
              mCameraStreamingManager.startStreaming();
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

  //TODO 各种参数判断
  public void startRecord() {
    mCameraStreamingManager.startStreaming();
  }

  public void stopRecord() {
    mCameraStreamingManager.stopStreaming();
  }

  @Override
  public boolean onStateHandled(int i, Object o) {
    return false;
  }
}
