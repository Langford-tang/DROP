
package org.drip.simm.estimator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * <i>ProductClassMargin</i> holds the Initial Margin Estimates for a Single Product Class across the Six
 * Risk Factors - Interest Rate, Credit Qualifying, Credit Non-Qualifying, Equity, Commodity, and FX. The
 * References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/estimator/README.md">ISDA SIMM Core + Add-On Estimator</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ProductClassMargin
{
	private org.drip.simm.margin.RiskClassAggregate _fxRiskClassAggregate = null;
	private org.drip.simm.margin.RiskClassAggregateIR _irRiskClassAggregate = null;
	private org.drip.simm.margin.RiskClassAggregate _equityRiskClassAggregate = null;
	private org.drip.simm.margin.RiskClassAggregate _commodityRiskClassAggregate = null;
	private org.drip.simm.margin.RiskClassAggregateCR _creditQualifyingRiskClassAggregate = null;
	private org.drip.simm.margin.RiskClassAggregateCR _creditNonQualifyingRiskClassAggregate = null;

	/**
	 * ProductClassMargin Constructor
	 * 
	 * @param irRiskClassAggregate IR Risk Class Aggregate
	 * @param creditQualifyingRiskClassAggregate Credit Qualifying Risk Class Aggregate
	 * @param creditNonQualifyingRiskClassAggregate Credit Non-Qualifying Risk Class Aggregate
	 * @param equityRiskClassAggregate Equity Risk Class Aggregate
	 * @param fxRiskClassAggregate FX Risk Class Aggregate
	 * @param commodityRiskClassAggregate Commodity Risk Class Aggregate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProductClassMargin (
		final org.drip.simm.margin.RiskClassAggregateIR irRiskClassAggregate,
		final org.drip.simm.margin.RiskClassAggregateCR creditQualifyingRiskClassAggregate,
		final org.drip.simm.margin.RiskClassAggregateCR creditNonQualifyingRiskClassAggregate,
		final org.drip.simm.margin.RiskClassAggregate equityRiskClassAggregate,
		final org.drip.simm.margin.RiskClassAggregate fxRiskClassAggregate,
		final org.drip.simm.margin.RiskClassAggregate commodityRiskClassAggregate)
		throws java.lang.Exception
	{
		_irRiskClassAggregate = irRiskClassAggregate;
		_fxRiskClassAggregate = fxRiskClassAggregate;
		_equityRiskClassAggregate = equityRiskClassAggregate;
		_commodityRiskClassAggregate = commodityRiskClassAggregate;
		_creditQualifyingRiskClassAggregate = creditQualifyingRiskClassAggregate;
		_creditNonQualifyingRiskClassAggregate = creditNonQualifyingRiskClassAggregate;

		if ((null == _equityRiskClassAggregate &&
			null == _commodityRiskClassAggregate &&
			null == _fxRiskClassAggregate &&
			null == _irRiskClassAggregate &&
			null == _creditQualifyingRiskClassAggregate &&
			null == _creditNonQualifyingRiskClassAggregate))
		{
			throw new java.lang.Exception ("ProductClassMargin => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Interest Rate Risk Class Aggregate
	 * 
	 * @return The Interest Rate Risk Class Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregateIR irRiskClassAggregate()
	{
		return _irRiskClassAggregate;
	}

	/**
	 * Retrieve the Credit Qualifying Risk Class Aggregate
	 * 
	 * @return The Credit Qualifying Risk Class Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregateCR creditQualifyingRiskClassAggregate()
	{
		return _creditQualifyingRiskClassAggregate;
	}

	/**
	 * Retrieve the Credit Non-Qualifying Risk Class Aggregate
	 * 
	 * @return The Credit Non-Qualifying Risk Class Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregateCR creditNonQualifyingRiskClassAggregate()
	{
		return _creditNonQualifyingRiskClassAggregate;
	}

	/**
	 * Retrieve the Equity Risk Class Aggregate
	 * 
	 * @return The Equity Risk Class Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregate equityRiskClassAggregate()
	{
		return _equityRiskClassAggregate;
	}

	/**
	 * Retrieve the FX Risk Class Aggregate
	 * 
	 * @return The FX Risk Class Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregate fxRiskClassAggregate()
	{
		return _fxRiskClassAggregate;
	}

	/**
	 * Retrieve the Commodity Risk Class Aggregate
	 * 
	 * @return The Commodity Risk Class Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregate commodityRiskClassAggregate()
	{
		return _commodityRiskClassAggregate;
	}

	/**
	 * Compute the Total IM
	 * 
	 * @param labelCorrelation Cross Risk Class Label Correlation
	 * 
	 * @return The Total IM
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double total (
		final org.drip.measure.stochastic.LabelCorrelation labelCorrelation)
		throws java.lang.Exception
	{
		if (null == labelCorrelation)
		{
			throw new java.lang.Exception ("ProductClassMargin::total => Invalid Inputs");
		}

		double irIM = null == _irRiskClassAggregate ? 0. : _irRiskClassAggregate.margin();

		double fxIM = null == _fxRiskClassAggregate ? 0. : _fxRiskClassAggregate.margin();

		double equityIM = null == _equityRiskClassAggregate ? 0. : _equityRiskClassAggregate.margin();

		double commodityIM = null == _commodityRiskClassAggregate ? 0. :
			_commodityRiskClassAggregate.margin();

		double creditQualifyingIM = null == _creditQualifyingRiskClassAggregate ? 0. :
			_creditQualifyingRiskClassAggregate.margin();

		double creditNonQualifyingIM = null == _creditNonQualifyingRiskClassAggregate ? 0. :
			_creditNonQualifyingRiskClassAggregate.margin();

		double totalIM = 0.;
		totalIM = totalIM + irIM *irIM;
		totalIM = totalIM + creditQualifyingIM * creditQualifyingIM;
		totalIM = totalIM + creditNonQualifyingIM * creditNonQualifyingIM;
		totalIM = totalIM + equityIM * equityIM;
		totalIM = totalIM + fxIM * fxIM;
		totalIM = totalIM + commodityIM * commodityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.IR,
			org.drip.simm.common.Chargram.CRQ
		) * irIM * creditQualifyingIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.IR,
			org.drip.simm.common.Chargram.CRNQ
		) * irIM * creditNonQualifyingIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.IR,
			org.drip.simm.common.Chargram.EQ
		) * irIM * equityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.IR,
			org.drip.simm.common.Chargram.FX
		) * irIM * fxIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.IR,
			org.drip.simm.common.Chargram.CT
		) * irIM * commodityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRQ,
			org.drip.simm.common.Chargram.CRNQ
		) * creditQualifyingIM * creditNonQualifyingIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRQ,
			org.drip.simm.common.Chargram.EQ
		) * creditQualifyingIM * equityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRQ,
			org.drip.simm.common.Chargram.FX
		) * creditQualifyingIM * fxIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRQ,
			org.drip.simm.common.Chargram.CT
		) * creditQualifyingIM * commodityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRNQ,
			org.drip.simm.common.Chargram.EQ
		) * creditNonQualifyingIM * equityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRNQ,
			org.drip.simm.common.Chargram.FX
		) * creditNonQualifyingIM * fxIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRNQ,
			org.drip.simm.common.Chargram.CT
		) * creditNonQualifyingIM * commodityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.EQ,
			org.drip.simm.common.Chargram.FX
		) * equityIM * fxIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.EQ,
			org.drip.simm.common.Chargram.CT
		) * equityIM * commodityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.FX,
			org.drip.simm.common.Chargram.CT
		) * fxIM * commodityIM;

		return java.lang.Math.sqrt (totalIM);
	}
}
