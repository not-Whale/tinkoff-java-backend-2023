package edu.project3.writers;

@SuppressWarnings("RegexpSinglelineJava")
public class ReportSoutWriter implements ReportWriter {
    public ReportSoutWriter() {}

    @Override
    public void write(String report) {
        System.out.println(report);
    }
}
