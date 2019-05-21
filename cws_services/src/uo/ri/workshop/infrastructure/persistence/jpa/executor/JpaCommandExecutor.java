package uo.ri.workshop.infrastructure.persistence.jpa.executor;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import uo.ri.workshop.application.service.BusinessException;
import uo.ri.workshop.application.util.command.Command;
import uo.ri.workshop.application.util.command.CommandExecutor;
import uo.ri.workshop.infrastructure.persistence.jpa.util.Jpa;

public class JpaCommandExecutor implements CommandExecutor {

	@Override
	public <T> T execute(Command<T> cmd) throws BusinessException {
		EntityManager mapper = Jpa.createEntityManager();
		try {
			EntityTransaction trx = mapper.getTransaction();
			trx.begin();
			
			try {
				T res = cmd.execute();
				trx.commit();
				
				return res;
				
			} catch (BusinessException | PersistenceException ex) {
				if ( trx.isActive() ) {
					trx.rollback();
				}
				throw ex;
			}
		} finally {
			if ( mapper.isOpen() ) {
				mapper.close();
			}
		}
	}
}
