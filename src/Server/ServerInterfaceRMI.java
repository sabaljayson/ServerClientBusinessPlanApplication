package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;


import Client.ClientImpl;
import Client.ViewingPlanPair;

public interface ServerInterfaceRMI extends Remote  {
	public ArrayList<ClientImpl> getAllClientinaBP(String bpinfo) throws RemoteException;
	public void notifyClientintheBP(String bpinfo) throws RemoteException;
	public void signalChange(String bpinfo) throws RemoteException;
	public ArrayList<ViewingPlanPair<String, ClientImpl>> returnViewingPlanList() throws RemoteException;
	public void addClient(String bpinfo, ClientImpl client) throws RemoteException;
	public boolean checkDuplicateClient(String bpinfo, ClientImpl client) throws RemoteException;
	public void printClientPairInfo() throws RemoteException;
	public ArrayList<String> returnCurrViewingClientname() throws RemoteException;
	public boolean isBPExist(String bpinfo)  throws RemoteException;
	public void removeClient(String bpinfo, ClientImpl client)  throws RemoteException;
	
	
	
	public void addPerson(Person person)  throws RemoteException;
	public void make_BlankBP(int year, String department, Boolean editable) throws RemoteException;
	public void make_CloneBP(int year, String department, Boolean editable, int new_year) throws RemoteException;
	public void addBP_Node(BP_Node node)  throws RemoteException;
	public void deleteBP_Node(BP_Node node) throws RemoteException;
	public void setEditStatus(BP_Node node, boolean edit_status)  throws RemoteException;
	public Person getPerson(String username, String password) throws RemoteException;
	public BP_Node getBP_Node(String department, int year)  throws RemoteException;
	
	public LinkedList<Person> getPeople()  throws RemoteException;
	public LinkedList<BP_Node> getBP()  throws RemoteException;
	
	public void readDisk() throws RemoteException;
	public void writeDisk() throws RemoteException;
	
	public String hello() throws RemoteException;
}
