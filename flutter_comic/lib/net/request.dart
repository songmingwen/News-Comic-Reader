class Request {
  String type;
  String url;
  Map<String, String> header;
  Map<String, dynamic> query;
  Map<String, dynamic> body;

  Request(this.type, this.url, {this.header, this.query, this.body});
}
