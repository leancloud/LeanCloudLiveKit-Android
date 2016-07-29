package com.daweibayu.livedemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wli on 16/7/27.
 */
public class CameraPreviewFragment extends Fragment implements SurfaceHolder.Callback {

  @Bind(R.id.camera_preview_surfaceview)
  SurfaceView surfaceView;

  private SurfaceHolder surfaceHolder;
  private Camera camera;
  private boolean isBackCameraOn = true;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.camera_preview_fragment, container, false);
    ButterKnife.bind(this, view);

    surfaceHolder = surfaceView.getHolder();
    surfaceHolder.addCallback(this);
    surfaceView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        camera.autoFocus(null);
      }
    });

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }


  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    setStartPreview(camera, surfaceHolder);
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    if (surfaceHolder.getSurface() == null) {
      return;
    }
    try {
      camera.stopPreview();
    } catch (Exception e) {
      e.printStackTrace();
    }
    setStartPreview(camera, surfaceHolder);
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    releaseCamera();
  }

  @Override
  public void onResume() {
    super.onResume();
    if (this.checkCameraHardware(getContext()) && (camera == null)) {
      camera = getCamera();
      if (surfaceHolder != null) {
        setStartPreview(camera, surfaceHolder);
      }
    }
  }

  /**
   * 切换前后摄像头
   *
   * @param view view
   */
  public void switchCamera(View view) {
    int cameraCount;
    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
    cameraCount = Camera.getNumberOfCameras();
    // 遍历可用摄像头
    for (int i = 0; i < cameraCount; i++) {
      Camera.getCameraInfo(i, cameraInfo);
      if (isBackCameraOn) {
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
          releaseCamera();
          camera = Camera.open(i);
          setStartPreview(camera, surfaceHolder);
          isBackCameraOn = false;
          break;
        }
      } else {
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
          releaseCamera();
          camera = Camera.open(i);
          setStartPreview(camera, surfaceHolder);
          isBackCameraOn = true;
          break;
        }
      }
    }
  }

  @Override
  public void onPause() {
    releaseCamera();
    super.onPause();
  }

  private void releaseCamera() {
    if (camera != null) {
      camera.setPreviewCallback(null);
      camera.stopPreview();
      camera.release();
      camera = null;
    }
  }

  private Camera getCamera() {
    Camera camera;
    try {
      camera = Camera.open();
    } catch (Exception e) {
      camera = null;
    }
    return camera;
  }

  private boolean checkCameraHardware(Context context) {
    return context.getPackageManager().hasSystemFeature(
      PackageManager.FEATURE_CAMERA);
  }

  private void setStartPreview(Camera camera, SurfaceHolder holder) {
    try {
      camera.setPreviewDisplay(holder);
      camera.setDisplayOrientation(90);
      camera.startPreview();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}