package com.yobny.opensource.osgi.karafview.parser;

import java.util.ArrayList;

import com.yobny.opensource.osgi.karafview.bean.ListUrlCmdPojo;

public class ListUrlCmdResponseParser implements IResponseParser {

	@Override
	public boolean parse(String data, ArrayList<Object> objects) {
		String[] listLines = data.split("\n");
		for (int index = 0; index < listLines.length; index++) {
			ListUrlCmdPojo listUrlCmdPojo = new ListUrlCmdPojo();
			listUrlCmdPojo.setUri(listLines[index].replaceAll("\r", ""));
			objects.add(listUrlCmdPojo);
		}
		return true;
	}
}
