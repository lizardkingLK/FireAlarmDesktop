package location;

public class Location {
	private String lid,floorNo,roomNo;

	public Location() {
		super();
	}

	public Location(String lid, String floorNo, String roomNo) {
		super();
		this.lid = lid;
		this.floorNo = floorNo;
		this.roomNo = roomNo;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	@Override
	public String toString() {
		return "Location [lid=" + lid + ", floorNo=" + floorNo + ", roomNo=" + roomNo + "]";
	}
}
