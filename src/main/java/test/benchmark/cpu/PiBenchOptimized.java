package test.benchmark.cpu;

import test.benchmark.IBenchmark;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class PiBenchOptimized implements IBenchmark {

    public static final int NUM_PI_DIGITS = 10000;
    private static final int NUM_DIGITS_TO_KEEP = NUM_PI_DIGITS + 2;

    int noOfThreads;
    int iterations;
    AtomicReference<BigDecimal> finalSum;
    BigDecimal result;

    @Override
    public void initialize(Object... params) {
        finalSum = new AtomicReference<BigDecimal>(BigDecimal.ZERO);
    }

    @Override
    public void warmUp() {

    }

    @Override
    public void run() {

    }

    @Override
    public void run(Object... params) {

        noOfThreads = (Integer)params[0];
        iterations = (Integer)params[1];

        ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);

        for(int i = 0; i < noOfThreads; i++){
            executor.execute(new PiThread(i));
        }

        executor.shutdown();

        while(!executor.isTerminated()) {
            try {
                executor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //compute the constant C
        BigDecimal sqrtRes = bigSqrt(new BigDecimal("10005"));

        BigDecimal C = sqrtRes.multiply(new BigDecimal("426880"));

        BigDecimal one = new BigDecimal("1");
        BigDecimal inversedSum = one.divide(finalSum.get(), MathContext.DECIMAL128);
        result = C.multiply(inversedSum);

        System.out.println(result);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void clean() {

    }

    @Override
    public String getResult() {
        return  result.toString();
    }

    public static BigDecimal fac(BigDecimal n, BigDecimal acc) {
        if (n.equals(BigDecimal.ONE)) {
            return acc;
        }

        if(n.equals(BigDecimal.ZERO)){
            return new BigDecimal("1");
        }

        BigDecimal lessOne = n.subtract(BigDecimal.ONE);
        return fac(lessOne, acc.multiply(lessOne));
    }

    public static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision) {
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
        return sqrtNewtonRaphson(c,new BigDecimal(1),new BigDecimal(1).divide(new BigDecimal(10).pow(NUM_DIGITS_TO_KEEP),MathContext.DECIMAL128));
    }

    class PiThread implements Runnable {

        int threadNumber;

        public PiThread(int threadNumber){
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {

            BigDecimal sum = new BigDecimal("0");

            //compute the sum up until iterations in this loop
            for(int q = threadNumber; q < iterations ; q += noOfThreads){

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

            finalSum.accumulateAndGet(sum, (f, b) -> f.add(b));
        }
    }

}
