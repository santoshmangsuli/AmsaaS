package com.ams.billingandpayment.domain.model.bill;

import com.ams.sharedkernel.domain.model.measuresandunits.Money;

public class Discount
{
	private Money	discntAmount;
	private String	discntDescription;

	public Money getDiscntAmount()
	{
		return discntAmount;
	}

	public String getDiscntDescription()
	{
		return discntDescription;
	}

	public void setDiscntAmount(Money discntAmount)
	{
		this.discntAmount = discntAmount;
	}

	public void setDiscntDescription(String discntDescription)
	{
		this.discntDescription = discntDescription;
	}
}
