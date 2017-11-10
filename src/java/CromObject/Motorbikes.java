/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CromObject;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tudt
 */
@XmlType(name = "", propOrder = {
    "motorbike"
})
@XmlRootElement(name = "Motorbikes", namespace = "http://www.thientu.com/Motorbike")
public class Motorbikes {

    private List<Motorbike> motorbike;

    public Motorbikes() {
    }

    public Motorbikes(List<Motorbike> motorbike) {
        this.motorbike = motorbike;
    }
    

    @XmlElement(name = "Motorbike", namespace = "http://www.thientu.com/Motorbike",required = true)

    public List<Motorbike> getMotorbike() {
        return motorbike;
    }

    public void setMotorbike(List<Motorbike> motorbike) {
        this.motorbike = motorbike;
    }
    
}
