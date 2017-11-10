/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import CromObject.Motorbike;
import CromObject.News;
import CromObject.TypeOfMotorbike;
import DAO.DAO;
import Parser.StAXParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.SAXException;

/**
 *
 * @author tudt
 */
public class WebParser {

    private static String HONDA_PAGE = "https://hondaxemay.com.vn";
    private static String YAMAHA_PAGE = "http://yamaha-motor.com.vn";
    private static String SUZUKI_PAGE = "https://www.suzuki.com.vn";
    private static String XML_TOP = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

//  not complete
    private static String fixOpenTag(String content, String tagName) {
        int index = content.indexOf("<" + tagName);
        int endTag = content.indexOf("</" + tagName + ">", index);
        if (endTag < -1) {

        } else {

        }
        return content;
    }

    /**
     * @param fullTagName Show full name of the open tag which you want to
     * search. for example
     *
     */
    public static String extractContent(String page, String fullTagName) {
        String result = null;
        int startDiv = page.indexOf(fullTagName);
        String tagName = fullTagName.trim().substring(1,
                fullTagName.indexOf(' ') > -1 ? fullTagName.indexOf(' ')
                : fullTagName.indexOf('>')).trim();
//        //System.out.println(tagName);
        if (startDiv != -1) {
//            page = page.substring(startDiv, page.length() - 1);
            int index = startDiv + fullTagName.length();
            int startTag = page.indexOf("<" + tagName, index);
            int endTag = page.indexOf("</" + tagName + ">", index);
            while (startTag < endTag && endTag > -1 && startTag > -1) {
                endTag = page.indexOf("</" + tagName + ">", endTag + 3 + tagName.length());
                startTag = page.indexOf("<" + tagName, page.indexOf('>', startTag) + 1);
            }
            if (endTag <= -1) {
                return null;
            }
            result = page.substring(startDiv, endTag + 3 + tagName.length());
            //System.out.println("Extracting content");
            return result;
        }
        return result;
    }

    public static String downloadImage(String url, String filePath, String imageName) throws MalformedURLException, IOException {
        File dir = new File(filePath);
//        if (dir.exists()) {
//            dir.delete();
//        }
        dir.mkdirs();
        File img = new File(filePath + "\\" + imageName);
        String newName = null;
        while (img.exists()) {
            newName = imageName.substring(0, imageName.indexOf('.')) + "_(" + Math.round(Math.random() * 100) + ")" + imageName.substring(imageName.indexOf('.'), imageName.length());
            img = new File(filePath + "\\" + newName);
        }
        if (newName != null) {
            imageName = newName;
        }
        Files.copy(new URL(url).openStream(), Paths.get(filePath + "/" + imageName));
        return imageName;
    }

