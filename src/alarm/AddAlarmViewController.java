package alarm;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
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
import javafx.scene.control.TextField;
import location.Location;
import rmi.FASensorClient;
import user.User;

public class AddAlarmViewController implements Initializable {
	@FXML
    public TextField fxAlarmId;
    public ComboBox<Integer> fxSmokeLevel;
    public ComboBox<Integer> fxCo2Level;
    public ComboBox<String> fxLocation;
    public TextField fxHandledBy;
    public Label fxStatus;
    public Button fxAdd;

    @FXML
    public void addAlarm(ActionEvent event) throws RemoteException, IOException {
    	String location = fxLocation.getValue();

    	if(location == null)
    		fxStatus.setText("Set values first");
    	else {
    		location = fxLocation.getValue();
        	String aid = fxAlarmId.getText();
        	Integer smoke = fxSmokeLevel.getValue();
        	Integer co2 = fxCo2Level.getValue();
    		String handler = fxHandledBy.getText();
        	String lid = location.trim().replaceFirst(" ", "");
        	int activeState = (smoke > 5 || co2 > 5) ? 1 : 0;

	    	boolean success = FASensorClient.addAlarm(aid,handler,lid,smoke,co2,activeState,1);

	    	if(success)
	    		fxStatus.setText("Add alarm successful");
	    	else
	    		fxStatus.setText("Alarm add failed");
    	}

    	// animate
    	new Pulse(fxStatus).play();
    }

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// get new alarm id
		try {
			String alarm = FASensorClient.getNewAlarmID();
			JSONObject jsonAid = new JSONObject(alarm);
			String aid = jsonAid.get("aid").toString();
			fxAlarmId.setText(aid);
		}
		catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		// set smoke Levels and default
		ObservableList<Integer> smokelist = FXCollections.observableArrayList();
		smokelist.addAll(1,2,3,4,5,6,7,8,9,10);
		fxSmokeLevel.setItems(smokelist);
		fxSmokeLevel.getSelectionModel().selectFirst();

		// set co2 Levels and default
		ObservableList<Integer> co2list = FXCollections.observableArrayList();
		co2list.addAll(1,2,3,4,5,6,7,8,9,10);
		fxCo2Level.setItems(co2list);
		fxCo2Level.getSelectionModel().selectFirst();

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

		// set email as handler
		fxHandledBy.setText(User.getInstance().getEmail());
	}

}
