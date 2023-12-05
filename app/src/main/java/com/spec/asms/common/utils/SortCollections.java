package com.spec.asms.common.utils;

import java.util.Comparator;

import com.spec.asms.vo.LogVO;

public class SortCollections implements Comparator<LogVO> {

	public int compare(LogVO lhs, LogVO rhs) {
		return (lhs.getDatetime() < rhs.getDatetime() ? 1 : (lhs.getDatetime() == rhs.getDatetime() ? 0 : -1));
	}
}
