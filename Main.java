
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class Main {
 
 public static void main(String[] args) throws TransformerException, ParserConfigurationException, IOException {

   //Data to write to XML
  Alien[] aliens = {new Alien(0,0,"Bob"), new Alien(10,0,"Suzy")};
   
//   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
   String root = "Aliens";
   DocumentBuilderFactory documentBuilderFactory =DocumentBuilderFactory.newInstance();
   DocumentBuilder documentBuilder =documentBuilderFactory.newDocumentBuilder();
   Document document = documentBuilder.newDocument();
   Element rootElement = document.createElement(root);
   document.appendChild(rootElement);

   for(int i = 0; i < aliens.length; i++){
     Element alienElement = document.createElement("Alien");
     Element x = document.createElement("x");
     x.appendChild(document.createTextNode(Integer.toString(aliens[i].x)));
     alienElement.appendChild(x);
     
     Element y = document.createElement("y");
     y.appendChild(document.createTextNode(Integer.toString(aliens[i].y)));
     alienElement.appendChild(y);
     Element alienName = document.createElement("alienName");
     alienName.appendChild(document.createTextNode(aliens[i].alienName));
     alienElement.appendChild(alienName);
     rootElement.appendChild(alienElement);
   }

   TransformerFactory transformerFactory = TransformerFactory.newInstance();
   Transformer transformer = transformerFactory.newTransformer();
   DOMSource source = new DOMSource(document);
   
   StreamResult result =  new StreamResult(new StringWriter());
   transformer.setOutputProperty(OutputKeys.INDENT, "yes");
   transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
   transformer.transform(source, result);

   
   //writing to file
   FileOutputStream fileOutputStream = null;
   File file;
   try {
     file = new File("aliens.txt");
     fileOutputStream = new FileOutputStream(file);

     // if file doesnt exists, then create it
     if (!file.exists()) {
       file.createNewFile();
       }

     // get the content in bytes
     String xmlString = result.getWriter().toString();
     System.out.println(xmlString);
     byte[] contentInBytes = xmlString.getBytes();
     
     fileOutputStream.write(contentInBytes);
     fileOutputStream.flush();
     fileOutputStream.close();
     
     System.out.println("Done");
     } catch (IOException e) {
       e.printStackTrace();
   } finally {
     try {
       if (fileOutputStream != null) {
         fileOutputStream.close();
         }
       } catch (IOException e) {
       e.printStackTrace();
     }
    }
  }
}