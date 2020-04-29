package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {
    Properties prop = null;

    public ReadPropertyFile() {
        try {
            FileInputStream ip = new FileInputStream("config/config.properties");
            prop = new Properties();
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getJDAToken() {
        return prop.getProperty("JDAToken");
    }


}
