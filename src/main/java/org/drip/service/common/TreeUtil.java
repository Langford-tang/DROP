
package org.drip.service.common;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>TreeUtil</i> implements a Tree Utility Functions.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common">Assorted Data Structures Support Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TreeUtil
{

	public class TreeNode
	{
		private TreeNode _left = null;
		private TreeNode _right = null;
		private int _value = java.lang.Integer.MIN_VALUE;

		public TreeNode (
			final int value,
			final TreeNode left,
			final TreeNode right)
		{
			_left = left;
			_right = right;
			_value = value;
		}

		public int value()
		{
			return _value;
		}

		public TreeNode left()
		{
			return _left;
		}

		public TreeNode right()
		{
			return _right;
		}
	}

	public static class DiameterHeightPair
	{
		private int _height = -1;
		private int _diameter = 0;

		public DiameterHeightPair (
			final int height,
			final int diameter)
			throws java.lang.Exception
		{
			if (-1 > (_height = height) ||
				0 > (_diameter = diameter))
			{
				throw new java.lang.Exception (
					"DiameterHeightPair Constructor => Invalid Inputs"
				);
			}
		}

		public int height()
		{
			return _height;
		}

		public int diameter()
		{
			return _diameter;
		}
	}

	public static final java.util.List<java.lang.Integer> RightSideView (
		final TreeNode root)
	{
		java.util.List<java.lang.Integer> rightNodeList = new java.util.ArrayList<java.lang.Integer>();

		if (null == root)
		{
			return rightNodeList;
		}

		java.util.Deque<TreeNode> nodeQueue = new java.util.ArrayDeque<TreeNode>();

		nodeQueue.add (
			root
		);

		while (!nodeQueue.isEmpty())
		{
			int queueSize = nodeQueue.size();

			for (int i = 0;
				i < queueSize;
				++i)
			{
				TreeNode currentNode = nodeQueue.poll();

				if (i == queueSize - 1)
				{
					rightNodeList.add (
						currentNode.value()
					);

					TreeNode left = currentNode.left();

					if (null != left)
					{
						nodeQueue.add (
							left
						);
					}

					TreeNode right = currentNode.right();

					if (null != right)
					{
						nodeQueue.add (
							right
						);
					}
				}
			}
		}

		return rightNodeList;
	}

	public static final DiameterHeightPair TreeDiameter (
		final TreeNode root)
	{
		if (null == root)
		{
			try
			{
				return new DiameterHeightPair (
					-1,
					0
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();
			}

			return null;
		}

		DiameterHeightPair leftPair = TreeDiameter (
			root.left()
		);

		DiameterHeightPair rightPair = TreeDiameter (
			root.right()
		);

		try
		{
			return new DiameterHeightPair (
				java.lang.Math.max (
					leftPair.height(), rightPair.height()
				) + 1,
				java.lang.Math.max (
					java.lang.Math.max (
						leftPair.diameter(),
						rightPair.diameter()
					),
					leftPair.height() + rightPair.height() + 2
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
