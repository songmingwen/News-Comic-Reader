import 'package:flutter/services.dart';

class SRouter {
  static const MethodChannel _aRouterChannel =
      const MethodChannel("plugins.flutter.song.arouter");

  static Future<bool> navigation(
      String path, Map<String, dynamic> query) async {
    return await _aRouterChannel.invokeMethod(path, {"query": query});
  }
}
