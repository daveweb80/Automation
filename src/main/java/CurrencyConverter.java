import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This tool enables to make a conversion between two currencies
 * by using a known rate.
 * User opts the original and target currencies from a menu and
 * typing the original amount.
 * The system make the calculation and displays the new amount of the target currency.
 * Many such conversions are possible.
 * When the user opt to end, the screen shows all
 * conversions made in session and writes that information to a file.
 */
public class CurrencyConverter {
    public static void main(String[] args) {
        StringBuilder mainMenu = new StringBuilder();
        mainMenu.append("Welcome to currency converter\n");
        mainMenu.append("Please choose an option (1/2):\n");
        mainMenu.append("1. Dollars to Shekels\n");
        mainMenu.append("2. Shekels to Dollars");

        String calcAgainYN = "y";

        // Saves every calculation made during the session
        List<String> resultsList = new ArrayList<String>();

        while (calcAgainYN.toLowerCase().equals("y")) {   // As far as the user asked for a new calculation
            int convertOption = 0; // User choice
            double amount = 0;  // User amount in the original currency.

            Scanner scnr = new Scanner(System.in);  // Create a Scanner object

            while (convertOption != 1 && convertOption != 2) { // Valid options

                // MAIN MENU IS DISPLAYED
                System.out.println(mainMenu.toString());
                // This message is possible only id wrong menu number is entered.
                if (convertOption != 0) System.out.println("WRONG MENU OPTION. PLEASE TRY AGAIN.");
                try {
                    convertOption = Integer.parseInt(scnr.nextLine());  // Read user input
                }
                catch(NumberFormatException ex)
                {
                    System.out.println("WRONG MENU OPTION. PLEASE ENTER VALID NUMBER");
                }
            }

            while (amount <= 0) {
                System.out.println("Please enter an amount to convert");
                try {
                    amount = Double.parseDouble(scnr.nextLine());  // Read user input
                    if (amount <= 0) System.out.println("YOU ENTERED WRONG AMOUNT. PLEASE ENTER POSITIVE NUMBER.");
                }
                catch(NumberFormatException ex)
                {
                    System.out.println("WRONG AMOUNT. PLEASE ENTER VALID NUMBER");
                }
            }

            try {
                // Calculating the amount of the targer currency
                double resultCalcAmount = calcCurrencyAmount(convertOption, amount);
                switch (convertOption) // Fills the results list
                {
                    case 1:
                        resultsList.add(String.format("Converted %.2f Dollars to %.2f Shekels. ", amount, resultCalcAmount));
                        break;
                    case 2:
                        resultsList.add(String.format("Converted %.2f Shekels to %.2f Dollars. ", amount, resultCalcAmount));
                        break;
                }

                System.out.printf("Total amount in your chosen currency is: %.2f \n", resultCalcAmount);
            }
            catch(Exception ex)
            {
                System.out.printf("ERROR DURING CALCULATION: " + ex.getMessage());
            }

            do
            {
                System.out.println("\nDo you want to make a new currency conversion (Y/N) ?");
                calcAgainYN = scnr.nextLine();
            }
            while (!calcAgainYN.toLowerCase().equals("y") && !calcAgainYN.toLowerCase().equals("n"));

            if (calcAgainYN.toLowerCase().equals("n"))
            {
                System.out.println("Thanks for using our currency converter.\n");

                // Loops through the results list, which has been filled and display them.
                for (String msg : resultsList) {
                    System.out.println(msg);

                }
                writeLogFile(resultsList,"c:\\logs\\results.txt"); // writes all conversion made in session to a file.
            }
        }
    }

    /**
     * Records all calculations made in session to a file.
     * @param resultsList
     * @param LogFileName
     */
    public static void writeLogFile (List<String> resultsList, String LogFileName){
        try {
            File file = new File(LogFileName);
            file.createNewFile();

            FileWriter fWriter = new FileWriter(file);
            for (String msg : resultsList) {
                fWriter.write(msg + System.getProperty("line.separator"));
            }
            fWriter.close();
            System.out.printf("Successfully wrote results to log file %s" , LogFileName);
        } catch (IOException e) {
            System.out.println("\nAn error occurred while writing to log file");
            e.printStackTrace();
        }
    }

    /**
     * Returns the calculated amount of a target currency by the original amount (the amount parameter(
     * @param convertOption
     * @param amount
     * @return calculated amount of a target currency
     * @throws Exception is thrown in any case of a problem in the calculation process.
     */
    private static double calcCurrencyAmount(int convertOption,double amount) throws Exception {
        double resultCalcAmount = 0;
            switch (convertOption) {
                case 1: // Convert Dollars to shekels
                    Coin usd = CoinFactory.getCoinInstance(Coins.USD);
                    resultCalcAmount = usd.calculate(amount);
                    break;
                case 2: // Convert Shekels to Dollars
                    Coin ils = CoinFactory.getCoinInstance(Coins.ILS);
                    resultCalcAmount = ils.calculate(amount);
                    break;
                default:
                    throw new Exception ("convert option number is unknown");
            }

        return resultCalcAmount;
    }
}
