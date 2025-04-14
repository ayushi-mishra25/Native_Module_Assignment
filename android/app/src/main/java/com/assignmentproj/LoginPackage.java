package com.assignmentproj;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class LoginPackage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext context) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new LoginModule(context));
        return modules;
    }

    @Override
    public List createViewManagers(ReactApplicationContext context) {
        return Collections.emptyList();
    }
}
