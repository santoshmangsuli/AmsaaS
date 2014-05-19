package com.ams.billingandpayment.domain.model.bill;

import com.ams.sharedkernel.domain.model.measuresandunits.Money;

public class BillSpecification
{

	public static final String	sourceEmailId	= "ams@xyz.com";

	public static boolean isPaymentWithinDueDate(Bill bill, Payment payment)
	{
		return payment.getPaymntDate().before(bill.getBillDueDate());
	}

	/**
	 * @return
	 */
	public static Money getPenaltyAmount()
	{
		return null;
	}

}
