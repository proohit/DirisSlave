package main;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import javax.security.auth.login.LoginException;

public class Main {

	public static void main(String[] args) throws LoginException {
		JDA jda = new JDABuilder(AccountType.BOT)
				.setToken("NTY2NjIwMzc0OTA1OTEzMzU1.XLHr9A.zOoMFpoibt2lyzkNXl9PLNqNC1o").build();
		jda.addEventListener(new MyEventListener(jda));
	}

}
