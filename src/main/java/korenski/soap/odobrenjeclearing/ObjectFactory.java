//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.20 at 02:03:21 PM CEST 
//


package korenski.soap.odobrenjeclearing;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the korenski.soap.odobrenjeclearing package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: korenski.soap.odobrenjeclearing
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Clearing }
     * 
     */
    public Clearing createClearing() {
        return new Clearing();
    }

    /**
     * Create an instance of {@link Clearing.StavkePrenosa }
     * 
     */
    public Clearing.StavkePrenosa createClearingStavkePrenosa() {
        return new Clearing.StavkePrenosa();
    }

    /**
     * Create an instance of {@link Clearing.StavkePrenosa.StavkaPrenosa }
     * 
     */
    public Clearing.StavkePrenosa.StavkaPrenosa createClearingStavkePrenosaStavkaPrenosa() {
        return new Clearing.StavkePrenosa.StavkaPrenosa();
    }

    /**
     * Create an instance of {@link OdobrenjeRequest }
     * 
     */
    public OdobrenjeRequest createOdobrenjeRequest() {
        return new OdobrenjeRequest();
    }

    /**
     * Create an instance of {@link OdobrenjeClearing }
     * 
     */
    public OdobrenjeClearing createOdobrenjeClearing() {
        return new OdobrenjeClearing();
    }

    /**
     * Create an instance of {@link OdobrenjeResponse }
     * 
     */
    public OdobrenjeResponse createOdobrenjeResponse() {
        return new OdobrenjeResponse();
    }

    /**
     * Create an instance of {@link TBanka }
     * 
     */
    public TBanka createTBanka() {
        return new TBanka();
    }

    /**
     * Create an instance of {@link Poruka }
     * 
     */
    public Poruka createPoruka() {
        return new Poruka();
    }

    /**
     * Create an instance of {@link TFinansijskiPodaci }
     * 
     */
    public TFinansijskiPodaci createTFinansijskiPodaci() {
        return new TFinansijskiPodaci();
    }

    /**
     * Create an instance of {@link Clearing.StavkePrenosa.StavkaPrenosa.PodaciOPlacanju }
     * 
     */
    public Clearing.StavkePrenosa.StavkaPrenosa.PodaciOPlacanju createClearingStavkePrenosaStavkaPrenosaPodaciOPlacanju() {
        return new Clearing.StavkePrenosa.StavkaPrenosa.PodaciOPlacanju();
    }

}