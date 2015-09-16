package com.metrosystem.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;

public class HelloWorldClient {

	public static void main(String[] args) throws MalformedURLException, RemoteException, ServiceException{
		/*URL url = new URL("http://localhost:9087/spring-ws-tutorial/HelloWorldService?wsdl");
		QName qname = new QName("http://ws.metrosystem.com/", "HelloWorldService");
		
		Service service = Service.create(url, qname);
		HelloWorld helloWorld = service.getPort(HelloWorld.class);
		User user = new User();
		user.setName("Rahu");
		User user1 = helloWorld.getHelloWorldAsString(user);*/
		//System.out.println(helloWorld.getHelloWorldAsString("Rahul"));
		
		/*System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");*/
		HelloWorldServiceLocator loc = new HelloWorldServiceLocator();
		HelloWorld helloWorld = loc.getHelloWorldImplPort();
		User user = new User();
		user.setName("Rahu");
		User user1 = helloWorld.getHelloWorldAsString(user);
		
	}
}
