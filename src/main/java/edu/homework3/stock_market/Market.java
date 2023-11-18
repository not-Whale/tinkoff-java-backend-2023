package edu.homework3.stock_market;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class Market implements StockMarket {
    private final PriorityQueue<Stock> priorityQueue;

    public Market() {
        this.priorityQueue = new PriorityQueue<>(new StockComparator());
    }

    @Override
    public void add(Stock stock) {
        this.priorityQueue.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        this.priorityQueue.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return this.priorityQueue.peek();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        PriorityQueue<Stock> tmp = new PriorityQueue<>(this.priorityQueue);
        while (!tmp.isEmpty()) {
            stringBuilder.append(tmp.poll()).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Market
            && Arrays.equals(((Market) other).priorityQueue.toArray(), this.priorityQueue.toArray())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.priorityQueue);
    }

    private static class StockComparator implements Comparator<Stock> {

        @Override
        public int compare(Stock stock1, Stock stock2) {
            return (-1) * stock1.compareTo(stock2);
        }
    }
}
