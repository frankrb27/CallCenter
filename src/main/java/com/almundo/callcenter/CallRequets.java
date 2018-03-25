package com.almundo.callcenter;

import org.apache.log4j.Logger;

import com.almundo.dispatcher.impl.DispatcherImpl;
import com.almundo.model.Call;

/**
 * @author Frank Rodriguez
 */
public class CallRequets extends CallCenter implements Runnable {

	//Logger
	private static final Logger logger = Logger.getLogger(DispatcherImpl.class);
	private static String CLASS_ = "[CallRequets]";
	//
	private Call call;

	/**
	 * Constructor
	 * @param Call call: Incoming call
	 */
	public CallRequets(Call call) {
		this.call = call;
	}

	/**
	 * @return the call
	 */
	public Call getCall() {
		return call;
	}

	/**
	 * Assign call
	 */
	public void run() {
		String METHOD_ = "[run]";	
		logger.info(CLASS_+METHOD_+"[Execute run]");
		dispatcher.dispatchCall(call);
		logger.info(CLASS_+METHOD_+"[Finished]");
	}
}
