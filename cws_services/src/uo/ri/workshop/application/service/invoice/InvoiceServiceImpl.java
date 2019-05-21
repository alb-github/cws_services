package uo.ri.workshop.application.service.invoice;

import java.util.List;
import java.util.Map;

import uo.ri.workshop.application.dto.BreakdownDto;
import uo.ri.workshop.application.dto.InvoiceDto;
import uo.ri.workshop.application.dto.PaymentMeanDto;
import uo.ri.workshop.application.service.BusinessException;
import uo.ri.workshop.application.service.invoice.command.CreateInvoiceFor;
import uo.ri.workshop.application.util.command.CommandExecutor;
import uo.ri.workshop.conf.Factory;

public class InvoiceServiceImpl implements InvoiceService {

	CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public InvoiceDto createInvoiceFor(List<Long> idsAveria)
			throws BusinessException {

		return executor.execute( new CreateInvoiceFor( idsAveria ));
	}

	@Override
	public InvoiceDto findInvoice(Long numeroInvoiceDto) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaymentMeanDto> findPayMethodsForInvoice(Long idInvoiceDto)
			throws BusinessException {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void settleInvoice(Long idInvoiceDto, Map<Long, Double> cargos)
			throws BusinessException {
		// TODO Auto-generated method stub
	}

	@Override
	public List<BreakdownDto> findRepairsByClient(String dni) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
