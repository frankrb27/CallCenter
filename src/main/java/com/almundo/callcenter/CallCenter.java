package com.almundo.callcenter;

import java.util.ResourceBundle;

import com.almundo.dispatcher.Dispatcher;
import com.almundo.dispatcher.impl.DispatcherImpl;

public abstract class CallCenter {
	//Bundle
	static String APP_BUNDLE = "com.almundo.config.application";
	static ResourceBundle appBundle = ResourceBundle.getBundle(APP_BUNDLE);
	//Dispatcher
	static Dispatcher dispatcher = new DispatcherImpl(Integer.parseInt(appBundle.getString("number.operator")),
			Integer.parseInt(appBundle.getString("number.supervisor")),
			Integer.parseInt(appBundle.getString("number.director")));
	
}
