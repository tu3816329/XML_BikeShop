/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author tudt
 */
public class Utility implements Serializable {

    private static class MyErrorHandler implements ErrorHandler {

        String error = null;

        public void error(SAXParseException arg0) throws SAXException {
            System.out.println("ERROR");
            error = arg0.getMessage();
            arg0.printStackTrace(System.out);
        }

        public void fatalError(SAXParseException arg0) throws SAXException {
            System.out.println("FATAL ERROR");
            error = arg0.getMessage();
            arg0.printStackTrace(System.out);
        }

        public void warning(SAXParseException arg0) throws SAXException {
            System.out.println("WARNING ERROR");
            error = arg0.getMessage();
            arg0.printStackTrace(System.out);
        }

    }

    private static void generateJAXBObject(String xsdFilePath,String output) throws IOException {
        SchemaCompiler sc=XJC.createSchemaCompiler();
        sc.setErrorListener(new ErrorListener() {
            @Override
            public void error(SAXParseException saxpe) {
               saxpe.printStackTrace();
            }

            @Override
            public void fatalError(SAXParseException saxpe) {
               saxpe.printStackTrace();
            }

            @Override
            public void warning(SAXParseException saxpe) {
           saxpe.printStackTrace();
            }

            @Override
            public void info(SAXParseException saxpe) {
                saxpe.printStackTrace();
            }
        });
        sc.forcePackageName("CromObject");
        File schema=new File(xsdFilePath);
        InputSource is=new InputSource(schema.toURI().toString());
        sc.parseSchema(is);
        S2JJAXBModel model=sc.bind();
        JCodeModel code=model.generateCode(null, null);
        code.build(new File(output));        
    }

    public static String marshalObjectToString(Object obj) throws JAXBException {
        String result = null;
        JAXBContext jaxb = JAXBContext.newInstance(obj.getClass());
        Marshaller mars = jaxb.createMarshaller();
        mars.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        mars.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter writer = new StringWriter();
        try {
            mars.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean marshallObjectToXML(Object obj, String filePath, String fileName) {
        try {
            JAXBContext jaxb = JAXBContext.newInstance(obj.getClass());
            Marshaller mars = jaxb.createMarshaller();
            mars.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            mars.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mars.marshal(obj, new File(filePath + "/" + fileName + ".xml"));
            return true;
        } catch (JAXBException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean validateObjectWithSchema(Object obj, String schemaLocation) throws JAXBException, SAXException, IOException {
        JAXBContext jContext = JAXBContext.newInstance(obj.getClass());
        JAXBSource source = new JAXBSource(jContext, obj);
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(schemaLocation));
        Validator validator = schema.newValidator();
        MyErrorHandler errorHandler = new MyErrorHandler();
        validator.setErrorHandler(errorHandler);
        validator.validate(source);
        if (errorHandler.error != null) {
            return false;
        }
        return true;

    }

}
