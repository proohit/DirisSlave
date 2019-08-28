import net.dv8tion.jda.core.JDABuilder;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;

public class Main {

	public static void main(String[] args) throws LoginException {
		JDA jda = new JDABuilder(AccountType.BOT)
				.setToken("NTY2NjIwMzc0OTA1OTEzMzU1.XLHr9A.zOoMFpoibt2lyzkNXl9PLNqNC1o").build();
		jda.addEventListener(new MyEventListener(jda));
		//System.out.println(YoutubeSearcher.search(new String[] {"live", "overflow"}));
	}

}
