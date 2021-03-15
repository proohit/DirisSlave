package calculator.commands;

import calculator.Calculator;
import calculator.DivisionByZero;
import main.MessageUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class CalculatorCommand extends Command {
    public CalculatorCommand() {
        addPermission("everyone");
        addCommendPrefix("calc", "calculate");
        setDescription("Solve calculations by typing the term.");
        setTopic("calculator");
        setHelpString("<number1> <+|-|*|/|(|)> <number2>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        String term = "";
        for (int i = 0; i < argStrings.length; i++) {
            term += argStrings[i];
        }
        Calculator calculator = new Calculator();
        try {
            MessageUtils.sendMessage(event.getTextChannel(), Double.toString(calculator.calculate(term)));
        } catch (DivisionByZero e) {
            MessageUtils.sendMessage(event.getTextChannel(), "http://math-fail.com/images-old/divide-by-zero3.jpg");
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtils.sendMessage(event.getTextChannel(), "#calc usage:\n number (+ - * /) number");
        }
    }

}
