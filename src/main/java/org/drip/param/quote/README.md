# DROP Param Quote Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Param Quote Package implements Multi-sided Multi-Measure Ticks Quotes.


## Class Components

 * [***MultiSided***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quote/MultiSided.java)
 <i>MultiSided</i> implements the Quote interface, which contains the stubs corresponding to a product quote.
 It contains the quote value, quote instant for the different quote sides (bid/ask/mid).

 * [***ProductMultiMeasure***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quote/ProductMultiMeasure.java)
 <i>ProductMultiMeasure</i> holds the different types of quotes for a given component. It contains a single market field/quote pair, but multiple alternate named quotes (to accommodate quotes on different measure for the component).

 * [***ProductTick***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quote/ProductTick.java)
 <i>ProductTick</i> holds the tick related product parameters - it contains the product ID, the quote
 composite, the source, the counter party, and whether the quote can be treated as a mark.

 * [***TickerPriceStatistics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quote/TickerPriceStatistics.java)
 <i>TickerPriceStatistics</i> maintains the Running "Thin" Price Statistics for a Single Ticker.

 * [***TickerPriceStatisticsContainer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quote/TickerPriceStatisticsContainer.java)
 <i>TickerPriceStatisticsContainer</i> maintains the Running "Thin" Price Statistics for all Tickers.


## DROP Specifications

 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
