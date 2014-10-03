package org.hybridsquad.android.library;

import android.app.Activity;
import android.net.Uri;

/**
 * Created with Android Studio.
 * User: ryan@xisue.com
 * Date: 10/1/14
 * Time: 11:00 AM
 * Desc: CropHandler
 * Revision:
 * - 10:20 2014/10/01 The basic interfaces.
 * - 13:00 2014/10/03 Able to get access to the CropParams the related Context.
 */
public interface CropHandler {

    void onPhotoCropped(Uri uri);

    void onCropCancel();

    void onCropFailed(String message);

    CropParams getCropParams();

    Activity getContext();
}
