package com.success_v1.res;

public class config {
	
	private static final String IP_SERVER = "http://54.217.205.27/";
	private static final String FILES_DIRECTORY = "Success2i_V1/";
	
	public static String getURL()
	{
		return IP_SERVER+FILES_DIRECTORY;
	}

}
