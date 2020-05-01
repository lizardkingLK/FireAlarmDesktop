package alarm;

import javafx.scene.image.ImageView;

public class TableAlarm extends Alarm {
	private String floorNo,roomNo;
	private ImageView signal;

	public TableAlarm() {
		super();
	}

	public TableAlarm(String aid, String email, String lid, int smokeLevel, int co2Level, int isActive, int isWorking) {
		super(aid, email, lid, smokeLevel, co2Level, isActive, isWorking);
		this.floorNo = lid.substring(0, 5);
		this.roomNo = lid.substring(5, 10);
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String lid) {
		this.floorNo = lid.substring(0, 5);
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String lid) {
		this.roomNo = lid.substring(5, 10);
	}

	public ImageView getSignal() {
		return signal;
	}

	public void setSignal(ImageView signal) {
		this.signal = signal;
	}

}
