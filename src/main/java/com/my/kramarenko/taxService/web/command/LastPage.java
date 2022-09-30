package ua.nure.kramarenko.SummaryTask4.web.command;

import ua.nure.kramarenko.SummaryTask4.db.Path;

public class LastPage {

	public static String getPage(String lastPage) {
		if (lastPage != null) {
			if (lastPage.equals("product")) {
				return (Path.COMMAND_LIST_PRODUCTS);
			}
			if (lastPage.equals("cart")) {
				return (Path.COMMAND_VIEW_CART);
			}
			if (lastPage.equals("allOrders")) {
				return (Path.COMMAND_ALL_ORDERS);
			}
			if (lastPage.equals("orders")) {
				return (Path.COMMAND_LIST_ORDERS);
			}

			if (lastPage.equals("users")) {
				return (Path.COMMAND_ALL_USERS);
			}
			if (lastPage.equals("manufacturers")) {
				return (Path.COMMAND_MANUFACTURERS);
			}
			if (lastPage.equals("charactiristics")) {
				return (Path.COMMAND_CHARACTIRISTICS);
			}
		}
		return "";
	}
}
