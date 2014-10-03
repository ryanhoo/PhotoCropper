package org.hybridsquad.android.library;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created with Android Studio.
 * User: ryan@xisue.com
 * Date: 10/3/14
 * Time: 11:12 AM
 * Desc: CropParams
 * Revision:
 * - 11:00 2014/10/03 Encapsulate the crop params.
 */
public class CropParams {

    public static final String CROP_TYPE = "image/*";
    public static final String OUTPUT_FORMAT = Bitmap.CompressFormat.JPEG.toString();

    public static final int DEFAULT_ASPECT = 1;
    public static final int DEFAULT_OUTPUT = 200;

    public Uri uri = CropHelper.buildUri();

    public String type = CROP_TYPE;
    public String outputFormat = OUTPUT_FORMAT;

    public boolean crop = true;
    public boolean scale = true;
    public boolean returnData = false;
    public boolean noFaceDetection = true;

    public int aspectX = DEFAULT_ASPECT;
    public int aspectY = DEFAULT_ASPECT;

    public int outputX = DEFAULT_OUTPUT;
    public int outputY = DEFAULT_OUTPUT;
}
