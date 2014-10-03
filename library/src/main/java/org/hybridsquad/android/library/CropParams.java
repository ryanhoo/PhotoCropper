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
 * - 13:20 2014/10/03 Put the initialization into constructor method.
 */
public class CropParams {

    public static final String CROP_TYPE = "image/*";
    public static final String OUTPUT_FORMAT = Bitmap.CompressFormat.JPEG.toString();

    public static final int DEFAULT_ASPECT = 1;
    public static final int DEFAULT_OUTPUT = 200;

    public Uri uri;

    public String type;
    public String outputFormat;

    public boolean crop;
    public boolean scale;
    public boolean returnData;
    public boolean noFaceDetection;

    public int aspectX;
    public int aspectY;

    public int outputX;
    public int outputY;

    public CropParams() {
        uri = CropHelper.buildUri();
        type = CROP_TYPE;
        outputFormat = OUTPUT_FORMAT;
        crop = true;
        scale = true;
        returnData = false;
        noFaceDetection = true;
        aspectX = DEFAULT_ASPECT;
        aspectY = DEFAULT_ASPECT;
        outputX = DEFAULT_OUTPUT;
        outputY = DEFAULT_OUTPUT;
    }
}
