package com.ams.billingandpayment.domain.model.bill;

import java.math.BigDecimal;

public class BillSpecification
{
	public enum status
	{
		PAID, UNPAID, PARTIALLY_PAID
	}

	protected static final BigDecimal	billDuePenaltyAmount	= new BigDecimal(50);

	public static String			sourceEmailId			= "ams@xyz.com";

	public static boolean isPaymentWithinDueDate(Bill bill, Payment payment)
	{
		return payment.getPaymntDate().before(bill.getBillDueDate());
	}

}
