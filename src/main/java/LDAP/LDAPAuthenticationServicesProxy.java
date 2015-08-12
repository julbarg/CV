package LDAP;

public class LDAPAuthenticationServicesProxy implements LDAP.LDAPAuthenticationServices {
  private String _endpoint = null;
  private LDAP.LDAPAuthenticationServices lDAPAuthenticationServices = null;
  
  public LDAPAuthenticationServicesProxy() {
    _initLDAPAuthenticationServicesProxy();
  }
  
  public LDAPAuthenticationServicesProxy(String endpoint) {
    _endpoint = endpoint;
    _initLDAPAuthenticationServicesProxy();
  }
  
  private void _initLDAPAuthenticationServicesProxy() {
    try {
      lDAPAuthenticationServices = (new LDAP.LDAPAuthenticationServicesServiceLocator()).getLDAPAuthenticationServices();
      if (lDAPAuthenticationServices != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)lDAPAuthenticationServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)lDAPAuthenticationServices)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (lDAPAuthenticationServices != null)
      ((javax.xml.rpc.Stub)lDAPAuthenticationServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public LDAP.LDAPAuthenticationServices getLDAPAuthenticationServices() {
    if (lDAPAuthenticationServices == null)
      _initLDAPAuthenticationServicesProxy();
    return lDAPAuthenticationServices;
  }
  
  public boolean userAuthentication(java.lang.String username, java.lang.String password, java.lang.String domainName) throws java.rmi.RemoteException{
    if (lDAPAuthenticationServices == null)
      _initLDAPAuthenticationServicesProxy();
    return lDAPAuthenticationServices.userAuthentication(username, password, domainName);
  }
  
  public boolean bdUserAuthentication(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException{
    if (lDAPAuthenticationServices == null)
      _initLDAPAuthenticationServicesProxy();
    return lDAPAuthenticationServices.bdUserAuthentication(username, password);
  }
  
  
}