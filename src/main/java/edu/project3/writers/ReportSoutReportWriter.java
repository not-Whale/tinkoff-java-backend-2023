package edu.project3.writers;

@SuppressWarnings("RegexpSinglelineJava")
public class ReportSoutReportWriter implements ReportWriter {
    public ReportSoutReportWriter() {}

    @Override
    public void write(String report) {
        System.out.println(report);
    }
}
