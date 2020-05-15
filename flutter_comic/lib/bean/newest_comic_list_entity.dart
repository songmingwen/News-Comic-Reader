import 'package:flutter_comic/generated/json/base/json_convert_content.dart';

class NewestComicListEntity with JsonConvert<NewestComicListEntity> {
	int code;
	String msg;
	NewestComicListData data;
}

class NewestComicListData with JsonConvert<NewestComicListData> {
	int stateCode;
	String message;
	NewestComicListDataReturnData returnData;
}

class NewestComicListDataReturnData with JsonConvert<NewestComicListDataReturnData> {
	int page;
	bool hasMore;
	List<NewestComicListDataReturnDataComic> comics;
}

class NewestComicListDataReturnDataComic with JsonConvert<NewestComicListDataReturnDataComic> {
	String author;
	int comicId;
	String conTag;
	String cover;
	String description;
	int flag;
	String name;
	List<String> tags;
}
