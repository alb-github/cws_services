package uo.ri.workshop.application.service.mechanic.command;

import uo.ri.workshop.application.repository.MechanicRepository;
import uo.ri.workshop.application.service.BusinessException;
import uo.ri.workshop.application.util.BusinessCheck;
import uo.ri.workshop.application.util.command.Command;
import uo.ri.workshop.conf.Factory;
import uo.ri.workshop.domain.Mechanic;

public class DeleteMechanic implements Command<Void> {

	private Long mechanicId;
	private MechanicRepository repository = Factory.repository.forMechanic();

	public DeleteMechanic(Long idMecanico) {
		this.mechanicId = idMecanico;
	}

	@Override
	public Void execute() throws BusinessException {
		Mechanic m = repository.findById(mechanicId);
		assertNotNull( m );
		assertCanBeDeleted( m );
		
		repository.remove( m );
		return null;
	}

	private void assertCanBeDeleted(Mechanic m) throws BusinessException {
		BusinessCheck.isTrue(m.getInterventions().size() == 0, 
			"Cannot be deleted, still has interventions");
		
		BusinessCheck.isTrue(m.getAssigned().size() == 0, 
			"Cannot be deleted, still has assigned work orders");
	}

	private void assertNotNull(Mechanic m) throws BusinessException {
		BusinessCheck.isNotNull(m, "The mechanic does not exist");
	}

}
