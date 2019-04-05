**This repository is deprecated and will be maintained no more.**

# PhotoCropper

- [Sample](#sample)
- [Usage](#usage)
  - [IMPORTANT NOTICE!](#important-notice)
  - [Step 1](#step-1)
  - [Step 2](#step-2)
  - [Step 3](#step-3)
    - [Crop from camera](#crop-from-camera)
    - [Crop from gallery](#crop-from-gallery)
  - [Step 4](#step-4)
- [Dependency Support](#dependency-support)
  - [Gradle](#gradle)
  - [Maven](#maven)
- [LICENSE](#license)

[ ![Download](https://api.bintray.com/packages/ryanhoo/maven/PhotoCropper/images/download.svg) ](https://bintray.com/ryanhoo/maven/PhotoCropper/_latestVersion)

PhotoCropper is a light-weight but sharp and smart tool to help you cropping photos on android devices. By providing a simple callback interface for developers and encapsulating the tricky things of cropping photos into a library. It makes the logic much more easier and simpler.

If you want to learn more details about this, my blogs might help you.

- [Android 大图片裁剪终极解决方案（上：原理分析）][2]
- [Android 大图片裁剪终极解决方案（中：从相册截图）][3]
- [Android 大图片裁剪终极解决方案（下：拍照截图）][4]

## Sample

![photo cropper demonstration][1]

## Usage

It's really easy to use, but always remember:

### IMPORTANT NOTICE!

**Always clear your cached cropped images from last time.**

```java
// MUST!! Clear Last Cached Image
CropHelper.clearCachedCropFile(mCropParams.uri);

startActivityForResult(CropHelper.buildCropFromGalleryIntent(mCropParams), CropHelper.REQUEST_CROP);
```

### Step 1

First you need a ``CropHandler`` to handle the activity results of cropping photos.

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    CropHelper.handleResult(cropHandler, requestCode, resultCode, data);
}
```

### Step 2

Make sure you implemented these methods:

```java
@Override
public void onPhotoCropped(Uri uri) {
    // The cropped result
    // uri relates to the cropped photo
    // ...
}

@Override
public Activity getContext() {
    // MUST NOT be null
    return mContext;
}

@Override
public CropParams getCropParams() {
    // Your own crop options, simply call new CropParams() will give you a set of default crop options 
    mCropParams = new CropParams();
    return mCropParams;
}

@Override
public void onCropFailed(String message) {
    // ...
}

@Override
public void onCropCancel() {
    // ...
}
```

### Step 3

Launch a request to crop photos.

#### Crop from camera

```java
Intent intent = CropHelper.buildCaptureIntent(mCropParams.uri);
startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
```

#### Crop from gallery

```java
Intent intent = CropHelper.buildCropFromGalleryIntent(mCropParams);
startActivityForResult(intent, CropHelper.REQUEST_CROP);
```
### Step 4

Clear the cached image if possible.

```java
@Override
protected void onDestroy() {
    if (cropHandler.getCropParams() != null)
        CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
    super.onDestroy();
}
```

## Dependency Support

### Gradle

```
compile 'org.hybridsquad.android.photocropper:library:2.1.0'
```

### Maven

```
<dependency>
        <groupId>org.hybridsquad.android.photocropper</groupId>
        <artifactId>library</artifactId>
        <version>2.1.0</version>
        <type>jar</type>
        <classifier>sources</classifier>
</dependency>
```

## LICENSE
The MIT License (MIT)

Copyright (c) 2014 [Ryan Hoo][5]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

[1]: /images/photo-cropper-demonstration.gif
[2]: http://ryanhoo.github.io/blog/2014/05/26/the-ultimate-approach-to-crop-photos-on-android-1
[3]: http://ryanhoo.github.io/blog/2014/06/03/the-ultimate-approach-to-crop-photos-on-android-2
[4]: http://ryanhoo.github.io/blog/2014/06/03/the-ultimate-approach-to-crop-photos-on-android-3
[5]: mailto:ryan.hoo.j@gmail.com
