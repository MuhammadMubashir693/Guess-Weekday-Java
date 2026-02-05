import java.util.Scanner;
import java.util.Random;
import java.time.LocalDate;
import java.time.YearMonth;

public class App {

    public static String sentenceCase(String s) {
        // Return nothing if empty string
        if (s.length() == 0)
            return "";

        // Convert to upper if single character
        else if (s.length() == 1)
            return s.toUpperCase();

        // Take first character of string and uppercase it,lowercase rest of the string
        // and concatenate them
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public static boolean isLeap(int year) {
        // Check if year is either only divisible by 4 and NOT by 100 OR divisible by
        // 400
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static int getRandomNumberUsingNextInt(int min, int max) {
        // Generate random number between min and max both inclusive
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static LocalDate generateRandomDate(int startYear, int endYear) {
        // Get any year between start and end
        int year = getRandomNumberUsingNextInt(startYear, endYear);
        // Get any month value of the year (1-12)
        int month = getRandomNumberUsingNextInt(1, 12);
        // Get any day of the month
        int day = getRandomNumberUsingNextInt(1, YearMonth.of(year, month).lengthOfMonth());

        return LocalDate.of(year, month, day);
    }

    public static String formatDate(LocalDate date) {
        String month = sentenceCase(date.getMonth().toString());
        int monthDay = date.getDayOfMonth();
        int year = date.getYear();

        // Formats the date in the format M D, YYYY e.g January 1, 1970
        return String.format("%s %d, %d", month, monthDay, year);
    }

    // Safe integer reader that keeps prompting until a valid integer is entered
    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Initialize years with dummy values
        int startYear = -1;
        int endYear = -1;
        Scanner sc = new Scanner(System.in);

        while (true) {
            // Keep taking input from user until start year is after 1582 and end year is
            // greater than start year
            while (startYear < 1582) {
                startYear = readInt(sc, "Enter start year (after 1582): ");
                if (startYear < 1582) {
                    System.out.println("Year must be after 1582.");
                }
            }

            while (endYear < startYear) {
                endYear = readInt(sc, "Enter end year (after start year): ");
                if (endYear < startYear) {
                    System.out.println("End year must be >= start year.");
                }
            }

            LocalDate randomDate = generateRandomDate(startYear, endYear);
            System.out.println("Date: " + formatDate(randomDate));

            int guess = -1;

            while (guess < 1 || guess > 7) {
                guess = readInt(sc, "Guess the weekday! (1-7): ");
                if (guess < 1 || guess > 7) {
                    System.out.println("Invalid range");
                }
            }

            int correct = randomDate.getDayOfWeek().getValue();

            if (guess == correct) {
                System.out.println("Bingo!");
            } else {
                System.out.println("Sorry! The answer was " + correct + " ("
                        + sentenceCase(randomDate.getDayOfWeek().toString()) + ").");
            }

            System.out.print("Wanna try another one? Press q or n to quit and anything else to continue: ");
            String contStr = sc.nextLine().trim().toLowerCase();
            char cont = contStr.isEmpty() ? '\n' : contStr.charAt(0);

            if (cont == 'q' || cont == 'n') {
                break;
            }
        }

        sc.close();
    }
}
