﻿
# Margin Analytics Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Margin Analytics Library computes the Initial and the Variation Margin Analytics.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | Technical Specification | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/MarginAnalytics/MarginAnalytics_v5.42.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/MarginAnalytics) |
 | User Guide              |  |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *SIMM* => Initial Margin Analytics based on ISDA SIMM and its Variants.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Asimm) }


## Coverage

 * Regression Sensitivities for Margin Portfolios
	* Abstract
	* Methodology
	* References
 * Principles Behind ISDA SIMM Specification
	* Introduction
	* Background
	* Objectives
	* Criteria
	* Modeling Constraints
	* Selecting the Model Specification
	* Scanning the Existing Industry Solutions
	* SIMM Specifications
	* Non-Procyclicality
	* Data Needs, Costs, and Maintenance
	* Transparency and Implementation Costs
	* Evolution of SIMM Through the Regulatory Process
	* SIMM and the Nested Variance/Covariance Formulas
	* Rationale Behind the Nested Sequence Approach
	* Explicit Expression for S_a
	* FRTB Approximation
	* SIMM Approximation
	* Testing the Approximations
	* Explicit Large Correlation Matrix
	* Proof that the Elements of the Eigenvectors are smaller than One in Magnitude
	* Numerical Example – Global Interest Rate Risk (GIRR)
	* SIMM Curvature Formulas – Introduction
	* ISDA SIMM Curvature Formula
	* Numerical Tests
	* References
 * ISDA SIMM Methodology
	* Contextual Considerations
	* General Provisions
	* Definition of the Interest Rate Risk
	* Definition of Sensitivity for Delta Margin Calculation
	* Interest Rate Risk Weights
	* Credit Qualifying: Risk Weights
	* Credit Qualifying: Correlations
	* Credit Non-Qualifying Risk
	* Credit Non-Qualifying – Correlations
	* Equity Risk Weights
	* Equity Correlations
	* Commodity Risk Weights
	* Commodity Correlations
	* Foreign Exchange Risk
	* Concentration Thresholds
	* Additional Initial Margin Expressions
	* Structure of the Methodology
	* Interest Rate Risk Delta Margin
	* Non Interest Rate Risk Classes
	* References
 * Dynamic Initial Margin Impact on Exposure
	* Abstract
	* Introduction
	* Exposure in the Presence of IM and VM
	* Modeling VM
	* Modeling U
	* Modeling IM
	* Summary and Calibration
	* The Impact of IM: No Trade Flows within the MPoR
	* Local Gaussian Approximation
	* Numerical Tests
	* The Impact of IM: Trade Flows within the MPoR
	* Expected Exposure – Numerical Example #1
	* The Impact of IM on CVA
	* Expected Exposure: Numerical Exposure 2
	* Numerical Techniques – Daily Time Grid
	* Calculation of the Path-wise IM
	* Calculation of Path-wise Exposure
	* Numerical Example
	* Conclusion
	* References
 * CCP and SIMM Initial Margin
	* Initial Margin
	* CCP IM
	* Interest Rate Swap Methodology
	* Interest Rate Swap Calculation
	* Credit Default Swap Methodology
	* SIMM
	* MVA
	* Summary
 * Aggregation Analytics
	* Aggregation Problem
	* Overview of the Aggregation Workflow
	* Aggregation Functionality
	* Calculation of Raw Exposure Matrix
	* Configuration Settings for Non Basel-III Regimes
	* Configuration Settings for Basel III Regimes
	* Calculation of Net Exposure Matrix
	* Collateral Matrix Calculation
	* Collateral Calculation for FCC Counterparties
	* Holding Period Adjustment
	* Equivalent Cash at Valuation Date
	* Haircut Adjustment Factor
	* Collateral Matrix Calculation - FCC (<i>t=0</i>)
	* Counterparty State 2 with Timepoint Less Than 90 Days or Counterparty State 3
	* Counterparty State in Green or Amber and Red State with Timepoint > 3 Months
	* 𝑺𝒉𝒐𝒄𝒌𝒆𝒅𝑷𝑳 for Type 1 Product for FCC
	* 𝑺𝒉𝒐𝒄𝒌𝒆𝒅𝑷𝑳 Model for Type 2 Product for FCC
	* 𝑺𝒉𝒐𝒄𝒌𝒆𝒅𝑷𝑳 Model for Type 3 Product for FCC
	* Post Processing
	* Calculation of the Upfront Collateral
	* Upfront Collateral at Silo Level
	* Upfront Collateral at Combined Level
	* Shifted Exposure Matrix
	* Basel III Rules for Margin Period of Risk
	* Exposure Shifted by Margin Period of Risk for Collateralized Counterparties
	* Exposure Shifted by Margin Period of Risk for Uncollateralized Counterparties
	* Exposure Shifted by Margin Period of Risk for FCC
	* Collateralized Exposure Matrix
	* Calculation of Adjustment
	* Actual Collateral Adjustment
	* Wrong Way Risk Adjustment
	* Final Exposure Matrix


## DROP Specifications

 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Repo Layout Taxonomy     => https://lakshmidrip.github.io/DROP/Taxonomy.md
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
