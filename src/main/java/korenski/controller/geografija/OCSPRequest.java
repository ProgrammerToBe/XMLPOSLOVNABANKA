/*package korenski.controller.geografija;

import java.io.IOException;

class OCSPRequest {

    private static final Debug debug = Debug.getInstance("certpath");
    private static final boolean dump = false;

    // List of request CertIds
    private final List<CertId> certIds;

    
     * Constructs an OCSPRequest. This constructor is used
     * to construct an unsigned OCSP Request for a single user cert.
     
    OCSPRequest(CertId certId) {
        this.certIds = Collections.singletonList(certId);
    }

    OCSPRequest(List<CertId> certIds) {
        this.certIds = certIds;
    }

    byte[] encodeBytes() throws IOException {

        // encode tbsRequest
        DerOutputStream tmp = new DerOutputStream();
        DerOutputStream requestsOut = new DerOutputStream();
        for (CertId certId : certIds) {
            DerOutputStream certIdOut = new DerOutputStream();
            certId.encode(certIdOut);
            requestsOut.write(DerValue.tag_Sequence, certIdOut);
        }

        tmp.write(DerValue.tag_Sequence, requestsOut);
        // No extensions supported
        DerOutputStream tbsRequest = new DerOutputStream();
        tbsRequest.write(DerValue.tag_Sequence, tmp);

        // OCSPRequest without the signature
        DerOutputStream ocspRequest = new DerOutputStream();
        ocspRequest.write(DerValue.tag_Sequence, tbsRequest);

        byte[] bytes = ocspRequest.toByteArray();

        if (dump) {
            HexDumpEncoder hexEnc = new HexDumpEncoder();
            System.out.println("OCSPRequest bytes are... ");
            System.out.println(hexEnc.encode(bytes));
        }

        return bytes;
    }

    List<CertId> getCertIds() {
        return certIds;
    }
}
*/