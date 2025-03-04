package ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import metier.Compte;

@WebService(serviceName = "BanqueWS")
public class BanqueService {

	private static final double EXCHANGE_RATE = 11.0;

	@WebMethod(operationName = "ConversionEuroToLBP")
	public double conversion(@WebParam(name = "montant") double mt) {
		if (mt <= 0) {
			throw new IllegalArgumentException("Amount must be greater than 0.");
		}
		return mt * EXCHANGE_RATE;
	}

	@WebMethod(operationName = "GetCompte")
	public Compte getCompte(@WebParam(name = "code") int code) {//@WebParam(name = "code") 
		return new Compte(code, Math.random() * 9000, new Date());
	}

	@WebMethod(operationName = "ListComptes")
	public List<Compte> listCompte() {
		List<Compte> comptes = new ArrayList<>();
		comptes.add(new Compte(1, Math.random() * 9000, new Date()));
		comptes.add(new Compte(2, Math.random() * 9000, new Date()));
		comptes.add(new Compte(3, Math.random() * 9000, new Date()));
		return comptes;
	}
}
