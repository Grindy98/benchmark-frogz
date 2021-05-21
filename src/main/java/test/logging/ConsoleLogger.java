package test.logging;

public class ConsoleLogger implements ILog
{
    @Override
    public void write(long val) {
        System.out.println(val);
    }

    @Override
    public void write(String str) {
        System.out.println(str);
    }

    @Override
    public void write(Object... params) {
        for(Object o : params){
            System.out.print(o + " ");
        }
        System.out.print("\n");
    }

    @Override
    public void close(){};
}
