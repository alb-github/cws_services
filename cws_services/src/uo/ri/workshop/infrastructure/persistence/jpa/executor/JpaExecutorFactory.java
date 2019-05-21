package uo.ri.workshop.infrastructure.persistence.jpa.executor;

import uo.ri.workshop.application.util.command.ComandExecutorFactory;
import uo.ri.workshop.application.util.command.CommandExecutor;

public class JpaExecutorFactory implements ComandExecutorFactory {

	@Override
	public CommandExecutor forExecutor() {
		return new JpaCommandExecutor();
	}

}
