package com.song.sunset.beans.basebeans;

import java.io.Serializable;
import java.util.List;

public interface PageEntity<T> extends Serializable {
	int getOnePageCount();
	int getCurrentPage();
	boolean isHasMore();
	List<T> getData();
}
