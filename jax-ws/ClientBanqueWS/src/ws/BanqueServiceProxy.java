package ws;

public class BanqueServiceProxy {
    private BanqueService service;

    public BanqueServiceProxy() {
        service = new BanqueWS().getBanqueServicePort();
    }

    public double convertEuroToLBP(double amount) {
        return service.conversionEuroToLBP(amount);
    }

    public Compte getCompte(int accountId) {
        return service.getCompte(accountId);
    }

    public Compte[] listComptes() {
        return service.listComptes().toArray(new Compte[0]); // Convert List to Array
    }
}
