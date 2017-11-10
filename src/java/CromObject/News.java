/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CromObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tudt
 */
@XmlType(name = "", propOrder = {"id", "title", "description", "previewImage", "content", "date"})
@XmlRootElement(name = "News", namespace = "http://www.thientu.com/news")
public class News {

    private int id;
    private String title;
    private String description;
    private String previewImage;
    private String content;
    private String date;

    public News() {
    }

    public News(int id, String title, String description, String previewImage, String content, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.previewImage = previewImage;
        this.content = content;
        this.date = date;
    }

    public News(String title, String description, String previewImage, String content, String date) {
        this.title = title;
        this.description = description;
        this.previewImage = previewImage;
        this.content = content;
        this.date = date;
    }

    @XmlElement(name = "Id", namespace = "http://www.thientu.com/news")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "Title", namespace = "http://www.thientu.com/news", required = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "Description", namespace = "http://www.thientu.com/news", required = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "PreviewImage", namespace = "http://www.thientu.com/news", required = true)
    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    @XmlElement(name = "Content", namespace = "http://www.thientu.com/news", required = true)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @XmlElement(name = "Date", namespace = "http://www.thientu.com/news", required = true)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
