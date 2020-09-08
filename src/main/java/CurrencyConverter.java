import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This tool enables to make a conversion between three currencies
 * by using a known rates.
 * User opts the original and target currencies from a menu and
 * typing the original amount.
 * The system make the calculation and displays the new amount of the target currency.
 * Its possible to repeat the conversions.
 * When the user opt to end, the screen shows all
 * conversions made in session and writes that information to a file.
 * User can choose whether to display this file in an editor or not.
 */
public class CurrencyConverter {

    private final static String LogFileName = "c:\\logs\\results.txt";

    public static void main(String[] args) {
        StringBuilder mainMenu = new StringBuilder();
        mainMenu.append("Welcome to currency converter\n");
        mainMenu.append("Please choose an option (1/3):\n");
        mainMenu.append("1. Dollar to Shekel\n");
        mainMenu.append("2. Shekel to Dollar\n");
        mainMenu.append("3. Shekel to Euro");

        String calcAgainYN = "y";

        // Saves every calculation made during the session
        List<Result> resultsList = new ArrayList<Result>();

        while (calcAgainYN.toLowerCase().equals("y")) {   // As far as the user asked for a new calculation
            int convertOption = 0; // User choice
            double amount = 0;  // User amount in the original currency.

            Scanner scnr = new Scanner(System.in);  // Create a Scanner object

            while (convertOption != 1 && convertOption != 2 && convertOption != 3) { // Not one of valid options

                // MAIN MENU IS DISPLAYED
                System.out.println(mainMenu.toString());
                // This message shows only if wrong menu number is entered.
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
                // Calculating the amount of the target currency
                double resultCalcAmount = calcCurrencyAmount(convertOption, amount);
                System.out.printf("Total amount in your chosen currency is: %.2f \n", resultCalcAmount);

                fillResultList(resultsList,amount,resultCalcAmount,convertOption);

            }
            catch(Exception ex)
            {
                System.out.printf("ERROR DURING CALCULATION: " + ex.getMessage());
            }

            // Ask for a new calculation. User must enter "y" on "n".
            do
            {
                System.out.println("\nDo you want to make a new currency conversion (Y/N) ?");
                calcAgainYN = scnr.nextLine();
            }
            while (!calcAgainYN.toLowerCase().equals("y") && !calcAgainYN.toLowerCase().equals("n"));

            // Displays list of entered conversions, and writes then to a file.
            if (calcAgainYN.toLowerCase().equals("n"))
            {
                System.out.println("Thanks for using our currency converter.\n");

                // Loops through the results list, which has been filled and display them.
                for (Result res : resultsList) {
                    System.out.println(res.getConversionDescription());
                }
                writeLogFile(resultsList,LogFileName); // writes all conversion made in session to a file.

                System.out.println("\n\nDo you want to edit the results file (y/n) ?");
                String openFileYN = scnr.nextLine();
                if (openFileYN.toLowerCase().equals("y"))
                {
                    try {
                        Desktop.getDesktop().edit(new File(LogFileName));
                    }
                    catch(IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }

            }
        }
    }

    /**
     * Inserts new currency calculation in the results list.
     * @param resultsList a List of strings that holds every conversion done by the user.
     * @param amount the original amount money (in the original currency) inputted by the user to convert to the target currency.
     * @param resultCalcAmount the calculated amount money (in the target currency) inputted by the user to convert.
     * @param convertOption indicate the conversion type : 1. Dollars to Shekels 2. Shekels to Dollars
     */
    public static void fillResultList(List<Result>resultsList,double amount,double resultCalcAmount,int convertOption)
    {
        Result res;

        switch (convertOption) // Fills the results list
        {
            case 1:
                res = new Result(resultCalcAmount,String.format("Converted %.2f Dollars to %.2f Shekels. ", amount, resultCalcAmount));
                resultsList.add(res);
                break;
            case 2:
                res = new Result(resultCalcAmount, String.format("Converted %.2f Shekels to %.2f Dollars. ", amount, resultCalcAmount));
                resultsList.add(res);
                break;
            case 3:
                res = new Result(resultCalcAmount, String.format("Converted %.2f Shekels to %.2f Euros. ", amount, resultCalcAmount));
                resultsList.add(res);
                break;
        }
    }


    /**
     * Records all calculations made in session to a file.
     * @param resultsList
     * @param LogFileName
     */
    public static void writeLogFile (List<Result> resultsList, String LogFileName){
        try {
            File file = new File(LogFileName);
            file.createNewFile();

            FileWriter fWriter = new FileWriter(file);
            for (Result res : resultsList) {
                fWriter.write(res.getConversionDescription() + System.getProperty("line.separator"));
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
    public static double calcCurrencyAmount(int convertOption,double amount) throws Exception {
        double resultCalcAmount = 0;
        Coin ils;
            switch (convertOption) {
                case 1: // Convert Dollars to shekels
                    Coin usd = CoinFactory.getCoinInstance(Coins.USD);
                    resultCalcAmount = usd.calculate(amount);
                    break;
                case 2: // Convert Shekels to Dollars
                    ils = CoinFactory.getCoinInstance(Coins.ILS);
                    resultCalcAmount = ils.calculate(amount);
                    break;
                case 3: // Convert Shekels to Euros
                    ils = CoinFactory.getCoinInstance(Coins.ILS_EUR);
                    resultCalcAmount = ils.calculate(amount);
                    break;
                default:
                    throw new Exception ("convert option number is unknown");
            }

        return resultCalcAmount;
    }
}
