package ua.nure.kramarenko.SummaryTask4.web.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ua.nure.kramarenko.SummaryTask4.db.derby.CategoryDb;
import ua.nure.kramarenko.SummaryTask4.db.entity.Category;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Context listener.
 * 
 ** @author Vlad Kramarenko
 * 
 */
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction starts");
		// do nothing
		log("Servlet context destruction finished");
	}

	public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);
		initCommandContainer();
		initI18N(servletContext);

		setCategoryList(servletContext);

		log("Servlet context initialization finished");
	}

	public void setCategoryList(ServletContext servletContext) {
		CategoryDb categoryDb = new CategoryDb();
		List<Category> categoryItems = categoryDb.getAllCategories();
		LOG.trace("Found in DB: categoryList --> " + categoryItems);
		// store category list in servlet context
		servletContext.setAttribute("categoryItems", categoryItems);
	}

	/**
	 * Initializes i18n subsystem.
	 */
	private void initI18N(ServletContext servletContext) {
		LOG.debug("I18N subsystem initialization started");

		String localesValue = servletContext.getInitParameter("locales");
		if (localesValue == null || localesValue.isEmpty()) {
			LOG.warn("'locales' init parameter is empty, the default encoding will be used");
		} else {
			List<String> locales = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(localesValue);
			while (st.hasMoreTokens()) {
				String localeName = st.nextToken();
				locales.add(localeName);
			}

			LOG.debug("Application attribute set: locales --> " + locales);
			servletContext.setAttribute("locales", locales);
		}

		LOG.debug("I18N subsystem initialization finished");
	}

	/**
	 * Initializes log4j framework.
	 * 
	 * @param servletContext
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext
					.getRealPath("WEB-INF/log4j.properties"));
		} catch (Exception ex) {
			LOG.error(ex);
		}

		log("Log4J initialization finished");
	}

	/**
	 * Initializes CommandContainer.
	 * 
//	 * @param servletContext
	 */
	private void initCommandContainer() {
		LOG.debug("Command container initialization started");

		// initialize commands container
		// just load class to JVM
		try {
			Class.forName("ua.nure.kramarenko.SummaryTask4.web.command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			LOG.error(ex);
		}

		LOG.debug("Command container initialization finished");
	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}

}