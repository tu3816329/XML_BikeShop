/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 *
 * @author tudt
 */
public class StAXParser {

    public StAXParser() {
    }

    public static String getNodeAttrStAXWithClass(XMLStreamReader reader, String elementName, String classNameValue, String namespace, String attrName) {
        if (reader != null) {
            try {
                while (reader.hasNext()) {
                    int event = reader.getEventType();
                    switch (event) {
                        case XMLStreamConstants.START_ELEMENT: {
                            String tagName = reader.getLocalName();
                            if (tagName.equals(elementName)) {
                                String className = reader.getAttributeValue(namespace, "class");
                                if (className.equals(classNameValue)) {
                                    return reader.getAttributeValue(namespace, attrName);
                                }
                            }
                            break;
                        }
                    }
                    reader.next();

                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static String getNodeStAXAttr(XMLStreamReader reader, String elementName, String namespace, String attrName) {
        if (reader != null) {
            try {
                while (reader.hasNext()) {
                    int event = reader.getEventType();
                    switch (event) {
                        case XMLStreamConstants.START_ELEMENT: {
                            String tagName = reader.getLocalName();
                            if (tagName.equals(elementName)) {
                                return reader.getAttributeValue(namespace, attrName);
                            }
                            break;
                        }
                    }
                    reader.next();

                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static String getXMLTextAndChar(XMLStreamReader reader) {
        String text = null;
        if (reader != null) {
            try {
                while (reader.hasNext()) {
                    int event = reader.getEventType();
                    switch (event) {
                        case XMLStreamConstants.CHARACTERS: {
                            text += reader.getText();
                            break;
                        }
                        case XMLStreamConstants.END_ELEMENT: {
                            return text;
                        }
                    }
                    reader.next();

                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return text;
    }

    public static String getNodeStAXText(XMLStreamReader reader, String elementName) {

        if (reader != null) {
            try {
                while (reader.hasNext()) {
                    int event = reader.getEventType();
                    switch (event) {
                        case XMLStreamConstants.START_ELEMENT: {
                            String tagName = reader.getLocalName();
                            if (tagName.equals(elementName)) {
                                String result = "";
                                try {
                                    reader.next();
                                    result = reader.getText();
                                    reader.nextTag();
                                } catch (Exception e) {
                                    reader.next();
                                    while (event == XMLStreamConstants.CHARACTERS || event == XMLStreamConstants.CDATA) {
                                        event = reader.getEventType();
                                        result += reader.getText();
                                        reader.next();
                                    }
                                    reader.nextTag();
                                }
                                return result;
                            }
                            break;
                        }
                    }
                    reader.next();

                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public static void getNodeStAX(XMLStreamReader reader, String elementName, String namespace, String attrName, String attrValue) {
        if (reader != null) {
            try {
                while (reader.hasNext()) {
                    int event = reader.getEventType();
                    switch (event) {
                        case XMLStreamConstants.START_ELEMENT: {
                            String tagName = reader.getLocalName();
                            if (tagName.equals(elementName)) {
                                String attr = reader.getAttributeValue(namespace, attrName);
                                if (attrValue.equals(attr)) {
//                                    reader.next();
//                                    String result = reader.getText();
//                                    reader.nextTag();
                                    return;
                                }
                            }
                            break;
                        }
                    }
                    reader.next();
                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(StAXParser.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

//        return null;
    }

    public static void getNodeStAX(XMLStreamReader reader, String elementName) {
        if (reader != null) {
            try {
                while (reader.hasNext()) {
                    int event = reader.getEventType();
                    switch (event) {
                        case XMLStreamConstants.START_ELEMENT: {
                            String tagName = reader.getLocalName();
                            if (tagName.equals(elementName)) {
                                return;
                            }
                            break;
                        }
                    }
                    reader.next();
                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(StAXParser.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

//        return null;
    }

    public static String getNodeStAXText(XMLStreamReader reader, String elementName, String namespace, String attrName, String attrValue) {
        if (reader != null) {
            try {
                while (reader.hasNext()) {
                    int event = reader.getEventType();
                    switch (event) {
                        case XMLStreamConstants.START_ELEMENT: {
                            String tagName = reader.getLocalName();
                            if (tagName.equals(elementName)) {
                                String attr = reader.getAttributeValue(namespace, attrName);
                                if (attrValue.equals(attr)) {
                                    reader.next();
                                    String result = reader.getText();
                                    reader.nextTag();
                                    return result;
                                }
                            }
                            break;
                        }
                    }
                    reader.next();
                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(StAXParser.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static XMLStreamReader parseFileToStAXCursor(String xmlFilePath) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        //---------------Yeu cau bo parser bo qua loi well-forrm khi parse tu website
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        factory.setProperty(XMLInputFactory.IS_VALIDATING, false);

        StreamSource source = new StreamSource(xmlFilePath);
        XMLStreamReader reader = factory.createXMLStreamReader(source);
        return reader;
    }

    public static XMLStreamReader parseStringToStAXCursor(String content) throws XMLStreamException {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
//---------------Yeu cau bo parser bo qua loi well-forrm khi parse tu website

            factory.setProperty(XMLInputFactory.IS_COALESCING, true);
            factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
            factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
            byte[] byteArray = content.getBytes("UTF-8");
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
            XMLStreamReader reader = factory.createXMLStreamReader(inputStream, "UTF-8");
            return reader;

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(StAXParser.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static XPath getXPath() {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        return xpath;
    }

}
