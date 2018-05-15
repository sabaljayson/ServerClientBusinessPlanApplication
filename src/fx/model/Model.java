package fx.model;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import Client.ClientImpl;
import Server.BP_Node;
import Server.BusinessEntity;
import Server.Person;
import Server.ServerImpl;
import Server.ServerInterfaceRMI;
import Server.ServerStarter;
import Server.Starter;
import fx.ICommand;

public class Model {

	Person currPerson;
	ICommand command;
	
	ClientImpl client;
	//private final static String host = "127.0.1.1";
	//ServerImpl server;
	//static ServerInterfaceRMI stub;

	
	void setupServer() {
		try {
			ServerImpl realServer = new ServerImpl();
			
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public Model() {
//		Registry registry;
//		try {
//			registry = LocateRegistry.getRegistry(host);
//			ServerInterfaceRMI stub = (ServerInterfaceRMI) registry.lookup("server");
//			client = new ClientImpl(stub);
//			client.proxy.readDisk();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		} catch (NotBoundException e) {
//			e.printStackTrace();
//		}
		
//		ServerImpl obj;
		//			obj = new ServerImpl();
		//			stub  = (ServerInterfaceRMI) UnicastRemoteObject.exportObject(obj,0);
		//			client = new ClientImpl(stub);
		
		
		
		
//					Starter starter = new Starter();
//					//sstarter.setup();
//					client = starter.client;
//					Person person = new Person("q","1","Math",true);
//					
//					System.out.println(client);
//					System.out.println(starter);
		
		this.clientRegistry();
		System.out.println(this.client);
		

	}
	
	
	public void clientRegistry(){	
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("");
			ServerInterfaceRMI server = (ServerInterfaceRMI) registry.lookup("server");
			this.client = new ClientImpl(server);
			
			System.err.println("Client ready") ;
			
		} catch (RemoteException e) {
			System.err.println("Client exception: " + e.toString()) ;
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("Client exception: " + e.toString()) ;
			e.printStackTrace();
		}		
	}
	
	
	public void setClient(ServerInterfaceRMI proxy) {
		this.client.proxy = proxy;
	}
	
	public void setCommand(ICommand command) {
		this.command = command;
	}
	
	public void signalChange() {
		
		this.client.signalChange();
	}
	
	public void notifyChanges(String message) {
		this.command.execute(message);
	}
	
	public void addClient() {
		this.client.addClient();
	}
	
	public void removeClient() {
		this.client.removeClient();
	}
	


	
	/// logs in user, otherwise no change
	public Person loginAction(String username, String password, boolean local, boolean selectHost) {
		
		client.login(username, password);
		System.out.println("look up...person..."+client.person);
		if (client.person != null) {
			this.currPerson = client.person;
			return client.person;

		}
		return null;
	}
	
	
	public Person getPerson() {
		return this.currPerson;
	}

	
	public void addPeople(String username, String password, String department, boolean isAdmin) {
		client.addPeople(username, password, department, isAdmin);
	}
	

	
	public String getUsername() {
		return client.person.username;
	}
	
	public boolean isAdmin() {
		return client.person.admin;
	}
	
	
	public List<BP_Node> getBP() {
		return client.getBP();
	}
	
	public String getDepartment() {
		return client.person.department;
	}
	
	public BP_Node getBusiness() {
		return client.business;
	}
	
	public void uploadBP(BP_Node plan){
		client.uploadBP(plan);
	}
	
	public int getBusinessYear() {
		return client.business.year;
	}

	public void deleteBP(BP_Node businessPlan) {
		client.deleteBP(businessPlan);
	}
	
	
	public void setBusiness(BP_Node plan) {
		client.business = plan;
	}
	
	public void requestBusinessPlan(int new_year) {
		client.requestBusinessPlan(client.person.getDepartment(), new_year);
	}
	
	public void make_CloneBP(int year, String department, boolean editable,int new_year) {
		System.out.println("make clone clinet");
		client.make_CloneBP(year, department, editable, new_year);
	}
	
	public void make_BlankBP(int year, String department, boolean editable) {
		client.make_BlankBP(year, department, editable);
	}
	

	public void setBPStatus(BP_Node plan, boolean set_editable) {
		client.setBPStatus(plan, set_editable);
		
	}
	
	public BusinessEntity getEntity() {
		return client.business.getEntity(); 
	}
	
	public void setEntity(BusinessEntity head) {
		client.business.entity = head;
	}
	
	public BusinessEntity returnEntity() {
		return client.business.entity;
	}
	
	public void writeDisk() {
		try {
			client.proxy.writeDisk();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void readDisk() {
		try {
			client.proxy.readDisk();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
