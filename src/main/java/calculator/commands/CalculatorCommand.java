package calculator.commands;

import calculator.Calculator;
import calculator.DivisionByZero;
import main.CommandManager;
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
            CommandManager.sendMessage(event.getTextChannel(), Double.toString(calculator.calculate(term)));
        } catch (DivisionByZero e) {
            CommandManager.sendMessage(event.getTextChannel(), "http://math-fail.com/images-old/divide-by-zero3.jpg");
        } catch (Exception e) {
            e.printStackTrace();
            CommandManager.sendMessage(event.getTextChannel(), "#calc usage:\n number (+ - * /) number");
        }
    }

}
