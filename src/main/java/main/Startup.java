package main;

import javax.security.auth.login.LoginException;

import org.tinylog.Logger;

import database.DBManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import server.RestServer;

public class Startup {
    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        try {
            ReadPropertyFile rpf = ReadPropertyFile.getInstance();
            jda = JDABuilder.createDefault(rpf.getJDAToken()).build().awaitReady();
            DBManager.initializeDatabase();
            jda.addEventListener(new MyEventListener(jda));
            RestServer restServer = new RestServer();
            restServer.start();
        } catch (Exception e) {
            Logger.error(e);
        }
    }

}
