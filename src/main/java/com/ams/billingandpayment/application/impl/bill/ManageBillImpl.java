package com.ams.billingandpayment.application.impl.bill;

import java.util.Date;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ams.billingandpayment.application.api.service.bill.ManageBill;
import com.ams.billingandpayment.domain.model.bill.Bill;
import com.ams.billingandpayment.domain.model.bill.Bill.BillBuilder;
import com.ams.billingandpayment.domain.model.bill.BillPaymentRegister;
import com.ams.billingandpayment.domain.model.bill.exception.BillExceptionCode;
import com.ams.billingandpayment.domain.model.bill.policy.DiscountPolicy;
import com.ams.billingandpayment.domain.model.bill.policy.TaxPolicy;
import com.ams.billingandpayment.domain.model.services.ServicePlan;
import com.ams.billingandpayment.domain.model.services.ServicePrice;
import com.ams.billingandpayment.domain.model.services.ServicePrice.ServicePriceCategory;
import com.ams.billingandpayment.domain.model.services.ServiceUsageEvent;
import com.ams.billingandpayment.domain.repository.BillRepository;
import com.ams.billingandpayment.domain.repository.ServicePlanRepository;
import com.ams.billingandpayment.domain.repository.ServiceUsageEventRepository;
import com.ams.billingandpayment.domain.service.DiscountPolicyAdvisor;
import com.ams.billingandpayment.domain.service.TaxPolicyAdvisor;
import com.ams.sharedkernel.domain.model.measuresandunits.Period;
import com.ams.sharedkernel.domain.model.measuresandunits.Quantity;
import com.ams.users.domain.model.Person;
import com.ams.users.domain.repository.PersonRepository;

/**
 * @author Raghavendra Badiger
 */

@Transactional(isolation = Isolation.DEFAULT)
@org.springframework.stereotype.Service("ManageBillService")
public class ManageBillImpl implements ManageBill
{

	@Autowired
	private BillRepository				billRepository;

	@Autowired
	private PersonRepository				personRepository;

	@Autowired
	private ServicePlanRepository			srvcPlanRepository;

	@Autowired
	private ServiceUsageEventRepository	srvcUsageEventRepository;

	@Autowired
	private TaxPolicyAdvisor				taxPolicyAdvisor;

	@Autowired
	private DiscountPolicyAdvisor			discountPolicyAdvisor;

	private final String				billingPartyEmailId	= "admin@amsaas.com";

	/**
	 * 
	 * Periodic Billing operations
	 */

	@Override
	public void billSubscriberForPeriod(Person srvcSubscriber, Period billPeriod, Date billDate, Date billDueDate)
	{
		BillPaymentRegister billPayReg = this.billRepository.findBillPaymentRegisterFor(srvcSubscriber);

		BillBuilder billBldr = new BillBuilder().header(srvcSubscriber, billDate, billDueDate, billPeriod, billPayReg);
		billBldr = this.nonUsageCharges(billBldr);
		billBldr = this.usageCharges(billBldr);
		Bill billForPeriod = billBldr.getBillInstance(this.discountPolicyAdvisor.adviseBillDiscountPolicy(srvcSubscriber, billPeriod), this.taxPolicyAdvisor.adviseBillTaxPolicy(srvcSubscriber, billPeriod));
		this.billRepository.createBill(billForPeriod);

		// this.manageMailService.sendMail(srvcSubscriber.getPersnDetail().getEmailId(),
		// this.billingPartyEmailId, "");
	}

	/*
	 * BillBuilder Decorators to calculate non-usage & usage charges
	 */

	public BillBuilder nonUsageCharges(BillBuilder billBldr)
	{

		if (billBldr.isHeaderSet())
		{
			Period billPeriod = billBldr.bill.getBillPeriod();
			ServicePlan srvcPlan = this.srvcPlanRepository.findServicePlanOfPerson(billBldr.bill.getBilledPersonId());

			List<ServicePrice> nonUsageServicePriceList = this.srvcPlanRepository.findAllServicePricesByCriteria(srvcPlan.getSrvcPlanName(), ServicePriceCategory.NON_USAGE.toString());

			for (ServicePrice nonUsageServicePrice : nonUsageServicePriceList)
			{

				if (nonUsageServicePrice.getSrvcPriceSpec().isApplicableFor(billPeriod))
				{
					TaxPolicy srvcTaxPolicy = this.taxPolicyAdvisor.adviseServiceTaxPolicy(nonUsageServicePrice.getService());

					DiscountPolicy srvcDiscntPolicy = this.discountPolicyAdvisor.adviseServiceDiscountPolicy(billBldr.bill.getBilledPersonId(), billBldr.bill.getBillPeriod(),
																							srvcPlan,
																							nonUsageServicePrice);
					Quantity qty = Period.convertTo(billPeriod, nonUsageServicePrice.getSrvcUnitOfMeasure());

					billBldr.addLineItem(nonUsageServicePrice, qty, srvcDiscntPolicy, srvcTaxPolicy);
				}

			}
			return billBldr;
		}
		else
		{
			throw new ServiceException(BillExceptionCode.HEADER_NOT_SET.getExceptionDetails());
		}
	}

	public BillBuilder usageCharges(BillBuilder billBldr)
	{
		if (billBldr.isHeaderSet())
		{

			Period billPeriod = billBldr.bill.getBillPeriod();
			long persnId = billBldr.bill.getBilledPersonId();
			List<ServiceUsageEvent> srvcUsageEventList = this.srvcUsageEventRepository.findAllForCustomerWithinPeriod(persnId, billPeriod);

			for (ServiceUsageEvent srvcUsageEvent : srvcUsageEventList)
			{
				ServicePlan subscbrSrvcPlan = this.srvcPlanRepository.findServicePlanOfPerson(persnId);
				ServicePrice srvcPrice = this.srvcPlanRepository.findServicePriceByCriteria(subscbrSrvcPlan.getSrvcPlanName(), srvcUsageEvent.getSrvc().getSrvcCode());
				DiscountPolicy srvcDiscountPolicy = this.discountPolicyAdvisor.adviseServiceDiscountPolicy(persnId, billPeriod, subscbrSrvcPlan, srvcPrice);
				TaxPolicy srvcTaxPolicy = this.taxPolicyAdvisor.adviseServiceTaxPolicy(srvcUsageEvent.getSrvc());
				Quantity qty = Period.convertTo(srvcUsageEvent.getSrvcUsagePeriod(), srvcPrice.getSrvcUnitOfMeasure());
				billBldr.addLineItem(srvcPrice, qty, srvcDiscountPolicy, srvcTaxPolicy);

			}
			return billBldr;
		}
		else
		{
			throw new ServiceException(BillExceptionCode.HEADER_NOT_SET.getExceptionDetails());
		}
	}
}
