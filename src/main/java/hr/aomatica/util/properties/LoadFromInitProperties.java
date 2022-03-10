package hr.aomatica.util.properties;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Properties;

public class LoadFromInitProperties {

    /**
     * Returns init properties
     * @param context
     * @return
     */
    public static Properties getInitProperties(ServletContext context) {
        return (Properties) context.getAttribute("initProperties");
    }

    /**
     * Returns value connected with key from init properties
     * @param key
     * @return
     */
    public static String getProperty(ServletContext context, String key) {
        return getInitProperties(context).getProperty(key);
    }

    /**
     * Loads properties from initProperties
     * @param context
     * @param key
     * @return
     */
    public static Properties loadProperty(ServletContext context, String key) {
        Properties properties = new Properties();
        try {
            properties.load(context.getResourceAsStream(getProperty(context, key)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
