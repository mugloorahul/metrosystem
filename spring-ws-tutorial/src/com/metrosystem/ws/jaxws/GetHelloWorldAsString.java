
package com.metrosystem.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getHelloWorldAsString", namespace = "http://ws.metrosystem.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getHelloWorldAsString", namespace = "http://ws.metrosystem.com/")
public class GetHelloWorldAsString {

    @XmlElement(name = "arg0", namespace = "")
    private com.metrosystem.ws.User user;

    /**
     * 
     * @return
     *     returns User
     */
    public com.metrosystem.ws.User getUser() {
        return this.user;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setUser(com.metrosystem.ws.User user) {
        this.user = user;
        System.out.println("testing");
    }

}
