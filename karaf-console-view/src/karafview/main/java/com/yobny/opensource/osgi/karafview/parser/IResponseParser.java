package com.yobny.opensource.osgi.karafview.parser;

import java.util.ArrayList;

public interface IResponseParser {
	
	public boolean parse(String data,
			ArrayList<Object> objects);
}
