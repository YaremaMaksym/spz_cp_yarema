package yaremax.util.input;

import java.util.Scanner;

public class InputHandler {
    private static InputHandler instance;
    private final Scanner scanner = new Scanner(System.in);

    private InputHandler() {}

    public static synchronized InputHandler getInstance() {
        if (instance == null) {
            instance = new InputHandler();
        }
        return instance;
    }

    public int inputInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("🛑🛑🛑 Введіть ціле число 🛑🛑🛑");
            }
        }
    }

    public long inputLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("🛑🛑🛑 Введіть число 🛑🛑🛑");
            }
        }
    }

    public String inputString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
