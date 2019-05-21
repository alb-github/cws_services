package uo.ri.workshop.application.service.mechanic.command;

import uo.ri.workshop.application.dto.MechanicDto;
import uo.ri.workshop.application.repository.MechanicRepository;
import uo.ri.workshop.application.service.BusinessException;
import uo.ri.workshop.application.util.DtoAssembler;
import uo.ri.workshop.application.util.command.Command;
import uo.ri.workshop.conf.Factory;
import uo.ri.workshop.domain.Mechanic;

public class FindMechanicById implements Command<MechanicDto> {

	private Long id;
	private MechanicRepository repository = Factory.repository.forMechanic();

	public FindMechanicById(Long id) {
		this.id = id;
	}

	@Override
	public MechanicDto execute() throws BusinessException {
		Mechanic m = repository.findById(id);
		return m != null 
				? DtoAssembler.toDto( m ) 
				: null;
	}

}
