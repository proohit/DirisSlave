package main;

import javax.security.auth.login.LoginException;

import org.tinylog.Logger;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Startup {
    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        try {
            ReadPropertyFile rpf = ReadPropertyFile.getInstance();
            jda = JDABuilder.createDefault(rpf.getJDAToken()).build().awaitReady();
            jda.addEventListener(new MyEventListener(jda));
        } catch (Exception e) {
            Logger.error(e);
        }
    }

}
