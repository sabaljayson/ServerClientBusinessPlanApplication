package fx.test.Sprint5Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Client.ClientImpl;
import Client.ViewingPlanPair;
import Server.BP_Node;
import Server.BusinessEntity;
import Server.Person;
import Server.ServerImpl;
import Server.ServerInterfaceRMI;
import fx.model.Model;

public class Part1RMITest {
	ServerImpl server;
	ServerInterfaceRMI stub;
	ServerImpl obj;
	Registry registry;
	
	
	@BeforeEach
	void setupServer() {
		try {
			ServerImpl realServer = new ServerImpl();
			
		} catch (RemoteException  e) {
			e.printStackTrace();
		}
	}
	
	@AfterEach
	void tearupServer() {
		server = null;
		try {
			UnicastRemoteObject.unexportObject(obj, false);
		} catch (NoSuchObjectException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void testingObservers() {
		//ServerImpl obj;
		try {
			//set up the server
			obj = new ServerImpl();
			ServerInterfaceRMI stub1 = (ServerInterfaceRMI) UnicastRemoteObject.exportObject(obj,0);

			registry = LocateRegistry.createRegistry(1099);
			registry.bind("server", obj); //name server need to be matched
			
			ServerInterfaceRMI stub = (ServerInterfaceRMI) registry.lookup("server");
			
		

			//create a current viewing plan
			BusinessEntity entity = new BusinessEntity();
			BP_Node bplan1 = new BP_Node(entity, 1990, "TestViewing",true);
			stub.addBP_Node(bplan1); 
			
			// sign up 5 users in the server's list
			Person person1 = new Person("user1","password", "CS",true);
			Person person2 = new Person("user2","password", "CS",true);
			Person person3 = new Person("user3","password", "CS",true);
			Person person4 = new Person("user4","password", "CS",true);
			Person person5 = new Person("user5","password", "CS",true);
			stub.addPerson(person1); 
			stub.addPerson(person2);
			stub.addPerson(person3);
			stub.addPerson(person4);
			stub.addPerson(person5);
			
			//three client login as the user
			ClientImpl client1 = new ClientImpl(stub);
			client1.login("user1", "password"); //login as user1
			client1.business = bplan1; //set the current viewing plan to bplan1
			
			ClientImpl client2 = new ClientImpl(stub);
			client2.login("user2", "password"); //login as user2
			client2.business = bplan1;//set the current viewing plan to bplan1
			
			ClientImpl client3 = new ClientImpl(stub);
			client3.login("user3", "password"); //login as user3
			client3.business = bplan1;//set the current viewing plan to bplan1
			
			//this client does not viewing the plan
			ClientImpl client4 = new ClientImpl(stub);
			client4.login("user4", "password"); //login as user4 did not viewing any plan
			
			//this client is viewing the different plan in different year
			ClientImpl client5 = new ClientImpl(stub);
			client5.login("user5", "password"); // login as user5 viewing bplan2
			BusinessEntity entity2 = new BusinessEntity();
			BP_Node bplan2 = new BP_Node(entity2, 2018, "TestViewing",true);
			stub.addBP_Node(bplan2); //upload the business plan to the server
			client5.business = bplan2;//set this client's current viewing plan to bplan2
			
			
			//pre check if the clients being set correctly
			//five clients represents 5 users
			Assert.assertEquals(client1.person.username,"user1");
			Assert.assertEquals(client2.person.username,"user2");
			Assert.assertEquals(client3.person.username,"user3");
			Assert.assertEquals(client4.person.username,"user4");
			Assert.assertEquals(client5.person.username,"user5");
			//first three client all viewing the plan 1990
			Assert.assertEquals(client1.business.department+client1.business.year,"TestViewing1990");
			Assert.assertEquals(client2.business.department+client2.business.year,"TestViewing1990");
			Assert.assertEquals(client3.business.department+client3.business.year,"TestViewing1990");
			//client5 viewing the plan 2018
			Assert.assertEquals(client5.business.department+client5.business.year,"TestViewing2018");
			
			
			//the users are viewing the plan, 
			//so they will call the add client to register to be notified
			client1.addClient(); //register themselves
			client2.addClient();
			client3.addClient();
			
			
			//get the current viewing clients list from the server
			//test if the server's list has the right clients registered and the right bplan name coresponding to those client
			ArrayList<ViewingPlanPair<String, ClientImpl>> viewinglist = stub.returnViewingPlanList();
			Assert.assertEquals(viewinglist.get(0).a.toString(),"TestViewing1990"); // should be the planname and year
			
			//should be user1 and user2 and user3 three clients viewing the plan
			ArrayList<String> currentClientnames = new ArrayList<String>();
			currentClientnames.add("user1");
			currentClientnames.add("user2");
			currentClientnames.add("user3");
			
			//the server should have two clients viewing the plan in the list
			Assert.assertEquals(stub.returnCurrViewingClientname(), currentClientnames);
	

			
			//client1 make a change to the plans
			client1.signalChange();
			
			//client 123 that are viewing the plan should get the message! "The plan has been revised"
			Assert.assertEquals(client1.currentMessage,"The plan has been revised");
			Assert.assertEquals(client2.currentMessage,"The plan has been revised");
			Assert.assertEquals(client3.currentMessage,"The plan has been revised");

			//client 4 and 5 do not get the message
			Assert.assertEquals(client4.currentMessage,null);
			Assert.assertEquals(client5.currentMessage,null);
			
			//client2 no longer viewing the plans
			client2.removeClient();
			
			//client 1 make the change again
			client1.signalChange();
			//two client being notified
			Assert.assertEquals(client1.currentMessage,"The plan has been revised");
			Assert.assertEquals(client3.currentMessage,"The plan has been revised");

			
			//client 4 starting to viewing the same plan as client 5
			client4.business = bplan2;
			client4.addClient(); //register itself
			
			//client 5 did not being notified
			Assert.assertEquals(client5.currentMessage,null);
			//client5 make a change to the viewing plan
			client5.signalChange();
			
			//client 4 should be notified!!
			Assert.assertEquals(client4.currentMessage,"The plan has been revised");
			
			System.out.printf("\n"+"Testing Done");
			
			
		} catch (RemoteException | AlreadyBoundException | NotBoundException e) {
			System.err.println("Test exception: " + e.toString()) ;
			e.printStackTrace();
		}
		
	}











}
