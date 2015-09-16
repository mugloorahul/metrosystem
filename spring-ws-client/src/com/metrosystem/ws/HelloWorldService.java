/**
 * HelloWorldService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.metrosystem.ws;

public interface HelloWorldService extends javax.xml.rpc.Service {
    public java.lang.String getHelloWorldImplPortAddress();

    public com.metrosystem.ws.HelloWorld getHelloWorldImplPort() throws javax.xml.rpc.ServiceException;

    public com.metrosystem.ws.HelloWorld getHelloWorldImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
