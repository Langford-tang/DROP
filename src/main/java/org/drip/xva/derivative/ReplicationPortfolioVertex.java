
package org.drip.xva.derivative;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>ReplicationPortfolioVertex</i> contains the Dynamic Replicating Portfolio of the Pay-out using the
 * Assets in the Economy, from the Dealer's View Point. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): <i>Modeling,
 *  			Pricing, and Hedging Counter-party Credit Exposure - A Technical Guide</i> <b>Springer
 *  			Finance</b> New York
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/derivative/README.md">Burgard Kjaer Dynamic Portfolio Replication</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ReplicationPortfolioVertex
{
	private double _cashAccount = java.lang.Double.NaN;
	private double _positionHoldings = java.lang.Double.NaN;
	private double _clientNumeraireHoldings = java.lang.Double.NaN;
	private double _dealerSeniorNumeraireHoldings = java.lang.Double.NaN;
	private double _dealerSubordinateNumeraireHoldings = java.lang.Double.NaN;

	/**
	 * Construct a ReplicationPortfolioVertex Instance without the Zero Recovery Dealer Numeraire
	 * 
	 * @param positionHoldings The Asset Numeraire Holdings
	 * @param dealerSeniorNumeraireHoldings The Dealer Senior Numeraire Holdings
	 * @param clientNumeraireHoldings The Client Numeraire Replication Holdings
	 * @param cashAccount The Cash Account
	 * 
	 * @return The ReplicationPortfolioVertex Instance without the Zero Recovery Dealer Numeraire
	 */

	public static final ReplicationPortfolioVertex Standard (
		final double positionHoldings,
		final double dealerSeniorNumeraireHoldings,
		final double clientNumeraireHoldings,
		final double cashAccount)
	{
		try
		{
			return new ReplicationPortfolioVertex (
				positionHoldings,
				dealerSeniorNumeraireHoldings,
				0.,
				clientNumeraireHoldings,
				cashAccount
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ReplicationPortfolioVertex Constructor
	 * 
	 * @param positionHoldings The Asset Numeraire Holdings
	 * @param dealerSeniorNumeraireHoldings The Dealer Senior Numeraire Holdings
	 * @param dealerSubordinateNumeraireHoldings The Dealer Subordinate Numeraire Holdings
	 * @param clientNumeraireHoldings The Client Numeraire Holdings
	 * @param cashAccount The Cash Account
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ReplicationPortfolioVertex (
		final double positionHoldings,
		final double dealerSeniorNumeraireHoldings,
		final double dealerSubordinateNumeraireHoldings,
		final double clientNumeraireHoldings,
		final double cashAccount)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dealerSeniorNumeraireHoldings =
				dealerSeniorNumeraireHoldings) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dealerSubordinateNumeraireHoldings =
				dealerSubordinateNumeraireHoldings) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_clientNumeraireHoldings = clientNumeraireHoldings) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_cashAccount = cashAccount))
		{
			throw new java.lang.Exception ("ReplicationPortfolioVertex Constructor => Invalid Inputs");
		}

		_positionHoldings = positionHoldings;
	}

	/**
	 * Retrieve the Number of Position Holdings
	 * 
	 * @return The Number of Position Holdings
	 */

	public double positionHoldings()
	{
		return _positionHoldings;
	}

	/**
	 * Retrieve the Number of Dealer Senior Numeraire Holdings
	 * 
	 * @return The Number of Dealer Senior Numeraire Holdings
	 */

	public double dealerSeniorNumeraireHoldings()
	{
		return _dealerSeniorNumeraireHoldings;
	}

	/**
	 * Retrieve the Number of Dealer Subordinate Numeraire Holdings
	 * 
	 * @return The Number of Dealer Subordinate Numeraire Holdings
	 */

	public double dealerSubordinateNumeraireHoldings()
	{
		return _dealerSubordinateNumeraireHoldings;
	}

	/**
	 * Retrieve the Client Numeraire Holdings
	 * 
	 * @return The Client Numeraire Holdings
	 */

	public double clientNumeraireHoldings()
	{
		return _clientNumeraireHoldings;
	}

	/**
	 * Retrieve the Cash Account Amount
	 * 
	 * @return The Cash Account Amount
	 */

	public double cashAccount()
	{
		return _cashAccount;
	}

	/**
	 * Compute the Market Value of the Dealer Position Pre-Default
	 * 
	 * @param marketVertex The Market Vertex
	 * 
	 * @return The Market Value of the Dealer Position Pre-Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dealerPreDefaultPositionValue (
		final org.drip.exposure.universe.MarketVertex marketVertex)
		throws java.lang.Exception
	{
		if (null == marketVertex)
		{
			throw new java.lang.Exception
				("ReplicationPortfolioVertex::dealerPreDefaultPositionValue => Invalid Inputs");
		}

		org.drip.exposure.universe.MarketVertexEntity dealerMarketVertex = marketVertex.dealer();

		double value = -1. * dealerMarketVertex.seniorFundingReplicator() * _dealerSeniorNumeraireHoldings;

		double dealerSubordinateFundingMarketVertex = dealerMarketVertex.subordinateFundingReplicator();

		if (org.drip.numerical.common.NumberUtil.IsValid (dealerSubordinateFundingMarketVertex))
		{
			value -= dealerSubordinateFundingMarketVertex * _dealerSubordinateNumeraireHoldings;
		}

		return value;
	}

	/**
	 * Compute the Market Value of the Dealer Position Post-Default
	 * 
	 * @param marketVertex The Market Vertex
	 * 
	 * @return The Market Value of the Dealer Position Post-Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dealerPostDefaultPositionValue (
		final org.drip.exposure.universe.MarketVertex marketVertex)
		throws java.lang.Exception
	{
		if (null == marketVertex)
		{
			throw new java.lang.Exception
				("ReplicationPortfolioVertex::dealerPostDefaultPositionValue => Invalid Inputs");
		}

		org.drip.exposure.universe.MarketVertexEntity dealerMarketVertex = marketVertex.dealer();

		double value = dealerMarketVertex.seniorFundingReplicator() * _dealerSeniorNumeraireHoldings *
			dealerMarketVertex.seniorRecoveryRate();

		double dealerSubordinateFundingMarketVertex = dealerMarketVertex.subordinateFundingReplicator();

		if (org.drip.numerical.common.NumberUtil.IsValid (dealerSubordinateFundingMarketVertex))
		{
			value -= dealerSubordinateFundingMarketVertex * _dealerSubordinateNumeraireHoldings *
				dealerMarketVertex.subordinateRecoveryRate();
		}

		return value;
	}
}
