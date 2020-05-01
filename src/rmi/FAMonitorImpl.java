package rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import alarm.Alarm;

public class FAMonitorImpl extends UnicastRemoteObject implements FAListener,Serializable,Runnable {
	private static final long serialVersionUID = 1L;
	volatile List<Alarm> alarmsList = new ArrayList<>();

	protected FAMonitorImpl() throws RemoteException {
		super();
	}

	@Override
	public void alarmsChanged(String alarms) throws RemoteException, JSONException {
		JSONArray alarmsJsArr = new JSONArray(alarms);
		if(!alarmsList.isEmpty())
			alarmsList.clear();

		for (int i=0;i<alarmsJsArr.length();i++) {
			JSONObject o = alarmsJsArr.getJSONObject(i);
			Alarm a = new Alarm();

			a.setAid(o.getString("aid"));
			a.setCo2Level(o.getInt("co2Level"));
			a.setEmail(o.getString("email"));
			a.setIsActive(o.getInt("isActive"));
			a.setIsWorking(o.getInt("isWorking"));
			a.setLid(o.getString("lid"));
			a.setSmokeLevel(o.getInt("smokeLevel"));

			alarmsList.add(a);
		}
	}

	@Override
	public void run() {
		for(;;) {
			try {
				Thread.sleep(10000);
			}
			catch (InterruptedException ie) {
				System.out.println(ie);
			}
		}
	}

	@Override
	public String getUpdatedAlarms() throws RemoteException, JSONException {
		return new JSONArray(alarmsList).toString();
	}
}
