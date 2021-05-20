package test.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements ILog{

    private FileWriter writer;
    public FileLogger(File file){
        try {
            file.createNewFile();
            writer = new FileWriter(file);
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public void write(long val) {
        try {
            writer.write(String.valueOf(val) + "\n");
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void write(String str) {
        try {
            writer.write(str + "\n");
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void write(Object... params) {
        try {
            for(Object o : params){
                writer.write(o + " ");
            }
            writer.write("\n");
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
