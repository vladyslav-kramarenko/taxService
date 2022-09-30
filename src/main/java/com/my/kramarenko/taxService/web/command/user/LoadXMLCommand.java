package com.my.kramarenko.taxService.web.command.user;

import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.command.Path;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class LoadXML extends Command {

    private static final long serialVersionUID = 1863978254689586513L;

    private static final Logger LOG = Logger.getLogger(LoadXML.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Command starts");


//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
//        OrderDb orderDb = new OrderDb();
//        List<UserOrderBean> userOrderBeanList = orderDb
//                .getAllUserOrdersBean(user.getId());
//        LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);
//
//        request.setAttribute("userOrderBeanList", userOrderBeanList);
//        session.setAttribute("page", "orders");
//        LOG.trace("Set the request attribute: userOrderBeanList --> "
//                + userOrderBeanList);

        LOG.debug("Command finished");
        return Path.PAGE_INFO;
    }


}
