package edu.project3.writers;

@SuppressWarnings("RegexpSinglelineJava")
public class LogSoutWriter implements Writer {
    public LogSoutWriter() {}

    @Override
    public void write(String report) {
        System.out.println(report);
    }
}
