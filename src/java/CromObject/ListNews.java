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
@XmlRootElement(name = "ListNews", namespace = "http://www.thientu.com/news")
public class ListNews {
    private List<News> list;
      public ListNews() {
        list = new ArrayList<News>();
    }

    public ListNews(List<News> list) {
        this.list = list;
    }
    
    @XmlElement(name = "News", namespace = "http://www.thientu.com/news")
    public List<News> getList() {
        return list;
    }

    public void setList(List<News> list) {
        this.list = list;
    }

}
