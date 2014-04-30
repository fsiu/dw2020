package net.darkwire.example.model;

import com.google.common.io.Closeables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by fsiu on 4/17/14.
 */
public class FiveHundredPxConfiguration {
    public static final FiveHundredPxConfiguration INSTANCE = new FiveHundredPxConfiguration();

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

        BufferedReader reader = null;
        try {
            final Properties props = new Properties();
            reader = new BufferedReader(new InputStreamReader(((Object) this).getClass().getClassLoader().getResourceAsStream("secrets.properties")));
            props.load(reader);
            setConsumerKey(props.getProperty("px_consumer_key"));
            setConsumerSecret(props.getProperty("px_consumer_secret"));
            setUsername(props.getProperty("username"));
            setPassword(props.getProperty("password"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                Closeables.close(reader, true);
            } catch (IOException e) {
                LOGGER.debug(e.getMessage());
            }
        }
    }

}
