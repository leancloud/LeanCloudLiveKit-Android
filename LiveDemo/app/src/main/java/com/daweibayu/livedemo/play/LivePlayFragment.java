package com.daweibayu.livedemo.play;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daweibayu.livedemo.R;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wli on 16/7/26.
 */
public class LivePlayFragment extends Fragment {

  public static final String LIVE_STREAM_PATH = "liveStreamPath";

  @Bind(R.id.live_play_video_textrue_view)
  PLVideoTextureView videoTextureView;

  @Bind(R.id.live_play_loading_layout)
  LinearLayout loadingLayout;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.live_play_fragment, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    videoTextureView.setBufferingIndicator(loadingLayout);

    String videoPath = getActivity().getIntent().getStringExtra(LIVE_STREAM_PATH);

    AVOptions options = new AVOptions();

    // the unit of timeout is ms
    options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
    options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
    options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
    options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
    options.setInteger(AVOptions.KEY_MEDIACODEC, 0);
    options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);
    videoTextureView.setAVOptions(options);
    videoTextureView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
    videoTextureView.setKeepScreenOn(true);
    videoTextureView.setOnCompletionListener(mOnCompletionListener);
    videoTextureView.setOnErrorListener(mOnErrorListener);
    videoTextureView.setVideoPath(videoPath);
    videoTextureView.start();
  }

  private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
    @Override
    public boolean onError(PLMediaPlayer mp, int errorCode) {
      switch (errorCode) {
        case PLMediaPlayer.ERROR_CODE_INVALID_URI:
          showToastTips("Invalid URL !");
          break;
        case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
          showToastTips("404 resource not found !");
          break;
        case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
          showToastTips("Connection refused !");
          break;
        case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
          showToastTips("Connection timeout !");
          break;
        case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
          showToastTips("Empty playlist !");
          break;
        case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
          showToastTips("Stream disconnected !");
          break;
        case PLMediaPlayer.ERROR_CODE_IO_ERROR:
          showToastTips("Network IO Error !");
          break;
        case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
          showToastTips("Unauthorized Error !");
          break;
        case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
          showToastTips("Prepare timeout !");
          break;
        case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
          showToastTips("Read frame timeout !");
          break;
        case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
        default:
          showToastTips("unknown error !");
          break;
      }
//      getActivity().finish();
      return true;
    }
  };

  private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
    @Override
    public void onCompletion(PLMediaPlayer plMediaPlayer) {
      showToastTips("Play Completed !");
//      getActivity().finish();
    }
  };

  private void showToastTips(final String tips) {
    getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(getActivity(), tips, Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    videoTextureView.start();
  }

  @Override
  public void onPause() {
    super.onPause();
    videoTextureView.pause();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    videoTextureView.stopPlayback();
  }
}
