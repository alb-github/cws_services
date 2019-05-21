package uo.ri.workshop.application.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uo.ri.workshop.application.dto.BreakdownDto;
import uo.ri.workshop.application.dto.CardDto;
import uo.ri.workshop.application.dto.CashDto;
import uo.ri.workshop.application.dto.ClientDto;
import uo.ri.workshop.application.dto.InvoiceDto;
import uo.ri.workshop.application.dto.MechanicDto;
import uo.ri.workshop.application.dto.PaymentMeanDto;
import uo.ri.workshop.application.dto.VoucherDto;
import uo.ri.workshop.domain.Cash;
import uo.ri.workshop.domain.Client;
import uo.ri.workshop.domain.CreditCard;
import uo.ri.workshop.domain.Invoice;
import uo.ri.workshop.domain.Mechanic;
import uo.ri.workshop.domain.PaymentMean;
import uo.ri.workshop.domain.Voucher;
import uo.ri.workshop.domain.WorkOrder;

public class DtoAssembler {

	public static ClientDto toDto(Client c) {
		 ClientDto dto = new ClientDto();
		 
		 dto.id = c.getId();
		 dto.dni = c.getDni();
		 dto.name = c.getName();
		 dto.surname = c.getSurname();
		 
		 return dto;
	}

	public static List<ClientDto> toClientDtoList(List<Client> clientes) {
		List<ClientDto> res = new ArrayList<>();
		for(Client c: clientes) {
			res.add( DtoAssembler.toDto( c ) );
		}
		return res;
	}

	public static MechanicDto toDto(Mechanic m) {
		MechanicDto dto = new MechanicDto();
		dto.id = m.getId();
		dto.dni = m.getDni();
		dto.name = m.getName();
		dto.surname = m.getSurname();
		return dto;
	}

	public static List<MechanicDto> toMechanicDtoList(List<Mechanic> list) {
		List<MechanicDto> res = new ArrayList<>();
		for(Mechanic m: list) {
			res.add( toDto( m ) );
		}
		return res;
	}

	public static List<VoucherDto> toVoucherDtoList(List<Voucher> list) {
		List<VoucherDto> res = new ArrayList<>();
		for(Voucher b: list) {
			res.add( toDto( b ) );
		}
		return res;
	}

	public static VoucherDto toDto(Voucher b) {
		VoucherDto dto = new VoucherDto();
		dto.id = b.getId();
		dto.clientId = b.getClient().getId();
		dto.accumulated = b.getAcummulated();
		dto.code = b.getCodigo();
		dto.description = b.getDescripcion();
		dto.available = b.getDisponible();
		return dto;
	}

	public static CardDto toDto(CreditCard tc) {
		CardDto dto = new CardDto();
		dto.id = tc.getId();
		dto.clientId = tc.getClient().getId();
		dto.accumulated = tc.getAcummulated();
		dto.cardNumber = tc.getNumber();
		dto.cardExpiration = tc.getValidThru();
		dto.cardType = tc.getType();
		return dto;
	}

	public static CashDto toDto(Cash m) {
		CashDto dto = new CashDto();
		dto.id = m.getId();
		dto.clientId = m.getClient().getId();
		dto.accumulated = m.getAcummulated();
		return dto;
	}

	public static InvoiceDto toDto(Invoice factura) {
		InvoiceDto dto = new InvoiceDto();
		dto.id = factura.getId();
		dto.number = factura.getNumber();
		dto.date = factura.getDate();
		dto.total = factura.getAmount();
		dto.vat = factura.getVat();
		dto.status = factura.getStatus().toString();
		return dto;
	}

	public static List<PaymentMeanDto> toPaymentMeanDtoList(List<PaymentMean> list) {
		return list.stream()
				.map( mp -> toDto( mp ) )
				.collect( Collectors.toList() );
	}

	private static PaymentMeanDto toDto(PaymentMean mp) {
		if (mp instanceof Voucher) {
			return toDto( (Voucher) mp );
		}
		else if (mp instanceof CreditCard) {
			return toDto( (CreditCard) mp );
		}
		else if (mp instanceof Cash) {
			return toDto( (Cash) mp);
		}
		else {
			throw new RuntimeException("Unexpected type of payment mean");
		}
	}

	public static List<BreakdownDto> toBreakdownDtoList(List<WorkOrder> list) {
		return list.stream()
				.map( a -> toDto( a ) )
				.collect( Collectors.toList() );
	}
	
	public static BreakdownDto toDto(WorkOrder a) {
		BreakdownDto dto = new BreakdownDto();
		dto.id = a.getId();
		dto.invoiceId = a.getInvoice() == null ? null : a.getInvoice().getId();
		dto.vehicleId = a.getVehicle().getId();
		dto.description = a.getDescription();
		dto.date = a.getDate();
		dto.total = a.getAmount();
		dto.status = a.getStatus().toString();
		return dto;
	}

}
