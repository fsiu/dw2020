package net.darkwire.example.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by fsiu on 4/17/14.
 */
public enum FiveHundredPxConfiguration {
    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(FiveHundredPxConfiguration.class);

    private String consumerKey;
    private String consumerSecret;
    private String username;
    private String password;

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private FiveHundredPxConfiguration() {
        final Properties props = new Properties();

        final InputStream inputStream = ((Object) this).getClass().getClassLoader().getResourceAsStream("secrets.properties");

        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            props.load(reader);
            setConsumerKey(props.getProperty("px_consumer_key"));
            setConsumerSecret(props.getProperty("px_consumer_secret"));
            setUsername(props.getProperty("username"));
            setPassword(props.getProperty("password"));
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}
