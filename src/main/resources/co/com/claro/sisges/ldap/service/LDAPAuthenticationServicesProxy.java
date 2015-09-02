package co.com.claro.sisges.ldap.service;

public class LDAPAuthenticationServicesProxy implements co.com.claro.sisges.ldap.service.LDAPAuthenticationServices {
  private String _endpoint = null;
  private co.com.claro.sisges.ldap.service.LDAPAuthenticationServices lDAPAuthenticationServices = null;
  
  public LDAPAuthenticationServicesProxy() {
    _initLDAPAuthenticationServicesProxy();
  }
  
  public LDAPAuthenticationServicesProxy(String endpoint) {
    _endpoint = endpoint;
    _initLDAPAuthenticationServicesProxy();
  }
  
  private void _initLDAPAuthenticationServicesProxy() {
    try {
      lDAPAuthenticationServices = (new co.com.claro.sisges.ldap.service.LDAPAuthenticationServicesServiceLocator()).getLDAPAuthenticationServices();
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
  
  public co.com.claro.sisges.ldap.service.LDAPAuthenticationServices getLDAPAuthenticationServices() {
    if (lDAPAuthenticationServices == null)
      _initLDAPAuthenticationServicesProxy();
    return lDAPAuthenticationServices;
  }
  
  
}