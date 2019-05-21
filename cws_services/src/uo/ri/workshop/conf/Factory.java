package uo.ri.workshop.conf;

import uo.ri.workshop.application.repository.RepositoryFactory;
import uo.ri.workshop.application.util.command.ComandExecutorFactory;
import uo.ri.workshop.infrastructure.persistence.jpa.executor.JpaExecutorFactory;
import uo.ri.workshop.infrastructure.persistence.jpa.repository.JpaRepositoryFactory;

public class Factory {

	public static RepositoryFactory repository = new JpaRepositoryFactory();
	public static ComandExecutorFactory executor = new JpaExecutorFactory();

}
