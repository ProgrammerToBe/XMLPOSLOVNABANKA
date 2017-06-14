//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.14 at 01:59:03 PM CEST 
//


package korenski.soap.rtgs;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the korenski.soap.rtgs package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: korenski.soap.rtgs
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RTGS }
     * 
     */
    public RTGS createRTGS() {
        return new RTGS();
    }

    /**
     * Create an instance of {@link RTGS.StavkaPrenosa }
     * 
     */
    public RTGS.StavkaPrenosa createRTGSStavkaPrenosa() {
        return new RTGS.StavkaPrenosa();
    }

    /**
     * Create an instance of {@link RtgsRequest }
     * 
     */
    public RtgsRequest createRtgsRequest() {
        return new RtgsRequest();
    }

    /**
     * Create an instance of {@link RtgsResponse }
     * 
     */
    public RtgsResponse createRtgsResponse() {
        return new RtgsResponse();
    }

    /**
     * Create an instance of {@link TBanka }
     * 
     */
    public TBanka createTBanka() {
        return new TBanka();
    }

    /**
     * Create an instance of {@link TFinansijskiPodaci }
     * 
     */
    public TFinansijskiPodaci createTFinansijskiPodaci() {
        return new TFinansijskiPodaci();
    }

    /**
     * Create an instance of {@link RTGS.StavkaPrenosa.PodaciOPlacanju }
     * 
     */
    public RTGS.StavkaPrenosa.PodaciOPlacanju createRTGSStavkaPrenosaPodaciOPlacanju() {
        return new RTGS.StavkaPrenosa.PodaciOPlacanju();
    }

}