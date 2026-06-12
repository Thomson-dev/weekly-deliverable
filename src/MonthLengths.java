import java.time.Year;
import java.time.Month;
import java.util.Scanner;

public class MonthLengths {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a year: ");
        int year = scanner.nextInt();

        boolean isLeap = Year.of(year).isLeap();
        System.out.println("\nMonth lengths for " + year + (isLeap ? " (leap year)" : "") + ":");

        for (Month month : Month.values()) {
            int days = month.length(isLeap);
            System.out.printf("%-12s: %d days%n", month, days);
        }

        scanner.close();
    }
}
