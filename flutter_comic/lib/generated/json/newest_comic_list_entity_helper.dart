import 'package:flutter_comic/bean/newest_comic_list_entity.dart';

newestComicListEntityFromJson(
    NewestComicListEntity data, Map<String, dynamic> json) {
  if (json['code'] != null) {
    data.code = json['code']?.toInt();
  }
  if (json['msg'] != null) {
    data.msg = json['msg']?.toString();
  }
  if (json['data'] != null) {
    data.data = new NewestComicListData().fromJson(json['data']);
  }
  return data;
}

Map<String, dynamic> newestComicListEntityToJson(NewestComicListEntity entity) {
  final Map<String, dynamic> data = new Map<String, dynamic>();
  data['code'] = entity.code;
  data['msg'] = entity.msg;
  if (entity.data != null) {
    data['data'] = entity.data.toJson();
  }
  return data;
}

newestComicListDataFromJson(
    NewestComicListData data, Map<String, dynamic> json) {
  if (json['stateCode'] != null) {
    data.stateCode = json['stateCode']?.toInt();
  }
  if (json['message'] != null) {
    data.message = json['message']?.toString();
  }
  if (json['returnData'] != null) {
    data.returnData =
        new NewestComicListDataReturnData().fromJson(json['returnData']);
  }
  return data;
}

Map<String, dynamic> newestComicListDataToJson(NewestComicListData entity) {
  final Map<String, dynamic> data = new Map<String, dynamic>();
  data['stateCode'] = entity.stateCode;
  data['message'] = entity.message;
  if (entity.returnData != null) {
    data['returnData'] = entity.returnData.toJson();
  }
  return data;
}

newestComicListDataReturnDataFromJson(
    NewestComicListDataReturnData data, Map<String, dynamic> json) {
  if (json['page'] != null) {
    data.page = json['page']?.toInt();
  }
  if (json['hasMore'] != null) {
    data.hasMore = json['hasMore'];
  }
  if (json['comics'] != null) {
    data.comics = new List<NewestComicListDataReturnDataComic>();
    (json['comics'] as List).forEach((v) {
      data.comics.add(new NewestComicListDataReturnDataComic().fromJson(v));
    });
  }
  return data;
}

Map<String, dynamic> newestComicListDataReturnDataToJson(
    NewestComicListDataReturnData entity) {
  final Map<String, dynamic> data = new Map<String, dynamic>();
  data['page'] = entity.page;
  data['hasMore'] = entity.hasMore;
  if (entity.comics != null) {
    data['comics'] = entity.comics.map((v) => v.toJson()).toList();
  }
  return data;
}

newestComicListDataReturnDataComicFromJson(
    NewestComicListDataReturnDataComic data, Map<String, dynamic> json) {
  if (json['author'] != null) {
    data.author = json['author']?.toString();
  }
  if (json['comicId'] != null) {
    data.comicId = json['comicId']?.toInt();
  }
  if (json['conTag'] != null) {
    data.conTag = json['conTag']?.toString();
  }
  if (json['cover'] != null) {
    data.cover = json['cover']?.toString();
  }
  if (json['description'] != null) {
    data.description = json['description']?.toString();
  }
  if (json['flag'] != null) {
    data.flag = json['flag']?.toInt();
  }
  if (json['name'] != null) {
    data.name = json['name']?.toString();
  }
  if (json['tags'] != null) {
    data.tags =
        json['tags']?.map((v) => v?.toString())?.toList()?.cast<String>();
  }
  return data;
}

Map<String, dynamic> newestComicListDataReturnDataComicToJson(
    NewestComicListDataReturnDataComic entity) {
  final Map<String, dynamic> data = new Map<String, dynamic>();
  data['author'] = entity.author;
  data['comicId'] = entity.comicId;
  data['conTag'] = entity.conTag;
  data['cover'] = entity.cover;
  data['description'] = entity.description;
  data['flag'] = entity.flag;
  data['name'] = entity.name;
  data['tags'] = entity.tags;
  return data;
}
