package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;

//import javax.swing.Timer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import Client.ClientImpl;
import Client.ViewingPlanPair;
import fx.viewPlanPage.NotifyCommand;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;



public class ServerImpl2_Copy implements ServerInterfaceRMI{
	
	
	LinkedList<Person> people;
	LinkedList<BP_Node> businessPlans;
	Person dummy = new Person("dummy","123","Math",true);
	ArrayList<ViewingPlanPair<String, ClientImpl>> currClients;
	//private final static String host = "127.0.1.1";
	ArrayList<NotifyCommand> commands = new ArrayList<NotifyCommand>();
	
	//constructor
	public ServerImpl2_Copy() throws RemoteException {
		super();
		businessPlans = new LinkedList<BP_Node>();
		people = new LinkedList<Person>();
		people.add(dummy);	
		currClients  = new ArrayList<ViewingPlanPair<String,ClientImpl>>();
	}
	
	
	public String hello() {
		return "Hellooo";
	}
	
	
	
	//get the client viewing the plan
	public ArrayList<ClientImpl> getAllClientinaBP(String bpinfo) throws RemoteException {
		for(int i = 0; i<currClients.size();i++) {
			//match the right bp
			if(this.currClients.get(i).a.equals(bpinfo)) {
				return this.currClients.get(i).arrayb;
			}
		}
		return null;
	}
	
	//notify the clients
	public void notifyClientintheBP(String bpinfo) throws RemoteException {
		
		ArrayList<ClientImpl> clientlist = this.getAllClientinaBP(bpinfo);
		if(clientlist == null) {
			System.out.println("the bpinfo passed does not have any client");
		}
		else {
			System.out.println("I am notifing...");
			for(int i = 0; i<clientlist.size();i++) {
				String message = "The plan has been revised";
				clientlist.get(i).notifyChanges(message);
				clientlist.get(i).setMessage(message);
			}
		}

	}
	
	//if the view change signal it back to server
	public void signalChange(String bpinfo) throws RemoteException {
		//System.out.println("one client just changed something");
		notifyClientintheBP(bpinfo);
	}
	
	//simply return the pair list
	public ArrayList<ViewingPlanPair<String, ClientImpl>> returnViewingPlanList() throws RemoteException {
		return this.currClients;
	}
	
	//add new client to the pair list
	public void addClient(String bpinfo, ClientImpl client) throws RemoteException {
		if(isBPExist(bpinfo)) {
			//find the pair and add the client
			//if it is not been already added
			if(!this.checkDuplicateClient(bpinfo, client)) {
				for (int i = 0 ; i<this.currClients.size(); i++) {
					if(this.currClients.get(i).a.equals(bpinfo)) {
						this.currClients.get(i).addB(client);
					}
				}
				this.printClientPairInfo();
			}
		}
		else {
			//System.out.println("creat new");
			//create a new pair and add the client
			ViewingPlanPair<String, ClientImpl> pair = new ViewingPlanPair<String, ClientImpl>(bpinfo);	
			pair.addB(client);
			this.currClients.add(pair);
			this.printClientPairInfo();
		
		}	
	}
	
