import java.util.*;

import constant.Constants;
import model.BookRoomRequest;
import model.Booking;
import model.Building;
import model.ConfRoom;
import model.Floor;

public class ConfRoomMgmt {
	
	List<Building> buildingList;

	public void addBuilding(String name, ConfRoomMgmt  confRoomMgmt) {
		
		if(buildingExists(name,confRoomMgmt )) {
			System.out.println( Constants.ADD_BUILDING_FAIL + name);
			return;
		}
		
		Building building = new Building(name);
		System.out.println( Constants.ADD_BUILDING_SUCCESS + building.bName);
		confRoomMgmt.buildingList.add(building);
	}
	
	public Floor addFloor(String buildingName, String floorName, ConfRoomMgmt  confRoomMgmt) {
			if(!buildingExists(buildingName, confRoomMgmt)) {
				System.out.print("Building does not exist");
				return null;
			}
			Building building = confRoomMgmt.buildingList.stream().filter(b->b.bName.equals(buildingName)).findFirst().get();
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

	public void addConfRoom(String buildingName, String floorName, String roomName,  ConfRoomMgmt  confRoomMgmt) {
		
		if(!buildingExists(buildingName, confRoomMgmt)) {
			System.out.print("Building does not exist");
			return;
		}
		
		Building building = confRoomMgmt.buildingList.stream().filter(b->b.bName.equals(buildingName)).findFirst().get();
		
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

	public void bookSlot(String slotSt,String slotEnd,String buildingName,String floorName,String roomName, ConfRoomMgmt confRoomMgmt) {
		
		if(!validateSlot(slotSt, slotEnd, buildingName, floorName, roomName, confRoomMgmt)) {
			return;
		}
		
		Building building = confRoomMgmt.buildingList.stream().filter(b->b.bName.equals(buildingName)).findFirst().get();
		Floor floor =  building.floorList.stream().filter(f->f.floorName.equals(floorName)).findFirst().get();
		ConfRoom confRoom = floor.confRoomList.stream().filter(c->c.roomName.equals(roomName)).findAny().get();
		if(Objects.isNull(confRoom.bookings)) {
			confRoom.bookings = new ArrayList();
		}
		
		Booking booking = new Booking(slotSt,slotEnd);
		confRoom.bookings.add(booking);
		System.out.println("Successfully booked the slot");
		
	}

	
	public void cancelSlot(String slotSt,String slotEnd,String buildingName,String floorName,String roomName, ConfRoomMgmt confRoomMgmt) {
		
		if(!validateSlot(slotSt, slotEnd, buildingName, floorName, roomName, confRoomMgmt)) {
			return;
		}
		Building building = confRoomMgmt.buildingList.stream().filter(b->b.bName.equals(buildingName)).findFirst().get();
		Floor floor =  building.floorList.stream().filter(f->f.floorName.equals(floorName)).findFirst().get();
		ConfRoom confRoom = floor.confRoomList.stream().filter(c->c.roomName.equals(roomName)).findAny().get();
		
		if(Objects.isNull(confRoom.bookings)) {
			System.out.println("no bookings found");
			return;
		}
		
		confRoom.bookings.removeIf(b->(b.start.equals(slotSt) && b.end.equals(slotEnd)));

	}
	
	public void listBooking(String buildingName, String floorName, ConfRoomMgmt confRoomMgmt) {
		Building building = confRoomMgmt.buildingList.stream().filter(b->b.bName.equals(buildingName)).findFirst().get();
		Floor floor =  building.floorList.stream().filter(f->f.floorName.equals(floorName)).findFirst().get();
		 floor.confRoomList.stream().forEach(r->{
			 r.bookings.forEach(b->System.out.println("room : "+r.roomName+"  booking start: "+b.start+"   booking end: "+b.end+" "));
		 });
	
	}

	public boolean validateSlot(String slotSt,String slotEnd,String buildingName,String floorName,String roomName, ConfRoomMgmt confRoomMgmt) {
		
		if(!buildingExists(buildingName, confRoomMgmt)) {
			System.out.print("Building does not exist");
			return false;

		}
		
		Building building = confRoomMgmt.buildingList.stream().filter(b->b.bName.equals(buildingName)).findFirst().get();
		
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
	
	public boolean buildingExists(String name, ConfRoomMgmt confRoomMgmt) {
		return (!confRoomMgmt.buildingList.stream().filter(building->building.bName.equals(name)).findAny().isEmpty());
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


	
	public static void main(String args[]) {
		
		ConfRoomMgmt  confRoomMgmt = new ConfRoomMgmt();
		confRoomMgmt.buildingList = new ArrayList();
	
		
		
		  Scanner scan = new Scanner(System.in);
		  
		  
		  
		    while(true){

				  System.out.println("\n***Conference room reservation system***");
		  		  System.out.println("Enter the Action:  add,book,cancel, list, exit");

		    	String commandArg1 =	scan.nextLine();
		    	
		    	if(commandArg1.equals("add")){
		  		  System.out.println("Enter Type among these:  buliding, floor, confroom");

		  		String   commandArg2 = scan.nextLine();
		           
		            if(commandArg2.equals("building")){
				  		System.out.println("Enter building name");
		                String  buildingName= scan.nextLine();
		                confRoomMgmt.addBuilding(buildingName,confRoomMgmt);
		                // printBuildings();
		            }
		            else if(commandArg2.equals("floor")){
				  		System.out.println("Enter building name");
		                String  buildingName= scan.nextLine();

				  		System.out.println("Enter floor name");
		                String  floorName= scan.nextLine();
		                confRoomMgmt.addFloor(buildingName,floorName,confRoomMgmt);
		                // printFloor(convertToLowerCase(buildingName));
		            }
		            else if(commandArg2.equals("confroom")){
		            	
		            	System.out.println("Enter building name");
		                String  buildingName= scan.nextLine();

				  		System.out.println("Enter floor name");
		                String  floorName= scan.nextLine();
		                
		                System.out.println("Enter conf room name");
		                String  room= scan.nextLine();
		                
		                confRoomMgmt.addConfRoom(buildingName,floorName,room, confRoomMgmt);
		            } else System.out.println("Invalid request");
		        }
		        else if(commandArg1.equals("book")|| commandArg1.equals("cancel")){
		        	
		           	System.out.println("Enter building name");
	                String  buildingName= scan.nextLine();

			  		System.out.println("Enter floor name");
	                String  floorName= scan.nextLine();
	                
	                System.out.println("Enter conf room name");
	                String  room= scan.nextLine();
	                
	                System.out.println("Enter start time");
	                String  slotSt= scan.nextLine();
	                
	                System.out.println("Enter end time");
	                String  slotEnd= scan.nextLine();
	                
	                if(commandArg1.equals("book")) {
	                
	                	confRoomMgmt.bookSlot(slotSt,slotEnd,buildingName,floorName,room,confRoomMgmt);
	                }else {
	                	confRoomMgmt.cancelSlot(slotSt,slotEnd,buildingName,floorName,room,confRoomMgmt);
	                }          
	                
	                }
	                
		        else if(commandArg1.equals("list")){
		        	System.out.println("Enter building name");
	                String  buildingName= scan.nextLine();

			  		System.out.println("Enter floor name");
	                String  floorName= scan.nextLine();		
	                
	                confRoomMgmt.listBooking(buildingName,floorName,confRoomMgmt);
		        }
		        else if(commandArg1.equals("exit")) break;
		        else System.out.println("Invalid request");
;
		    }
	
		
		
	}
	

}
