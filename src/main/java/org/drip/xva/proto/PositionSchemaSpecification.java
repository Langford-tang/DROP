
package org.drip.xva.proto;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>PositionSchemaSpecification</i> contains the Specifications of a Position Schema. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/proto/README.md">Collateral, Counter Party, Netting Groups</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PositionSchemaSpecification extends ObjectSpecification
{
	private FundingGroupSpecification _fundingGroupSpecification = null;
	private PositionGroupSpecification _positionGroupSpecification = null;
	private CreditDebtGroupSpecification _creditDebtGroupSpecification = null;
	private CollateralGroupSpecification _collateralGroupSpecification = null;

	/**
	 * PositionSchemaSpecification Constructor
	 * 
	 * @param id The Position Group ID
	 * @param name The Position Group Name
	 * @param positionGroupSpecification The Position Group Specification
	 * @param collateralGroupSpecification The Collateral Group Specification
	 * @param creditDebtGroupSpecification The Credit Debt Group Specification
	 * @param fundingGroupSpecification The Funding Group Specification
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public PositionSchemaSpecification (
		final String id,
		final String name,
		final PositionGroupSpecification positionGroupSpecification,
		final CollateralGroupSpecification collateralGroupSpecification,
		final CreditDebtGroupSpecification creditDebtGroupSpecification,
		final FundingGroupSpecification fundingGroupSpecification)
		throws Exception
	{
		super (id, name);

		if (null == (_positionGroupSpecification = positionGroupSpecification) ||
			null == (_collateralGroupSpecification = collateralGroupSpecification) ||
			null == (_creditDebtGroupSpecification = creditDebtGroupSpecification) ||
			null == (_fundingGroupSpecification = fundingGroupSpecification)) {
			throw new Exception ("PositionSchemaSpecification Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Margin Group Specification
	 * 
	 * @return The Margin Group Specification
	 */

	public PositionGroupSpecification positionGroupSpecification()
	{
		return _positionGroupSpecification;
	}

	/**
	 * Retrieve the Collateral Group Specification
	 * 
	 * @return The Collateral Group Specification
	 */

	public CollateralGroupSpecification collateralGroupSpecification()
	{
		return _collateralGroupSpecification;
	}

	/**
	 * Retrieve the Credit Debt Group Specification
	 * 
	 * @return The Credit Debt Group Specification
	 */

	public CreditDebtGroupSpecification creditDebtGroupSpecification()
	{
		return _creditDebtGroupSpecification;
	}

	/**
	 * Retrieve the Funding Group Specification
	 * 
	 * @return The Funding Group Specification
	 */

	public FundingGroupSpecification fundingGroupSpecification()
	{
		return _fundingGroupSpecification;
	}
}
