package com.ams.finance.application.api.servicedata;

import java.util.Date;

public class AccountServiceData
{

	private long	number;
	private String	name;
	private String	type;
	private String	currency;
	private float	balance;
	private String	detail;
	private Date	creationDate;
	private long	personId;

	public float getBalance()
	{
		return balance;
	}

	public void setBalance(float balance)
	{
		this.balance = balance;
	}

	public Date getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getDetail()
	{
		return detail;
	}

	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getNumber()
	{
		return number;
	}

	public void setNumber(long l)
	{
		this.number = l;
	}

	public long getPersonId()
	{
		return personId;
	}

	public void setPersonId(long l)
	{
		this.personId = l;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

}
