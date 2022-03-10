package hr.aomatica.web.init;

import hr.aomatica.constant.Constants;
import hr.aomatica.dao.jpa.JPAEMFProvider;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

@WebListener
public class Inicijalizacija implements ServletContextListener {



	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testSchema");
		sce.getServletContext().setAttribute("my.application.emf", emf);
		JPAEMFProvider.setEmf(emf);

		Properties initProperties = new Properties();
		try {
			initProperties.load(sce.getServletContext().getResourceAsStream(Constants.initPropertiesPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		sce.getServletContext().setAttribute("initProperties", initProperties);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Entered contextDestroyed");

		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory)sce.getServletContext().getAttribute("my.application.emf");
		if(emf!=null) {
			emf.close();
		}
		// Now deregister JDBC drivers in this context's ClassLoader:
		// Get the webapp's ClassLoader
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		// Loop through all drivers
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			if (driver.getClass().getClassLoader() == cl) {
				// This driver was registered by the webapp's ClassLoader, so deregister it:
				try {
					System.out.println("Deregistering JDBC driver {}" + driver);
					DriverManager.deregisterDriver(driver);
				} catch (SQLException ex) {
					System.out.println("Error deregistering JDBC driver {}" +  driver + ex);
				}
			} else {
				// driver was not registered by the webapp's ClassLoader and may be in use elsewhere
				System.out.println("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader" + driver);
			}
		}
	}
}