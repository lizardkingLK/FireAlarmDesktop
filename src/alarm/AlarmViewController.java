package alarm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import image.MyImage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import location.AddLocationUI;
import rmi.FASensorClient;

public class AlarmViewController implements Initializable {
    ObservableList<TableAlarm> alarmslist = FXCollections.observableArrayList();

	@FXML
    public Button fxAddLocation;
    public Button fxAddAlarm;
    public Button fxRefresh;
    public Button fxEditAlarm;
    public Label fxTime;

    @FXML
    public TableView<TableAlarm> fxAlarmsTable;
    public TableColumn<TableAlarm, String> fxAlarmId;
    public TableColumn<TableAlarm, Integer> fxFloorNo;
    public TableColumn<TableAlarm, Integer> fxRoomNo;
    public TableColumn<TableAlarm, Integer> fxSmokeLevel;
    public TableColumn<TableAlarm, Integer> fxCo2Level;
    public TableColumn<TableAlarm, Integer> fxActiveState;
    public TableColumn<TableAlarm, Integer> fxWorkingState;
    public TableColumn<TableAlarm, ImageView> fxSignal;

    @FXML
    public void loadAddLocation(ActionEvent event) throws IOException {
    	AddLocationUI addlocationinstance = AddLocationUI.getInstance();
        Stage addlocationstage = new Stage();
        addlocationinstance.display(addlocationstage);
    }

    @FXML
    public void loadAddAlarm(ActionEvent event) throws IOException, JSONException, NotBoundException {
    	AddAlarmUI addalarminstance = AddAlarmUI.getInstance();
        Stage addalarmstage = new Stage();
        addalarminstance.display(addalarmstage);
    }

    @FXML
    public void loadEditAlarm(ActionEvent event) throws IOException {
    	EditAlarmUI editalarminstance = EditAlarmUI.getInstance();
    	Stage editalarmstage = new Stage();
    	editalarminstance.display(editalarmstage);
    }

    @FXML
    public void refreshTable() throws MalformedURLException, RemoteException, IOException, JSONException, NotBoundException {
    	if(!alarmslist.isEmpty())
    		alarmslist.clear();
    	String alarms = FASensorClient.getUpdatedAlarms();

    	JSONArray alarmsJSONObj = new JSONArray(alarms);
    	alarmslist = deserializeAndGet(alarmsJSONObj);

    	fxAlarmsTable.setItems(alarmslist);
    }

    public void loadAlarmsFirst() throws MalformedURLException, RemoteException, IOException, JSONException, NotBoundException {
    	if(!alarmslist.isEmpty())
    		alarmslist.clear();
		String alarms = FASensorClient.getInitialAlarms();
    	System.out.println(alarms);

    	JSONArray alarmsJSONObj = new JSONArray(alarms);
    	alarmslist = deserializeAndGet(alarmsJSONObj);

    	fxAlarmsTable.setItems(alarmslist);
    }

    public ObservableList<TableAlarm> deserializeAndGet(JSONArray alarmsJsArr) throws JSONException {
    	ObservableList<TableAlarm> oblist = FXCollections.observableArrayList();

    	for (int i=0;i<alarmsJsArr.length();i++) {
			JSONObject o = alarmsJsArr.getJSONObject(i);
			TableAlarm a = new TableAlarm();

			a.setAid(o.getString("aid"));
			a.setCo2Level(o.getInt("co2Level"));
			a.setEmail(o.getString("email"));
			a.setIsActive(o.getInt("isActive"));
			a.setIsWorking(o.getInt("isWorking"));
			a.setLid(o.getString("lid"));
			a.setSmokeLevel(o.getInt("smokeLevel"));
			a.setFloorNo(o.getString("lid"));
			a.setRoomNo(o.getString("lid"));
			a.setSignal(getIcon(o.getInt("isWorking"), o.getInt("smokeLevel"), o.getInt("co2Level")));

			oblist.add(a);
		}

    	fxTime.setText(new Date().toString());

    	return oblist;
    }

    public ImageView getIcon(int isWorking, int smoke, int co2) {
    	if(isWorking == 0)
    		return MyImage.getNW();
    	else {
    		if(smoke > 5 || co2 > 5)
    			return MyImage.getF();
    	}

    	return null;
    }

    public void refreshService() {
    	ScheduledService<Boolean> ssv = new ScheduledService<Boolean>() {
			@Override
			protected Task<Boolean> createTask() {
				return new Task<Boolean>() {
					protected Boolean call() {
						Platform.runLater( () -> {
							try {
								refreshTable();
							}
							catch (IOException | JSONException | NotBoundException e) {
								e.printStackTrace();
							}
						});
						return true;
					}
				};
			}
    	};
    	ssv.setPeriod(Duration.millis(15000));
    	ssv.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	// set table placeholder
    	fxAlarmsTable.setPlaceholder(new Label("Please wait... Connecting..."));

    	try {
			FASensorClient.registerListener();
		}
    	catch (RemoteException e) {
			e.printStackTrace();
		}

    	// set table columns
    	fxAlarmId.setCellValueFactory(new PropertyValueFactory<>("aid"));
    	fxFloorNo.setCellValueFactory(new PropertyValueFactory<>("floorNo"));
    	fxRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
    	fxSmokeLevel.setCellValueFactory(new PropertyValueFactory<>("smokeLevel"));
    	fxCo2Level.setCellValueFactory(new PropertyValueFactory<>("co2Level"));
    	fxActiveState.setCellValueFactory(new PropertyValueFactory<>("isActive"));
    	fxWorkingState.setCellValueFactory(new PropertyValueFactory<>("isWorking"));
    	fxSignal.setCellValueFactory(new PropertyValueFactory<>("signal"));

    	// set time
    	fxTime.setText(new Date().toString());

    	// calls to refresh every 30 seconds
    	refreshService();
    }
}
