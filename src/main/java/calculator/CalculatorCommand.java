package calculator;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class CalculatorCommand extends Command {
    public CalculatorCommand() {
        setCommand(prefix + "calc");
        addPermission("everyone");
        setTopic("calculator");
        setDescription("Solve calculations by typing the term.");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length <= 0) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        String term = "";
        for (int i = 0; i < argStrings.length; i++) {
            term += argStrings[i];
        }
        Calculator calculator = new Calculator();
        try {
            Commands.sendMessage(event.getTextChannel(), Double.toString(calculator.calculate(term)));
        } catch (DivisionByZero e) {
            Commands.sendMessage(event.getTextChannel(), "http://math-fail.com/images-old/divide-by-zero3.jpg");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Commands.sendMessage(event.getTextChannel(), "#calc usage:\n number (+ - * /) number");
        }
    }
}
