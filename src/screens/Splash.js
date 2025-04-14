import {StyleSheet, Text, View, Animated, StatusBar} from 'react-native';
import React, {useEffect, useRef} from 'react';
import Commonstyles, {COLORS} from '../Styles/Commonstyles';
import {useNavigation} from '@react-navigation/native';

const Splash = () => {
  const fadeAnim = useRef(new Animated.Value(0)).current;
  const navigation = useNavigation();

  useEffect(() => {
    Animated.timing(fadeAnim, {
      toValue: 1,
      duration: 2000,
      useNativeDriver: true,
    }).start();
  }, [fadeAnim]);

  
  useEffect(() => {
    setTimeout(() => {
      navigation.navigate('Login');
    }, 3000);
  });
  return (
    <View style={[Commonstyles.container, {backgroundColor: COLORS.primary}]}>
      <StatusBar backgroundColor={COLORS.primary} />
      <Animated.Text
        style={[
          styles.title,
          {
            opacity: fadeAnim,
          },
        ]}>
        RTC Tek
      </Animated.Text>
    </View>
  );
};

export default Splash;

const styles = StyleSheet.create({
  title: {
    fontWeight: '600',
    color: COLORS.white,
    fontSize: 24,
  },
});
