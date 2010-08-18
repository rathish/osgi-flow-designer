package com.yobny.opensource.osgi.karafview.parser;

import java.util.ArrayList;

import com.yobny.opensource.osgi.karafview.bean.ListCmdPojo;

public class ListCmdResponseParser implements IResponseParser {

	@Override
	public boolean parse(String data, ArrayList<Object> objects) {
		String[] listLines = data.split("\n");
		for (int index = 0; index < listLines.length; index++) {
			String[] tokens = listLines[index].split("] ");
			if (tokens.length > 1) {
				ListCmdPojo cmdPojo = new ListCmdPojo();
				cmdPojo.setBundleId(tokens[0].replace("[", "").trim());
				cmdPojo.setBundleState(tokens[1].replace("[", "").trim());
				cmdPojo.setBundleBluePrint(tokens[2].replace("[", "").trim());
				cmdPojo.setBundleLevel(tokens[3].replace("[", "").trim());
				cmdPojo.setBundleName(tokens[4].replace("[", "").trim());
				objects.add(cmdPojo);
			}
		}
		return false;
	}
}
