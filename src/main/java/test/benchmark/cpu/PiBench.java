package test.benchmark.cpu;

import test.benchmark.IBenchmark;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class PiBench implements IBenchmark {

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

        result = finalSum.get().multiply(new BigDecimal("6"));
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



    class PiThread implements Runnable {

        int threadNumber;

        public PiThread(int threadNumber){
            this.threadNumber = threadNumber;
        }

        private BigDecimal partialProduct(int iter)
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

        @Override
        public void run() {
            BigDecimal currentTerm ;
            BigDecimal sum = new BigDecimal("0");
            BigDecimal x = new BigDecimal("0.5");

            for(int i = threadNumber + 1; i < iterations; i += noOfThreads)
            {
                currentTerm = partialProduct(i);
                BigDecimal currentPow = x.pow(2*i-1);

                String iString = String.valueOf(2*i-1);

                BigDecimal multiplicand = currentPow.divide(new BigDecimal(iString), MathContext.DECIMAL128);
                currentTerm=currentTerm.multiply(multiplicand);

                sum=sum.add(currentTerm);
            }

            finalSum.accumulateAndGet(sum, (f, b) -> f.add(b));
        }

    }
}
