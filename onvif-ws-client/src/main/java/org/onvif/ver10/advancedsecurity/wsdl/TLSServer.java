package org.onvif.ver10.advancedsecurity.wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * TLS server functionality.
 *
 * This class was generated by Apache CXF 3.3.2
 * Generated source version: 3.3.2
 *
 */
@WebService(targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", name = "TLSServer")
@XmlSeeAlso({ObjectFactory.class})
public interface TLSServer {

    /**
     * This operation removes a key pair and certificate assignment (including certification path)
     * to the TLS server on the device.
     * 
     * Certification paths are identified using certification path IDs. If the supplied
     * certification path ID is not associated to the TLS server, an InvalidArgVal fault is
     * produced.
     *       
     */
    @WebMethod(operationName = "RemoveServerCertificateAssignment", action = "http://www.onvif.org/ver10/advancedsecurity/wsdl/RemoveServerCertificateAssignment")
    @RequestWrapper(localName = "RemoveServerCertificateAssignment", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.RemoveServerCertificateAssignment")
    @ResponseWrapper(localName = "RemoveServerCertificateAssignmentResponse", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.RemoveServerCertificateAssignmentResponse")
    public void removeServerCertificateAssignment(

        @WebParam(name = "CertificationPathID", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl")
        java.lang.String certificationPathID
    );

    /**
     * This operation returns whether TLS client authentication is active.
     *       
     */
    @WebMethod(operationName = "GetClientAuthenticationRequired", action = "http://www.onvif.org/ver10/advancedsecurity/wsdl/GetClientAuthenticationRequired")
    @RequestWrapper(localName = "GetClientAuthenticationRequired", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.GetClientAuthenticationRequired")
    @ResponseWrapper(localName = "GetClientAuthenticationRequiredResponse", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.GetClientAuthenticationRequiredResponse")
    @WebResult(name = "clientAuthenticationRequired", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public boolean getClientAuthenticationRequired()
;

    /**
     * This operation replaces an existing key pair and certificate assignment to the TLS server on
     * the device by a new key pair and certificate assignment (including certification paths).
     * 
     * 
     * After the replacement, the TLS server shall use the new certificate and certification path
     * exactly in those cases in which it would have used the old certificate and certification
     * path.
     * Therefore, especially in the case that several server certificates are assigned to the TLS
     * server, clients that wish to replace an old certificate assignment by a new assignment
     * should use this operation instead of a combination of the Add TLS Server Certificate
     * Assignment and the Remove TLS Server Certificate Assignment operations.
     * 
     * 
     * Certification paths are identified using certification path IDs. If the supplied old
     * certification path ID is not associated to the TLS server, or no certification path exists
     * under the new certification path ID, the corresponding InvalidArgVal faults are produced and
     * the associations are unchanged.
     * The first certificate in the new certification path must be the TLS server certificate.
     * 
     * Since each certificate has exactly one associated key pair, a reference to the key pair that
     * is associated with the new server certificate is not supplied explicitly.
     * Devices shall obtain the private key or results of operations under the private key by
     * suitable internal interaction with the keystore.
     * 
     * If a device chooses to perform a TLS key exchange based on the new certification path, it
     * shall use the key pair that is associated with the server certificate
     * for key exchange and transmit the certification path to TLS clients as-is, i.e., the device
     * shall not check conformance of the certification path to RFC 4346 norRFC 2246.
     * In order to use the server certificate during the TLS handshake, the corresponding private
     * key is required.
     * Therefore, if the key pair that is associated with the server certificate, i.e., the first
     * certificate in the certification path, does not have an associated private key,
     * the NoPrivateKey fault is produced and the certification path is not associated to the TLS
     * server.
     *       
     */
    @WebMethod(operationName = "ReplaceServerCertificateAssignment", action = "http://www.onvif.org/ver10/advancedsecurity/wsdl/ReplaceServerCertificateAssignment")
    @RequestWrapper(localName = "ReplaceServerCertificateAssignment", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.ReplaceServerCertificateAssignment")
    @ResponseWrapper(localName = "ReplaceServerCertificateAssignmentResponse", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.ReplaceServerCertificateAssignmentResponse")
    public void replaceServerCertificateAssignment(

        @WebParam(name = "OldCertificationPathID", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl")
        java.lang.String oldCertificationPathID,
        @WebParam(name = "NewCertificationPathID", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl")
        java.lang.String newCertificationPathID
    );

    /**
     * This operation assigns a certification path validation policy to the TLS server on the
     * device. The TLS server shall enforce the policy when authenticating TLS clients and consider
     * a client authentic if and only if the algorithm returns valid.
     * If no certification path validation policy is stored under the requested
     * CertPathValidationPolicyID, the device shall produce a CertPathValidationPolicyID fault.
     * A TLS server may use different certification path validation policies to authenticate
     * clients. Therefore more than one certification path validation policy may be assigned to the
     * TLS server. If the maximum number of certification path validation policies that may be
     * assigned to the TLS server simultaneously is reached, the device shall produce a
     * MaximumNumberOfTLSCertPathValidationPoliciesReached fault and shall not assign the requested
     * certification path validation policy to the TLS server.
     *       
     */
    @WebMethod(operationName = "AddCertPathValidationPolicyAssignment", action = "http://www.onvif.org/ver10/advancedsecurity/wsdl/AddCertPathValidationPolicyAssignment")
    @RequestWrapper(localName = "AddCertPathValidationPolicyAssignment", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.AddCertPathValidationPolicyAssignment")
    @ResponseWrapper(localName = "AddCertPathValidationPolicyAssignmentResponse", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.AddCertPathValidationPolicyAssignmentResponse")
    public void addCertPathValidationPolicyAssignment(

        @WebParam(name = "CertPathValidationPolicyID", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl")
        java.lang.String certPathValidationPolicyID
    );

    /**
     * This operation removes a certification path validation policy assignment from the TLS server
     * on the device.
     * If the certification path validation policy identified by the requested
     * CertPathValidationPolicyID is not associated to the TLS server, the device shall produce a
     * CertPathValidationPolicy fault.
     *       
     */
    @WebMethod(operationName = "RemoveCertPathValidationPolicyAssignment", action = "http://www.onvif.org/ver10/advancedsecurity/wsdl/RemoveCertPathValidationPolicyAssignment")
    @RequestWrapper(localName = "RemoveCertPathValidationPolicyAssignment", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.RemoveCertPathValidationPolicyAssignment")
    @ResponseWrapper(localName = "RemoveCertPathValidationPolicyAssignmentResponse", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.RemoveCertPathValidationPolicyAssignmentResponse")
    public void removeCertPathValidationPolicyAssignment(

        @WebParam(name = "CertPathValidationPolicyID", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl")
        java.lang.String certPathValidationPolicyID
    );

    /**
     * This operation replaces a certification path validation policy assignment to the TLS server
     * on the device with another certification path validation policy assignment.
     * If the certification path validation policy identified by the requested
     * OldCertPathValidationPolicyID is not associated to the TLS server, the device shall produce
     * an OldCertPathValidationPolicyID fault and shall not associate the certification path
     * validation policy identified by the NewCertPathValidationPolicyID to the TLS server.
     * If no certification path validation policy exists under the requested
     * NewCertPathValidationPolicyID in the device’s keystore, the device shall produce a
     * NewCertPathValidationPolicyID fault and shall not remove the association of the old
     * certification path validation policy to the TLS server.
     *       
     */
    @WebMethod(operationName = "ReplaceCertPathValidationPolicyAssignment", action = "http://www.onvif.org/ver10/advancedsecurity/wsdl/ReplaceCertPathValidationPolicyAssignment")
    @RequestWrapper(localName = "ReplaceCertPathValidationPolicyAssignment", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.ReplaceCertPathValidationPolicyAssignment")
    @ResponseWrapper(localName = "ReplaceCertPathValidationPolicyAssignmentResponse", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.ReplaceCertPathValidationPolicyAssignmentResponse")
    public void replaceCertPathValidationPolicyAssignment(

        @WebParam(name = "OldCertPathValidationPolicyID", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl")
        java.lang.String oldCertPathValidationPolicyID,
        @WebParam(name = "NewCertPathValidationPolicyID", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl")
        java.lang.String newCertPathValidationPolicyID
    );

    /**
     * This operation assigns a key pair and certificate along with a certification path
     * (certificate chain) to the TLS server on the device.
     * The TLS server shall use this information for key exchange during the TLS handshake,
     * particularly for constructing server certificate messages as specified in RFC 4346 and RFC
     * 2246.
     * 
     * 
     * Certification paths are identified by their certification path IDs in the keystore. The
     * first certificate in the certification path must be the TLS server certificate.
     * Since each certificate has exactly one associated key pair, a reference to the key pair that
     * is associated with the server certificate is not supplied explicitly.
     * Devices shall obtain the private key or results of operations under the private key by
     * suitable internal interaction with the keystore.
     * 
     * If a device chooses to perform a TLS key exchange based on the supplied certification path,
     * it shall use the key pair that is associated with the server certificate for
     * key exchange and transmit the certification path to TLS clients as-is, i.e., the device
     * shall not check conformance of the certification path to RFC 4346 norRFC 2246.
     * In order to use the server certificate during the TLS handshake, the corresponding private
     * key is required.
     * Therefore, if the key pair that is associated with the server certificate, i.e., the first
     * certificate in the certification path, does not have an associated private key,
     * the NoPrivateKey fault is produced and the certification path is not associated to the TLS
     * server.
     * 
     * A TLS server may present different certification paths to different clients during the TLS
     * handshake instead of presenting the same certification path to all clients.
     * Therefore more than one certification path may be assigned to the TLS server.
     * 
     * If the maximum number of certification paths that may be assigned to the TLS server
     * simultaneously is reached, the device shall generate a
     * MaximumNumberOfCertificationPathsReached
     * fault and the requested certification path shall not be assigned to the TLS server.
     *       
     */
    @WebMethod(operationName = "AddServerCertificateAssignment", action = "http://www.onvif.org/ver10/advancedsecurity/wsdl/AddServerCertificateAssignment")
    @RequestWrapper(localName = "AddServerCertificateAssignment", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.AddServerCertificateAssignment")
    @ResponseWrapper(localName = "AddServerCertificateAssignmentResponse", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.AddServerCertificateAssignmentResponse")
    public void addServerCertificateAssignment(

        @WebParam(name = "CertificationPathID", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl")
        java.lang.String certificationPathID
    );

    /**
     * This operation returns the IDs of all key pairs and certificates (including certification
     * paths) that are assigned to the TLS server on the device.
     * 
     * This operation may be used, e.g., if a client lost track of the certification path
     * assignments on the device.
     * If no certification path is assigned to the TLS server, an empty list is returned.
     *       
     */
    @WebMethod(operationName = "GetAssignedServerCertificates", action = "http://www.onvif.org/ver10/advancedsecurity/wsdl/GetAssignedServerCertificates")
    @RequestWrapper(localName = "GetAssignedServerCertificates", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.GetAssignedServerCertificates")
    @ResponseWrapper(localName = "GetAssignedServerCertificatesResponse", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.GetAssignedServerCertificatesResponse")
    @WebResult(name = "CertificationPathID", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public java.util.List<java.lang.String> getAssignedServerCertificates()
;

    /**
     * This operation returns the IDs of all certification path validation policies that are
     * assigned to the TLS server on the device.
     *       
     */
    @WebMethod(operationName = "GetAssignedCertPathValidationPolicies", action = "http://www.onvif.org/ver10/advancedsecurity/wsdl/GetAssignedCertPathValidationPolicies")
    @RequestWrapper(localName = "GetAssignedCertPathValidationPolicies", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.GetAssignedCertPathValidationPolicies")
    @ResponseWrapper(localName = "GetAssignedCertPathValidationPoliciesResponse", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.GetAssignedCertPathValidationPoliciesResponse")
    @WebResult(name = "CertPathValidationPolicyID", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public java.util.List<java.lang.String> getAssignedCertPathValidationPolicies()
;

    /**
     * This operation activates or deactivates TLS client authentication for the TLS server on the
     * device.
     * The TLS server on the device shall require client authentication if and only if
     * clientAuthenticationRequired is set to true.
     * If TLS client authentication is requested to be enabled and no certification path validation
     * policy is assigned to the TLS server, the device shall return an
     * EnablingTLSClientAuthenticationFailed fault and shall not enable TLS client authentication.
     * The device shall execute this command regardless of the TLS enabled/disabled state
     * configured in the ONVIF Device Management Service.
     *       
     */
    @WebMethod(operationName = "SetClientAuthenticationRequired", action = "http://www.onvif.org/ver10/advancedsecurity/wsdl/SetClientAuthenticationRequired")
    @RequestWrapper(localName = "SetClientAuthenticationRequired", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.SetClientAuthenticationRequired")
    @ResponseWrapper(localName = "SetClientAuthenticationRequiredResponse", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl", className = "org.onvif.ver10.advancedsecurity.wsdl.SetClientAuthenticationRequiredResponse")
    public void setClientAuthenticationRequired(

        @WebParam(name = "clientAuthenticationRequired", targetNamespace = "http://www.onvif.org/ver10/advancedsecurity/wsdl")
        boolean clientAuthenticationRequired
    );
}
