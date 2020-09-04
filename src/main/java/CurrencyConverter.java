import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) {
        StringBuilder mainMenu = new StringBuilder();
        mainMenu.append("Welcome to currency converter\n");
        mainMenu.append("Please choose an option (1/2):\n");
        mainMenu.append("1. Dollars to Shekels\n");
        mainMenu.append("2. Shekels to Dollars");

        String calcAgainYN = "y";

        List<String> resultsList = new ArrayList<String>();

        while (calcAgainYN.toLowerCase().equals("y")) {
            int convertOption = 0;
            double amount = 0;
            double resultCalcAmount = 0;

            Scanner scnr = new Scanner(System.in);  // Create a Scanner object

            while (convertOption != 1 && convertOption != 2) {
                System.out.println(mainMenu.toString());
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
                switch (convertOption) {
                    case 1: // Convert Dollars to shekels
                        Coin usd = CoinFactory.getCoinInstance(Coins.USD);
                        resultCalcAmount = usd.calculate(amount);
                        resultsList.add(String.format("Converted %.2f Dollars to %.2f Shekels. ",amount, resultCalcAmount));
                        break;
                    case 2: // Convert Shekels to Dollars
                        Coin ils = CoinFactory.getCoinInstance(Coins.ILS);
                        resultCalcAmount = ils.calculate(amount);
                        resultsList.add(String.format("Converted %.2f Shekels to %.2f Dollars. ",amount, resultCalcAmount));
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.printf("Total amount in your chosen currency is: %.2f \n",resultCalcAmount);

            do
            {
                System.out.println("\nDo you want to make a new currency conversion (Y/N) ?");
                calcAgainYN = scnr.nextLine();
            }
            while (!calcAgainYN.toLowerCase().equals("y") && !calcAgainYN.toLowerCase().equals("n"));

            if (calcAgainYN.toLowerCase().equals("n"))
            {
                System.out.println("Thanks for using our currency converter.\n");

                for (String msg : resultsList) {
                    System.out.println(msg);

                }
                writeLogFile(resultsList,"c:\\logs\\results.txt");
            }
        }
    }

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
}
