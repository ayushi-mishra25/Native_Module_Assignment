import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Splash from '../screens/Splash';
import { NavigationContainer } from '@react-navigation/native';
import Login from '../screens/Login.js/Index';


const Stack = createNativeStackNavigator();
export default function RootNavigation() {
  return (
    <NavigationContainer>
      <Stack.Navigator screenOptions={{headerShown:false}}>
        <Stack.Screen
          name="Splash"
          component={Splash}
      
        />
         <Stack.Screen
          name="Login"
          component={Login}
      
        />
       
      </Stack.Navigator>
    </NavigationContainer>
  )
}

const styles = StyleSheet.create({})