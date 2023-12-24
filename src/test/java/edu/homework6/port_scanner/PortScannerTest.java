package edu.homework6.port_scanner;

import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сканирование портов и определение их занятости.")
public class PortScannerTest {
    @Test
    @DisplayName("Сканирование и вывод портов от 20 до 25.")
    void scanAndPrint() {
        // given
        PrettyPrinter prettyPrinter = new PrettyPrinter();

        // when
        String result = prettyPrinter.getPrettyPortStatusString(PortScanner.scan(20, 26));

        // then
        assertThat(result).isNotNull().isEqualTo(
            """
                Протокол    Порт    Статус    Сервис
                TCP         20      CLOSED    File Transfer [Default Data]
                UDP         20      CLOSED    File Transfer [Default Data]
                TCP         21      CLOSED    File Transfer Protocol [Control]
                UDP         21      CLOSED    File Transfer Protocol [Control]
                TCP         22      CLOSED    The Secure Shell (SSH) Protocol
                UDP         22      CLOSED    The Secure Shell (SSH) Protocol
                TCP         23      CLOSED    Telnet
                UDP         23      CLOSED    Telnet
                TCP         24      CLOSED    N/A
                UDP         24      CLOSED    N/A
                TCP         25      CLOSED    Simple Mail Transfer
                UDP         25      CLOSED    Simple Mail Transfer
                """
        );
    }

    @Test
    @DisplayName("Сканирование портов от 20 до 25 и вывод только с подписью")
    void scanAndPrintWithServiceName() {
        // given
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        Predicate<PortState> withServiceName = (portState -> !portState.service().equals("N/A"));
        Predicate<PortState> between20And25 = (portState -> portState.port() > 19 && portState.port() < 26);

        // when
        String result = prettyPrinter.getPrettyPortStatusString(
            PortScanner.scan(),
            withServiceName.and(between20And25)
        );

        // then
        assertThat(result).isNotNull().isEqualTo(
            """
                Протокол    Порт    Статус    Сервис
                TCP         20      CLOSED    File Transfer [Default Data]
                UDP         20      CLOSED    File Transfer [Default Data]
                TCP         21      CLOSED    File Transfer Protocol [Control]
                UDP         21      CLOSED    File Transfer Protocol [Control]
                TCP         22      CLOSED    The Secure Shell (SSH) Protocol
                UDP         22      CLOSED    The Secure Shell (SSH) Protocol
                TCP         23      CLOSED    Telnet
                UDP         23      CLOSED    Telnet
                TCP         25      CLOSED    Simple Mail Transfer
                UDP         25      CLOSED    Simple Mail Transfer
                """
        );
    }
}
