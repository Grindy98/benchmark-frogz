package test.logging;

public interface ILog
{
    /**
     * Write to given medium a value
     * @param val Value to be written
     */
    void write(long val);

    /**
     * Write to given medium a string
     * @param str String to be written
     */
    void write(String str);

    /**
     * Write to given medium a list of Objects with ToString
     * @param params List of objects to be written
     */
    void write(Object... params);

    /**
     * Function should always be called before an object implementing ILogger goes out of scope<br>
     */
    void close();
}