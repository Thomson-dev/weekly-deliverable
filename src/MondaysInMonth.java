import java.time.LocalDate;
import java.time.Month;
import java.time.DayOfWeek;
import java.time.Year;
import java.util.Scanner;

public class MondaysInMonth {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a month number (1-12): ");
        int monthNum = scanner.nextInt();

        int year = Year.now().getValue();
        Month month = Month.of(monthNum);
        LocalDate date = LocalDate.of(year, month, 1);

        System.out.println("\nMondays in " + month + " " + year + ":");

        while (date.getMonth() == month) {
            if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
                System.out.println("  " + date);
            }
            date = date.plusDays(1);
        }

        scanner.close();
    }
}
