
package com.metrosystem.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getHelloWorldAsStringResponse", namespace = "http://ws.metrosystem.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getHelloWorldAsStringResponse", namespace = "http://ws.metrosystem.com/")
public class GetHelloWorldAsStringResponse {

    @XmlElement(name = "return", namespace = "")
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
     * @param _return
     *     the value for the _return property
     */
    public void setUser(com.metrosystem.ws.User user) {
        this.user = user;
    }

}
