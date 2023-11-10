package edu.homework3.stock_market;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.stream.Stream;
import static edu.homework3.stock_market.Companies.FIX_PRICE;
import static edu.homework3.stock_market.Companies.OZON_HOLDINGS_PLC;
import static edu.homework3.stock_market.Companies.POLYMETAL;
import static edu.homework3.stock_market.Companies.QIWI;
import static edu.homework3.stock_market.Companies.TCS_GROUP;
import static edu.homework3.stock_market.Companies.WHOOSH;
import static edu.homework3.stock_market.Companies.X5_RETAIL_GROUP;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Биржа.")
class MarketTest {
    @ParameterizedTest
    @ArgumentsSource(StockMarketArgumentsProvider.class)
    @DisplayName("Вывод самой дорогой акции.")
    void stockMarketSort(Stock[] stocks, Stock answer) {
        // given
        Market market = new Market();
        for (Stock stock : stocks) {
            market.add(stock);
        }

        // when
        Stock mostValuableStock = market.mostValuableStock();

        // then
        assertThat(mostValuableStock).isEqualTo(answer);
    }

    static class StockMarketArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            Stock fixPrice = new Stock(FIX_PRICE.company(), FIX_PRICE.price());
            Stock ozon = new Stock(OZON_HOLDINGS_PLC.company(), OZON_HOLDINGS_PLC.price());
            Stock polymetal = new Stock(POLYMETAL.company(), POLYMETAL.price());
            Stock qiwi = new Stock(QIWI.company(), QIWI.price());
            Stock tcs = new Stock(TCS_GROUP.company(), TCS_GROUP.price());
            Stock whoosh = new Stock(WHOOSH.company(), WHOOSH.price());
            Stock x5 = new Stock(X5_RETAIL_GROUP.company(), X5_RETAIL_GROUP.price());

            return Stream.of(
                Arguments.of(new Stock[] {fixPrice, polymetal, tcs, x5}, tcs),
                Arguments.of(new Stock[] {ozon, qiwi, whoosh}, ozon),
                Arguments.of(new Stock[] {fixPrice, polymetal, qiwi, whoosh}, qiwi),
                Arguments.of(new Stock[] {ozon, tcs, x5}, tcs)
            );
        }
    }

    @Test
    @DisplayName("Удаление элемента.")
    void stockMarketRemove() {
        // given
        Market market = new Market() {{
            add(new Stock(FIX_PRICE.company(), FIX_PRICE.price()));
            add(new Stock(OZON_HOLDINGS_PLC.company(), OZON_HOLDINGS_PLC.price()));
        }};

        Market marketAfterRemove = new Market() {{
            add(new Stock(FIX_PRICE.company(), FIX_PRICE.price()));
        }};

        // when
        market.remove(market.mostValuableStock());

        // then
        assertThat(market).isEqualTo(marketAfterRemove);
    }
}
