package uo.ri.workshop.application.repository;

import java.util.List;

import uo.ri.workshop.domain.PaymentMean;

public interface PaymentMeanRepository extends Repository<PaymentMean> {
	
	/**
	 * @param id of the client
	 * @return a list with all the payment means owned by the client
	 */
	List<PaymentMean> findPaymentMeansByClientId(Long id);
}
