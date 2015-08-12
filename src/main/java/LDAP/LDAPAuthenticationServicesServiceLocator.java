/**
 * LDAPAuthenticationServicesServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package LDAP;

public class LDAPAuthenticationServicesServiceLocator extends org.apache.axis.client.Service implements LDAP.LDAPAuthenticationServicesService {

    public LDAPAuthenticationServicesServiceLocator() {
    }


    public LDAPAuthenticationServicesServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LDAPAuthenticationServicesServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LDAPAuthenticationServices
    private java.lang.String LDAPAuthenticationServices_address = "http://localhost:81/LdapAuthentication/services/LDAPAuthenticationServices";

    public java.lang.String getLDAPAuthenticationServicesAddress() {
        return LDAPAuthenticationServices_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LDAPAuthenticationServicesWSDDServiceName = "LDAPAuthenticationServices";

    public java.lang.String getLDAPAuthenticationServicesWSDDServiceName() {
        return LDAPAuthenticationServicesWSDDServiceName;
    }

    public void setLDAPAuthenticationServicesWSDDServiceName(java.lang.String name) {
        LDAPAuthenticationServicesWSDDServiceName = name;
    }

    public LDAP.LDAPAuthenticationServices getLDAPAuthenticationServices() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LDAPAuthenticationServices_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLDAPAuthenticationServices(endpoint);
    }

    public LDAP.LDAPAuthenticationServices getLDAPAuthenticationServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            LDAP.LDAPAuthenticationServicesSoapBindingStub _stub = new LDAP.LDAPAuthenticationServicesSoapBindingStub(portAddress, this);
            _stub.setPortName(getLDAPAuthenticationServicesWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLDAPAuthenticationServicesEndpointAddress(java.lang.String address) {
        LDAPAuthenticationServices_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (LDAP.LDAPAuthenticationServices.class.isAssignableFrom(serviceEndpointInterface)) {
                LDAP.LDAPAuthenticationServicesSoapBindingStub _stub = new LDAP.LDAPAuthenticationServicesSoapBindingStub(new java.net.URL(LDAPAuthenticationServices_address), this);
                _stub.setPortName(getLDAPAuthenticationServicesWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("LDAPAuthenticationServices".equals(inputPortName)) {
            return getLDAPAuthenticationServices();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.ldap.sisges.claro.com.co", "LDAPAuthenticationServicesService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.ldap.sisges.claro.com.co", "LDAPAuthenticationServices"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LDAPAuthenticationServices".equals(portName)) {
            setLDAPAuthenticationServicesEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
