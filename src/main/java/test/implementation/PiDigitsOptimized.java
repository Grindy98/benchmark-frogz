package test.implementation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class PiDigitsOptimized {

    public static final int NUM_PI_DIGITS = 10000;
    private static final int NUM_DIGITS_TO_KEEP = NUM_PI_DIGITS + 2;

    static BigDecimal fac(BigDecimal n, BigDecimal acc) {
        if (n.equals(BigDecimal.ONE)) {
            return acc;
        }

        if(n.equals(BigDecimal.ZERO)){
            return new BigDecimal("1");
        }

        BigDecimal lessOne = n.subtract(BigDecimal.ONE);
        return fac(lessOne, acc.multiply(lessOne));
    }

    public static BigDecimal computePi(int iterations){

        //compute the constant C
        BigDecimal sqrtRes = bigSqrt(new BigDecimal("10005"));

        BigDecimal C = sqrtRes.multiply(new BigDecimal("426880"));
        BigDecimal sum = new BigDecimal("0");

        //compute the sum up until iterations in this loop
        for(int q = 0; q < iterations; q++){

            //compute Mq
            int temp = 6 * q;
            String tempString = String.valueOf(temp);
            BigDecimal bigTemp1 = new BigDecimal(tempString);
            bigTemp1 = fac(bigTemp1, bigTemp1);

            temp = 3 * q;
            tempString = String.valueOf(temp);
            BigDecimal bigTemp2 = new BigDecimal(tempString);
            bigTemp2 = fac(bigTemp2, bigTemp2);

            temp = q;
            tempString = String.valueOf(q);
            BigDecimal bigTemp3 = new BigDecimal(tempString);
            bigTemp3 = fac(bigTemp3, bigTemp3);
            bigTemp3 = bigTemp3.pow(3);

            BigDecimal Mq = bigTemp1.divide(bigTemp2.multiply(bigTemp3), MathContext.DECIMAL128);

            //compute Lq
            BigDecimal Lq = new BigDecimal(tempString);
            Lq = Lq.multiply(new BigDecimal("545140134"));
            Lq = Lq.add(new BigDecimal("13591409"));

            //compute Xq
            BigDecimal Xq = new BigDecimal("-262537412640768000");
            Xq = Xq.pow(q);

            //update sum
            sum = sum.add((Mq.multiply(Lq)).divide(Xq, MathContext.DECIMAL128));


        }

        BigDecimal one = new BigDecimal("1");
        BigDecimal inversedSum = one.divide(sum, MathContext.DECIMAL128);

        return C.multiply(inversedSum);
    }

    private static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision) {
        BigDecimal fx = xn.pow(2).add(c.negate());
        BigDecimal fpx = xn.multiply(new BigDecimal(2));
        BigDecimal xn1 = fx.divide(fpx,MathContext.DECIMAL128);
        xn1 = xn.add(xn1.negate());
        //2*NUM_DIGITS_TO_KEEP,BigDecimal.ROUND_HALF_EVEN
        //----
        BigDecimal currentSquare = xn1.pow(2);
        BigDecimal currentPrecision = currentSquare.subtract(c);
        currentPrecision = currentPrecision.abs();
        if ( currentPrecision.compareTo(precision) <= -1 )
        {
            return xn1;
        }
        return sqrtNewtonRaphson(c, xn1,precision);
    }

    public static BigDecimal bigSqrt(BigDecimal c) {
        return sqrtNewtonRaphson(c,new BigDecimal(1),new BigDecimal(1).divide(new BigDecimal(10).pow(NUM_DIGITS_TO_KEEP)));
    }

    public static void main(String[] args){
        BigDecimal tst = new BigDecimal("0");
        System.out.println(computePi(15));

    }
}
