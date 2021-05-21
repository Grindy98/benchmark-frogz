package test.benchmark.cpu;

import test.benchmark.IBenchmark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CPUThreadedSHA256Hash implements  IBenchmark{
    private String result;
    volatile boolean running = true;

    @Override
    public void initialize(Object... params)
    {

    }

    @Override
    public void warmUp()
    {

    }

    @Override
    public void run()
    {
        throw new UnsupportedOperationException("Method not implemented. Use run(Object) instead");
    }

    @Override
    public void run(Object... options)
    {
        // maximum text length
        int maxTextLength = (Integer)options[0];
        // thread pool size
        int nThreads = (Integer)options[1];
        // hash code
        int hashCode = (Integer)options[2];

        //create thread pool and hash manager
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        HashManager hasher = new HashManager();



        //spawn all n threads
        for(int i = 0; i < nThreads; ++i)
        {
            HashBreakerTask worker = new HashBreakerTask(hasher, maxTextLength, hashCode, i, nThreads);
            executor.execute(worker);
        }

        // stop executor
        executor.shutdown();

        //wait for threads to finish
        while (!executor.isTerminated())
        {
        }
        System.out.println("Running done!");
    }

    @Override
    public void clean()
    {

    }

    @Override
    public String getResult()
    {
        return String.valueOf(result);
    }

    @Override
    public void cancel()
    {
        running = false;
    }

    class HashBreakerTask implements Runnable
    {
        // used to compute hashes from strings
        private final HashManager hasher;
        // maximum string length
        private final int maxlen;
        // the expected hash output
        private final int expectedHash;
        //starting offset
        private final int offset;
        //increment
        private final int increment;



        //for backtracking
        private char [] bk_st;

        public HashBreakerTask(HashManager hasher, int maxlen, int expectedHash, int offset, int increment)
        {
            this.hasher = hasher;
            this.maxlen = maxlen;
            this.expectedHash = expectedHash;
            this.offset = offset;
            this.increment = increment;

            bk_st = null;
        }

        @Override
        public void run()
        {
            for(int i = 1; i <= maxlen && running == true; ++i)
            {
                System.out.println("Thread " + offset + ", setting text length to " + i + " characters.");
                bk_st = new char[i];
                bk(0, i);
            }
        }


        private void bk(int lvl, int maxlvl)
        {
            if(running == false)
                return;

            if (lvl >= maxlvl)
            {

                String s=String.valueOf(bk_st);
                String res="";
                try {
                    res = hasher.toHexString(hasher.getSHA(s));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                //System.out.println("Thread " + offset + ", trying " + new String(bk_st) + " - " + res);
                if(String.valueOf(expectedHash) == res)
                {
                    running = false;
                    result = new String(bk_st);
                }

                return;
            }

            if(lvl == maxlvl - 1)
            {
                for(int i = offset; i < 26; i = i + increment)
                {
                    bk_st[lvl] = (char) (i + 'a');
                    bk(lvl + 1, maxlvl);
                }
            }
            else
            {
                for(int i = 0; i < 26; i = i + 1)
                {
                    bk_st[lvl] = (char) (i + 'a');
                    bk(lvl + 1, maxlvl);
                }
            }

            bk_st[lvl] = 0;
        }
    }

    /**
     * Used to compute hashes from strings
     */
    class HashManager
    {
        // do not change alphabet
        private final String charSet = "abcdefghijklmnopqrstuvwxyz";

        // do not change function

        public byte[] getSHA(String input) throws NoSuchAlgorithmException
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }
        public String toHexString(byte[] hash)
        {
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32)
            {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        }
    }
}
