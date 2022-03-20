
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File fileName = new File("data.xml");
        List<Employee> myList = parseXML(fileName);
        String myJson = listToJson(myList);
        writeString(myJson);


    }

    public static List<Employee> parseXML(File fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> myList = new ArrayList<Employee>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(fileName);
        Node root = doc.getDocumentElement(); // отдал [staff: null]
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) { //  5 элементов
            Node nodeSan = nodeList.item(i);
            if (Node.ELEMENT_NODE != nodeSan.getNodeType()) continue;
            //      System.out.println(nodeSan.getNodeName());
            NodeList nodeList1 = nodeSan.getChildNodes();
             long id = 0;
             String firstName = null;
             String lastName = null;
             String country = null;
             int age = 0;

            for (int j = 0; j < nodeList1.getLength(); j++) {
                Node nodeSan2 = nodeList1.item(j);
                if (Node.ELEMENT_NODE == nodeSan2.getNodeType()) {  //отдает узлы без #text
              //     System.out.println(nodeSan2.getNodeName());
              //      System.out.println(j); вывести счетчик, чтобы ключи со значениями сопоставить
                    Element element = (Element) nodeSan2;
                    String nodeValue = element.getTextContent(); // это кошмар( хоть бы намекнули
                    //The getNodeValue method in the DOM is defined to always return null for element nodes
                    // (see the table at the top of the JavaDoc page for org.w3c.dom.Node for details).
                    // If you want the text inside the element then you should use getTextContent() instead.
                     if (j==1) {
                         String newId = element.getTextContent();
                         id = Long.parseLong(newId);
                     }
                     if (j==3) firstName = (String) element.getTextContent();
                     if (j==5) lastName = (String) element.getTextContent();
                     if (j==7) country = (String) element.getTextContent();
                     if (j==9) {
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

    public static String listToJson(List<Employee> list) {

        Type listType = new TypeToken<List<Employee>>() {}.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(list, listType);
        return json;
    }

    public static void writeString(String json) {
        try ( FileWriter file = new FileWriter("XMLtoJson.json")) {
            file.write(json);
            file.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}





