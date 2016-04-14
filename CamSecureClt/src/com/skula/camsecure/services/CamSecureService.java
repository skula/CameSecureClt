package com.skula.camsecure.services;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import com.skula.camsecure.models.Snapshot;

public class CamSecureService {
	private static final String NAMESPACE = "http://ws.camsecure.skula.com/";
	//private static final String SERVICE_URL = "http://192.168.1.22:8080/CamSecure/ws?WSDL";
	private static final String SERVICE_URL = "http://31.34.50.65:8080/CamSecure/ws?WSDL";
	private static final String METHODE_SNAPSHOT = "takeSnapshot";
	private static final String SOAP_ACTION_SNAPSHOT = "http://ws.camsecure.skula.com/takeSnapshot";

	public static String sendCommand(Snapshot snapshot) throws Exception {
		SoapObject request = new SoapObject(NAMESPACE, METHODE_SNAPSHOT);
		PropertyInfo p1 = new PropertyInfo();
		p1.setName("snapshot");
		p1.setNamespace(NAMESPACE);
		p1.setValue(snapshot);
		p1.setType(snapshot.getClass());

		request.addProperty(p1);
		SoapSerializationEnvelope envelope = SoapUtils.getEnvelope(request);
		SoapObject response = SoapUtils.getResponse(SOAP_ACTION_SNAPSHOT, SERVICE_URL, envelope);

		return response.getProperty(0).toString();
	}
}