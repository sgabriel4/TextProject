
package soa;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the soa package. 
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

    private final static QName _GetTextHTML_QNAME = new QName("http://SOA/", "getTextHTML");
    private final static QName _GetTextDocResponse_QNAME = new QName("http://SOA/", "getTextDocResponse");
    private final static QName _GetTextHTMLResponse_QNAME = new QName("http://SOA/", "getTextHTMLResponse");
    private final static QName _GetTextDoc_QNAME = new QName("http://SOA/", "getTextDoc");
    private final static QName _GetTextTXTResponse_QNAME = new QName("http://SOA/", "getTextTXTResponse");
    private final static QName _UploadResponse_QNAME = new QName("http://SOA/", "uploadResponse");
    private final static QName _GetTextPDFResponse_QNAME = new QName("http://SOA/", "getTextPDFResponse");
    private final static QName _GetTextPDF_QNAME = new QName("http://SOA/", "getTextPDF");
    private final static QName _Upload_QNAME = new QName("http://SOA/", "upload");
    private final static QName _DownloadResponse_QNAME = new QName("http://SOA/", "downloadResponse");
    private final static QName _GetTextTXT_QNAME = new QName("http://SOA/", "getTextTXT");
    private final static QName _Download_QNAME = new QName("http://SOA/", "download");
    private final static QName _GetTextPDFFile_QNAME = new QName("", "file");
    private final static QName _UploadArg1_QNAME = new QName("", "arg1");
    private final static QName _DownloadResponseReturn_QNAME = new QName("", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: soa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Download }
     * 
     */
    public Download createDownload() {
        return new Download();
    }

    /**
     * Create an instance of {@link GetTextPDF }
     * 
     */
    public GetTextPDF createGetTextPDF() {
        return new GetTextPDF();
    }

    /**
     * Create an instance of {@link Upload }
     * 
     */
    public Upload createUpload() {
        return new Upload();
    }

    /**
     * Create an instance of {@link DownloadResponse }
     * 
     */
    public DownloadResponse createDownloadResponse() {
        return new DownloadResponse();
    }

    /**
     * Create an instance of {@link GetTextTXT }
     * 
     */
    public GetTextTXT createGetTextTXT() {
        return new GetTextTXT();
    }

    /**
     * Create an instance of {@link GetTextTXTResponse }
     * 
     */
    public GetTextTXTResponse createGetTextTXTResponse() {
        return new GetTextTXTResponse();
    }

    /**
     * Create an instance of {@link UploadResponse }
     * 
     */
    public UploadResponse createUploadResponse() {
        return new UploadResponse();
    }

    /**
     * Create an instance of {@link GetTextPDFResponse }
     * 
     */
    public GetTextPDFResponse createGetTextPDFResponse() {
        return new GetTextPDFResponse();
    }

    /**
     * Create an instance of {@link GetTextHTML }
     * 
     */
    public GetTextHTML createGetTextHTML() {
        return new GetTextHTML();
    }

    /**
     * Create an instance of {@link GetTextDocResponse }
     * 
     */
    public GetTextDocResponse createGetTextDocResponse() {
        return new GetTextDocResponse();
    }

    /**
     * Create an instance of {@link GetTextHTMLResponse }
     * 
     */
    public GetTextHTMLResponse createGetTextHTMLResponse() {
        return new GetTextHTMLResponse();
    }

    /**
     * Create an instance of {@link GetTextDoc }
     * 
     */
    public GetTextDoc createGetTextDoc() {
        return new GetTextDoc();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTextHTML }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOA/", name = "getTextHTML")
    public JAXBElement<GetTextHTML> createGetTextHTML(GetTextHTML value) {
        return new JAXBElement<GetTextHTML>(_GetTextHTML_QNAME, GetTextHTML.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTextDocResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOA/", name = "getTextDocResponse")
    public JAXBElement<GetTextDocResponse> createGetTextDocResponse(GetTextDocResponse value) {
        return new JAXBElement<GetTextDocResponse>(_GetTextDocResponse_QNAME, GetTextDocResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTextHTMLResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOA/", name = "getTextHTMLResponse")
    public JAXBElement<GetTextHTMLResponse> createGetTextHTMLResponse(GetTextHTMLResponse value) {
        return new JAXBElement<GetTextHTMLResponse>(_GetTextHTMLResponse_QNAME, GetTextHTMLResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTextDoc }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOA/", name = "getTextDoc")
    public JAXBElement<GetTextDoc> createGetTextDoc(GetTextDoc value) {
        return new JAXBElement<GetTextDoc>(_GetTextDoc_QNAME, GetTextDoc.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTextTXTResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOA/", name = "getTextTXTResponse")
    public JAXBElement<GetTextTXTResponse> createGetTextTXTResponse(GetTextTXTResponse value) {
        return new JAXBElement<GetTextTXTResponse>(_GetTextTXTResponse_QNAME, GetTextTXTResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOA/", name = "uploadResponse")
    public JAXBElement<UploadResponse> createUploadResponse(UploadResponse value) {
        return new JAXBElement<UploadResponse>(_UploadResponse_QNAME, UploadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTextPDFResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOA/", name = "getTextPDFResponse")
    public JAXBElement<GetTextPDFResponse> createGetTextPDFResponse(GetTextPDFResponse value) {
        return new JAXBElement<GetTextPDFResponse>(_GetTextPDFResponse_QNAME, GetTextPDFResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTextPDF }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOA/", name = "getTextPDF")
    public JAXBElement<GetTextPDF> createGetTextPDF(GetTextPDF value) {
        return new JAXBElement<GetTextPDF>(_GetTextPDF_QNAME, GetTextPDF.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Upload }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOA/", name = "upload")
    public JAXBElement<Upload> createUpload(Upload value) {
        return new JAXBElement<Upload>(_Upload_QNAME, Upload.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOA/", name = "downloadResponse")
    public JAXBElement<DownloadResponse> createDownloadResponse(DownloadResponse value) {
        return new JAXBElement<DownloadResponse>(_DownloadResponse_QNAME, DownloadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTextTXT }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOA/", name = "getTextTXT")
    public JAXBElement<GetTextTXT> createGetTextTXT(GetTextTXT value) {
        return new JAXBElement<GetTextTXT>(_GetTextTXT_QNAME, GetTextTXT.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Download }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOA/", name = "download")
    public JAXBElement<Download> createDownload(Download value) {
        return new JAXBElement<Download>(_Download_QNAME, Download.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "file", scope = GetTextPDF.class)
    public JAXBElement<byte[]> createGetTextPDFFile(byte[] value) {
        return new JAXBElement<byte[]>(_GetTextPDFFile_QNAME, byte[].class, GetTextPDF.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg1", scope = Upload.class)
    public JAXBElement<byte[]> createUploadArg1(byte[] value) {
        return new JAXBElement<byte[]>(_UploadArg1_QNAME, byte[].class, Upload.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "file", scope = GetTextDoc.class)
    public JAXBElement<byte[]> createGetTextDocFile(byte[] value) {
        return new JAXBElement<byte[]>(_GetTextPDFFile_QNAME, byte[].class, GetTextDoc.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "file", scope = GetTextTXT.class)
    public JAXBElement<byte[]> createGetTextTXTFile(byte[] value) {
        return new JAXBElement<byte[]>(_GetTextPDFFile_QNAME, byte[].class, GetTextTXT.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = DownloadResponse.class)
    public JAXBElement<byte[]> createDownloadResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_DownloadResponseReturn_QNAME, byte[].class, DownloadResponse.class, ((byte[]) value));
    }

}
