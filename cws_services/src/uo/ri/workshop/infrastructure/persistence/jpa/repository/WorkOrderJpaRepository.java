package uo.ri.workshop.infrastructure.persistence.jpa.repository;

import java.util.List;

import uo.ri.workshop.application.repository.WorkOrderRepository;
import uo.ri.workshop.domain.WorkOrder;
import uo.ri.workshop.infrastructure.persistence.jpa.util.BaseRepository;
import uo.ri.workshop.infrastructure.persistence.jpa.util.Jpa;

public class WorkOrderJpaRepository 
		extends BaseRepository<WorkOrder> 
		implements WorkOrderRepository {

	@Override
	public List<WorkOrder> findByIds(List<Long> idsAveria) {
		return Jpa.getManager()
				.createNamedQuery("WorkOrder.findByIds", WorkOrder.class)
				.setParameter( 1, idsAveria )
				.getResultList();
	}

	@Override
	public List<WorkOrder> findNotInvoicedByDni(String dni) {
		// TODO Auto-generated method stub
		return null;
	}

}
