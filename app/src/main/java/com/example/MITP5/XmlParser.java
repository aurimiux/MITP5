package com.example.MITP5;

import android.util.Log;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParser {
    public static String getRatesFromECB(InputStream stream) throws IOException {
        String result = "";
        try {
            DocumentBuilderFactory xmlDocFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlDocBuilder = xmlDocFactory.newDocumentBuilder();
            Document doc = xmlDocBuilder.parse(stream);

            NodeList rateNodes = doc.getElementsByTagName("Cube");
            for (int i = 2; i < rateNodes.getLength(); ++i) {
                Element rate = (Element) rateNodes.item(i);
                result = result + rate.getAttribute("currency") + " - " + rate.getAttribute("rate") + "\n";
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        Log.d("LOGGER", "XmlParser.getRateFromECB() is executed!");
        return result;
    }

}
