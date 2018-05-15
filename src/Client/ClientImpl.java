package Client;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;

import Server.BP_Node;
import Server.Person;
import Server.ServerInterfaceRMI;
import fx.model.Model;

public class ClientImpl  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ServerInterfaceRMI proxy;
	public Person person;
	public BP_Node business;
	public Model model;
	public String currentMessage;
	
	public ClientImpl(ServerInterfaceRMI proxy){
		this.proxy = proxy;
	}
	
	public ClientImpl(ServerInterfaceRMI proxy, Model model){
		this.proxy = proxy;
		this.model = model;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public void notifyChanges(String message) {
		System.out.println(this.person.username+" <- "+message);
		currentMessage = message;
		
		if(model!= null) {
			this.model.notifyChanges(message);
		}

	}
	
	public void setMessage(String message) {
		this.currentMessage = message;
		
	}
	
	public void signalChange() {
		try {
			this.proxy.signalChange(this.business.department+this.business.year);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void addClient() {
		try {
			
			System.out.println(this);
			proxy.addClient(this.business.getDepartment()+this.business.getYear(), this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	//remove the client from pair list in server
	public void removeClient() {
		try {
			proxy.removeClient(this.business.getDepartment()+this.business.getYear(), this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	// Takes a username and password (to be given by GUI in future)
	// call get person on server with user and pass, set person variable
	public void login(String username, String password) 
	{ 
		try {
			person = proxy.getPerson(username, password);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	// Same as login, but for the business plan
	public void requestBusinessPlan(String department, int year) 
	{
		try {
			business = proxy.getBP_Node(department, year);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	// write BP_Node to the disk
	public void writeLocalBP(BP_Node business) {
		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("businessPlan.xml")));
		} catch (FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File businessPlan.xml");
		}
		encoder.writeObject(business);
		encoder.close();

	}
	
	// Read from disk
	public void readLocalBP() {
		XMLDecoder decoder = null;
		try
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("businessPlan.xml")));
		} catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File businessPlan.xml not found");
		}
		business = (BP_Node) decoder.readObject();
		decoder.close();
	}
	
	// add person to list on server
	public void addPeople(String username, String password, String department, Boolean admin) {
		// Check if person is admin before allowing
		if (person.isAdmin()){
			// makes new person based on parameters
			Person newPerson = new Person(username, password, department, admin); 
			try {
				proxy.addPerson(newPerson);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("You definitely don't have permission to do this.");
		}
	}
		
	 // make a BP_Node with blank business plan
	public void make_BlankBP(int year, String department, Boolean editable) {

		try {
			proxy.make_BlankBP(year,department,editable);
			business = proxy.getBP_Node(department,year);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		writeLocalBP(business);
	}
	
	
	public void make_CloneBP(int year, String department, Boolean editable, int new_year){

		try {
			proxy.make_CloneBP(year,department,editable,new_year);
			business = proxy.getBP_Node(department,new_year);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		writeLocalBP(business);
	}
	
	public void setBPStatus(BP_Node businessPlan, boolean editable){
		try {
			proxy.setEditStatus(businessPlan, editable);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void uploadBP(BP_Node businessPlan){
		try {
			proxy.addBP_Node(businessPlan);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBP(BP_Node businessPlan){
		try {
			proxy.deleteBP_Node(businessPlan);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public LinkedList<BP_Node> getBP() {
		try {
			return proxy.getBP();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public LinkedList<Person> getPeople(){
		try {
			return proxy.getPeople();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){	
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("");
			ServerInterfaceRMI server = (ServerInterfaceRMI) registry.lookup("server");
			ClientImpl client = new ClientImpl(server);
			System.err.println("Client ready") ;
			
		} catch (RemoteException e) {
			System.err.println("Client exception: " + e.toString()) ;
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("Client exception: " + e.toString()) ;
			e.printStackTrace();
		}		
	}

	
	
}
