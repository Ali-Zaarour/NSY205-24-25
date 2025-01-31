
package ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListComptesResponse_QNAME = new QName("http://ws/", "ListComptesResponse");
    private final static QName _ConversionEuroToLBP_QNAME = new QName("http://ws/", "ConversionEuroToLBP");
    private final static QName _ConversionEuroToLBPResponse_QNAME = new QName("http://ws/", "ConversionEuroToLBPResponse");
    private final static QName _GetCompteResponse_QNAME = new QName("http://ws/", "GetCompteResponse");
    private final static QName _GetCompte_QNAME = new QName("http://ws/", "GetCompte");
    private final static QName _ListComptes_QNAME = new QName("http://ws/", "ListComptes");
    private final static QName _Compte_QNAME = new QName("http://ws/", "compte");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListComptesResponse }
     * 
     */
    public ListComptesResponse createListComptesResponse() {
        return new ListComptesResponse();
    }

    /**
     * Create an instance of {@link ConversionEuroToLBP }
     * 
     */
    public ConversionEuroToLBP createConversionEuroToLBP() {
        return new ConversionEuroToLBP();
    }

    /**
     * Create an instance of {@link ConversionEuroToLBPResponse }
     * 
     */
    public ConversionEuroToLBPResponse createConversionEuroToLBPResponse() {
        return new ConversionEuroToLBPResponse();
    }

    /**
     * Create an instance of {@link GetCompteResponse }
     * 
     */
    public GetCompteResponse createGetCompteResponse() {
        return new GetCompteResponse();
    }

    /**
     * Create an instance of {@link GetCompte }
     * 
     */
    public GetCompte createGetCompte() {
        return new GetCompte();
    }

    /**
     * Create an instance of {@link ListComptes }
     * 
     */
    public ListComptes createListComptes() {
        return new ListComptes();
    }

    /**
     * Create an instance of {@link Compte }
     * 
     */
    public Compte createCompte() {
        return new Compte();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListComptesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "ListComptesResponse")
    public JAXBElement<ListComptesResponse> createListComptesResponse(ListComptesResponse value) {
        return new JAXBElement<ListComptesResponse>(_ListComptesResponse_QNAME, ListComptesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConversionEuroToLBP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "ConversionEuroToLBP")
    public JAXBElement<ConversionEuroToLBP> createConversionEuroToLBP(ConversionEuroToLBP value) {
        return new JAXBElement<ConversionEuroToLBP>(_ConversionEuroToLBP_QNAME, ConversionEuroToLBP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConversionEuroToLBPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "ConversionEuroToLBPResponse")
    public JAXBElement<ConversionEuroToLBPResponse> createConversionEuroToLBPResponse(ConversionEuroToLBPResponse value) {
        return new JAXBElement<ConversionEuroToLBPResponse>(_ConversionEuroToLBPResponse_QNAME, ConversionEuroToLBPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCompteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "GetCompteResponse")
    public JAXBElement<GetCompteResponse> createGetCompteResponse(GetCompteResponse value) {
        return new JAXBElement<GetCompteResponse>(_GetCompteResponse_QNAME, GetCompteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCompte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "GetCompte")
    public JAXBElement<GetCompte> createGetCompte(GetCompte value) {
        return new JAXBElement<GetCompte>(_GetCompte_QNAME, GetCompte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListComptes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "ListComptes")
    public JAXBElement<ListComptes> createListComptes(ListComptes value) {
        return new JAXBElement<ListComptes>(_ListComptes_QNAME, ListComptes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Compte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "compte")
    public JAXBElement<Compte> createCompte(Compte value) {
        return new JAXBElement<Compte>(_Compte_QNAME, Compte.class, null, value);
    }

}
