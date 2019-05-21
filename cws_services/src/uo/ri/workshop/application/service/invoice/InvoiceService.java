package uo.ri.workshop.application.service.invoice;

import java.util.List;
import java.util.Map;

import uo.ri.workshop.application.dto.BreakdownDto;
import uo.ri.workshop.application.dto.InvoiceDto;
import uo.ri.workshop.application.dto.PaymentMeanDto;
import uo.ri.workshop.application.service.BusinessException;

public interface InvoiceService {

	InvoiceDto createInvoiceFor(List<Long> workOrderIds) throws BusinessException;
	InvoiceDto findInvoice(Long numeroFactura) throws BusinessException;
	List<PaymentMeanDto> findPayMethodsForInvoice(Long invoiceId) throws BusinessException;
	void settleInvoice(Long invoiceId, Map<Long, Double> cargos) throws BusinessException;

	List<BreakdownDto> findRepairsByClient(String dni) throws BusinessException;
	
}
