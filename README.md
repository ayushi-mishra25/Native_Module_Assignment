✅ Stack Navigation Setup
Implemented Stack Navigation to handle screen transitions smoothly. Currently includes two screens:

Splash Screen

Login Screen

📱 Login Screen UI
Designed the Login Screen with three interactive buttons:

Show Toast

Capture Image

Go to Login Activity

🔔 Custom Toast Integration
Added a custom native module for showing toasts:

Created CustomToast package with ToastModule.java

Registered it in MainApplication.java

Designed a custom layout for the toast under res/layout

Integrated the native toast functionality directly within the Login screen

🔐 Native Login Activity
Integrated a native Android login screen:

Created LoginActivity.java, LoginModule.java, and LoginPackage.java

Registered everything in MainApplication.java

Added the activity in AndroidManifest.xml:

xml
Copy
Edit
<activity android:name=".LoginActivity" />
Invoked this activity from the React Native Login screen using the native module

📷 Native Camera Capture
Built image capture functionality using the device camera:

Created CameraModule.java and CameraPackage.java

Set up file_paths.xml under res/xml for file access

Registered the camera module in MainApplication.java

Handled runtime camera permissions and native intent calls from the Login screen

✅ Tested on a real device — image capture works as expected
🚫 Not supported on simulators due to lack of camera hardware
