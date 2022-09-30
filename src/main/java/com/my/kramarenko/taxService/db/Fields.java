package com.my.kramarenko.taxService.db;

/**
 * Holder for fields names of DB tables.
 * 
 * @author Vlad Kramarenko
 * 
 */
public final class Fields {

	// entities
	public static final String ENTITY_ID = "id";

	public static final String ORDERSPRODUCT_QUANTITY = "quantity";

//	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FIRST_NAME = "first_name";
	public static final String USER_LAST_NAME = "last_name";
	public static final String USER_PHONE = "phone";
	public static final String USER_EMAIL = "email";
//	public static final String USER_CITY = "city";
//	public static final String USER_ADDRESS = "address";
	public static final String USER_ROLE_ID = "role_id";

	public static final String ORDER_DATE = "date";
	public static final String ORDER_BILL = "bill";
	public static final String ORDER_USER_ID = "user_id";
	public static final String ORDER_STATUS_ID = "status_id";
	public static final String ORDER_CITY = "city";
	public static final String ORDER_PHONE = "phone";
	public static final String ORDER_EMAIL = "email";
	public static final String ORDER_ADDRESS = "address";

	public static final String CHARACTERISTIC_DESCRIPTION = "description";
	public static final String CHARACTERISTIC_NAME = "name";
	public static final String CHARACTERISTIC_VALUE = "value";
	public static final String CHARACTERISTIC_PRODUCT_ID = "product_id";

	public static final String PRODUCT_CHARACTERISTIC_ID = "characteristic_id";

	public static final String MANUFACTURER_NAME = "name";

	public static final String CATEGORY_NAME = "name";

	public static final String PRODUCT_ITEM_MANUFACTURER_ID = "manufacturer_id";
	public static final String PRODUCT_ITEM_MANUFACTURER = "manufacturer";
	public static final String PRODUCT_ITEM_NAME = "name";
	public static final String PRODUCT_ITEM_DESCRIPTION = "description";
	public static final String PRODUCT_ITEM_PRICE = "price";
	public static final String PRODUCT_ITEM_CATEGORY_ID = "category_id";
	public static final String PRODUCT_ITEM_CATEGORY = "category";
	public static final String PRODUCT_ITEM_AVAILABILITY = "availability";
	public static final String PRODUCT_ITEM_IMG = "img";

	// beans
	public static final String USER_ORDER_BEAN_ORDER_ID = "id";
	public static final String USER_ORDER_BEAN_USER_FIRST_NAME = "first_name";
	public static final String USER_ORDER_BEAN_USER_LAST_NAME = "last_name";
	public static final String USER_ORDER_BEAN_ORDER_BILL = "bill";
	public static final String USER_ORDER_BEAN_STATUS_NAME = "name";
}