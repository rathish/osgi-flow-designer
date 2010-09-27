package com.yobny.opensource.osgi.karafview.parser;

import java.util.ArrayList;

import com.yobny.opensource.osgi.karafview.bean.BundleListCmdPojo;

public class RepoBundleListCmdResponseParser implements IResponseParser {

	@Override
	public boolean parse(String data, ArrayList<Object> objects) {
		String[] listLines = data.split("\n");
		for (int index = 0; index < listLines.length; index++) {
			String[] tokens = listLines[index].split("\\(");
			if (tokens.length > 1) {
				BundleListCmdPojo cmdPojo = new BundleListCmdPojo();
				cmdPojo.setPresentationName(tokens[0].trim());
				cmdPojo.setVersion(tokens[1].replace(")", "").trim());
				objects.add(cmdPojo);
			}
		}
		return true;
	}
}
