package com.my.kramarenko.taxService.web.command.outOfControl;

import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.PasswordCreator;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.dao.UserDAO;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.command.LastPage;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.common.VerifyRecaptcha;
import com.my.kramarenko.taxService.web.command.util.UserUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;
import java.util.Map;

import static com.my.kramarenko.taxService.Util.resetAvailableError;
import static com.my.kramarenko.taxService.Util.updateLastPageIfEmpty;
import static com.my.kramarenko.taxService.web.command.util.UserUtil.createCompanyName;

public class RegistrationCommand extends Command {

    @Serial
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger
            .getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException {
        LOG.debug("Command starts");

        updateLastPageIfEmpty(request, Path.PAGE_REGISTRATION);

        if (request.getMethod().equals("GET")) {
            resetAvailableError(request);
            return Path.PAGE_REGISTRATION;
        }

        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            return forwardError("error.empty_credentials");
        }

        String gRecaptchaResponse = request
                .getParameter("g-recaptcha-response");
        boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
        if (!verify) {
            return forwardError("error.invalid_captcha");
        }

        LOG.trace("Request parameter: registration --> " + email);
        User user = createUserBean(request);

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return forwardError("error.empty_credentials");
        }

        UserDAO userManager = DBManager.getInstance().getUserDAO();
        if (userManager.findUserByEmail(email).isPresent()) {
            return forwardError("error.email_forbidden");
        }
        try {
            return createUser(user, request);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            return forwardError("error.can_not_create_user");
        }
//                setRequestAttributes(request, user);
    }

    private String createUser(User user, HttpServletRequest request) throws DBException {
        HttpSession session = request.getSession();
        LOG.trace("everything is ok => write user to DB");
        user.setPassword(PasswordCreator.getPassword(user.getPassword()));
        UserUtil.setUserFields(user, request);

        LOG.debug("isIndividual = " + user.isIndividual());
        if (user.isIndividual()) {
            user.setCompanyName(createCompanyName(user));
            LOG.debug("create company name:" + user.getCompanyName());
        }

        user.setRoleId(Role.USER.getId());
        UserDAO userManager = DBManager.getInstance().getUserDAO();
        userManager.addUser(user);

        ServletContext sc = request.getServletContext();
        Map<Integer, Role> roleMap = (Map<Integer, Role>) sc.getAttribute("roleMap");
        setSessionAttributes(session, user, roleMap);

        String forward = LastPage.getPage((String) session
                .getAttribute("page"));
        if (forward.isEmpty() || forward.equals(Path.PAGE_REGISTRATION)) {
            forward = Path.COMMAND_SETTINGS;
        }
        return forward;
    }

    private String forwardError(String errorMessage) {
        LOG.trace(errorMessage);
        String forward = Path.COMMAND_REGISTRATION + "&error=" + errorMessage;
        return forward;
    }

    private void setSessionAttributes(HttpSession session, User user, Map<Integer, Role> roleMap) {
        session.setAttribute("user", user);
        LOG.trace("Set the session attribute: user --> " + user);
        Role role = roleMap.get(user.getRoleId());
        session.setAttribute("userRole", role);
//        LOG.trace("Set the session attribute: userRole --> "
//                + role.getName());
        LOG.info("User " + user + " logged as "
                + role.getName());
    }

//    private void setRequestAttributes(HttpServletRequest request, User user) {
//        request.setAttribute("email", user.getEmail());
//        request.setAttribute("phone", user.getPhone());
//        request.setAttribute("name", user.getFirstName());
//        request.setAttribute("lastName", user.getLastName());
//        request.setAttribute("patronymic", user.getPatronymic());
//        request.setAttribute("company_name", user.getCompanyName());
//        request.setAttribute("is_individual", user.isIndividual());
//        request.setAttribute("code", user.getCode());
//    }

//    private String getRequestAttributes(HttpServletRequest request) {
//        StringBuilder stringBuilder = new StringBuilder();
//        String email=request.getParameter("email");
//        if (email != null && !email.isEmpty())
//            stringBuilder.append("&email=" + email);
////            request.setAttribute("email", user.getEmail());
////        request.setAttribute("phone", user.getPhone());
////        request.setAttribute("name", user.getFirstName());
////        request.setAttribute("lastName", user.getLastName());
////        request.setAttribute("patronymic", user.getPatronymic());
////        request.setAttribute("company_name", user.getCompanyName());
////        request.setAttribute("is_individual", user.isIndividual());
////        request.setAttribute("code", user.getCode());
//        return stringBuilder.toString();
//    }

    private User createUserBean(HttpServletRequest request) {
        int id = Role.USER.getId();
        User user = new User();
        user.setId(id);
        String password = request.getParameter("password");
        user.setPassword(password);
        UserUtil.setUserFields(user, request);
        return user;
    }
}