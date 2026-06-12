import java.util.Scanner;

class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends Exception {
    private double amount;

    public InsufficientFundsException(double amount) {
        super("Insufficient funds. Short by: $" + amount);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

public class CustomExceptionDemo {

    static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Age " + age + " is not a valid age.");
        }
        System.out.println("Valid age: " + age);
    }

    static void withdraw(double balance, double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(amount - balance);
        }
        System.out.println("Withdrawal successful. Remaining balance: $" + (balance - amount));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Test InvalidAgeException ---
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        try {
            validateAge(age);
        } catch (InvalidAgeException e) {
            System.out.println("Caught InvalidAgeException: " + e.getMessage());
        }

        // --- Test InsufficientFundsException ---
        System.out.print("\nEnter your account balance: $");
        double balance = scanner.nextDouble();
        System.out.print("Enter amount to withdraw: $");
        double amount = scanner.nextDouble();
        try {
            withdraw(balance, amount);
        } catch (InsufficientFundsException e) {
            System.out.println("Caught InsufficientFundsException: " + e.getMessage());
            System.out.printf("You need an additional $%.2f%n", e.getAmount());
        }

        scanner.close();
    }
}
