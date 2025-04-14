package com.assignmentproj;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class LoginModule extends ReactContextBaseJavaModule {

    LoginModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "LoginModule";
    }

    @ReactMethod
    public void showLoginScreen() {
        Intent intent = new Intent(getCurrentActivity(), LoginActivity.class);
        getCurrentActivity().startActivity(intent);
    }
}
