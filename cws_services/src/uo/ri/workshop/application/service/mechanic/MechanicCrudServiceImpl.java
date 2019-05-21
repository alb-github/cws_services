package uo.ri.workshop.application.service.mechanic;

import java.util.List;

import uo.ri.workshop.application.dto.MechanicDto;
import uo.ri.workshop.application.service.BusinessException;
import uo.ri.workshop.application.service.mechanic.command.AddMechanic;
import uo.ri.workshop.application.service.mechanic.command.DeleteMechanic;
import uo.ri.workshop.application.service.mechanic.command.FindAllMechanics;
import uo.ri.workshop.application.service.mechanic.command.FindMechanicById;
import uo.ri.workshop.application.service.mechanic.command.UpdateMechanic;
import uo.ri.workshop.application.util.command.CommandExecutor;
import uo.ri.workshop.conf.Factory;

public class MechanicCrudServiceImpl implements MechanicCrudService {
	
	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public void addMechanic(MechanicDto mecanico) throws BusinessException {
		executor.execute( new AddMechanic( mecanico ) );
	}

	@Override
	public void updateMechanic(MechanicDto mecanico) throws BusinessException {
		executor.execute( new UpdateMechanic( mecanico ) );
	}

	@Override
	public void deleteMechanic(Long idMecanico) throws BusinessException {
		executor.execute( new DeleteMechanic(idMecanico) );
	}

	@Override
	public List<MechanicDto> findAllMechanics() throws BusinessException {
		return executor.execute( new FindAllMechanics() );
	}

	@Override
	public MechanicDto findMechanicById(Long id) throws BusinessException {
		return executor.execute( new FindMechanicById(id) );
	}

}