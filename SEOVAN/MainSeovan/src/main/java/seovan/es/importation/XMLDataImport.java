/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seovan.es.importation;

import static beleris.es.finalinformationmanager.MainErrorManager.ShowDError;
import beleris.es.finalprimaryclasses.CLAllObjectList;
import beleris.es.finalutils.FileManagerGenerator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Emilio David Diaus López 2023-2026
 */
public class XMLDataImport implements Serializable {

    private static final long serialVersionUID = -3899963182363586461L;

    private Document doc = null;
    private int XMLItemstoImport = 0;
    String RCA = "";
    private CLAllObjectList<Document> ListofElements = new CLAllObjectList<>();
    FileManagerGenerator FMG = new FileManagerGenerator();

    /**
     *
     */
    public XMLDataImport() {

    }

    /**
     *
     * @param filepath
     */
    public XMLDataImport(String filepath) {
        try {
            RCA = FMG.readFile(filepath, StandardCharsets.UTF_8);
            String itemstoimport = SearchNumberofItems(RCA);
            XMLItemstoImport = Integer.parseInt(itemstoimport);
        } catch (IOException ex) {
            ShowDError(" No puedo acceder al fichero, error al cargar fichero XML");
        }
        if (XMLItemstoImport == 1) {
            doc = ImportFile(repairXML());
        } else {
            ListofElements = ImportFiles(filepath, XMLItemstoImport);
        }
    }

    String SearchNumberofItems(String texto) {
        String result = "";
        // Cambia "_doc" por la cadena fija que estés buscando
        String regex = "<recordId>\\b(?!0000)\\d{4}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);

        // Lista para guardar todas las coincidencias
        List<String> encontrados = new ArrayList<>();

        while (matcher.find()) {
            String coincidencia = matcher.group();
            coincidencia = coincidencia.replaceAll("<recordId>", "");
            encontrados.add(coincidencia);
        }

        // Comprobar si hay resultados antes de sacar el mayor
        if (!encontrados.isEmpty()) {
            String masAlto = Collections.max(encontrados);
            result = masAlto;
        }
        return result;
    }

    InputSource repairXML() {
        String xmlCorregido = RCA.replaceAll("&(?!(amp|lt|gt|quot|apos);)", "&amp;");
        InputSource is = new InputSource(new ByteArrayInputStream(xmlCorregido.getBytes()));
        return is;
    }

    /**
     *
     * @param is
     * @return
     */
    public Document ImportFile(InputSource is) {
        Document doc = null;
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
            ShowDError("Error al parsear XML : " + ex.getLocalizedMessage());
        }

        if (builder != null) {
            try {

                doc = builder.parse(is);
                doc.getDocumentElement().normalize();

            } catch (SAXException | IOException ex) {
                Exceptions.printStackTrace(ex);
                ShowDError("Error al cargar XML : " + ex.getLocalizedMessage());
            }

        }
        if (doc != null) {
        }
        return doc;
    }

    /**
     *
     * @param tmpChildNodeList
     * @param nodename
     * @return
     */
    public NodeList GetChilDNodeRecursed(NodeList tmpChildNodeList, String nodename) {
        NodeList NL, NLR = null;
        Node ci = null;
        NL = tmpChildNodeList;
        int n = NL.getLength();
        for (int i = 0; i < n; i++) {
            ci = NL.item(i);
            if (ci != null) {
                if (ci.getNodeType() == Node.ELEMENT_NODE) {
                    String NodeNametmp = ci.getNodeName().strip();
                    if (!NodeNametmp.startsWith("\n")) {
                        //child = childList.item(o);
                        if (nodename.equals(ci.getNodeName())) {
                            NLR = ci.getChildNodes();
                            break;
                        }
                    }
                }
            }
        }

        return NLR;
    }

    /**
     *
     * @param tmpChildNodeList
     * @param NodeToSearch
     * @return
     */
    public String GetResultFromRecurseDeeperNode(NodeList tmpChildNodeList, String NodeToSearch) {

        String Result = null;
        int childlistlength = tmpChildNodeList.getLength();
        for (int o = 0; o < childlistlength; o++) {
            Node ci = tmpChildNodeList.item(o);
            if (ci != null) {
                if (ci.getNodeType() == Node.ELEMENT_NODE) {
                    String NodeNametmp = ci.getNodeName().strip();
                    if (!NodeNametmp.startsWith("\n")) {
                        if (NodeToSearch.equals(ci.getNodeName())) {
                            Result = ci.getTextContent().strip();
                            System.out.println(Result);
                        }
                    }
                    System.out.println(
                            ci.getNodeName() + ": ");

                }
            }
        }
        return Result;
    }

    /**
     * @return the doc
     */
    public Document getDoc() {
        return doc;
    }

    /**
     * @param doc the doc to set
     */
    public void setDoc(Document doc) {
        this.doc = doc;
    }

    private CLAllObjectList<Document> ImportFiles(String filepath, int elements) {
        CLAllObjectList<Document> ListofElements = new CLAllObjectList<>();
        InputSource tmpis = repairXML();
        for (int ile = 1; ile <= elements; ile++) {
            Document tmpDoc = ImportFile(tmpis);
            ListofElements.add(tmpDoc);
            tmpis = Refine(tmpis);
        }
        return ListofElements;
    }

    InputSource Refine(InputSource tmpis) {
        InputSource rtmpis = null;
        String XMLBytefinal = "";
        rtmpis = tmpis;
        try {
            InputStream byteStream = tmpis.getByteStream();
            byteStream.reset();
            byte[] tmpreadAllBytes = byteStream.readAllBytes();
            String XMLbyte = new String(tmpreadAllBytes, "UTF-8");
            int iposition = XMLbyte.indexOf("<control>");
            int fposition = XMLbyte.indexOf("</sourceinformation>") + "</sourceinformation>".length();
            if (iposition != -1) {
                String Header = XMLbyte.substring(0, iposition - 1);
                String RestofString = XMLbyte.substring(fposition, XMLbyte.length());
                XMLBytefinal = Header + RestofString;
            }
            rtmpis.setByteStream(new ByteArrayInputStream(XMLBytefinal.getBytes()));
        } catch (IOException ex) {
            ShowDError("Error al procesar-refinar XML : " + ex.getLocalizedMessage());
        }
        return rtmpis;
    }

    /**
     * @return the ListofElements
     */
    public CLAllObjectList<Document> getListofElements() {
        return ListofElements;
    }

    /**
     * @param ListofElements the ListofElements to set
     */
    public void setListofElements(CLAllObjectList<Document> ListofElements) {
        this.ListofElements = ListofElements;
    }

    /**
     * @return the XMLItemstoImport
     */
    public int getXMLItemstoImport() {
        return XMLItemstoImport;
    }

    /**
     * @param XMLItemstoImport the XMLItemstoImport to set
     */
    public void setXMLItemstoImport(int XMLItemstoImport) {
        this.XMLItemstoImport = XMLItemstoImport;
    }

}
