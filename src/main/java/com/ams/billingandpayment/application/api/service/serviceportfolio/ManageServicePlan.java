package com.ams.billingandpayment.application.api.service.serviceportfolio;

import java.util.List;

import com.ams.billingandpayment.application.api.commandquery.ServicePriceCommand;
import com.ams.billingandpayment.domain.model.servicecatalog.ServicePlan;
import com.ams.billingandpayment.domain.model.servicecatalog.ServicePrice;
import com.ams.sharedkernel.domain.repository.Page;

public interface ManageServicePlan
{
	/*
	 * Service Plan command/write functions
	 */

	List<ServicePlan> getAllServicePlans();

	Page<ServicePrice> getNextPageOfServicePricesForPlan(String srvcPlanName, int startIndex, int offset);

	/*
	 * Service Plan query/read functions
	 */
	ServicePlan getServicePlan(String planName);

	Page<ServicePlan> getServicePlansNextPage(int index, int offset);

	List<ServicePrice> getServicePricesForPlan(String srvcPlanName);

	String registerServicePlan(ServicePlan svcPlan);

	/*
	 * Plan-Service Price command/write functions
	 */

	String registerServicePriceToPlan(ServicePriceCommand spc);

	void removeServicePlan(String srvcPlanName);

	void removeServicePrice(ServicePriceCommand spc) throws InvalidServicePricePlanException;

	void removeServicePrice(String srvcPlanName, String srvcCode) throws InvalidServicePricePlanException;

	/*
	 * Plan-Service Price query/read functions
	 */

	String updateServicePlanDetails(ServicePlan srvcPlan);

	void updateServicePriceDetails(ServicePriceCommand spc);

}
