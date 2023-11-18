package edu.homework3.stock_market;

import java.util.Objects;

public class Stock implements Comparable<Stock> {
    private final String companyName;

    private final double rublePrice;

    public Stock(String companyName, double rublePrice) {
        if (companyName == null) {
            throw new IllegalArgumentException();
        }

        this.companyName = companyName;
        this.rublePrice = rublePrice;
    }

    @Override
    public int compareTo(Stock other) {
        return (int) (this.rublePrice - other.rublePrice);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Stock
            && ((Stock) other).companyName.equals(this.companyName)
            && ((Stock) other).rublePrice == this.rublePrice
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.companyName, this.rublePrice);
    }

    @Override
    public String toString() {
        return "(" + this.companyName + ": " + this.rublePrice + ")";
    }
}
