import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_comic/bean/newest_comic_list_entity.dart';
import 'package:flutter_comic/net/response.dart';
import 'package:flutter_comic/router/arouter.dart';
import 'package:flutter_comic/utils/BatteryUtils.dart';

import 'net/net.dart';

void main() {
  runApp(ComicList());
}

class ComicList extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return ComicStateList();
  }
}

class ComicStateList extends State<ComicList> {
  static const EventChannel _eventChannel =
      const EventChannel("plugins.flutter.song.sensor");

  StreamSubscription _streamSubscription;

  List<NewestComicListDataReturnDataComic> comicList = List();

  ScrollController _scrollController = ScrollController(); //listview的控制器
  int _page = 1; //加载的页数
  bool isLoading = false; //是否正在加载数据

  @override
  void initState() {
    super.initState();
    printBatteryLevel();
    print('sensor_event:initState');
    _streamSubscription = _eventChannel.receiveBroadcastStream().listen((dynamic event) {
      print('sensor_event_listen_success: $event');
    }, onError: (dynamic error) {
      print('sensor_event_listen_error: ${error.message}');
    }, cancelOnError: true);

    _getData();
    _scrollController.addListener(() {
      if (_scrollController.position.pixels ==
          _scrollController.position.maxScrollExtent) {
        print('ComicList------滑动到了最底部');
        _getMore();
      }
    });
  }

  @override
  void deactivate() {
    cancelSensor();
    super.deactivate();
  }

  void cancelSensor() {
    print('sensor_event:cancelSensor');
    if (_streamSubscription != null) {
      _streamSubscription.cancel();
      _streamSubscription = null;
    }
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter 最新漫画',
      home: Scaffold(
        appBar: AppBar(
          title: Text('Flutter 最新漫画'),
        ),
        body: Center(child: _buildComicList()),
      ),
    );
  }

  void printBatteryLevel() async{
    int level = await BatteryUtils.getBatteryLevel();
    print("getBatteryLevel = " + level.toString());
  }

  void _getData() async {
    Response response = await Net.get("/list/commonComicList",
        query: {'page': _page, 'argName': 'sort', "argValue": 0},
        header: {'x-header': 'flutter'}); // 拉热榜接口
    var jsonResponse = jsonDecode(response.data);

    var data = NewestComicListEntity().fromJson(jsonResponse);

    setState(() {
      comicList.addAll(data.data.returnData.comics);
      _page++;
    });
  }

  void _getMore() async {
    if (isLoading) {
      return;
    }
    setState(() {
      isLoading = true;
    });
    Response response = await Net.get("/list/commonComicList",
        query: {'page': _page, 'argName': 'sort', "argValue": 0},
        header: {'x-header': 'flutter'}); // 拉热榜接口
    var jsonResponse = jsonDecode(response.data);

    var data = NewestComicListEntity().fromJson(jsonResponse);

    setState(() {
      comicList.addAll(data.data.returnData.comics);
      _page++;
      isLoading = false;
    });
  }

  Future<Null> _onRefresh() async {
    _page = 1;
    Response response = await Net.get("/list/commonComicList",
        query: {'page': _page, 'argName': 'sort', "argValue": 0},
        header: {'x-header': 'flutter'}); // 拉热榜接口
    var jsonResponse = jsonDecode(response.data);

    var data = NewestComicListEntity().fromJson(jsonResponse);

    setState(() {
      comicList.clear();
      comicList.addAll(data.data.returnData.comics);
      _page++;
    });
  }

  Widget _buildComicList() => RefreshIndicator(
        onRefresh: () => _onRefresh(),
        child: ListView.builder(
          controller: _scrollController,
          itemCount: comicList.length + 1,
          itemBuilder: (context, index) {
            if (index < comicList.length) {
              return _comicCard(comicList[index]);
            }
            //如果是最后一个 item 显示加载更多控件
            return _getMoreWidget();
          },
        ),
      );

  Widget _getMoreWidget() {
    return Padding(
      padding: EdgeInsets.all(20.0),
      child: Text(
        "加载更多...",
        textAlign: TextAlign.center,
      ),
    );
  }

  Widget _comicCard(NewestComicListDataReturnDataComic comic) {
    return GestureDetector(
      onTap: () => _onClick(comic),
      child: Container(
        height: 150,
        margin: EdgeInsets.fromLTRB(5, 0, 5, 0),
        child: Card(
          child: Padding(
            padding: EdgeInsets.fromLTRB(0, 0, 5, 0),
            child: Row(
              children: <Widget>[
                Padding(
                  padding: EdgeInsets.fromLTRB(0, 0, 5, 0),
                  child: ClipRRect(
                    borderRadius: BorderRadius.only(
                        topLeft: Radius.circular(4),
                        bottomLeft: Radius.circular(4)),
                    child: Image.network(
                      comic.cover,
                      width: 110,
                      height: 150,
                      fit: BoxFit.fill,
                    ),
                  ),
                ),
                Expanded(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      Text(
                        comic.name,
                        style: TextStyle(
                          fontSize: 18,
                          color: Colors.grey[900],
                        ),
                      ),
                      Text(
                        comic.author,
                        style: TextStyle(
                          fontSize: 13,
                          color: Colors.grey[800],
                        ),
                      ),
                      Text(
                        comic.tags.toString(),
                        style: TextStyle(
                          fontSize: 13,
                          color: Colors.grey[800],
                        ),
                      ),
                      Text(
                        comic.description,
                        maxLines: 2,
                        style: TextStyle(
                          fontSize: 12,
                          color: Colors.grey[700],
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  _onClick(NewestComicListDataReturnDataComic comic) {
    SRouter.navigation("/comic/detail", {"comic_id": comic.comicId});
  }
}
