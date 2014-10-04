package org.hybridsquad.android.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created with Android Studio.
 * User: ryan@xisue.com
 * Date: 10/1/14
 * Time: 11:08 AM
 * Desc: CropHelper
 * Revision:
 * - 10:00 2014/10/03 Basic utils.
 * - 11:30 2014/10/03 Add static methods for generating crop intents.
 * - 15:00 2014/10/03 Finish the logic of handling crop intents.
 * - 12:20 2014/10/04 Add "scaleUpIfNeeded" crop options for scaling up cropped images if the size is too small.
 */
public class CropHelper {

    public static final String TAG = "CropHelper";

    /**
     * request code of Activities or Fragments
     * You will have to change the values of the request codes below if they conflict with your own.
     */
    public static final int REQUEST_CROP = 127;
    public static final int REQUEST_CAMERA = 128;

    public static final String CROP_CACHE_FILE_NAME = "crop_cache_file.jpg";

    public static Uri buildUri() {
        return Uri
                .fromFile(Environment.getExternalStorageDirectory())
                .buildUpon()
                .appendPath(CROP_CACHE_FILE_NAME)
                .build();
    }

    public static void handleResult(CropHandler handler, int requestCode, int resultCode, Intent data) {
        if (handler == null) return;

        if (resultCode == Activity.RESULT_CANCELED) {
            handler.onCropCancel();
        } else if (resultCode == Activity.RESULT_OK) {
            CropParams cropParams = handler.getCropParams();
            if (cropParams == null) {
                handler.onCropFailed("CropHandler's params MUST NOT be null!");
                return;
            }
            switch (requestCode) {
                case REQUEST_CROP:
                    Log.d(TAG, "Photo cropped!");
                    handler.onPhotoCropped(handler.getCropParams().uri);
                    break;
                case REQUEST_CAMERA:
                    Intent intent = buildCropFromUriIntent(handler.getCropParams());
                    Activity context = handler.getContext();
                    if (context != null) {
                        context.startActivityForResult(intent, REQUEST_CROP);
                    } else {
                        handler.onCropFailed("CropHandler's context MUST NOT be null!");
                    }
                    break;
            }
        }
    }

    public static boolean clearCachedCropFile(Uri uri) {
        if (uri == null) return false;

        File file = new File(uri.getPath());
        if (file.exists()) {
            boolean result = file.delete();
            if (result)
                Log.i(TAG, "Cached crop file cleared.");
            else
                Log.e(TAG, "Failed to clear cached crop file.");
            return result;
        } else {
            Log.w(TAG, "Trying to clear cached crop file but it does not exist.");
        }
        return false;
    }

    public static Intent buildCropFromUriIntent(CropParams params) {
        return buildCropIntent("com.android.camera.action.CROP", params);
    }

    public static Intent buildCropFromGalleryIntent(CropParams params) {
        return buildCropIntent(Intent.ACTION_GET_CONTENT, params);
    }

    public static Intent buildCaptureIntent(Uri uri) {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        .putExtra(MediaStore.EXTRA_OUTPUT, uri);
    }

    public static Intent buildCropIntent(String action, CropParams params) {
        return new Intent(action, null)
                .setDataAndType(params.uri, params.type)
                        //.setType(params.type)
                .putExtra("crop", params.crop)
                .putExtra("scale", params.scale)
                .putExtra("aspectX", params.aspectX)
                .putExtra("aspectY", params.aspectY)
                .putExtra("outputX", params.outputX)
                .putExtra("outputY", params.outputY)
                .putExtra("return-data", params.returnData)
                .putExtra("outputFormat", params.outputFormat)
                .putExtra("noFaceDetection", params.noFaceDetection)
                .putExtra("scaleUpIfNeeded", params.scaleUpIfNeeded)
                .putExtra(MediaStore.EXTRA_OUTPUT, params.uri);
    }

    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        if (context == null || uri == null) return null;

        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}
