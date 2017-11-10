/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CromObject;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tudt
 */
@XmlType(name = "", propOrder = {
    "list"
})
@XmlRootElement(name = "ListType", namespace = "http://www.thientu.com/MotorbikeType")
public class ListType {

    private List<TypeOfMotorbike> list;

    public ListType() {
        list = new ArrayList<TypeOfMotorbike>();
    }

    public ListType(List<TypeOfMotorbike> list) {
        this.list = list;
    }
    
    @XmlElement(name = "MotorType", namespace = "http://www.thientu.com/MotorbikeType")
    public List<TypeOfMotorbike> getList() {
        return list;
    }

    public void setList(List<TypeOfMotorbike> list) {
        this.list = list;
    }

}
