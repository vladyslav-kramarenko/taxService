package com.my.kramarenko.taxService.web.command;

import com.my.kramarenko.taxService.web.Path;

public class LastPage {

	public static String getPage(String lastPage) {
		if (lastPage != null) {
			if (lastPage.equals("users")) {
				return (Path.COMMAND_ALL_USERS);
			}
			if (lastPage.equals("info")) {
				return (Path.PAGE_INFO);
			}
		}
		return "";
	}
}
