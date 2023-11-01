package Helpers;

import java.util.Scanner;

public class SafeInput {
    public static int getInt() {
        Scanner s = new Scanner(System.in);

        while (true) {
            try {
                return s.nextInt();
            } catch (Exception e) {
                System.out.println("Некоректний ввід цілого числа");
            }
        }
    }
}
