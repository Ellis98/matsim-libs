//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.11 at 07:36:12 AM CEST 
//


package playground.pbouman.agentproperties.xml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the playground.pbouman.agentproperties.xml package. 
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

    private final static QName _AgentsAgentActivityDuration_QNAME = new QName("", "duration");
    private final static QName _AgentsAgentActivityStart_QNAME = new QName("", "start");
    private final static QName _AgentsAgentActivityLocation_QNAME = new QName("", "location");
    private final static QName _AgentsAgentActivityEnd_QNAME = new QName("", "end");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: playground.pbouman.agentproperties.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Agents.Agent.Home }
     * 
     */
    public Agents.Agent.Home createAgentsAgentHome() {
        return new Agents.Agent.Home();
    }

    /**
     * Create an instance of {@link Agents.Agent.Activity }
     * 
     */
    public Agents.Agent.Activity createAgentsAgentActivity() {
        return new Agents.Agent.Activity();
    }

    /**
     * Create an instance of {@link Agents.Agent.LoadingTolerance }
     * 
     */
    public Agents.Agent.LoadingTolerance createAgentsAgentLoadingTolerance() {
        return new Agents.Agent.LoadingTolerance();
    }

    /**
     * Create an instance of {@link Agents.Agent }
     * 
     */
    public Agents.Agent createAgentsAgent() {
        return new Agents.Agent();
    }

    /**
     * Create an instance of {@link Location }
     * 
     */
    public Location createLocation() {
        return new Location();
    }

    /**
     * Create an instance of {@link Agents.Agent.Travel }
     * 
     */
    public Agents.Agent.Travel createAgentsAgentTravel() {
        return new Agents.Agent.Travel();
    }

    /**
     * Create an instance of {@link Agents.Agent.DefaultPreferences }
     * 
     */
    public Agents.Agent.DefaultPreferences createAgentsAgentDefaultPreferences() {
        return new Agents.Agent.DefaultPreferences();
    }

    /**
     * Create an instance of {@link Agents }
     * 
     */
    public Agents createAgents() {
        return new Agents();
    }

    /**
     * Create an instance of {@link Agents.Agent.Money }
     * 
     */
    public Agents.Agent.Money createAgentsAgentMoney() {
        return new Agents.Agent.Money();
    }

    /**
     * Create an instance of {@link TemporalProperties }
     * 
     */
    public TemporalProperties createTemporalProperties() {
        return new TemporalProperties();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TemporalProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "duration", scope = Agents.Agent.Activity.class)
    public JAXBElement<TemporalProperties> createAgentsAgentActivityDuration(TemporalProperties value) {
        return new JAXBElement<TemporalProperties>(_AgentsAgentActivityDuration_QNAME, TemporalProperties.class, Agents.Agent.Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TemporalProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "start", scope = Agents.Agent.Activity.class)
    public JAXBElement<TemporalProperties> createAgentsAgentActivityStart(TemporalProperties value) {
        return new JAXBElement<TemporalProperties>(_AgentsAgentActivityStart_QNAME, TemporalProperties.class, Agents.Agent.Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Location }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "location", scope = Agents.Agent.Activity.class)
    public JAXBElement<Location> createAgentsAgentActivityLocation(Location value) {
        return new JAXBElement<Location>(_AgentsAgentActivityLocation_QNAME, Location.class, Agents.Agent.Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TemporalProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "end", scope = Agents.Agent.Activity.class)
    public JAXBElement<TemporalProperties> createAgentsAgentActivityEnd(TemporalProperties value) {
        return new JAXBElement<TemporalProperties>(_AgentsAgentActivityEnd_QNAME, TemporalProperties.class, Agents.Agent.Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TemporalProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "duration", scope = Agents.Agent.DefaultPreferences.class)
    public JAXBElement<TemporalProperties> createAgentsAgentDefaultPreferencesDuration(TemporalProperties value) {
        return new JAXBElement<TemporalProperties>(_AgentsAgentActivityDuration_QNAME, TemporalProperties.class, Agents.Agent.DefaultPreferences.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TemporalProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "start", scope = Agents.Agent.DefaultPreferences.class)
    public JAXBElement<TemporalProperties> createAgentsAgentDefaultPreferencesStart(TemporalProperties value) {
        return new JAXBElement<TemporalProperties>(_AgentsAgentActivityStart_QNAME, TemporalProperties.class, Agents.Agent.DefaultPreferences.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TemporalProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "end", scope = Agents.Agent.DefaultPreferences.class)
    public JAXBElement<TemporalProperties> createAgentsAgentDefaultPreferencesEnd(TemporalProperties value) {
        return new JAXBElement<TemporalProperties>(_AgentsAgentActivityEnd_QNAME, TemporalProperties.class, Agents.Agent.DefaultPreferences.class, value);
    }

}