package ys09.conf;

public class ConfigurationException extends RuntimeException {

    public ConfigurationException(String msg) {
        super(msg);
    }

    public ConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
