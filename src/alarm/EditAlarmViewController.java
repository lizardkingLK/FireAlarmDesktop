package alarm;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import animatefx.animation.Pulse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import location.Location;
import rmi.FASensorClient;
import user.User;

public class EditAlarmViewController implements Initializable {
	Map<String,Alarm> alarmsmap = new HashMap<>();

	@FXML
    public ComboBox<String> fxAlarmId;
    public ComboBox<Integer> fxSmokeLevel;
    public ComboBox<Integer> fxCo2Level;
    public ComboBox<String> fxLocation;
    public ComboBox<Integer> fxWorkingState;
    public Label fxHandledBy;
    public Label fxStatus;
    public Button fxEdit;

    @FXML
    public void editAlarm(ActionEvent event) throws RemoteException, IOException {
    	String aid = fxAlarmId.getValue();

    	if(aid != null) {
	    	int smoke = fxSmokeLevel.getValue();
	    	int co2 = fxCo2Level.getValue();
	    	String lid = fxLocation.getValue().trim().replaceFirst(" ", "");
	    	int workingState = fxWorkingState.getValue();
	    	String handler = fxHandledBy.getText();
	    	int activeState = (smoke > 5 || co2 > 5) ? 1 : 0;

	    	boolean success = FASensorClient.updateAlarm(aid,smoke,co2,lid,workingState,handler,activeState);

	    	if(success)
	    		fxStatus.setText("Update alarm successful");
	    	else
	    		fxStatus.setText("Alarm update failed");
    	}
    	else
    		fxStatus.setText("Set details first");

    	// animate
    	new Pulse(fxStatus).play();
    }

    @FXML
    public void loadAlarm(ActionEvent event) {
    	String key = fxAlarmId.getValue();

    	Alarm a = alarmsmap.get(key);

    	fxSmokeLevel.setValue(a.getSmokeLevel());
    	fxCo2Level.setValue(a.getCo2Level());
    	fxLocation.setValue(a.getLid().substring(0, 5)+" "+a.getLid().substring(5,10));
    	fxWorkingState.setValue(a.getIsWorking());
    }

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// get alarms id s
		try {
			String alarms = FASensorClient.getInitialAlarms();

			JSONArray alarmsJsArr = new JSONArray(alarms);

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

				alarmsmap.put(a.getAid(), a);
			}

			ObservableList<String> aids = FXCollections.observableArrayList();
			aids.addAll(alarmsmap.keySet());

			fxAlarmId.setItems(aids);
		}
		catch (IOException | JSONException | NotBoundException e) {
			e.printStackTrace();
		}

		// set smoke Levels
		ObservableList<Integer> smokelist = FXCollections.observableArrayList();
		smokelist.addAll(1,2,3,4,5,6,7,8,9,10);
		fxSmokeLevel.setItems(smokelist);

		// set co2 Levels
		ObservableList<Integer> co2list = FXCollections.observableArrayList();
		co2list.addAll(1,2,3,4,5,6,7,8,9,10);
		fxCo2Level.setItems(co2list);

		// set locations in combo box
		ObservableList<String> locations = FXCollections.observableArrayList();

		JSONArray jsonArr;
		try {
			jsonArr = new JSONArray(FASensorClient.getLocations());

			for (int i=0;i<jsonArr.length();i++) {
				JSONObject o = jsonArr.getJSONObject(i);
				Location l = new Location();

				l.setLid(o.getString("lid"));
				l.setFloorNo(o.getString("floorNo"));
				l.setRoomNo(o.getString("roomNo"));

				locations.add(l.getFloorNo()+" "+l.getRoomNo());
			}

	        fxLocation.setItems(locations);
		}
		catch (JSONException | IOException e1) {
			e1.printStackTrace();
		}

		// set working state
		ObservableList<Integer> workingstates = FXCollections.observableArrayList();
		workingstates.addAll(0,1);
		fxWorkingState.setItems(workingstates);

		// set email as handler
		fxHandledBy.setText(User.getInstance().getEmail());
	}
}
