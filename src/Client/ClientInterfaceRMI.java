package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

import Server.BP_Node;
import Server.Person;
import fx.model.Model;

public interface ClientInterfaceRMI extends Remote {
	public void setModel(Model model) throws RemoteException;
	public void notifyChanges(String message) throws RemoteException;
	public void setMessage(String message) throws RemoteException;
	public void addClient() throws RemoteException;
	public void removeClient() throws RemoteException;
	public void login(String username, String password) throws RemoteException;
	public void requestBusinessPlan(String department, int year) throws RemoteException;
	public void writeLocalBP(BP_Node business) throws RemoteException;
	public void readLocalBP() throws RemoteException;
	public void addPeople(String username, String password, String department, Boolean admin) throws RemoteException;
	public void make_BlankBP(int year, String department, Boolean editable) throws RemoteException;
	public void make_CloneBP(int year, String department, Boolean editable, int new_year) throws RemoteException;
	public void setBPStatus(BP_Node businessPlan, boolean editable) throws RemoteException;
	public void uploadBP(BP_Node businessPlan) throws RemoteException;
	public void deleteBP(BP_Node businessPlan) throws RemoteException;
	public LinkedList<BP_Node> getBP() throws RemoteException;
	public LinkedList<Person> getPeople() throws RemoteException;
	
}
