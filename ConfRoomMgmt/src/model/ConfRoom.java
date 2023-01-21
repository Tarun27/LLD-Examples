package model;

import java.util.List;

public class ConfRoom {

	public ConfRoom(String roomName) {
		this.roomName=roomName;
	}
	
	public String roomName;		
	public List<Booking> bookings;
	
}
