package rmi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.json.JSONException;

public class FASensorClient {
	private static FASensorClient faSensorClientInstance = null;
	private FASensorClient() {}
	static FASensor faSensor = null;
	static FAListener faListener = null;

	public static FASensorClient getInstance() {
		if(faSensorClientInstance == null) {
			synchronized (FASensorClient.class) {
				faSensorClientInstance = new FASensorClient();
			}
		}

		return faSensorClientInstance;
	}

	public FASensor setNewClient() throws MalformedURLException, RemoteException, NotBoundException {
		System.setProperty("java.security.policy", "file:allowall.policy");

		Remote remoteSensor = Naming.lookup("//localhost:5500/faSensor");
		FASensor fas = (FASensor) remoteSensor;

		faSensor = fas;

		return faSensor;
	}

	public static void registerListener() throws RemoteException {
		FAListener faLi = new FAMonitorImpl();
		faListener = faLi;
		faSensor.addFAListener(faLi);
	}

	public static String getInitialAlarms() throws MalformedURLException, RemoteException, IOException, JSONException, NotBoundException {
		return faSensor.getAlarms();
	}

	public static boolean addLocation(String f, String r, String lid) throws IOException {
		return faSensor.addLocation(f,r,lid);
	}

	public static FASensor getFaSensor() {
		return faSensor;
	}

	public static FAListener getFaListener() {
		return faListener;
	}

	public static String getLocations() throws IOException, JSONException {
		return faSensor.getLocations();
	}

	public static String getNewAlarmID() throws IOException, JSONException {
		return faSensor.getNewAlarmID();
	}

	public static boolean addAlarm(String aid, String handler, String lid, int smoke, int co2, int activeState, int workingState) throws RemoteException, IOException {
		return faSensor.addAlarm(aid,handler,lid,smoke,co2,activeState,workingState);
	}

	public static String getUpdatedAlarms() throws RemoteException, JSONException {
		return faListener.getUpdatedAlarms();
	}

	public static boolean updateAlarm(String aid, int smoke, int co2, String lid, int workingState, String handler, int activeState) throws RemoteException, IOException {
		return faSensor.updateAlarm(aid,smoke,co2,lid,workingState,handler,activeState);
	}

}

