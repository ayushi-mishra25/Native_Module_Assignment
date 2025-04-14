// package com.assignmentproj; // ✅ Corrected: Removed "public"

// import android.app.Activity;
// import android.content.Intent;
// import android.net.Uri;
// import android.os.Environment;
// import android.provider.MediaStore;
// import android.util.Log; // Corrected: moved import here

// import androidx.core.content.FileProvider;

// import com.facebook.react.bridge.ActivityEventListener;
// import com.facebook.react.bridge.Promise;
// import com.facebook.react.bridge.ReactApplicationContext;
// import com.facebook.react.bridge.ReactContextBaseJavaModule;
// import com.facebook.react.bridge.ReactMethod;

// import java.io.File;
// import java.io.IOException;
// import java.text.SimpleDateFormat;
// import java.util.Date;

// public class CameraModule extends ReactContextBaseJavaModule implements ActivityEventListener {
//     private static final int REQUEST_IMAGE_CAPTURE = 1;
//     private Promise cameraPromise;
//     private String currentPhotoPath;
//     private static final String TAG = "CameraModule"; // Corrected: moved declaration here

//     public CameraModule(ReactApplicationContext reactContext) {
//         super(reactContext);
//         reactContext.addActivityEventListener(this);
//         Log.d(TAG, "CameraModule initialized");
//     }

//     @Override
//     public String getName() {
//         return "CameraModule";
//     }

//     @ReactMethod
//     public void captureImage(Promise promise) {
//         Log.d(TAG, "captureImage called");
//         Activity activity = getCurrentActivity();
//         if (activity == null) {
//             Log.e(TAG, "Activity doesn't exist");
//             promise.reject("NO_ACTIVITY", "Activity doesn't exist");
//             return;
//         }

//         Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//         if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
//             File photoFile;
//             try {
//                 photoFile = createImageFile(activity);
//             } catch (IOException ex) {
//                 Log.e(TAG, "Could not create image file", ex);
//                 promise.reject("FILE_CREATION_FAILED", "Could not create image file");
//                 return;
//             }

//             if (photoFile != null) {
//                 Uri photoURI = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", photoFile);
//                 takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                 cameraPromise = promise;
//                 activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                 Log.d(TAG, "Starting camera activity");
//             }
//         } else {
//             Log.e(TAG, "Camera is not available");
//             promise.reject("NO_CAMERA", "Camera is not available");
//         }
//     }

//     private File createImageFile(Activity activity) throws IOException {
//         Log.d(TAG, "Creating image file");
//         String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//         String imageFileName = "JPEG_" + timeStamp + "_";
//         File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//         File image = File.createTempFile(imageFileName, ".jpg", storageDir);
//         currentPhotoPath = image.getAbsolutePath();
//         Log.d(TAG, "Image file created: " + currentPhotoPath);
//         return image;
//     }

//     @Override
//     public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
//         Log.d(TAG, "onActivityResult called");
//         if (requestCode == REQUEST_IMAGE_CAPTURE) {
//             if (resultCode == Activity.RESULT_OK) {
//                 Log.d(TAG, "Camera capture successful");
//                 cameraPromise.resolve(currentPhotoPath);
//             } else {
//                 Log.e(TAG, "Camera capture was cancelled");
//                 cameraPromise.reject("CANCELLED", "Camera capture was cancelled");
//             }
//         }
//     }

//     @Override
//     public void onNewIntent(Intent intent) {
//         // Not used
//         Log.d(TAG, "onActivityResult called");
//     }
// }

package com.assignmentproj;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class CameraModule extends ReactContextBaseJavaModule {

    private static final int IMAGE_PICKER_REQUEST = 1;
    private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
    private static final String E_PICKER_CANCELLED = "E_PICKER_CANCELLED";
    private static final String E_FAILED_TO_SHOW_PICKER = "E_FAILED_TO_SHOW_PICKER";
    private static final String E_NO_IMAGE_DATA_FOUND = "E_NO_IMAGE_DATA_FOUND";

    private Promise mPickerPromise;

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
            if (requestCode == IMAGE_PICKER_REQUEST) {
                if (mPickerPromise != null) {
                    if (resultCode == Activity.RESULT_CANCELED) {
                        mPickerPromise.reject(E_PICKER_CANCELLED, "Image picker was cancelled");
                    } else if (resultCode == Activity.RESULT_OK) {
                        Uri uri = intent.getData();
                        if (uri == null) {
                            mPickerPromise.reject(E_NO_IMAGE_DATA_FOUND, "No image data found");
                        } else {
                            mPickerPromise.resolve(uri.toString());
                        }
                    }
                    mPickerPromise = null;
                }
            }
        }
    };

    public CameraModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "ImagePickerModule";
    }

    @ReactMethod
    public void pickImage(final Promise promise) {
        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
            return;
        }

        mPickerPromise = promise;

        try {
            final Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");

            final Intent chooserIntent = Intent.createChooser(galleryIntent, "Pick an image");
            currentActivity.startActivityForResult(chooserIntent, IMAGE_PICKER_REQUEST);
        } catch (Exception e) {
            mPickerPromise.reject(E_FAILED_TO_SHOW_PICKER, e);
            mPickerPromise = null;
        }
    }
}
