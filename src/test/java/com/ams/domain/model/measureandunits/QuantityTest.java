package com.ams.domain.model.measureandunits;

import java.math.BigDecimal;

import com.ams.sharedkernel.domain.model.measuresandunits.Quantity;
import com.ams.sharedkernel.domain.model.measuresandunits.Unit;

public class QuantityTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Quantity qty = new Quantity(new BigDecimal(100), Unit.valueOf("Hrs"));
		System.out.println(qty);

	}

}
