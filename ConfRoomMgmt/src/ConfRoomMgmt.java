import java.util.*;

import Repo.ConfRoomRepo;
import service.ConfRoomService;

public class ConfRoomMgmt extends ConfRoomService {
	
 
	
	public static void main(String args[]) {
		
		ConfRoomMgmt  confRoomMgmt = new ConfRoomMgmt();
		
		ConfRoomRepo confRoomRepo = new ConfRoomRepo();
		 confRoomRepo.buildingList = new ArrayList();

		
		
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
		                confRoomMgmt.addBuilding(buildingName,confRoomRepo);
		                // printBuildings();
		            }
		            else if(commandArg2.equals("floor")){
				  		System.out.println("Enter building name");
		                String  buildingName= scan.nextLine();

				  		System.out.println("Enter floor name");
		                String  floorName= scan.nextLine();
		                confRoomMgmt.addFloor(buildingName,floorName,confRoomRepo);
		                // printFloor(convertToLowerCase(buildingName));
		            }
		            else if(commandArg2.equals("confroom")){
		            	
		            	System.out.println("Enter building name");
		                String  buildingName= scan.nextLine();

				  		System.out.println("Enter floor name");
		                String  floorName= scan.nextLine();
		                
		                System.out.println("Enter conf room name");
		                String  room= scan.nextLine();
		                
		                confRoomMgmt.addConfRoom(buildingName,floorName,room, confRoomRepo);
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
	                
	                	confRoomMgmt.bookSlot(slotSt,slotEnd,buildingName,floorName,room,confRoomRepo);
	                }else {
	                	confRoomMgmt.cancelSlot(slotSt,slotEnd,buildingName,floorName,room,confRoomRepo);
	                }          
	                
	                }
	                
		        else if(commandArg1.equals("list")){
		        	System.out.println("Enter building name");
	                String  buildingName= scan.nextLine();

			  		System.out.println("Enter floor name");
	                String  floorName= scan.nextLine();		
	                
	                confRoomMgmt.listBooking(buildingName,floorName,confRoomRepo);
		        }
		        else if(commandArg1.equals("exit")) break;
		        else System.out.println("Invalid request");
;
		    }
	
		
		
	}
	

}
