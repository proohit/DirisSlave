package main;

import javax.security.auth.login.LoginException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.tinylog.Logger;

import database.DBManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { R2dbcAutoConfiguration.class })
public class Startup {
    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        try {
            SpringApplication.run(Startup.class);
            ReadPropertyFile rpf = ReadPropertyFile.getInstance();
            jda = JDABuilder.createDefault(rpf.getJDAToken()).build().awaitReady();
            DBManager.initializeDatabase();
            jda.addEventListener(new MyEventListener(jda));
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000", "https://dirisslave.timurnet.de");
            }
        };
    }

}
