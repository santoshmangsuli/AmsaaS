package com.ams.sharedkernel.domain.model.measuresandunits;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Embeddable;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;

@Embeddable
public class Quantity implements Serializable
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param srvcUsagePeriod
	 * @param unitOfMeasure
	 * @return
	 */
	public static Quantity converToUnit(Quantity qty, TimeUnit unitOfMeasure)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args)
	{

		Quantity qty = Quantity.quantify(new Period(new Date(2013, 1, 1, 0, 0, 0), new Date(2014, 3, 1, 0, 0, 0)), TimeUnit.FortNights);
		System.out.println(qty);

	}

	/**
	 * @param srvcUsagePeriod
	 * @param unitOfMeasure
	 * @return
	 */

	public static Quantity quantify(Period srvcUsagePeriod, TimeUnit unitOfMeasure)
	{
		BigDecimal value;

		switch (unitOfMeasure)
		{

		case Hrs:
			value = BigDecimal.valueOf(Hours.hoursBetween(new DateTime(srvcUsagePeriod.getFromDate()), new DateTime(srvcUsagePeriod.getToDate())).getHours());
			break;
		case Days:
			value = BigDecimal.valueOf(Days.daysBetween(new DateTime(srvcUsagePeriod.getFromDate()), new DateTime(srvcUsagePeriod.getToDate())).getDays());
			break;
		case Weeks:
			value = BigDecimal.valueOf(Weeks.weeksBetween(new DateTime(srvcUsagePeriod.getFromDate()), new DateTime(srvcUsagePeriod.getToDate())).getWeeks());
			break;
		case FortNights:
			value = BigDecimal.valueOf(Weeks.weeksBetween(new DateTime(srvcUsagePeriod.getFromDate()), new DateTime(srvcUsagePeriod.getToDate())).getWeeks() / 2);
			break;
		case Months:
			value = BigDecimal.valueOf(Months.monthsBetween(new DateTime(srvcUsagePeriod.getFromDate()), new DateTime(srvcUsagePeriod.getToDate())).getMonths());
			break;
		case Years:
			value = BigDecimal.valueOf(Years.yearsBetween(new DateTime(srvcUsagePeriod.getFromDate()), new DateTime(srvcUsagePeriod.getToDate())).getYears());
			break;
		case QuarterYears:
			value = BigDecimal.valueOf(Years.yearsBetween(new DateTime(srvcUsagePeriod.getFromDate()), new DateTime(srvcUsagePeriod.getToDate())).getYears() * 4);
			break;
		case HalfYears:
			value = BigDecimal.valueOf(Years.yearsBetween(new DateTime(srvcUsagePeriod.getFromDate()), new DateTime(srvcUsagePeriod.getToDate())).getYears() * 2);
			break;

		default:
			throw new IllegalArgumentException("Unknown Unit to convert!!");

		}

		return new Quantity(value, unitOfMeasure);
	}

	private BigDecimal	value;

	private TimeUnit	unit;

	public Quantity()
	{}

	public Quantity(BigDecimal qty, TimeUnit qtUnit)
	{
		this.value = qty;
		this.unit = qtUnit;
	}

	public Quantity add(BigDecimal other)
	{
		this.value = this.value.add(other);
		return this;

	}

	public Quantity add(Quantity qty) throws InvalidUnitException
	{
		if (this.unit.equals(qty.getUnit()))
		{
			this.value = this.value.add(qty.getValue());
		}
		else
		{
			throw new InvalidUnitException("Unit of Quantity to be added is different!!");
		}

		return this;

	}

	public Quantity divideBy(BigDecimal other)
	{
		this.value = this.value.divide(other);
		return this;

	}

	public Quantity divideBy(Quantity qty) throws InvalidUnitException
	{
		if (this.unit.equals(qty.getUnit()))
		{
			this.value = this.value.divide(qty.getValue());
		}
		else
		{
			throw new InvalidUnitException("Unit of Quantity to be divided is different!!");
		}

		return this;

	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof Quantity))
		{
			return false;
		}
		Quantity other = (Quantity) obj;
		if (this.unit != other.unit)
		{
			return false;
		}
		if (this.value == null)
		{
			if (other.value != null)
			{
				return false;
			}
		}
		else if (!this.value.equals(other.value))
		{

			return false;
		}
		return true;
	}

	public TimeUnit getUnit()
	{
		return this.unit;
	}

	public BigDecimal getValue()
	{
		return this.value;
	}

	/*
	 * ACCESSOR & MUTATORS
	 */

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.unit == null) ? 0 : this.unit.hashCode());
		result = (prime * result) + ((this.value == null) ? 0 : this.value.hashCode());
		return result;
	}

	public Quantity multiplyBy(BigDecimal other)
	{
		this.value = this.value.multiply(other);
		return this;

	}

	public Quantity multiplyBy(Quantity qty) throws InvalidUnitException
	{
		if (this.unit.equals(qty.getUnit()))
		{
			this.value = this.value.multiply(qty.getValue());
		}
		else
		{
			throw new InvalidUnitException("Unit of Quantity to be multiplied is different!!");
		}

		return this;

	}

	@SuppressWarnings("unused")
	private void setUnit(TimeUnit unit)
	{
		this.unit = unit;
	}

	@SuppressWarnings("unused")
	private void setValue(BigDecimal value)
	{
		this.value = value;
	}

	public Quantity subtract(BigDecimal other)
	{
		this.value = this.value.subtract(other);
		return this;

	}

	public Quantity subtract(Quantity qty) throws InvalidUnitException
	{
		if (this.unit.equals(qty.getUnit()))
		{
			this.value = this.value.subtract(qty.getValue());
		}
		else
		{
			throw new InvalidUnitException("Unit of Quantity to be subtracted is different!!");
		}

		return this;

	}

	@Override
	public String toString()
	{
		return "Quantity [" + this.value + this.unit + "]";
	}

}
