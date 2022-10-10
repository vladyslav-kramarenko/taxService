package com.my.kramarenko.taxService.web.mail;

import com.my.kramarenko.taxService.db.entity.User;
import org.apache.log4j.Logger;

public class MailCreator {

    private static final Logger LOG = Logger.getLogger(MailCreator.class);

//	public static String createMail(User user, Order order) {
//
//		StringBuffer mail = new StringBuffer();
//		mail.append("������������, " + user.getFirstName() + " "
//				+ user.getLastName() + "!");
//
//		if (order.getStatusId() == 0) {
//			mail.append("\n�� ������� ������� � ����� ��������.<br>����� ������ ������ �"
//					+ order.getId() + "<br>������: ");
//		} else {
//			mail.append("\n������ ������ ������ �" + order.getId()
//					+ " ������� ��: ");
//		}
//		mail.append(Status.values()[order.getStatusId()]);
//		mail.append("\n������ ������:");
//		mail.append("\n��������\t���-��\t����");
//		mail.append("\n_________________________________________");
//		OrdersProductsDb ordersProductsDb = new OrdersProductsDb();
//		ShoppingCart cart = new ShoppingCart(
//				ordersProductsDb.getAllOrderProducts(order.getId()));
//		System.out.println("order=" + order.getId());
//		for (ShoppingCartItem sci : cart.getItems()) {
//			mail.append("\n");
//			mail.append(sci.getProduct().getName()).append("\t\t");
//			mail.append(sci.getQuantity());
//			mail.append("\t").append(sci.getTotal()).append("$");
//			mail.append("\n");
//		}
//		mail.append("\n_________________________________________");
//
//		mail.append("\n");
//		mail.append("�����").append("\t\t");
//		mail.append(cart.getNumberOfItems()).append("\t");
//		mail.append(cart.getTotal()).append("$");
//		mail.append("\n");
//
//		mail.append("������� �� �������!");
//		LOG.trace("sent mail: "+mail.toString());
//		System.out.println(mail.toString());
//		return mail.toString();
//	}

//	public static void sentMail(int orderId) {
//		OrderDb orderDb = new OrderDb();
//		Order order = orderDb.getOrderByID(orderId);
//		UserDb userDb = new UserDb();
//		User user = userDb.getUser(order.getUserId());
//		sentMail(user, order);
//	}

//	public static void sentMail(User user, Order order) {
//		String message = createMail(user, order);
//		String subject = "New order";
//		String mail = order.getEmail();
//		try {
//			MailHelper.sendMail(mail, subject, message);
//		} catch (Exception e) {
//			LOG.trace(e);
//		}
//	}
}
