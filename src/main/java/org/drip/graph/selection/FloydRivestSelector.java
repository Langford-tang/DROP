
package org.drip.graph.selection;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>FloydRivestSelector</i> implements the Floyd-Rivest Selection Algorithm. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Floyd, R. W., and R. L. Rivest (1975): Expected Time Bounds for Selection <i>Communications of
 *  			the ACM</i> <b>18 (3)</b> 165-172
 *  	</li>
 *  	<li>
 *  		Floyd, R. W., and R. L. Rivest (1975): The Algorithm SELECT � for finding the i<sup>th</sup>
 *  			smallest of n Elements <i>Communications of the ACM</i> <b>18 (3)</b> 173
 *  	</li>
 *  	<li>
 *  		Hoare, C. A. R. (1961): Algorithm 65: Find <i>Communications of the ACM</i> <b>4 (1)</b> 321-322
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Floyd-Rivest Algorithm
 *  			https://en.wikipedia.org/wiki/Floyd%E2%80%93Rivest_algorithm
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Quickselect https://en.wikipedia.org/wiki/Quickselect
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/selection/README.md">k<sup>th</sup> Order Statistics Selection Scheme</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FloydRivestSelector<K extends java.lang.Comparable<K>>
	extends org.drip.graph.selection.QuickSelector<K>
{
	private org.drip.graph.selection.FloydRivestPartitionControl _partitionControl = null;

	private int shrinkAndSelectIndex (
		int leftIndex,
		int rightIndex,
		final int k)
		throws java.lang.Exception
	{
		if (null != _partitionControl)
		{
			double shrinkageFactor = _partitionControl.shrinkage();

			if (rightIndex - leftIndex > _partitionControl.widthLimit())
			{
				int width = rightIndex - leftIndex + 1;
				int i = k - leftIndex + 1;

				double z = java.lang.Math.log (
					width
				);

				double s = shrinkageFactor * java.lang.Math.exp (
					2. * z / 3.
				);

				double sd = (2 * i > width ? 1 : -1) * shrinkageFactor * java.lang.Math.sqrt (
					z * s * (width - s) / width
				);

				double sOverWidth = s / width;

				return shrinkAndSelectIndex (
					java.lang.Math.max (
						leftIndex,
						k - (int) (sOverWidth * i + sd)
					),
					java.lang.Math.min (
						rightIndex,
						k + (int) (sOverWidth * (width - i) + sd)
					),
					k
				);
			}
		}

		K[] elementArray = elementArray();

		while (rightIndex >= leftIndex)
		{
			K t = elementArray[k];
			int j = rightIndex;
			int i = leftIndex;

			if (!swapLocations (
				leftIndex,
				k
			))
			{
				throw new java.lang.Exception (
					"FloydRivestSelector::shrinkAndSelectIndex => Cannot Swap Locations"
				);
			}

			if (1 == elementArray[rightIndex].compareTo (
				t
			))
			{
				if (!swapLocations (
					leftIndex,
					rightIndex
				))
				{
					throw new java.lang.Exception (
						"FloydRivestSelector::shrinkAndSelectIndex => Cannot Swap Locations"
					);
				}
			}

			while (i < j)
			{
				if (!swapLocations (
					i,
					j
				))
				{
					throw new java.lang.Exception (
						"FloydRivestSelector::shrinkAndSelectIndex => Cannot Swap Locations"
					);
				}

				++i;
				--j;

				while (-1 == elementArray[i].compareTo (
					t
				))
				{
					++i;
				}

				while (1 == elementArray[j].compareTo (
					t
				))
				{
					--j;
				}
			}

			if (0 == elementArray[leftIndex].compareTo (
				t
			))
			{
				if (!swapLocations (
					leftIndex,
					j
				))
				{
					throw new java.lang.Exception (
						"FloydRivestSelector::shrinkAndSelectIndex => Cannot Swap Locations"
					);
				}
			}
			else
			{
				if (!swapLocations (
					rightIndex,
					++j
				))
				{
					throw new java.lang.Exception (
						"FloydRivestSelector::shrinkAndSelectIndex => Cannot Swap Locations"
					);
				}
			}

			if (j <= k)
			{
				leftIndex = j + 1;
			}

			if (k <= j)
			{
				rightIndex = j - 1;
			}
		}

		return k;
	}

	/**
	 * FloydRivestSelector Constructor
	 * 
	 * @param elementArray Array of Elements
	 * @param partitionControl Floyd-Rivest Partition Control
	 * 
	 * @throws java.lang.Exception Thrown if the Input is Invalid
	 */

	public FloydRivestSelector (
		final K[] elementArray,
		final org.drip.graph.selection.FloydRivestPartitionControl partitionControl)
		throws java.lang.Exception
	{
		super (
			elementArray,
			false,
			null
		);

		_partitionControl = partitionControl;
	}

	/**
	 * Retrieve the Floyd-Rivest Partition Control
	 * 
	 * @return The Floyd-Rivest Partition Control
	 */

	public org.drip.graph.selection.FloydRivestPartitionControl partitionControl()
	{
		return _partitionControl;
	}

	@Override public int selectIndex (
		int leftIndex,
		int rightIndex,
		final int k)
		throws java.lang.Exception
	{
		K[] elementArray = elementArray();

		int arraySize = elementArray.length;

		if (leftIndex < 0 ||
			rightIndex >= arraySize || rightIndex < leftIndex ||
			0 > k || k >= arraySize
		)
		{
			throw new java.lang.Exception (
				"FloydRivestSelector::selectIndex => Invalid Inputs"
			);
		}

		return shrinkAndSelectIndex (
			leftIndex,
			rightIndex,
			k
		);
	}
}
