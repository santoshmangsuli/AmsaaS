package com.ams.billing.domain.model.servicecatalog;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ams.sharedkernel.domain.model.measuresandunits.Currency;
import com.ams.sharedkernel.domain.model.measuresandunits.Money;
import com.ams.sharedkernel.domain.model.measuresandunits.TimeUnit;

/**
 * @author Raghavendra Badiger
 */

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "T_SERVICEPRICE")
@IdClass(ServicePriceId.class)
public class ServicePrice
{
	private ServicePlan			srvcPlan;
	private Service			service;
	private ServicePriceCategory	srvcPriceCategory;
	private Money				srvcPricePerUnit;
	private TimeUnit			srvcUnitOfMeasure;

	public ServicePrice()
	{}

	public ServicePrice(ServicePlan servicePlan, Service srvc, String srvcPriceCategory,
					Money srvcPrice,
					String unitOfMeasure)
	{
		this.srvcPlan = servicePlan;
		this.service = srvc;
		this.srvcPriceCategory = ServicePriceCategory.valueOf(srvcPriceCategory);
		this.srvcPricePerUnit = srvcPrice;
		this.srvcUnitOfMeasure = TimeUnit.valueOf(unitOfMeasure);

	}

	public ServicePrice(ServicePlan srvcPlan, Service srvc, String srvcPriceCategory,
					BigDecimal pricePerUnit, String currency,
					String unitOfMeasure)
	{
		this.srvcPlan = srvcPlan;
		this.service = srvc;
		this.srvcPriceCategory = ServicePriceCategory.valueOf(srvcPriceCategory);
		this.srvcPricePerUnit = new Money(pricePerUnit, Currency.valueOf(currency));
		this.srvcUnitOfMeasure = TimeUnit.valueOf(unitOfMeasure);
	}

	/*
	 * Domain functions
	 */

	/*
	 * Accessor & Mutators
	 */

	@Id
	@ManyToOne
	@JoinColumn(name = "srvcPlanName")
	public ServicePlan getSrvcPlan()
	{
		return this.srvcPlan;
	}

	@SuppressWarnings("unused")
	private void setSrvcPlan(ServicePlan srvcPlan)
	{
		this.srvcPlan = srvcPlan;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "srvcCode")
	public Service getService()
	{
		return this.service;
	}

	@SuppressWarnings("unused")
	private void setService(Service srvc)
	{
		this.service = srvc;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public ServicePriceCategory getSrvcPriceCategory()
	{
		return this.srvcPriceCategory;
	}

	@SuppressWarnings("unused")
	private void setSrvcPriceCategory(ServicePriceCategory srvcPriceCategory)
	{
		this.srvcPriceCategory = srvcPriceCategory;
	}

	public Money getSrvcPricePerUnit()
	{
		return this.srvcPricePerUnit;
	}

	@SuppressWarnings("unused")
	private void setSrvcPricePerUnit(Money srvcPricePerUnit)
	{
		this.srvcPricePerUnit = srvcPricePerUnit;
	}

	@Enumerated(EnumType.STRING)
	public TimeUnit getSrvcUnitOfMeasure()
	{
		return this.srvcUnitOfMeasure;
	}

	@SuppressWarnings("unused")
	private void setSrvcUnitOfMeasure(TimeUnit srvcUnitOfMeasure)
	{
		this.srvcUnitOfMeasure = srvcUnitOfMeasure;
	}

}
