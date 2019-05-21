package uo.ri.workshop.application.repository;

import uo.ri.workshop.domain.Mechanic;

public interface MechanicRepository extends Repository<Mechanic> {

	/**
	 * @param dni
	 * @return the mechanic identified by the dni or null if none 
	 */
	Mechanic findByDni(String dni);
	
}
