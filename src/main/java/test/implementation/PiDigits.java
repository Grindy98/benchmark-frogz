package test.implementation;
import javafx.scene.control.PasswordField;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class PiDigits
{
    private static BigDecimal partialProduct(int iter)
    {
        BigDecimal numerator=new BigDecimal("1");
        BigDecimal denominator=new BigDecimal("1");
        int currentAdd = 2;

        if(iter == 1)
            return new BigDecimal("1");

        for(int i=1; i<iter; i++)
        {
            String iString = String.valueOf(currentAdd);
            BigDecimal iBigDecimal = new BigDecimal(iString);

            numerator = numerator.multiply(iBigDecimal.subtract(new BigDecimal("1")));
            denominator = denominator.multiply(iBigDecimal);

            currentAdd = currentAdd + 2;
        }

        return numerator.divide(denominator, MathContext.DECIMAL128);
    }

    private static BigDecimal getArcsin(int precision)
    {
        BigDecimal currentTerm ;
        BigDecimal sum = new BigDecimal("0");
        BigDecimal x = new BigDecimal("0.5");
        for(int i=1; i<precision; i++)
        {
            currentTerm= partialProduct(i);
            BigDecimal currentPow = x.pow(2*i-1);

            String iString = String.valueOf(2*i-1);

            BigDecimal multiplicand = currentPow.divide(new BigDecimal(iString), MathContext.DECIMAL128);
            currentTerm=currentTerm.multiply(multiplicand);

            sum=sum.add(currentTerm);
        }
        return sum;
    }

    public static BigDecimal getPi()
    {
        return getArcsin(100).multiply(new BigDecimal("6"));
    }

    public static void main(String[] args) {
        System.out.println(getPi());
    }
}
