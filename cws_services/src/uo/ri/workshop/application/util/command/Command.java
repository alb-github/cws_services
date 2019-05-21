package uo.ri.workshop.application.util.command;

import uo.ri.workshop.application.service.BusinessException;

public interface Command<T> {

	T execute() throws BusinessException; 
}
