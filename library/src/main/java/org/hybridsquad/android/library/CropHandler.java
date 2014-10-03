package org.hybridsquad.android.library;

import android.net.Uri;

/**
 * Created with Android Studio.
 * User: ryan@xisue.com
 * Date: 10/1/14
 * Time: 11:00 AM
 * Desc: CropHandler
 * Revision:
 * - 10:20 2014/10/01 The basic interfaces.
 */
public interface CropHandler {

    void onPhotoCropped(Uri uri);

    void onCropCancel();

    void onCropFailed(String message);
}
