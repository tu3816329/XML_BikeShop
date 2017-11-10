/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CromObject;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tudt
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "detail",
    "status",
    "price",
    "color",
    "picture"
})
@XmlRootElement(name = "Motorbike", namespace = "http://www.thientu.com/Motorbike")
public class Motorbike implements Serializable {

    private int id;
    private TypeOfMotorbike detail;
    private String status;
    private BigDecimal price;
    private String color;
    private String picture;

    public Motorbike() {
    }

    public Motorbike(int id, TypeOfMotorbike detail,  String status, BigDecimal price,String color, String picture) {
        this.id = id;
        this.detail = detail;
        this.status = status;
        this.price = price;
        this.color = color;
        this.picture = picture;
    }

    public Motorbike(TypeOfMotorbike detail, String status, BigDecimal price, String color, String picture) {
//        this.id = -1;
        this.detail = detail;
        this.status = status;
        this.price = price;
        this.picture = picture;
        this.color = color;
    }

    @XmlElement(name = "Id",namespace = "http://www.thientu.com/Motorbike")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @XmlElement(name = "Detail", type = TypeOfMotorbike.class, required = true,namespace = "http://www.thientu.com/Motorbike")
    public TypeOfMotorbike getDetail() {
        return detail;
    }

    public void setDetail(TypeOfMotorbike detail) {
        this.detail = detail;
    }

    @XmlElement(name = "Color", required = true, namespace = "http://www.thientu.com/Motorbike")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @XmlElement(name = "Status", required = true, namespace = "http://www.thientu.com/Motorbike")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement(name = "Price", required = true, namespace = "http://www.thientu.com/Motorbike")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @XmlElement(name = "Picture", required = true, namespace = "http://www.thientu.com/Motorbike")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
