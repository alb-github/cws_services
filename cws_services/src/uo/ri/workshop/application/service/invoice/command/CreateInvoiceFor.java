package uo.ri.workshop.application.service.invoice.command;

import java.util.List;

import uo.ri.workshop.application.dto.InvoiceDto;
import uo.ri.workshop.application.repository.InvoiceRepository;
import uo.ri.workshop.application.repository.WorkOrderRepository;
import uo.ri.workshop.application.service.BusinessException;
import uo.ri.workshop.application.util.BusinessCheck;
import uo.ri.workshop.application.util.DtoAssembler;
import uo.ri.workshop.application.util.command.Command;
import uo.ri.workshop.conf.Factory;
import uo.ri.workshop.domain.Invoice;
import uo.ri.workshop.domain.WorkOrder;

public class CreateInvoiceFor implements Command<InvoiceDto>{

	private List<Long> workOrderIds;
	private WorkOrderRepository wrkrsRepo = Factory.repository.forWorkOrder();
	private InvoiceRepository invsRepo = Factory.repository.forInvoice();

	public CreateInvoiceFor(List<Long> workOrderIds) {
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		List<WorkOrder> avs = wrkrsRepo.findByIds( workOrderIds );
		BusinessCheck.isTrue( allFinished(avs), "Not all work orders are finished");
		
		Long numero = invsRepo.getNextInvoiceNumber();
		
		Invoice f = new Invoice(numero, avs);
		invsRepo.add( f );

		return DtoAssembler.toDto( f );
	}

	private boolean allFinished(List<WorkOrder> avs) {
		for(WorkOrder a: avs) {
			if ( ! a.isFinished() ) {
				return false;
			}
		}
		return true;
	}

}
