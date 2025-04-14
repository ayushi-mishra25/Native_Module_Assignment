import React, {useState} from 'react';
import {View, Button, Alert, StyleSheet, Platform, Image,NativeModules, PermissionsAndroid} from 'react-native';

const {CameraModule, LoginModule,ToastExample,CalendarModule} = NativeModules;


const Login = () => {
  const [imagePath, setImagePath] = useState(null);

  const handleCreateEvent = async () => {
    try {
      const eventId = await CalendarModule.createCalendarEvent(
        'Party',
        'My House',
      );
      console.log(`Created a new event with id ${eventId}`);
    } catch (e) {
      console.error(e);
    }
  };
  
  
  
  async function requestCameraPermission() {
    if (Platform.OS === 'android') {
      try {
        const granted = await PermissionsAndroid.request(
          PermissionsAndroid.PERMISSIONS.CAMERA,
          {
            title: 'Camera Permission',
            message: 'App needs access to your camera',
            buttonNeutral: 'Ask Me Later',
            buttonNegative: 'Cancel',
            buttonPositive: 'OK',
          },
        );
        return granted === PermissionsAndroid.RESULTS.GRANTED;
      } catch (err) {
        console.warn(err);
        return false;
      }
    } else {
      return true; // iOS handled separately
    }
  }

  const handleCaptureImage = async () => {
    const hasPermission = await requestCameraPermission();
    console.log(hasPermission,'hasPermission')
    if (!hasPermission) {
      Alert.alert(
        'Permission Denied',
        'Camera and Storage permissions are required.',
      );
      return;
    }

    try {
      const imagePath = await CameraModule.captureImage();
      // Alert.alert('Image Captured', `Saved to: ${imagePath}`);
      console.log('Image Path:', imagePath);
      setImagePath(`file://${imagePath}`);
    } catch (error) {
      // Alert.alert('Error', error.message || 'Failed to capture image.');
      console.log('Capture Error:', error);
    }
  };
  

  return (
    <View style={styles.container}>
      {imagePath && (
        <Image
          source={{uri: imagePath}}
          style={styles.image}
          resizeMode="contain"
        />
      )}
      <View style={{flexDirection: 'row', gap: 10, flexWrap:'wrap', justifyContent:'center'}}>
        <Button
          title="Login"
          onPress={() => {
            LoginModule.showLoginScreen();
          }}
        />
        <Button title="Capture Image" onPress={handleCaptureImage} />
        <Button
        title="Show Toast"
        onPress={() => ToastExample?.show('Hello from Native!', ToastExample.SHORT)}
      />
          {/* <Button title="Create Calendar Event" onPress={handleCreateEvent} /> */}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  image: {
    marginBottom: 5,
    width: '75%',
    height: '50%',
    borderRadius: 30,
    resizeMode: 'contain',
  },
});

export default Login;