	//return true if the client is already been added
	public boolean checkDuplicateClient(String bpinfo, ClientImpl client) throws RemoteException{
		for (int i = 0 ; i<this.currClients.size(); i++) {
			if(this.currClients.get(i).a.equals(bpinfo)) {
				for(int t = 0; t<this.currClients.get(i).arrayb.size(); t++) {
					if(this.currClients.get(i).arrayb.get(t).person.username.equals(client.person.username)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//print all info from the pair list
	public void printClientPairInfo() throws RemoteException{
		ArrayList<String> bpinfos = new ArrayList<String>();
		ArrayList<String> clientinfos = new ArrayList<String>();
		for (int i = 0; i<this.currClients.size(); i++) {
			bpinfos.add(this.currClients.get(i).a);
			for(int t = 0; t<this.currClients.get(i).arrayb.size(); t++) {
				clientinfos.add(this.currClients.get(i).arrayb.get(t).person.username);
			}
			
		}
		System.out.println("current business plan being viewed: "+bpinfos);
		System.out.println("all the current client viewing plans: "+clientinfos);
	}
	
	//just return the client's name as an arraylist
	public ArrayList<String> returnCurrViewingClientname() throws RemoteException{
		ArrayList<String> clientinfos = new ArrayList<String>();
		for (int i = 0; i<this.currClients.size(); i++) {
			for(int t = 0; t<this.currClients.get(i).arrayb.size(); t++) {
				clientinfos.add(this.currClients.get(i).arrayb.get(t).person.username);
			}
			
		}
		return clientinfos;
	}
	
	//check if a plan already exist in the pair list
	public boolean isBPExist(String bpinfo)  throws RemoteException {
		for (int i = 0 ; i<this.currClients.size(); i++) {
			if(this.currClients.get(i).a.equals(bpinfo)) {
				return true;
			}
		}
		return false;

	}
	
	//remove a client from the pair list
	public void removeClient(String bpinfo, ClientImpl client)  throws RemoteException{
		if(!this.isBPExist(bpinfo)) {
			System.out.println("the plan does not exist, something goes wrong");
		}
		else {
			for (int i = 0 ; i<this.currClients.size(); i++) {
				if(this.currClients.get(i).a.equals(bpinfo)) {
					this.currClients.get(i).removeB(client);
				}
			}
			this.printClientPairInfo();
		}
	}
	

	//takes person handed to it and adds it to the list
	//admin rights handled in client
	public void addPerson(Person person)  throws RemoteException{							
		people.add(person);
		writeDisk();
	}
	
	
	
	//create a bp
	public void make_BlankBP(int year, String department, Boolean editable) throws RemoteException {	
		EntityFactory centre_factory = new CentrePlanFactory();
		BusinessEntity centre_plan = centre_factory.nextLayer(null);
		BusinessEntity mission_statement = centre_plan.createNewSubentity();
		BusinessEntity department_statement = mission_statement.createNewSubentity();
		BusinessEntity goal = department_statement.createNewSubentity();
		BusinessEntity SLO = goal.createNewSubentity();
		SLO.createNewSubentity();
		addBP_Node(new BP_Node(centre_plan, year, department, editable));
	}
	
	//make a clone of bp
	public void make_CloneBP(int year, String department, Boolean editable, int new_year) throws RemoteException {
		BusinessEntity oldEntity = getBP_Node(department, year).getEntity();

		String new_entity_title = oldEntity.getEntityTitle();
		ArrayList<EntityStatement> old_statements = oldEntity.getStatements();
		ArrayList<EntityStatement> new_statements = new ArrayList<EntityStatement>();
		for (int i = 0; i < old_statements.size(); i++)
		{
			new_statements.add(old_statements.get(i));
		}

		ArrayList<BusinessEntity> old_entities = oldEntity.getSubentities();
		ArrayList<BusinessEntity> new_entities = new ArrayList<BusinessEntity>();

		for (int i = 0; i < old_entities.size(); i++)
		{
			new_entities.add(old_entities.get(i));
		}
		BusinessEntity new_parent = oldEntity.getParentEntity();
		EntityFactory new_subentity_factory = oldEntity.getSubentityFactory();

		BusinessEntity newEntity = new BusinessEntity(new_entity_title, new_statements, new_entities, new_parent,
				new_subentity_factory);
		addBP_Node(new BP_Node(newEntity, new_year, department, editable));
	}
	
	//takes node given and adds it to the list if it doesn't exist
	public void addBP_Node(BP_Node node)  throws RemoteException {
		int index = 0;
		boolean found = false;
		while(!found && index<businessPlans.size()){
			BP_Node curr_node = businessPlans.get(index);
			if(curr_node.getYear() == node.getYear() && curr_node.getDepartment().equalsIgnoreCase(node.getDepartment())){	
				found = true;
			}
			else{
				index++;
			}
		}
		//if the business plan already exists, replace with the updated node
		if(found) 	{
			businessPlans.remove(index);
			businessPlans.add(index, node);
		}
		else{
			businessPlans.add(node);
		}
		writeDisk();
		readDisk();
	}
	
	//remove a bp
	public void deleteBP_Node(BP_Node node) throws RemoteException {
		int index = 0;
		boolean found = false;
		while(!found && index<businessPlans.size()){
			BP_Node curr_node = businessPlans.get(index);
			if(curr_node.getYear() == node.getYear() && curr_node.getDepartment().equalsIgnoreCase(node.getDepartment())){	
				found = true;
			}
			else{
				index++;
			}
		}
		//if the business plan exists, delete it
		if(found) {
			businessPlans.remove(index);
		}
		writeDisk();
		readDisk();
	}
	
	public void setEditStatus(BP_Node node, boolean edit_status)  throws RemoteException {
		int index = 0;
		boolean found = false;
		while(!found && index<businessPlans.size()){
			BP_Node curr_node = businessPlans.get(index);
			if(curr_node.getYear() == node.getYear() && curr_node.getDepartment().equalsIgnoreCase(node.getDepartment())){	
				found = true;
			}
			else{
				index++;
			}
		}
		
		if(found){
			businessPlans.get(index).setEditable(edit_status);
		}
		writeDisk();
		readDisk();
	}
	
	//iterates through the list and gets the person with the given username and password
	//return null if not there
	public Person getPerson(String username, String password) throws RemoteException{														  
		for (int i = 0; i < people.size(); i++){
			Person currentPerson = people.get(i);
			if (currentPerson.username.equals(username) && currentPerson.password.equals(password)){
				return currentPerson;
			}
		}
		return null;
	}
	
	//Same as get person, iterate the list and return the node with the matching dept and year
	public BP_Node getBP_Node(String department, int year)  throws RemoteException {
		for (int i = 0; i < businessPlans.size(); i++){
			BP_Node currentBP = businessPlans.get(i);
			if (currentBP.department.equals(department) && currentBP.year == year){
				return currentBP;
			}
		}
		return null;
	}
	
	public LinkedList<Person> getPeople()  throws RemoteException{
		return this.people;
	}

	public LinkedList<BP_Node> getBP()  throws RemoteException {
		return this.businessPlans;
	}
	
	public void readDisk() throws RemoteException//Reads data from disk and sets lists accordingly
	{
		XMLDecoder decoder=null; //Read the list from disk, set the to list in server
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("businessPlans.xml")));
		} catch (FileNotFoundException e) {
			
		}
		businessPlans= (LinkedList<BP_Node>)decoder.readObject();
		
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("people.xml")));
		} catch (FileNotFoundException e) {
			
		}
		people = (LinkedList<Person>)decoder.readObject();
		
//		
//		try {
//			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("viewingUser.xml")));
//		} catch (FileNotFoundException e) {
//			
//		}
//		currClients = (ArrayList<ViewingPlanPair<String, Client>>)decoder.readObject();
		
		
		decoder.close();
	}
	
	public void writeDisk() throws RemoteException//Write each person and BP_Node in the lists to the disk
	{
		XMLEncoder encoder1=null;
		
		try{
			encoder1=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("businessPlans.xml")));
			}catch(FileNotFoundException fileNotFound){
				System.out.println("ERROR: While Creating or Opening the File businessPlans.xml");
			}
		encoder1.writeObject(businessPlans);
		encoder1.close();
		
		XMLEncoder encoder2=null;
		
		try{
			encoder2=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("people.xml")));
			}catch(FileNotFoundException fileNotFound){
				System.out.println("ERROR: While Creating or Opening the File businessPlans.xml");
			}
		encoder2.writeObject(people);
		encoder2.close();
		
		
//		XMLEncoder encoder4 = null;
//		
//		try{
//			encoder4=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("viewingUser.xml")));
//			}catch(FileNotFoundException fileNotFound){
//				System.out.println("ERROR: While Creating or Opening the File viewingUser.xml");
//			}
//		encoder4.writeObject(currClients);
//		encoder4.close();
		
		
		
	}
	
	
	public static void main(String[] args) {
		try {

//			System.setProperty("java.rmi.server.hostname", "127.0.1.1");

			ServerImpl2_Copy obj = new ServerImpl2_Copy();
			ServerInterfaceRMI stub = (ServerInterfaceRMI) UnicastRemoteObject.exportObject(obj,0);
			
			// Bind the remote object's stub in the registry
//			Registry registry = LocateRegistry.getRegistry();
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("server", stub); //name server need to be matched
			
			System.err.println("Server ready") ;
			
			
			
			
			stub.readDisk();
			Boolean isDoneRunning = false;
			while(isDoneRunning == false)
			{
				try {
					TimeUnit.MINUTES.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				stub.writeDisk();
			}		
			
			

			
		} catch(Exception e) {
			System.err.println("Server exception: " + e.toString()) ;
			e.printStackTrace();
		}
    }
	
	
	

}
