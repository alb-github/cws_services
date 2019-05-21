package uo.ri.workshop.infrastructure.persistence.jpa.repository;

import java.util.List;

import uo.ri.workshop.application.repository.PaymentMeanRepository;
import uo.ri.workshop.domain.PaymentMean;
import uo.ri.workshop.infrastructure.persistence.jpa.util.BaseRepository;

public class PaymentMeanJpaRepository
		extends BaseRepository<PaymentMean> 
		implements PaymentMeanRepository {

	@Override
	public List<PaymentMean> findPaymentMeansByClientId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
