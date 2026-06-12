import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.Scanner;

public class FridayThe13th {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a date (YYYY-MM-DD): ");
        String input = scanner.next();

        LocalDate date = LocalDate.parse(input);

        boolean isFriday13th = date.getDayOfMonth() == 13
                && date.getDayOfWeek() == DayOfWeek.FRIDAY;

        if (isFriday13th) {
            System.out.println(date + " is Friday the 13th!");
        } else {
            System.out.println(date + " is NOT Friday the 13th.");
            System.out.println("  Day: " + date.getDayOfWeek());
            System.out.println("  Day of month: " + date.getDayOfMonth());
        }

        scanner.close();
    }
}
