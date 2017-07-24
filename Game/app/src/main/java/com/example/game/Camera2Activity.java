package com.example.game;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Comparator;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2Activity extends AppCompatActivity {

    private CameraDevice mCameraDevice = null;
    private CaptureRequest.Builder mCaptureRequestBuilder = null;
    private CameraCaptureSession mCameraCaptureSession  = null;
    private TextureView mTextureView = null;
    private Size mPreviewSize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        Toast.makeText(Camera2Activity.this, "Nėra duomenų", Toast.LENGTH_LONG).show();

        mTextureView = (TextureView) findViewById(R.id.textureView);
        mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);

        final FloatingActionButton mapAction = (FloatingActionButton) findViewById(R.id.action_map);
        mapAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /// MAP INTENT
                Toast.makeText(Camera2Activity.this, "Nėra duomenų", Toast.LENGTH_LONG).show();
                Intent homeIntent = new Intent(Camera2Activity.this, MapsActivity.class);
                startActivity(homeIntent);
            }
        });


       /* View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.action_map:
                        Intent homeIntent = new Intent(Camera2Activity.this, MapsActivity.class);
                        startActivity(homeIntent);
                        //finish();
                        Toast.makeText(getApplicationContext(), "TEXT", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        } ;

        mapAction.setOnClickListener(onClickListener);
*/

    }

    static class CompareSizesByArea implements Comparator<Size> {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public int compare(Size lhs, Size rhs) {
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() - (long) rhs.getWidth() * rhs.getHeight());
        }
    }

    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice = camera;
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            if (texture == null) {
                return;
            }
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            Surface surface = new Surface(texture);
            try {
                mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            } catch (CameraAccessException e){
                e.printStackTrace();
            }
            mCaptureRequestBuilder.addTarget(surface);
            try {
                mCameraDevice.createCaptureSession(Arrays.asList(surface), mPreviewStateCallback, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onError(CameraDevice camera, int error) {}
        @Override
        public void onDisconnected(CameraDevice camera) {

        }
    };

    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }
    };

    private CameraCaptureSession.StateCallback mPreviewStateCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession session) {
            startPreview(session);
        }
        @Override
        public void onConfigureFailed(CameraCaptureSession session) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (mCameraDevice != null) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mTextureView.isAvailable()) {
            openCamera();
        } else {
            mTextureView.setSurfaceTextureListener(
                    mSurfaceTextureListener);
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try{
            String cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            mPreviewSize = map.getOutputSizes(SurfaceTexture.class) [0];
            manager.openCamera(cameraId, mStateCallback, null);
        } catch(CameraAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void startPreview(CameraCaptureSession session) {
        mCameraCaptureSession = session;
        mCaptureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        HandlerThread backgroundThread = new HandlerThread("CameraPreview");
        backgroundThread.start();
        Handler backgroundHandler = new Handler(backgroundThread. getLooper());
        try {
            mCameraCaptureSession.setRepeatingRequest(mCaptureRequestBuilder.build(), null, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

}
