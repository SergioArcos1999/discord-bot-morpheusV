package listeners.twitter.config;

import java.io.InputStream;
import java.util.Properties;

public class TwitterContext {
    public static final String CONF_PATH = "consumer.conf";
    Properties prop;

    public TwitterContext() throws Exception {
        prop = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream(CONF_PATH);
        prop.load(is);
    }

    public String getString(String key) { return prop.getProperty(key); }
}
