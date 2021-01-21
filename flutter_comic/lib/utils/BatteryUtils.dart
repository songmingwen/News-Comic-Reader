import 'package:flutter/services.dart';

class BatteryUtils {
  static const MethodChannel batteryMethodChannel =
      MethodChannel('plugins.flutter.song.battery');

  static Future<int> getBatteryLevel() async {
    return await batteryMethodChannel.invokeMethod('getBatteryLevel');
  }
}
