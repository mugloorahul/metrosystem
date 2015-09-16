package com.metrosystem.ws;

import javax.jws.WebService;

@WebService(endpointInterface="com.metrosystem.ws.HelloWorld"
,serviceName="HelloWorldService")
public class HelloWorldImpl implements HelloWorld {

	@Override
	public User getHelloWorldAsString(User user) {
		
		return user;
	}

}
