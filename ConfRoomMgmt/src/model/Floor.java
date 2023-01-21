package model;

import java.util.List;

public class Floor {
	
	public Floor(String bname, String floorName) {
		this.bname=bname;
		this.floorName = floorName;
	}
	
	public String bname;
	public String floorName;
	public List<ConfRoom> confRoomList;

}
