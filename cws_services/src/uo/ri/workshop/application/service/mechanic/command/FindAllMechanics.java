package uo.ri.workshop.application.service.mechanic.command;

import java.util.List;

import uo.ri.workshop.application.dto.MechanicDto;
import uo.ri.workshop.application.repository.MechanicRepository;
import uo.ri.workshop.application.util.DtoAssembler;
import uo.ri.workshop.application.util.command.Command;
import uo.ri.workshop.conf.Factory;
import uo.ri.workshop.domain.Mechanic;

public class FindAllMechanics implements Command<List<MechanicDto>> {

	private MechanicRepository repository = Factory.repository.forMechanic();

	@Override
	public List<MechanicDto> execute() {
		List<Mechanic> ms = repository.findAll();
		return DtoAssembler.toMechanicDtoList( ms );
	}

}
