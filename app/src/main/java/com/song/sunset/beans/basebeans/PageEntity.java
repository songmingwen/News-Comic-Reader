package com.song.sunset.beans.basebeans;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author 13leaf
 *
 */
public interface PageEntity extends Serializable {
	int getPageSum();
	List<?> getData();
}
