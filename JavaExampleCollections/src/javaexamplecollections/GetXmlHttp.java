/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaexamplecollections;

/**
 *
 * @author leo
 */
import java.net.URL;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

public class GetXmlHttp {

    public static void main(String[] args) throws Exception {
// or if you prefer DOM:
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL("http://localhost:8080/BMDService/getData?target=factory&oper=selectOne&id=1").openStream());

        doc.getDocumentElement().normalize();
        System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());
        NodeList listOfFactory = doc.getElementsByTagName("item");
        int totalPersons = listOfFactory.getLength();
        System.out.println("Total num of factory : " + totalPersons);

        for (int s = 0; s < listOfFactory.getLength(); s++) {

            Node firstPersonNode = listOfFactory.item(s);
            if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {

                Element firstPersonElement = (Element) firstPersonNode; //——-
                NodeList firstNameList = firstPersonElement.getElementsByTagName("title");
                Element firstNameElement = (Element) firstNameList.item(0);
                NodeList textFNList = firstNameElement.getChildNodes();
                System.out.println("title : " + ((Node) textFNList.item(0)).getNodeValue().trim()); //——-
                NodeList lastNameList = firstPersonElement.getElementsByTagName("future");
                Element lastNameElement = (Element) lastNameList.item(0);
                NodeList textLNList = lastNameElement.getChildNodes();
                System.out.println("future : " + ((Node) textLNList.item(0)).getNodeValue().trim()); //—-
                NodeList ageList = firstPersonElement.getElementsByTagName("comment");
                Element ageElement = (Element) ageList.item(0);
                NodeList textAgeList = ageElement.getChildNodes();
                System.out.println("comment : " + ((Node) textAgeList.item(0)).getNodeValue().trim()); //——

            }//end of if clause

        }//end of for loop with s var

    }
}