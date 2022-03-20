
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File fileName = new File("data.xml");
        parseXML(fileName);

    }

    public static List<Employee> parseXML(File fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> myList = new ArrayList<Employee>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(fileName);
        Node root = doc.getDocumentElement(); // отдал [staff: null]
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) { // длина 5 элементов (2 employee и #text обертка какая-то)
            Node nodeSan = nodeList.item(i);
            if (Node.ELEMENT_NODE != nodeSan.getNodeType()) continue;
            System.out.println(nodeSan.getNodeName());
            NodeList nodeList1 = nodeSan.getChildNodes();
             long id = 0;
             String firstName = null;
             String lastName = null;
             String country = null;
             int age = 0;

            for (int j = 0; j < nodeList1.getLength(); j++) {
                Node nodeSan2 = nodeList1.item(j);
                if (Node.ELEMENT_NODE == nodeSan2.getNodeType()) {  //отдает узлы без #text
         //           System.out.println(nodeSan2.getNodeName());
                    Element element = (Element) nodeSan2;
                    String nodeValue = element.getTextContent(); // это кошмар( хоть бы намекнули
                    //The getNodeValue method in the DOM is defined to always return null for element nodes
                    // (see the table at the top of the JavaDoc page for org.w3c.dom.Node for details).
                    // If you want the text inside the element then you should use getTextContent() instead.
                     if (j==0) {
                         String newId = element.getTextContent();
                         id = Long.parseLong(newId);
                     }
                     if (j==1) firstName = (String) element.getTextContent();
                     if (j==2) lastName = (String) element.getTextContent();
                     if (j==3) country = (String) element.getTextContent();
                     if (j==4) {
                         String newAge = element.getTextContent();
                          age = Integer.parseInt(newAge);
                     }
                }
            }
            Employee employee = new Employee(id, firstName,lastName, country, age);
            myList.add(employee);
        }
          return myList;
    }
}





