//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.14 at 12:55:53 AM CEST 
//


package korenski.soap.izvestaji_model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="presek" type="{http://korenski/soap/izvestaji_model}Presek"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "presek"
})
@XmlRootElement(name = "izvestajResponse")
public class IzvestajResponse {

    @XmlElement(required = true)
    protected Presek presek;

    /**
     * Gets the value of the presek property.
     * 
     * @return
     *     possible object is
     *     {@link Presek }
     *     
     */
    public Presek getPresek() {
        return presek;
    }

    /**
     * Sets the value of the presek property.
     * 
     * @param value
     *     allowed object is
     *     {@link Presek }
     *     
     */
    public void setPresek(Presek value) {
        this.presek = value;
    }

}
