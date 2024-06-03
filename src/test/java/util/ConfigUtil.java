package util;

public class ConfigUtil {
    public static String getBaseUrl() {
        String baseUrl = System.getProperty("base.url");
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("Base URL not specified in system properties");
        }
        return baseUrl;
    }
}
