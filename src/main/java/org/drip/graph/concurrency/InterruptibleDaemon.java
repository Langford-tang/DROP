
package org.drip.graph.concurrency;

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
 * <i>InterruptibleDaemon</i> implements a Runnable Task that can be Interrupted Gracefully.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/concurrency/README.md">Helper Classes For Concurrent Tasks</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class InterruptibleDaemon
	implements java.lang.Runnable
{
	private long _sleepTime = -1L;
	private boolean _checkForInterruption = false;
	private java.util.List<java.lang.Runnable> _taskList = null;

	/**
	 * InterruptibleDaemon Constructor
	 * 
	 * @param taskList Underlying Runnable Task List
	 * @param sleepTime Sleep Time
	 * @param checkForInterruption TRUE - Interruption Check can be applied
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public InterruptibleDaemon (
		final java.util.List<java.lang.Runnable> taskList,
		final long sleepTime,
		final boolean checkForInterruption)
		throws java.lang.Exception
	{
		if (null == (_taskList = taskList) || 0 == _taskList.size())
		{
			throw new java.lang.Exception (
				"InterruptibleDaemon Constructor => Invalid Inputs"
			);
		}

		_sleepTime = sleepTime;
		_checkForInterruption = checkForInterruption;

		for (java.lang.Runnable task : _taskList)
		{
			if (null == task)
			{
				throw new java.lang.Exception (
					"InterruptibleDaemon Constructor => Invalid Inputs"
				);
			}
		}
	}

	/**
	 * Retrieve the Underlying Runnable Task List
	 * 
	 * @return The Underlying Runnable Task List
	 */

	public java.util.List<java.lang.Runnable> taskList()
	{
		return _taskList;
	}

	/**
	 * Retrieve the Sleep Time
	 * 
	 * @return The Sleep Time
	 */

	public long sleepTime()
	{
		return _sleepTime;
	}

	/**
	 * Indicate if the Interruption Check can be applied
	 * 
	 * @return TRUE - Interruption Check can be applied
	 */

	public boolean checkForInterruption()
	{
		return _checkForInterruption;
	}

	@Override public void run()
	{
		for (java.lang.Runnable task : _taskList)
		{
			try
			{
				if (0L < _sleepTime)
				{
					java.lang.Thread.sleep (
						_sleepTime
					);
				}

				task.run();

				if (_checkForInterruption && java.lang.Thread.interrupted())
				{
					return;
				}
			}
			catch (java.lang.InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
	}
}
