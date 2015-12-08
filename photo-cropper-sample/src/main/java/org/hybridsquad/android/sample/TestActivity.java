package org.hybridsquad.android.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created with Android Studio.
 * User: ryan@xisue.com
 * Date: 10/3/14
 * Time: 11:44 AM
 * Desc: TestActivity
 */
public class TestActivity extends AppCompatActivity implements CropHandler, View.OnClickListener {

    public static final String TAG = "TestActivity";

    ImageView mImageView;

    CropParams mCropParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        mCropParams = new CropParams(this);
        mImageView = (ImageView) findViewById(R.id.image);

        findViewById(R.id.bt_crop_capture).setOnClickListener(this);
        findViewById(R.id.bt_crop_gallery).setOnClickListener(this);
        findViewById(R.id.bt_capture).setOnClickListener(this);
        findViewById(R.id.bt_gallery).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mCropParams.refreshUri();
        switch (v.getId()) {
            case R.id.bt_crop_capture: {
                mCropParams.enable = true;
                mCropParams.compress = false;
                Intent intent = CropHelper.buildCameraIntent(mCropParams);
                startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
            }
            break;
            case R.id.bt_crop_gallery: {
                mCropParams.enable = true;
                mCropParams.compress = false;
                Intent intent = CropHelper.buildGalleryIntent(mCropParams);
                startActivityForResult(intent, CropHelper.REQUEST_CROP);
            }
            break;
            case R.id.bt_capture: {
                mCropParams.enable = false;
                mCropParams.compress = true;
                Intent intent = CropHelper.buildCameraIntent(mCropParams);
                startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
            }
            break;
            case R.id.bt_gallery: {
                mCropParams.enable = false;
                mCropParams.compress = true;
                Intent intent = CropHelper.buildGalleryIntent(mCropParams);
                startActivityForResult(intent, CropHelper.REQUEST_PICK);
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.e(TAG, "");
        }
    }

    @Override
    protected void onDestroy() {
        CropHelper.clearCacheDir();
        super.onDestroy();
    }

    @Override
    public CropParams getCropParams() {
        return mCropParams;
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        // Original or Cropped uri
        Log.d(TAG, "Crop Uri in path: " + uri.getPath());
        if (!mCropParams.compress)
            mImageView.setImageBitmap(BitmapUtil.decodeUriAsBitmap(this, uri));
    }

    @Override
    public void onCompressed(Uri uri) {
        // Compressed uri
        mImageView.setImageBitmap(BitmapUtil.decodeUriAsBitmap(this, uri));
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "Crop canceled!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, "Crop failed: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }
}
