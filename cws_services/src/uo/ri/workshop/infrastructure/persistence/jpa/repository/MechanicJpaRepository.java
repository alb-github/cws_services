package uo.ri.workshop.infrastructure.persistence.jpa.repository;

import uo.ri.workshop.application.repository.MechanicRepository;
import uo.ri.workshop.domain.Mechanic;
import uo.ri.workshop.infrastructure.persistence.jpa.util.BaseRepository;
import uo.ri.workshop.infrastructure.persistence.jpa.util.Jpa;

public class MechanicJpaRepository 
			extends BaseRepository<Mechanic> 
			implements MechanicRepository {

	@Override
	public Mechanic findByDni(String dni) {
		return Jpa.getManager()
				.createNamedQuery("Mechanic.findByDni", Mechanic.class)
				.setParameter(1, dni)
				.getResultList().stream()
				.findFirst()
				.orElse(null);
	}

}
