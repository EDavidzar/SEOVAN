/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seovan.es.importation;

import static beleris.es.finalinformationmanager.MainErrorManager.ShowDError;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Emilio David Diaus López 2023-2026
 */
public class XMLDataImport implements Serializable {

    private static final long serialVersionUID = -3899963182363586461L;

    private Document doc = null;

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
        ImportFile(filepath);
    }

    /**
     *
     * @param filepath
     */
    public void ImportFile(String filepath) {
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        }

        if (builder != null) {
            try {
                File myfile = null;
                myfile = new File(filepath);

                doc = builder.parse(myfile);
                doc.getDocumentElement().normalize();

            }
            catch (SAXException | IOException ex) {
                Exceptions.printStackTrace(ex);
                ShowDError("Error al cargar XML : " + ex.getLocalizedMessage());
            }

        }
        if (doc != null) {
        }

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

}
