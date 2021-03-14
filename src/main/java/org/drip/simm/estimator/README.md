# DROP SIMM Estimator Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP SIMM Estimator Package implements the ISDA SIMM Core + Add-On Estimator.


## Class Components

 * [***AdditionalInitialMargin***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/estimator/AdditionalInitialMargin.java)
 <i>AdditionalInitialMargin</i> holds the Additional Initial Margin along with the Product Specific Add-On
 Components.

 * [***ProductClassMargin***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/estimator/ProductClassMargin.java)
 <i>ProductClassMargin</i> holds the Initial Margin Estimates for a Single Product Class across the Six Risk
 Factors - Interest Rate, Credit Qualifying, Credit Non-Qualifying, Equity, Commodity, and FX.

 * [***ProductClassSensitivity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/estimator/ProductClassSensitivity.java)
 <i>ProductClassSensitivity</i> holds the multiple Risk Class Sensitivities for a single Product Class.

 * [***ProductClassSettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/estimator/ProductClassSettings.java)
 <i>ProductClassSettings</i> holds the Settings that govern the Generation of the ISDA SIMM Bucket Sensitivities across Individual Product Classes.


## References

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin Calculations
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>

 * Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing Framework
 	for Forecasting Initial Margin Requirements https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279
 		<b>eSSRN</b>

 * Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements - A
 	Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167 <b>eSSRN</b>

 * International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
		https://www.isda.org/a/oFiDE/isda-simm-v2.pdf


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
