package hr.aomatica.util.properties;

import javax.servlet.ServletContext;
import java.util.Properties;

public abstract class LoadProperties {

    protected String propertiesName;

    /**
     * Loads this Properties from initProperties
     * @param context
     * @return
     */
    public Properties loadThisProperties(ServletContext context) {
        return LoadFromInitProperties.loadProperty(context, propertiesName);
    }

    /**
     * Gets value from this Property
     * @param context
     * @param key
     * @return
     */
    public String getValueFromThisProperty(ServletContext context, String key) {
        return loadThisProperties(context).getProperty(key);
    }
}
