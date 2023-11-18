package edu.homework3.stock_market;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.homework3.stock_market.Companies.FIX_PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Акция.")
class StockTest {
    @Test
    @DisplayName("Null-аргумент.")
    void stockNullInput() {
        // given
        String name = null;
        double price = 15.0;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Stock(name, price)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Эквивалетность акций.")
    void stockEquals() {
        // given
        Stock stock1 = new Stock(FIX_PRICE.company(), FIX_PRICE.price());
        Stock stock2 = new Stock(FIX_PRICE.company(), FIX_PRICE.price());

        // when
        boolean equals = stock1.equals(stock2);

        // then
        assertThat(equals).isTrue();
    }
}
