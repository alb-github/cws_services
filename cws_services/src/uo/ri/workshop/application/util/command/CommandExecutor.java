package uo.ri.workshop.application.util.command;

import uo.ri.workshop.application.service.BusinessException;

public interface CommandExecutor {

	<T> T execute(Command<T> cmd) throws BusinessException;

}