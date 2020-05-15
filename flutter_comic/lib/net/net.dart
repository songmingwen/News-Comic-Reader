import 'package:flutter/services.dart';
import 'package:flutter_comic/net/request.dart';
import 'package:flutter_comic/net/response.dart';

class Net {
  static const MethodChannel _netServiceChannel =
      const MethodChannel("plugins.flutter.song.net");

  static Future<Response> netCall(Request request) async {
    return await _netServiceChannel
        .invokeMethod(request.type, {
          'url': request.url,
          'header': request.header,
          'query': request.query,
          'body': request.body
        })
        .then((result) async => Map<String, dynamic>.from(result))
        .then((result) => Response.fromJson(result));
  }

  static Future<Response> get(String url,
          {Map<String, String> header,
          Map<String, dynamic> query,
          Map<String, dynamic> body}) async =>
      netCall(Request("get", url, header: header, query: query));

  static Future<Response> post(String url,
          {Map<String, dynamic> header,
          Map<String, dynamic> query,
          Map<String, dynamic> body}) async =>
      netCall(Request("post", url, header: header, query: query, body: body));

  static Future<Response> put(String url,
          {Map<String, dynamic> header,
          Map<String, dynamic> query,
          Map<String, dynamic> body}) async =>
      netCall(Request("put", url, header: header, query: query, body: body));

  static Future<Response> delete(String url,
          {Map<String, dynamic> header,
          Map<String, dynamic> query,
          Map<String, dynamic> body}) async =>
      netCall(Request("delete", url, header: header, query: query, body: body));
}
