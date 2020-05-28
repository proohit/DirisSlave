package main;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Startup {
    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        //insert bot token here
        ReadPropertyFile rpf = ReadPropertyFile.getInstance();
        jda = JDABuilder.createDefault(rpf.getJDAToken()).build();
        jda.addEventListener(new MyEventListener(jda));
    }

}
