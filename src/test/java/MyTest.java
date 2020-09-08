import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MyTest {

    @Test
    public void checkDollar2shekels()
    {
        CurrencyConverter curConv = new CurrencyConverter();

        try {
            double resultCalcAmount = curConv.calcCurrencyAmount(1, 100);
            Assert.assertEquals(resultCalcAmount,352);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Test
    public void checkCurrencyConverterLogFile()
    {
        CurrencyConverter curConv = new CurrencyConverter();

        List<Result> resultsList = new ArrayList<Result>();
        curConv.fillResultList(resultsList,100,352,1);
        curConv.fillResultList(resultsList,100,28,2);
        curConv.writeLogFile(resultsList,"c:\\logs\\results.txt");

        try {
            String contents = Files.readString(Paths.get("c:\\logs\\results.txt"));
            System.out.println("\nFile contents is:\n");
            System.out.println(contents);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }








}
