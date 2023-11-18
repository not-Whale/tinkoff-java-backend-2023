package edu.homework3.stock_market;

public enum Companies {
    FIX_PRICE("Fix Price", 365.5),
    OZON_HOLDINGS_PLC("Ozon Holdings", 2685.0),
    POLYMETAL("Polymetal", 545.2),
    QIWI("QIWI", 621.0),
    TCS_GROUP("TCS Group", 3465.0),
    WHOOSH("Whoosh", 215.02),
    X5_RETAIL_GROUP("X5 Group", 2390.5);

    private final String name;

    private final double price;

    Companies(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String company() {
        return this.name;
    }

    public double price() {
        return this.price;
    }
}