    public static void parseNewsHondaPage() throws IOException, URISyntaxException, XMLStreamException {
        for (int i = 1; i < 6; i++) {
            String xmlPage = XML_TOP;
            String newsPage = accessWebsite(HONDA_PAGE + "/tin-tuc/page/" + i + "/");
            String extractNewsPage = extractContent(newsPage, "<div class=\"row-list\">");
            System.out.println("Parsing page" + i);
//            System.out.println(extractNewsPage);
            if (extractNewsPage != null) {
                xmlPage += "\n" + extractNewsPage;
                XMLStreamReader reader = StAXParser.parseStringToStAXCursor(xmlPage);
                if (i == 2) {
                    System.out.println(extractNewsPage);
                }
                if (reader != null) {
                    while (reader.hasNext()) {
                        String title = "";
                        String des = "";
                        String previewImage = "";
                        String content = "";
                        String date = "";
                        StAXParser.getNodeStAX(reader, "div", "", "class", "news-item clearfix");
                        StAXParser.getNodeStAX(reader, "div", "", "class", "inner clearfix");
                        String link = StAXParser.getNodeAttrStAXWithClass(reader, "a", "link", "", "href");
//                        StAXParser.getNodeStAX(reader, "div", "", "class", "inner clearfix");
                        previewImage = StAXParser.getNodeStAXAttr(reader, "img", "", "src");
                        StAXParser.getNodeStAX(reader, "div", "", "class", "desc");
                        title = StAXParser.getNodeStAXText(reader, "div", "", "class", "title");
                        date = StAXParser.getNodeStAXText(reader, "div", "", "class", "date");
//                        date=StAXParser.getNodeStAXText(reader, "div","","class","date");
                        StAXParser.getNodeStAX(reader, "div", "", "class", "short-cont short-cont-desk");
                        des = StAXParser.getNodeStAXText(reader, "p");
                        if (link != null) {
                            try {
                                String contentPage = accessWebsite(link);
                                content = extractContent(contentPage, "<div class=\"editable\">");
                                String imagePath = System.getProperty("user.dir") + "\\web\\NewsImage";
                                String previewImageName = previewImage.substring(previewImage.lastIndexOf('/') + 1, previewImage.length());
                                previewImageName = downloadImage(previewImage, imagePath, previewImageName);
                                News news = new News(title, des, previewImageName, content, date);
                                Utility.validateObjectWithSchema(news, "web/WEB-INF/News.xsd");
                                DAO.insertNewṣ(news);
                            } catch (JAXBException ex) {
                                Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SAXException ex) {
                                Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void parseYamahaPage() {
        try {
            String name = "";
            double length = 0;
            double width = 0;
            double height = 0;
            double fuel = 0;
            String engine = "";
            String horsePower = "";
            String moment = "";
            double weight = 0;
            String brandName = "Yamaha";
            String type = "";
            String fuelProvider = "";
            String xyloCapacity = "";
            String pitong = "";
            String compressor = "";
            String startEngine = "";
            String previewImage = "";
//            /-------------------------/

            String xmlHomePage = accessWebsite(YAMAHA_PAGE + "/xe");
            String extractedPage = extractContent(xmlHomePage, "<ul class=\"clearfix filterList\">");
            if (extractedPage != null) {
                xmlHomePage = XML_TOP + "\n" + extractedPage;
                XMLStreamReader reader = StAXParser.parseStringToStAXCursor(xmlHomePage);
                boolean skipNext = false;
                while (reader.hasNext()) {
                    if (!skipNext) {
                        reader.next();
                    } else {
                        skipNext = false;
                    }
//                    StAXParser.getNodeStAXText(reader, "a");
                    int eventType = reader.getEventType();
                    switch (eventType) {
                        case XMLStreamConstants.START_ELEMENT: {
                            String tagName = reader.getLocalName();
                            System.out.println(tagName);
                            if (tagName.equals("a")) {
                                String attr = reader.getAttributeValue("", "href");
                                type = StAXParser.getNodeStAXText(reader, "a");
                                skipNext = true;
                                System.out.println("Type:" + type);
                                if (type != null && !type.equals("TẤT CẢ")) {
                                    String xmlListPage = accessWebsite(attr);
                                    String extractedListPage = extractContent(xmlListPage, "<div class=\"filtr-container\">");
                                    xmlListPage = XML_TOP + "\n" + extractedListPage;
                                    XMLStreamReader lReader = StAXParser.parseStringToStAXCursor(xmlListPage);
                                    String imagePath1 = System.getProperty("user.dir") + "\\web\\ReviewImages";
                                    String imagePath2 = System.getProperty("user.dir") + "\\web\\DetailImage";
                                    while (lReader.hasNext()) {
                                        int lEventType = lReader.getEventType();
                                        switch (lEventType) {
                                            case XMLStreamConstants.START_ELEMENT: {
                                                String lTagName = lReader.getLocalName();
                                                if (lTagName.equals("li")) {
                                                    String color = "";
                                                    String status = "In Store";
                                                    BigDecimal price = null;
                                                    String picture = "";
                                                    String srcPicture = StAXParser.getNodeStAXAttr(lReader, "img", "", "src");
                                                    previewImage = srcPicture.substring(srcPicture.lastIndexOf('/') + 1, srcPicture.length());
//                                                    downloadImage(srcPicture, imagePath1, previewImage);
                                                    picture = previewImage;
//                                                    picture = downloadImage(color, imagePath2, picture);
                                                    System.out.println("picture:"+picture);
                                                    StAXParser.getNodeStAX(lReader, "h3", "", "class", "name");
                                                    StAXParser.getNodeStAX(lReader, "a");
                                                    String link = lReader.getAttributeValue("", "href");
                                                    name = StAXParser.getNodeStAXText(lReader, "a");
                                                    System.out.println("Name:" + name+"Link:"+link);
                                                    
                                                }
                                                break;
                                            }
                                        }
                                        lReader.next();
                                    }

                                }
                            }
                            break;
                        }
                    }

                }
            }
        } catch (NumberFormatException ex) {
            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void parseHondaPage() {
        try {
            parseNewsHondaPage();
            String originPage = accessWebsite(HONDA_PAGE);
            String xmlPage = XML_TOP;
            String extractedPage = extractContent(originPage, "<div class=\"menu-sanpham\">");
//            //System.out.println(extractedPage)
            if (extractedPage != null) {
                xmlPage += "\n" + extractedPage;
//                //System.out.println(xmlPage);
                XMLStreamReader reader = StAXParser.parseStringToStAXCursor(xmlPage);
                if (reader != null) {
                    //---------------------------Variable declaration---------------------------------

                    boolean dSkipNext = false;
                    boolean bContent = false;
                    boolean bGetContent = false;
                    String name = "";
                    double length = 0;
                    double width = 0;
                    double height = 0;
                    double fuel = 0;
                    String engine = "";
                    String horsePower = "";
                    String moment = "";
                    double weight = 0;
                    String brandName = "Honda";
                    String type = "";
                    String fuelProvider = "";
                    String xyloCapacity = "";
                    String pitong = "";
                    String compressor = "";
                    String startEngine = "";
                    String previewImage = "";
                    boolean skipNext = false;
                    while (reader.hasNext()) {
                        if (skipNext) {
                            skipNext = false;
                        } else {
                            reader.next();
                        }
                        int eventType = reader.getEventType();
                        switch (eventType) {
                            case XMLStreamConstants.START_ELEMENT: {
                                String tagName = reader.getLocalName();
                                if (tagName.equals("div")) {
                                    String sClass = StAXParser.getNodeStAXAttr(reader, "div", "", "class");
//                                    //System.out.println(sClass);
                                    if (sClass.equals("item-wrp")) {
                                        type = StAXParser.getNodeStAXText(reader, "p", "", "class", "type");
//                                        //System.out.println("----------" + type + "----------");
                                        while (reader.hasNext()) {
                                            reader.next();
                                            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
                                                tagName = reader.getLocalName();
                                                if (tagName.equals("div")) {
                                                    sClass = StAXParser.getNodeStAXAttr(reader, "div", "", "class");
                                                    if (sClass.equals("item")) {
                                                        String srcItem = StAXParser.getNodeStAXAttr(reader, "a", "", "href");
                                                        String srcPreviewImage = StAXParser.getNodeStAXAttr(reader, "img", "", "src");
                                                        // Name of motorbike
                                                        name = StAXParser.getNodeStAXText(reader, "p", "", "class", "name");
                                                        //---------------------//
                                                        previewImage = srcPreviewImage.substring(srcPreviewImage.lastIndexOf('/') + 1, srcPreviewImage.length());
                                                        String imagePath = System.getProperty("user.dir") + "\\web\\ReviewImages";

                                                        //---------------------//
//                                                        //System.out.println(imageName);
                                                        previewImage = downloadImage(srcPreviewImage, imagePath, previewImage);
//                                                        System.out.println(name+"_"+srcPreviewImage);
                                                        //Detail Page
                                                        String dXmlPage = accessWebsite(srcItem);
                                                        try {
                                                            //---------------------------Get Page Content-----------------------------------
//                                                            String srcItem = "https://hondaxemay.com.vn/san-pham/vision-110cc/";
//                                                            String dXmlPage = accessWebsite(srcItem);
//            //System.out.println(dXmlPage);
                                                            String dDetailTag = "<div class=\"scrollable-section active-section\" data-section-title=\""
                                                                    + "\" id=\"scrollto-section-3\">";
                                                            String dExtractedPage = XML_TOP + "\n" + extractContent(dXmlPage, "<div class=\"product-detail\">");
//            //System.out.println(dExtractedPage);
                                                            XMLStreamReader dReader = StAXParser.parseStringToStAXCursor(dExtractedPage);
//                                                            XMLStreamReader dReader = StAXParser.parseFileToStAXCursor(System.getProperty("user.dir") + "/src/java/Parser/test.xml");

                                                            //-----------------------------Start While----------------------------------------
                                                            while (dReader.hasNext()) {
                                                                if (dSkipNext) {
                                                                    dSkipNext = false;
                                                                } else {
                                                                    dReader.next();
                                                                }
                                                                int dEventType = dReader.getEventType();
                                                                switch (dEventType) {
                                                                    case XMLStreamConstants.START_ELEMENT: {
                                                                        String dTagName = dReader.getLocalName();
//                        //System.out.println(dTagName);
//                        //System.out.println("Class:" + dClass + " id:" + dId + " title:" + dSectionTitle);
                                                                        if (dTagName.equals("div")) {
                                                                            String dClass = StAXParser.getNodeStAXAttr(dReader, "div", "", "class");
//                            String dId = StAXParser.getNodeStAXAttr(dReader, "div", "", "id");
//                            String dSectionTitle = StAXParser.getNodeStAXAttr(dReader, "div", "", "data-section-title");
                                                                            //log error 19/10/2017 -> data-section-title="<b></b>"
                                                                            if (dClass != null && dClass.equals("section-content")) {
                                                                                //System.out.println("section content");
                                                                                bContent = true;
                                                                            }
                                                                        }
                                                                        if (bContent && dTagName.equals("h2")) {
                                                                            //System.out.println("h2");
                                                                            String attrTitle = StAXParser.getNodeStAXAttr(dReader, "h2", "", "class");
                                                                            if (attrTitle.equals("title")) {
                                                                                String title = StAXParser.getNodeStAXText(dReader, "h2");
//                                //System.out.println(title);
                                                                                //get content sorted by title
                                                                                if (title.equals("Thông số kỹ thuật")) {
                                                                                    //System.out.println("Thong so ky thuat");
                                                                                    bGetContent = true;
//                                                                                    int trCounter = 1;
                                                                                    String infoHeader = "";
                                                                                    int tdCounter = 1;
                                                                                    while (dReader.hasNext() && bGetContent) {//                                        //System.out.println(dTagName);
                                                                                        dReader.next();
                                                                                        dEventType = dReader.getEventType();
                                                                                        switch (dEventType) {
                                                                                            case XMLStreamConstants.START_ELEMENT: {
                                                                                                dTagName = dReader.getLocalName();//                                                //System.out.println(dTagName);
                                                                                                if (dTagName.equals("td")) {
                                                                                                    if (tdCounter == 1) {
                                                                                                        infoHeader = StAXParser.getNodeStAXText(dReader, "b").trim();
//                                                                                                        System.out.println(infoHeader);
                                                                                                    } else if (tdCounter == 2) {
                                                                                                        if (infoHeader.equals("Khối lượng bản thân")) {
                                                                                                            String sWeight = StAXParser.getNodeStAXText(dReader, "td").trim();
                                                                                                            if (name.equals("Blade 110cc")) {
                                                                                                                System.out.println("ss");
                                                                                                            }
//                                                                                                            if(sWeight.contains("Bản vành nan hoa:")){
//                                                                                                                System.out.println("What?");
//                                                                                                            }
                                                                                                            sWeight = sWeight.substring(0, sWeight.lastIndexOf("kg") + 2);

//                                                                                                            System.out.println("sWeight"+sWeight);
                                                                                                            try {
                                                                                                                while (sWeight.lastIndexOf(" ") > -1 || sWeight.matches("\\d+\\s*kg$") == false) {
                                                                                                                    sWeight = sWeight.substring(sWeight.indexOf(" "), sWeight.indexOf("kg") + 2).trim();
                                                                                                                }
                                                                                                                weight = Double.parseDouble(sWeight.substring(0, sWeight.length() - 2));
                                                                                                            } catch (Exception e) {
                                                                                                                System.out.println("sWeight:" + sWeight + "_" + e.getMessage());
                                                                                                            }
                                                                                                            //System.out.println("weight:" + weight);
                                                                                                        } else if (infoHeader.equals("Dài x Rộng x Cao")) {
                                                                                                            String measurement = StAXParser.getNodeStAXText(dReader, "td");
                                                                                                            measurement = measurement.trim().replace(".", "");
                                                                                                            //System.out.println("Measurement:" + measurement);
                                                                                                            try {
                                                                                                                length = Double.parseDouble(measurement.substring(0, measurement.indexOf("mm x")));
                                                                                                                //System.out.println("Length:" + length);
                                                                                                                width = Double.parseDouble(measurement.substring(measurement.indexOf("mm x") + 4, measurement.lastIndexOf("mm x")).trim());
                                                                                                                //System.out.println("Width:" + width);
                                                                                                                height = Double.parseDouble(measurement.substring(measurement.lastIndexOf(" x") + 4, measurement.length() - 3).trim());
                                                                                                                //System.out.println("Height:" + height);
                                                                                                            } catch (Exception e) {
                                                                                                            }
                                                                                                        } else if (infoHeader.equals("Dung tích bình xăng")) {
                                                                                                            String measurement = StAXParser.getNodeStAXText(dReader, "td").replace(",", ".").replace("lít", "").replace(" lít", "").trim();
                                                                                                            //System.out.println("FuelCapacity:" + measurement);

                                                                                                            fuel = Double.parseDouble(measurement);
                                                                                                        } else if (infoHeader.equals("Loại động cơ")) {
                                                                                                            String measurement = StAXParser.getNodeStAXText(dReader, "td").trim();
                                                                                                            engine = measurement;
                                                                                                            //System.out.println("Engine Type:" + measurement);
                                                                                                        } else if (infoHeader.equals("Công suất tối đa")) {
                                                                                                            String measurement = StAXParser.getNodeStAXText(dReader, "td").trim().replace(",", ".");
                                                                                                            horsePower = measurement;
                                                                                                            //System.out.println("Horse Power:" + measurement);

                                                                                                        } else if (infoHeader.equals("Mô-men cực đại")) {
                                                                                                            String measurement = StAXParser.getNodeStAXText(dReader, "td").trim().replace(",", ".");
                                                                                                            moment = measurement;
                                                                                                            //System.out.println("Momentum:" + measurement);

                                                                                                        } else if (infoHeader.equals("Loại truyền động")) {
                                                                                                            String measurement = StAXParser.getNodeStAXText(dReader, "td").trim().replace(",", ".");
                                                                                                            fuelProvider = measurement;
                                                                                                            //System.out.println("Provider:" + measurement);

                                                                                                        } else if (infoHeader.equals("Dung tích xy-lanh")) {
                                                                                                            String measurement = StAXParser.getNodeStAXText(dReader, "td").trim().replace(",", ".");
//                                                                measurement = measurement.substring(0, measurement.length() - 3);
                                                                                                            xyloCapacity = measurement;
                                                                                                            //System.out.println("Xylo Capacity:" + measurement);
                                                                                                        } else if (infoHeader.equals("Đường kính x hành trình pít-tông")) {
                                                                                                            String measurement = StAXParser.getNodeStAXText(dReader, "td").trim().replace(",", ".");
//                                                                measurement = measurement.substring(0, measurement.length() - 3);
                                                                                                            pitong = measurement;
                                                                                                            //System.out.println("Pitong:" + measurement);
                                                                                                        } else if (infoHeader.equals("Tỉ số nén")) {
                                                                                                            String measurement = StAXParser.getNodeStAXText(dReader, "td").trim().replace(",", ".");
//                                                                measurement = measurement.substring(0, measurement.length() - 3);
                                                                                                            compressor = measurement;
                                                                                                            //System.out.println("compressor:" + measurement);                                                                                                         
                                                                                                        } else if (infoHeader.equals("Hệ thống khởi động")) {
                                                                                                            String measurement = StAXParser.getNodeStAXText(dReader, "td").trim().replace(",", ".");
//                                                                measurement = measurement.substring(0, measurement.length() - 3);
                                                                                                            startEngine = measurement;
                                                                                                            //System.out.println("Start Engine:" + measurement);                                                                                                              
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                                break;
                                                                                            }
                                                                                            case XMLStreamConstants.END_ELEMENT: {
                                                                                                dTagName = dReader.getLocalName();
                                                                                                if (dTagName.equals("tr")) {
//                                                                                                    trCounter++;
                                                                                                    tdCounter = 1;
                                                                                                }
                                                                                                if (dTagName.equals("td")) {
                                                                                                    tdCounter++;
                                                                                                }
                                                                                                if (dTagName.equals("table")) {
                                                                                                    bGetContent = false;
                                                                                                }
                                                                                                break;
                                                                                            }
                                                                                        }
                                                                                    }//end loop get Content
                                                                                }//end if detail information
                                                                                else if (title.equals("Bảng giá & màu sắc")) {
                                                                                    TypeOfMotorbike motorType = new TypeOfMotorbike(-1, name, length, width, height, fuel, engine, horsePower, moment, weight, brandName, type, fuelProvider, xyloCapacity, pitong, compressor, startEngine, previewImage);
//                                                                                    Utility.marshallObjectToXML(motorType, System.getProperty("user.dir"), "motorType");
                                                                                    try {
                                                                                        Boolean validate = Utility.validateObjectWithSchema(motorType, "web/WEB-INF/TypeOfMotorbike.xsd");
                                                                                        if (validate) {
                                                                                            int id = DAO.insertMotorbikeType(motorType);
                                                                                            System.out.println("Type added :" + name);
                                                                                            //System.out.println("inserted id:" + id);
                                                                                            motorType.setId(id);
                                                                                        } else {
                                                                                            return;
                                                                                        }
                                                                                    } catch (JAXBException ex) {
                                                                                        Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                                                                                    } catch (SAXException ex) {
                                                                                        Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                                                                                    } catch (SQLException ex) {
                                                                                        Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                                                                                    }

                                                                                    //System.out.println("interesting");
                                                                                    bGetContent = true;
//                                    downloadImage(srcPreviewImage, imagePath, previewImage);
                                                                                    String color = "";
                                                                                    String status = "In Store";
                                                                                    BigDecimal price = null;
                                                                                    String srcPicture = "";
                                                                                    boolean bColorList = false;
//                                                                                    if (motorType.getType().equals("XE SỐ")) {
//                                                                                        StAXParser.getNodeStAX(dReader, "div", "", "class", "table-version");
//                                                                                        StAXParser.getNodeStAXText(dReader, "td");
//                                                                                        sPrice = StAXParser.getNodeStAXText(dReader, "td");
//                                                                                        System.out.println("sPrice:" + sPrice);
//                                                                                        sPrice = sPrice.trim().replace("VNĐ", "").replace(".", "").trim();
//                                                                                        price = new BigDecimal(sPrice);
//                                                                                    } else {
//                                                                                        StAXParser.getNodeStAXText(dReader, "div", "", "class", "wrap-360");
//                                                                                        StAXParser.getNodeStAXText(dReader, "div", "", "class", "price");
//                                                                                        //set default price 
//                                                                                        sPrice = StAXParser.getNodeStAXText(dReader, "div", "", "class", "text");
//                                                                                        if (sPrice != null && !sPrice.isEmpty()) {
//                                                                                            sPrice = sPrice.trim().replace("VNĐ", "").replace(".", "").trim();
//                                                                                            price = new BigDecimal(sPrice);
//                                                                                        }
//                                                                                    }
                                                                                    StAXParser.getNodeStAXText(dReader, "td", "", "class", "choose-color");

                                                                                    while (bGetContent && dReader.hasNext()) {
                                                                                        dEventType = dReader.getEventType();
                                                                                        switch (dEventType) {
                                                                                            case XMLStreamConstants.START_ELEMENT: {
                                                                                                dTagName = dReader.getLocalName();
                                                                                                if (dTagName.equals("a")) {
                                                                                                    color = StAXParser.getNodeStAXText(dReader, "div", "", "class", "text");

                                                                                                }
                                                                                                if (dTagName.equals("div")) {
                                                                                                    String dClassName = StAXParser.getNodeStAXAttr(dReader, "div", "", "class");
                                                                                                    if (dClassName != null && dClassName.equals("no-360")) {
                                                                                                        srcPicture = StAXParser.getNodeStAXAttr(dReader, "img", "", "src");
                                                                                                    } else if (motorType.getType().equals("XE SỐ")) {
                                                                                                        if (dClassName != null && (dClassName.equals("table-version") || dClassName.equals("data-version"))) {
                                                                                                            StAXParser.getNodeStAXText(dReader, "td");
                                                                                                            String sPrice = StAXParser.getNodeStAXText(dReader, "td");
                                                                                                            if (sPrice != null && !sPrice.trim().isEmpty()) {
                                                                                                                sPrice = sPrice.replace("VNĐ", "").replace(".", "").trim();
                                                                                                                price = new BigDecimal(sPrice);
                                                                                                                String detailImagePath = System.getProperty("user.dir") + "\\web\\DetailImages";
                                                                                                                String picture = srcPicture.substring(srcPicture.lastIndexOf('/') + 1, srcPicture.length());
                                                                                                                picture = downloadImage(srcPicture, detailImagePath, picture);
                                                                                                                Motorbike motor = new Motorbike(-1, motorType, status, price, color, picture);
                                                                                                                try {
                                                                                                                    Utility.validateObjectWithSchema(motor, "web/WEB-INF/Motorbike.xsd");
                                                                                                                    int id = DAO.insertMotorbike(motor);
                                                                                                                    motor.setId(id);
                                                                                                                    System.out.println("Color added: " + color);
//                                                        //System.out.println(valid);
                                                                                                                } catch (JAXBException ex) {
                                                                                                                    Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                                                                                                                } catch (SAXException ex) {
                                                                                                                    Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                                                                                                                } catch (SQLException ex) {
                                                                                                                    Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    } else if (dClassName != null && dClassName.equals("price")) {
                                                                                                        price = new BigDecimal(StAXParser.getNodeStAXText(dReader, "div", "", "class", "text").replace(".", "").replace("VNĐ", "").trim());//                                                                                                      
                                                                                                        String detailImagePath = System.getProperty("user.dir") + "\\web\\DetailImages";
                                                                                                        String picture = srcPicture.substring(srcPicture.lastIndexOf('/') + 1, srcPicture.length());
                                                                                                        picture = downloadImage(srcPicture, detailImagePath, picture);
                                                                                                        Motorbike motor = new Motorbike(-1, motorType, status, price, color, picture);
                                                                                                        try {
                                                                                                            Utility.validateObjectWithSchema(motor, "web/WEB-INF/Motorbike.xsd");
                                                                                                            int id = DAO.insertMotorbike(motor);
                                                                                                            motor.setId(id);
                                                                                                            System.out.println("Color added: " + color);
//                                                        //System.out.println(valid);
                                                                                                        } catch (JAXBException ex) {
                                                                                                            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                                                                                                        } catch (SAXException ex) {
                                                                                                            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                                                                                                        } catch (SQLException ex) {
                                                                                                            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                                break;
                                                                                            }
                                                                                            case XMLStreamConstants.END_ELEMENT: {
                                                                                                dTagName = dReader.getLocalName();
                                                                                                if (dTagName.equals("td")) {
                                                                                                    bGetContent = false;
                                                                                                }

                                                                                                if (dTagName.equals("a")) {
//                                                                                                    System.out.println("end A");
                                                                                                }
                                                                                                break;
                                                                                            }
                                                                                        }
                                                                                        dReader.next();
                                                                                    }
                                                                                }//end if price and image
                                                                            }
                                                                            bContent = false;
                                                                        }
                                                                        break;
                                                                    }
                                                                }//end dReader
                                                            }
                                                            dReader.close();
                                                        } catch (IOException ex) {
                                                            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                                                        } catch (XMLStreamException ex) {
                                                            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
                                                        }
                                                    } else {
                                                        skipNext = true;
                                                        break;
                                                    }
                                                }
                                            }
//                                            reader.next();
                                        }

                                    }
                                }//end if div
                                break;
                            }//end case Start Element
                            }
                    }//end while
                    reader.close();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String accessWebsite(String urlPath) throws IOException, URISyntaxException {
        URI uri = new URI(urlPath);
        URL url = new URL(urlPath);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String content = "";
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                if (line.contains("<img")) {
                    int startTag = line.indexOf("<img");
                    int endTag = line.indexOf(">", startTag);
                    if (endTag > -1) {
                        if (line.charAt(endTag - 1) != '/') {
                            line = line.substring(0, endTag) + '/' + line.substring(endTag, line.length());
                        }
                    }
                }
                while (line.contains("\"<")) {
                    int startError = line.indexOf("\"<");
                    if (line.charAt(startError + 2) != '/') {
                        int endError = line.indexOf(">\"");
                        line = line.substring(0, startError + 1) + line.substring(endError + 1, line.length());
                    } else {
                        break;
                    }
                }
                content += line.trim() + "\n";
            }
        }
        content = content.replaceAll("&igrave;", "ì");
        content = content.replaceAll("&iacute;", "í");
        content = content.replaceAll("&ograve;", "ò");
        content = content.replaceAll("&oacute;", "ó");
        content = content.replaceAll("&ocric;", "ô");
        content = content.replaceAll("&ugrave;", "ù");
        content = content.replaceAll("&uacute;", "ú");
        content = content.replaceAll("&acric;", "â");
        content = content.replaceAll("&agrave;", "à");
        content = content.replaceAll("&aacute;", "á");
        content = content.replaceAll("&ecric;", "ê");
        content = content.replaceAll("&egrave;", "è");
        content = content.replaceAll("&eacute;", "é");
        content = content.replaceAll("&", "&amp;");
        content = content.replaceAll("<br>", "<br/>");
        content = content.replaceAll(" selected", " selecte=\"selected\"");
        br.close();
        //System.out.println("Getting content");
        return content.trim();
    }

    public static void main(String[] args) {
        WebParser.parseHondaPage();
    }
}
