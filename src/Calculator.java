import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Calculator {
	ArrayList<String> states;
	HashMap<String, Integer> operators = new HashMap<String, Integer>();
	{
		operators.put("+", 1);
		operators.put("-", 1);
		operators.put("*", 2);
		operators.put("/", 2);
		operators.put("(", 0);
		operators.put(")", 0);
	}

	public Token[] tokenize(String term) {
		String[] array = term.split("(?<=[\\+\\-\\*\\/\\(\\)])|(?=[\\+\\-\\*\\/\\(\\)])");
		Token[] tokens = new Token[array.length];

		for (int i = 0; i < tokens.length; i++) {
			if (isNumber(array[i]))
				tokens[i] = new Token("number", array[i]);
			else if (operators.containsKey(array[i]))
				tokens[i] = new Token("operator", array[i]);
		}
		return tokens;
	}

	private static boolean isNumber(String text) {
		return text.matches("\\d+(\\.\\d+)?");
	}

	public double calculate(String term) throws IllegalArgumentException, DivisionByZero {
		// infix in postfix
		Token[] tokens = tokenize(term);
		Stack<Token> operatorsStack = new Stack<Token>();
		ArrayList<Token> output = new ArrayList<Token>();

		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].value.equals("(")) {
				operatorsStack.add(tokens[i]);
			} else if (tokens[i].value.equals(")")) {
				while (!operatorsStack.isEmpty() && !operatorsStack.lastElement().value.equals("(")) {
					output.add(operatorsStack.pop());
				}
				operatorsStack.pop();
				continue;
			} else if (tokens[i].type.equals("operator")) {
				while (!operatorsStack.isEmpty()
						&& operators.get(tokens[i].value) <= operators.get(operatorsStack.lastElement().value)) {

					output.add(operatorsStack.pop());
				}
				operatorsStack.add(tokens[i]);
			} else if (tokens[i].type.equals("number")) {
				output.add(tokens[i]);
			} else {
				throw new IllegalArgumentException();
			}
		}
		while (!operatorsStack.isEmpty()) {
			output.add(operatorsStack.pop());
		}
		Stack<Token> evaluation = new Stack<Token>();
		for (Token token : output) {
			if (token.type.equals("number"))
				evaluation.add(token);
			else if (token.type.equals("operator")) {
				BigDecimal tempVal = new BigDecimal(0);
				BigDecimal val1 = new BigDecimal(evaluation.pop().value);
				BigDecimal val2 = new BigDecimal(evaluation.pop().value);
				switch (token.value) {
				case "+":
					tempVal= val2.add(val1);
					break;
				case "-":
					tempVal = val2.subtract(val1);
					break;
				case "*":
					tempVal = val1.multiply(val2);
					break;
				case "/":
					if (val1.doubleValue() == 0)
						throw new DivisionByZero();
					tempVal = val2.divide(val1, MathContext.DECIMAL128);
					break;
				}
				Token temptoken = new Token("number", tempVal.toString());
				evaluation.add(temptoken);
			}
		}
		// postfix evaluation
		if (evaluation.size() > 1) {
			throw new IllegalArgumentException();
		}
		return Double.parseDouble(evaluation.pop().value);
	}
}
