package com.my.kramarenko.taxService.web.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("logout", new LogoutCommand());

        commands.put("noCommand", new NoCommand());

        commands.put("updateLocale", new UpdateLocaleCommand());

//		commands.put("addToCart", new AddToCartCommand());
//		commands.put("deleteFromCart", new AddToCartCommand());
//		commands.put("viewCart", new CartCommand());
//
//		commands.put("productList", new ProductListCommand());
//		commands.put("productInfo", new ProductInfoCommand());

        commands.put("complete", new CompleteCommand());

        // client commands
//		commands.put("viewSettings", new ViewSettingsCommand());
//		commands.put("confirmOrder", new CartCommand());
//		commands.put("orderCheckout", new CartCommand());
//		commands.put("listOrders", new ClientOrdersCommand());

        // admin commands
//		commands.put("completeManufacturer", new CompleteManufacturerCommand());
//		commands.put("allUsers", new AllUsersCommand());
//		commands.put("changeUserRole", new AllUsersCommand());
//		commands.put("allOrders", new AllOrdersCommand());
//		commands.put("changeOrderStatus", new AllOrdersCommand());
//		commands.put("editProduct", new ProductEditCommand());
//		commands.put("saveProduct", new ProductSaveCommand());
//		commands.put("manufacturers", new ManufacturersCommand());
//		commands.put("characteristics", new CharacteristicsCommand());
//		commands.put("userProducts", new UsersProductsCommand());

        LOG.debug("Command container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        System.out.println("command get");
        return commands.get(commandName);
    }

}