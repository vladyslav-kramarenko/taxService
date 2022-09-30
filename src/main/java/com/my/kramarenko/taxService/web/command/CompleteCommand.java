package com.my.kramarenko.taxService.web.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.bean.product.CompleteCommandProduct;
import ua.nure.kramarenko.SummaryTask4.db.derby.ProductListBeanDb;

import java.io.IOException;
import java.util.List;

public class CompleteCommand extends Command {

	/**
	 * @author Vlad Kramarenko
	 */
	private static final long serialVersionUID = -1219684162891924648L;

	@Override
	public String execute(HttpServletRequest request,
						  HttpServletResponse response) throws IOException, ServletException {

		String action = request.getParameter("action");
		String targetId = request.getParameter("id");
		StringBuffer sb = new StringBuffer();

		if (targetId != null) {
			targetId = targetId.trim().toLowerCase();
		} else {
			return Path.PAGE_ERROR_PAGE;
		}

		boolean namesAdded = false;
		if (action.equals("complete")) {

			// check if user sent empty string
			if (!targetId.equals("")) {
				ProductListBeanDb productListBeanDb = new ProductListBeanDb();

				List<CompleteCommandProduct> products = productListBeanDb
						.getCompleteCommandProdcuts();
				for (CompleteCommandProduct p : products) {

					if ( // targetId matches first name
					p.getName().toLowerCase().startsWith(targetId) ||
					// targetId matches last name
							p.getManufacturer().toLowerCase()
									.startsWith(targetId) ||
							// targetId matches full name
							p.getManufacturer().toLowerCase().concat(" ")
									.concat(p.getName().toLowerCase())
									.startsWith(targetId)) {

						sb.append("<product>");
						sb.append("<id>" + p.getId() + "</id>");
						sb.append("<manufacturer>" + p.getManufacturer()
								+ "</manufacturer>");
						sb.append("<name>" + p.getName() + "</name>");
						sb.append("</product>");

						namesAdded = true;
					}
				}
			}

			if (namesAdded) {
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(
						"<products>" + sb.toString() + "</products>");
			} else {
				// nothing to show
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
		}
		if (action.equals("lookup")) {
			response.sendRedirect("controller?command=productInfo&product_id="
					+ targetId);
			return null;
		}

		return null;
	}

}
