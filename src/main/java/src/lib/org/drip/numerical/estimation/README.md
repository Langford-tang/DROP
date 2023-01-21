# DROP Numerical Estimation Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Numerical Estimation Package implements Function Numerical Estimates/Corrections/Bounds.


## Class Components

 * [***R0ToR1Series***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R0ToR1Series.java)
 <i>R0ToR1Series</i> generates a Series of Weighted Numerical R<sup>0</sup> To R<sup>1</sup> Terms.

 * [***R0ToR1SeriesTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R0ToR1SeriesTerm.java)
 <i>R0ToR1SeriesTerm</i> exposes a R<sup>0</sup> To R<sup>1</sup> Term of a Numerical Series.

 * [***R1Estimate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R1Estimate.java)
 <i>R1Estimate</i> holds the Bounded R<sup>1</sup> Numerical Estimate of a Function.

 * [***R1ToR1Estimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R1ToR1Estimator.java)
 <i>R1ToR1Estimator</i> exposes the Stubs behind R<sup>1</sup> - R<sup>1</sup> Approximate Numerical Estimators.

 * [***R1ToR1IntegrandEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R1ToR1IntegrandEstimator.java)
 <i>R1ToR1IntegrandEstimator</i> exposes the Stubs behind the Integrand Based R<sup>1</sup> - R<sup>1</sup> Approximate Numerical Estimators.

 * [***R1ToR1IntegrandGenerator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R1ToR1IntegrandGenerator.java)
 <i>R1ToR1IntegrandGenerator</i> exposes the Integrand Generation behind the R<sup>1</sup> - R<sup>1</sup> Approximate Numerical Estimators.

 * [***R1ToR1IntegrandLimitEstimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R1ToR1IntegrandLimitEstimator.java)
 <i>R1ToR1IntegrandLimitEstimator</i> exposes the Stubs behind the Integrand Based R<sup>1</sup> - R<sup>1</sup> Approximate Numerical Estimators with the Limits as the Variate.

 * [***R1ToR1Series***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R1ToR1Series.java)
 <i>R1ToR1Series</i> holds the R<sup>1</sup> To R<sup>1</sup> Expansion Terms in the Ordered Series of the Numerical Estimate for a Function.

 * [***R1ToR1SeriesTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R1ToR1SeriesTerm.java)
 <i>R1ToR1SeriesTerm</i> exposes the R<sup>1</sup> To R<sup>1</sup> Series Expansion Term in the Ordered Series of the Numerical Estimate for a Function.

 * [***R2ToR1Estimator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R2ToR1Estimator.java)
 <i>R2ToR1Estimator</i> exposes the Stubs behind R<sup>2</sup> - R<sup>1</sup> Approximate Numerical Estimators.

 * [***R2ToR1Series***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R2ToR1Series.java)
 <i>R2ToR1Series</i> holds the R<sup>2</sup> To R<sup>1</sup> Expansion Terms in the Ordered Series of the Numerical Estimate for a Function.

 * [***R2ToR1SeriesTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R2ToR1SeriesTerm.java)
 <i>R2ToR1SeriesTerm</i> exposes the R<sup>2</sup> To R<sup>1</sup> Series Expansion Term in the Ordered Series of the Numerical Estimate for a Function.

 * [***R3ToR1SeriesTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/R3ToR1SeriesTerm.java)
 <i>R3ToR1SeriesTerm</i> exposes the R<sup>3</sup> To R<sup>1</sup> Series Expansion Term in the Ordered Series of the Numerical Estimate for a Function.

 * [***RxToR1Series***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/RxToR1Series.java)
 <i>RxToR1Series</i> contains the R<sup>x</sup> To R<sup>1</sup> Expansion Terms in the Ordered Series of the Numerical Estimate for a Function.


## References

 * Abramowitz, M., and I. A. Stegun (2007): Handbook of Mathematics Functions <b>Dover Book on Mathematics</b>

 * Blagouchine, I. V. (2018): Three Notes on Ser's and Hasse's Representations for the Zeta-Functions https://arxiv.org/abs/1606.02044 <b>arXiv</b>

 * Mezo, I., and M. E. Hoffman (2017): Zeros of the Digamma Function and its Barnes G-function Analogue <i>Integral Transforms and Special Functions</i> <b>28 (28)</b> 846-858

 * Mortici, C. (2011): Improved Asymptotic Formulas for the Gamma Function <i>Computers and Mathematics with Applications</i> <b>61 (11)</b> 3364-3369

 * National Institute of Standards and Technology (2018): NIST Digital Library of Mathematical Functions https://dlmf.nist.gov/5.11

 * Nemes, G. (2010): On the Coefficients of the Asymptotic Expansion of n! https://arxiv.org/abs/1003.2907 <b>arXiv</b>

 * Toth V. T. (2016): Programmable Calculators; The Gamma Function http://www.rskey.org/CMS/index.php/the-library/11

 * Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge University Press</b> New York

 * Wikipedia (2019): Digamma Function https://en.wikipedia.org/wiki/Digamma_function

 * Wikipedia (2019): Stirling's Approximation https://en.wikipedia.org/wiki/Stirling%27s_approximation


## DROP Specifications

 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
