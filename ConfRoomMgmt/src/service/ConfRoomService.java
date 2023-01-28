package service;

import java.util.ArrayList;
import java.util.Objects;

import Repo.ConfRoomRepo;
import constant.Constants;
import model.Booking;
import model.Building;
import model.ConfRoom;
import model.Floor;

public class ConfRoomService {
	
	
	public void addBuilding(String name, ConfRoomRepo  ConfRoomRepo) {
		
		if(buildingExists(name,ConfRoomRepo )) {
			System.out.println( Constants.ADD_BUILDING_FAIL + name);
			return;
		}
		
		Building building = new Building(name);
		System.out.println( Constants.ADD_BUILDING_SUCCESS + building.bName);
		ConfRoomRepo.buildingList.add(building);
	}
	
	public Floor addFloor(String buildingName, String floorName, ConfRoomRepo  ConfRoomRepo) {
			if(!buildingExists(buildingName, ConfRoomRepo)) {
				System.out.print("Building does not exist");
				return null;
			}
			Building building = ConfRoomRepo.buildingList.stream().filter(b->b.bName.equals(buildingName)).findFirst().get();
			if(Objects.isNull(building.floorList)) {
				building.floorList = new ArrayList();
			}
			
			if(floorExists(floorName, building)) {
				System.out.print("Floor with name "+floorName+ " already exists");
				return null;
			}
			Floor floor = new Floor(buildingName, floorName);
			System.out.print("Successfully created floor with name "+ floorName);

			building.floorList.add(floor);
			
			
		return floor;
	}

	public void addConfRoom(String buildingName, String floorName, String roomName,  ConfRoomRepo  ConfRoomRepo) {
		
		if(!buildingExists(buildingName, ConfRoomRepo)) {
			System.out.print("Building does not exist");
			return;
		}
		
		Building building = ConfRoomRepo.buildingList.stream().filter(b->b.bName.equals(buildingName)).findFirst().get();
		
		if(!floorExists(floorName, building)) {
			System.out.print("Floor with name "+floorName+ " already exists");
			return;
		}
		
		Floor floor =  building.floorList.stream().filter(f->f.floorName.equals(floorName)).findFirst().get();
		if(Objects.isNull(floor.confRoomList)) {
			floor.confRoomList = new ArrayList();
     	}
		
		if(confRoomExists(roomName,floor)) {
			System.out.print("Floor with name "+floorName+ " already exists");
			return;
		}
		
		ConfRoom confRoom = new ConfRoom(roomName);
		floor.confRoomList.add(confRoom);
		System.out.print("Successfully created conf room with name "+ roomName);

	}

	public void bookSlot(String slotSt,String slotEnd,String buildingName,String floorName,String roomName, ConfRoomRepo ConfRoomRepo) {
		
		if(!validateSlot(slotSt, slotEnd, buildingName, floorName, roomName, ConfRoomRepo)) {
			return;
		}
		
		Building building = ConfRoomRepo.buildingList.stream().filter(b->b.bName.equals(buildingName)).findFirst().get();
		Floor floor =  building.floorList.stream().filter(f->f.floorName.equals(floorName)).findFirst().get();
		ConfRoom confRoom = floor.confRoomList.stream().filter(c->c.roomName.equals(roomName)).findAny().get();
		if(Objects.isNull(confRoom.bookings)) {
			confRoom.bookings = new ArrayList();
		}
		
		Booking booking = new Booking(slotSt,slotEnd);
		confRoom.bookings.add(booking);
		System.out.println("Successfully booked the slot");
		
	}

	
	public void cancelSlot(String slotSt,String slotEnd,String buildingName,String floorName,String roomName, ConfRoomRepo ConfRoomRepo) {
		
		if(!validateSlot(slotSt, slotEnd, buildingName, floorName, roomName, ConfRoomRepo)) {
			return;
		}
		Building building = ConfRoomRepo.buildingList.stream().filter(b->b.bName.equals(buildingName)).findFirst().get();
		Floor floor =  building.floorList.stream().filter(f->f.floorName.equals(floorName)).findFirst().get();
		ConfRoom confRoom = floor.confRoomList.stream().filter(c->c.roomName.equals(roomName)).findAny().get();
		
		if(Objects.isNull(confRoom.bookings)) {
			System.out.println("no bookings found");
			return;
		}
		
		confRoom.bookings.removeIf(b->(b.start.equals(slotSt) && b.end.equals(slotEnd)));

	}
	
	public void listBooking(String buildingName, String floorName, ConfRoomRepo ConfRoomRepo) {
		Building building = ConfRoomRepo.buildingList.stream().filter(b->b.bName.equals(buildingName)).findFirst().get();
		Floor floor =  building.floorList.stream().filter(f->f.floorName.equals(floorName)).findFirst().get();
		 floor.confRoomList.stream().forEach(r->{
			 r.bookings.forEach(b->System.out.println("room : "+r.roomName+"  booking start: "+b.start+"   booking end: "+b.end+" "));
		 });
	
	}

	public boolean validateSlot(String slotSt,String slotEnd,String buildingName,String floorName,String roomName, ConfRoomRepo ConfRoomRepo) {
		
		if(!buildingExists(buildingName, ConfRoomRepo)) {
			System.out.print("Building does not exist");
			return false;

		}
		
		Building building = ConfRoomRepo.buildingList.stream().filter(b->b.bName.equals(buildingName)).findFirst().get();
		
		if(!floorExists(floorName, building)) {
			System.out.print("Floor with name "+floorName+ " does not exist");
			return false;

		}
		
		Floor floor =  building.floorList.stream().filter(f->f.floorName.equals(floorName)).findFirst().get();
	
		
		if(!confRoomExists(roomName,floor)) {
			System.out.print("conf room with name "+roomName+ " does not exist");
			return false;

		}
		
		try {
		int slStart = Integer.parseInt(slotSt);
		int slEnd = Integer.parseInt(slotEnd);
		if(slStart>slEnd|| slStart<0 || slStart>23) {
			System.out.println("Invalid slot end time");;
			return false;
		}
		
		if(slEnd<slStart|| slEnd>24 || slEnd <1) {
			System.out.println("Invalid slot end time");;
			return false;
		}
		}catch(NumberFormatException e) {
				
			System.out.println("Invalid slot start time or end time");

		}
		
		return true;
	}
	
	public boolean buildingExists(String name, ConfRoomRepo ConfRoomRepo) {
		return (!ConfRoomRepo.buildingList.stream().filter(building->building.bName.equals(name)).findAny().isEmpty());
	}
	
	public boolean floorExists(String fName, Building building) {
		if(Objects.isNull(building.floorList)) {
			return false;
		}

		return (!building.floorList.stream().filter(floor-> floor.floorName.equals(fName)).findAny().isEmpty());
	}
	
	public boolean confRoomExists(String name, Floor floor) {
		
		if(Objects.isNull(floor.confRoomList)) {
			return false;
		}
		return (!floor.confRoomList.stream().filter(room->room.roomName.equals(name)).findAny().isEmpty());
	}

}
