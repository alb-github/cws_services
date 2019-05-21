package uo.ri.workshop.infrastructure.persistence.jpa.repository;

import java.util.Date;
import java.util.List;

import uo.ri.workshop.application.repository.InterventionRepository;
import uo.ri.workshop.domain.Intervention;
import uo.ri.workshop.infrastructure.persistence.jpa.util.BaseRepository;
import uo.ri.workshop.infrastructure.persistence.jpa.util.Jpa;

public class InterventionJpaRepository
		extends BaseRepository<Intervention>
		implements InterventionRepository {

	@Override
	public List<Intervention> findByMechanicIdBetweenDates(
			Long id, 
			Date startDate, 
			Date endDate) {
		
		return Jpa.getManager()
				.createNamedQuery("Intervention.findByMechanicIdBetweenDates", Intervention.class)
				.setParameter(1, id)
				.setParameter(2, startDate)
				.setParameter(3, endDate)
				.getResultList();
	}

}
