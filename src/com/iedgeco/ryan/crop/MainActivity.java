package com.iedgeco.ryan.crop;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	//data
	private static final String TAG = "MainActivity";
	private static final int TAKE_BIG_PICTURE = 1;
	private static final int TAKE_SMALL_PICTURE = 2;
	private static final int CROP_BIG_PICTURE = 3;
	private static final int CROP_SMALL_PICTURE = 4;
	private static final int CHOOSE_BIG_PICTURE = 5;
	private static final int CHOOSE_SMALL_PICTURE = 6;
	private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";
	private Uri imageUri;//to store the big bitmap
	
	//views
	private ImageView imageView;
	
	private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 2);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		// android4.3 nexus7 测试，相机拍摄后使用Uri方式存储并裁剪
		// 若设置了MediaStore.EXTRA_OUTPUT这个参数，裁剪后的图片为0kb
		// onActivityResult中得到的resultCode为0。
		//去掉下面这句之后就能正常裁剪了
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}
	
	private Bitmap decodeUriAsBitmap(Uri uri){
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	
	/** Handle click events */
	public void onClick(View v){
		Intent intent = null;
		switch (v.getId()) {
		case R.id.buttonTakeBigPicture:
			if(imageUri == null)
				Log.e(TAG, "image uri can't be null");
			//capture a big bitmap and store it in Uri
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, TAKE_BIG_PICTURE);
			break;
		case R.id.buttonTakeSmallPicture:
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, TAKE_SMALL_PICTURE);
			break;
		case R.id.buttonChooseBigPicture:
			intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 2);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 600);
			intent.putExtra("outputY", 300);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", false);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", false); // no face detection
			startActivityForResult(intent, CHOOSE_BIG_PICTURE);
			break;
		case R.id.buttonChooseSmallPicture:
			intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 2);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 100);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", true); // no face detection
			startActivityForResult(intent, CHOOSE_SMALL_PICTURE);
			break;
		default:
			break;
		}
	}
	
	/** Activity life cycle */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//views
		imageView = (ImageView) findViewById(R.id.imageView);
		
		//instantiate
		imageUri = Uri.parse(IMAGE_FILE_LOCATION);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode != Activity.RESULT_OK){//result is not correct
			Log.e(TAG, "requestCode = " + requestCode);
			Log.e(TAG, "resultCode = " + resultCode);
			Log.e(TAG, "data = " + data);
			return;
		}else{
			switch (requestCode) {
			case TAKE_BIG_PICTURE:
				Log.d(TAG, "TAKE_BIG_PICTURE: data = " + data);//it seems to be null
				//TODO sent to crop
				cropImageUri(imageUri, 780, 600, CROP_BIG_PICTURE);
				//or decode as bitmap and display it
				// if(imageUri != null){
				// Bitmap bitmap = decodeUriAsBitmap(imageUri);
				// imageView.setImageBitmap(bitmap);
				// }
				break;
			case CROP_BIG_PICTURE://from crop_big_picture
				Log.d(TAG, "CROP_BIG_PICTURE: data = " + data);//it seems to be null
				if(imageUri != null){
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					imageView.setImageBitmap(bitmap);
				}
				break;
			case TAKE_SMALL_PICTURE:
				Log.i(TAG, "TAKE_SMALL_PICTURE: data = " + data);
				//TODO sent to crop
				cropImageUri(imageUri, 260, 200, CROP_SMALL_PICTURE);
				//or decode as bitmap and display it
				// if(imageUri != null){
				// Bitmap bitmap = decodeUriAsBitmap(imageUri);
				// imageView.setImageBitmap(bitmap);
				// }
				break;
			case CROP_SMALL_PICTURE:
				if(imageUri != null){
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					imageView.setImageBitmap(bitmap);
				}else{
					Log.e(TAG, "CROP_SMALL_PICTURE: data = " + data);
				}
				break;
			case CHOOSE_BIG_PICTURE:
				Log.d(TAG, "CHOOSE_BIG_PICTURE: data = " + data);//it seems to be null
				if(imageUri != null){
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					imageView.setImageBitmap(bitmap);
				}
				break;
			case CHOOSE_SMALL_PICTURE:
				if(data != null){
					Bitmap bitmap = data.getParcelableExtra("data");
					imageView.setImageBitmap(bitmap);
				}else{
					Log.e(TAG, "CHOOSE_SMALL_PICTURE: data = " + data);
				}
				break;
			default:
				break;
			}
		}
	}
}
