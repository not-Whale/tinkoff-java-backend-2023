package edu.homework6.port_scanner;

import java.util.function.Predicate;

public class PrettyPrinter {
    public PrettyPrinter() {}

    public String getPrettyPortStatusString(PortState[] portStates) {
        return getPrettyPortStatusString(portStates, (portState) -> true);
    }

    @SuppressWarnings("MagicNumber")
    public String getPrettyPortStatusString(PortState[] portStates, Predicate<PortState> predicate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Протокол    Порт    Статус    Сервис\n");
        for (PortState port : portStates) {
            if (predicate.test(port)) {
                stringBuilder
                    .append(port.protocol().verbal())
                    .repeat(" ", 9)
                    .append(port.port())
                    .repeat(" ", 8 - (port.port() + "").length())
                    .append(port.status())
                    .repeat(" ", 4)
                    .append(port.service())
                    .append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
