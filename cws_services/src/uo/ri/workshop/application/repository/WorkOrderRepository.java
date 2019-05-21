package uo.ri.workshop.application.repository;

import java.util.List;

import uo.ri.workshop.domain.WorkOrder;

public interface WorkOrderRepository extends Repository<WorkOrder>{

	/**
	 * @param idsAveria, lista de los id de avería a recuperar
	 * @return lista con averias cuyo id aparece en idsAveria,
	 * 	o lista vacía si no hay ninguna
	 */
	List<WorkOrder> findByIds(List<Long> workOrderIds);
	
	/**
	 * @param dni
	 * @return lista con averias no facturadas de un cliente 
	 * 	identificado por su dni o lista vacía si no hay ninguna
	 */
	List<WorkOrder> findNotInvoicedByDni(String dni);
}