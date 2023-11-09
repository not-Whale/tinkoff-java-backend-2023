package edu.homework5.date_parser.parsers;

import java.time.LocalDate;

public interface Parser {
    boolean canFormatDate(String date);

    LocalDate formatDate(String date);
}
