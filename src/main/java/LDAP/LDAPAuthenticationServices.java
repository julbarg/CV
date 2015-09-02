/**
 * LDAPAuthenticationServices.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package LDAP;

public interface LDAPAuthenticationServices extends java.rmi.Remote {
    public boolean userAuthentication(java.lang.String username, java.lang.String password, java.lang.String domainName) throws java.rmi.RemoteException;
    public boolean bdUserAuthentication(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
}
