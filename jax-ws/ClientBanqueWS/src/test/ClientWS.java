package test;

import javax.xml.ws.WebServiceException;

import ws.BanqueServiceProxy;
import ws.Compte;

public class ClientWS {

	public static void main(String[] args) {
		try {

			BanqueServiceProxy proxy = new BanqueServiceProxy();

			double convertedAmount = proxy.convertEuroToLBP(800);
			System.out.println("800 EUR = " + convertedAmount + " LBP");

			System.out.println("\nFetching account details...");
			Compte compte = proxy.getCompte(1);
			if (compte != null) {
				System.out.println("Account Code: " + compte.getCode());
				System.out.println("Account Balance: " + compte.getSolde());
			} else {
				System.out.println("Account not found.");
			}

			System.out.println("\nListing all accounts:");
			Compte[] comptes = proxy.listComptes();
			for (Compte c : comptes) {
				System.out.println("Account Code: " + c.getCode() + ", Balance: " + c.getSolde());
			}
		} catch (WebServiceException e) {
			System.out.println("ERREUR: Le service Web n'est pas accessible !");
			System.out.println("Détails: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("ERREUR GÉNÉRALE: Une erreur inattendue s'est produite.");
			e.printStackTrace();
		}

	}

}
