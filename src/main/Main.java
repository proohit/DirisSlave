package main;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.JDAInfo;

import javax.security.auth.login.LoginException;

public class Main {
    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        //insert bot token here
        ReadPropertyFile rpf = new ReadPropertyFile();
        jda = JDABuilder.createDefault(rpf.getJDAToken()).build();
        jda.addEventListener(new MyEventListener(jda));
        jda.getTextChannelById("544815528179531776").sendMessage("starting the engine...").queue();
    }

}
