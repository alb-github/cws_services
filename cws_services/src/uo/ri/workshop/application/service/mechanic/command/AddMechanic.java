package uo.ri.workshop.application.service.mechanic.command;

import uo.ri.workshop.application.dto.MechanicDto;
import uo.ri.workshop.application.repository.MechanicRepository;
import uo.ri.workshop.application.service.BusinessException;
import uo.ri.workshop.application.util.BusinessCheck;
import uo.ri.workshop.application.util.command.Command;
import uo.ri.workshop.conf.Factory;
import uo.ri.workshop.domain.Mechanic;

public class AddMechanic implements Command<Void> {

	private MechanicDto dto;
	private MechanicRepository repository = Factory.repository.forMechanic();

	public AddMechanic(MechanicDto mecanico) {
		this.dto = mecanico;
	}

	@Override
	public Void execute() throws BusinessException {
		checkNotRepeatedDni( dto.dni );
		
		Mechanic m = new Mechanic(dto.dni, dto.name, dto.surname);
		repository.add( m );
		return null;
	}

	private void checkNotRepeatedDni(String dni) throws BusinessException {
		Mechanic m = repository.findByDni( dni );
		BusinessCheck.isNull( m, "The mechanic aready exists");
	}

}
