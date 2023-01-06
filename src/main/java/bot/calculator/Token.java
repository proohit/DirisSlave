package bot.calculator;

public class Token {
	public String type, value;

	Token(String type, String value) {
		this.type = type;
		this.value = value;
	}

	public String toString() {
		return value;
	}

}
