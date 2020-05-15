import 'dart:convert';

/// desc 需要对 data 进行 decode 转成 Map<String, dynamic>
/// 可以直接用 getData()
class Response {
  int code;

  String msg;

  dynamic headers;

  dynamic data;

  Map<String, dynamic> _response;

  dynamic toJson() => _response;

  Response();

  factory Response.fromJson(Map<String, dynamic> response) {
    return Response()
      ..code = response["code"]
      ..msg = response["msg"]
      ..headers = response["headers"]
      ..data = response["data"]
      .._response = response;
  }
}
