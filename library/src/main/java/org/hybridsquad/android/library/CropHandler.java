package org.hybridsquad.android.library;

import android.net.Uri;

/**
 * Created with Android Studio.
 * User: ryan@xisue.com
 * Date: 10/1/14
 * Time: 11:00 AM
 * Desc: CropHandler
 */
public interface CropHandler {

    void onPhotoCropped(Uri uri);

    void onCropCancel();

    void onCropFailed();
}
