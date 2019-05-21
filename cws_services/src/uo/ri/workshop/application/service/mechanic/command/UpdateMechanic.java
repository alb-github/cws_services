package uo.ri.workshop.application.service.mechanic.command;

import uo.ri.workshop.application.dto.MechanicDto;
import uo.ri.workshop.application.repository.MechanicRepository;
import uo.ri.workshop.application.service.BusinessException;
import uo.ri.workshop.application.util.BusinessCheck;
import uo.ri.workshop.application.util.command.Command;
import uo.ri.workshop.conf.Factory;
import uo.ri.workshop.domain.Mechanic;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;
	private MechanicRepository repository = Factory.repository.forMechanic();

	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		Mechanic m = repository.findById(dto.id);
		BusinessCheck.isNotNull( m, "The mechanic does not exist" );
		
		m.setSurname( dto.surname );
		m.setName( dto.name );

		return null;
	}

}
