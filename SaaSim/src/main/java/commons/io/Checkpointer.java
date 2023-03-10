package commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.configuration.ConfigurationException;

import planning.util.MachineUsageData;

import commons.cloud.Provider;
import commons.cloud.User;
import commons.config.Configuration;
import commons.sim.AccountingSystem;
import commons.sim.Simulator;
import commons.sim.components.LoadBalancer;
import commons.sim.jeevent.JEEventScheduler;
import commons.sim.util.SimulatorFactory;
import commons.util.SimulationInfo;

/**
 * This class is responsible for saving and restoring the simulation status 
 * @author David Candeia Medeiros Maia - davidcmm@lsd.ufcg.edu.br
 * @author Ricardo Araújo Santos - ricardo@lsd.ufcg.edu.br
 *
 */
public class Checkpointer {

	public static final String MACHINE_DATA_DUMP = "machineData.txt";
	public static final String CHECKPOINT_FILE = ".je.dat";
	
	public static final long INTERVAL = 24 * 60 * 60 * 1000;
	
	private static JEEventScheduler scheduler;
	private static SimulationInfo simulationInfo;
	private static Simulator application;
	private static Provider[] providers;
	private static User[] users;
	private static AccountingSystem accountingSystem;
	private static int[] priorities;
	
	/**
	 * Check if there's a previous checkpoint available to read. Such operation consists in check if there
	 * exists a readable and writable simulation status file.
	 * @return <code>true</code> if there is an available checkpoint to read, <code>false</code> otherwise.
	 */
	public static boolean hasCheckpoint(){
		return new File(CHECKPOINT_FILE).canWrite();
	}
	
	/**
	 * Saving simulation status
	 */
	public static void save() {
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(CHECKPOINT_FILE));
			out.writeObject(scheduler);
			out.writeObject(simulationInfo);
			out.writeObject(application);
			out.writeObject(providers);
			out.writeObject(users);
			out.writeObject(accountingSystem);
			out.writeObject(priorities);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Deprecated
	public static void save(SimulationInfo info, User[] users, Provider[] providers,
			LoadBalancer[] application){
		
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(CHECKPOINT_FILE));
			out.writeObject(Checkpointer.loadScheduler());
			out.writeObject(info);
			out.writeObject(application);
			out.writeObject(providers);
			out.writeObject(users);
			out.writeObject(accountingSystem);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Saves machine usage data
	 * @param machineData
	 * @throws IOException
	 */
	public static void dumpMachineData(MachineUsageData machineData) throws IOException {
		if(machineData == null){
			return;
		}
		
		FileOutputStream fout = new FileOutputStream(new File(MACHINE_DATA_DUMP));
		ObjectOutputStream out = new ObjectOutputStream(fout);
		
		out.writeObject(machineData);
		
		out.close();
		fout.close();
	}
	
	
	public static SimulationInfo loadSimulationInfo() {
		return simulationInfo;
	}

	public static Simulator loadApplication() {
		return application;
	}

	public static Provider[] loadProviders() {
		return providers;
	}

	public static User[] loadUsers() {
		return users;
	}
	
	/**
	 * It removes all dump files.
	 */
	public static void clear(){
		new File(CHECKPOINT_FILE).delete();
		new File(MACHINE_DATA_DUMP).delete();
	}

	/**
	 * @return
	 */
	public static JEEventScheduler loadScheduler() {
		return scheduler;
	}
	
	/**
	 * Loads simulation status
	 * @throws ConfigurationException
	 */
	public static void loadData() throws ConfigurationException{
		if(hasCheckpoint()){
			ObjectInputStream in;
			try {
				in = new ObjectInputStream(new FileInputStream(CHECKPOINT_FILE));
				scheduler = (JEEventScheduler) in.readObject();
				simulationInfo = (SimulationInfo) in.readObject(); 
				simulationInfo.addDay();
				scheduler.reset(simulationInfo.getCurrentDayInMillis(), simulationInfo.getCurrentDayInMillis() + INTERVAL);
				application = (Simulator) in.readObject();
				providers = (Provider[]) in.readObject();
				users = (User[]) in.readObject();
				accountingSystem = (AccountingSystem) in.readObject();
				priorities = (int []) in.readObject();
				in.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}else{
			simulationInfo = new SimulationInfo();
			scheduler = new JEEventScheduler(INTERVAL);
			application = SimulatorFactory.buildSimulator(Checkpointer.loadScheduler());
			providers = Configuration.getInstance().getProviders();
			users = Configuration.getInstance().getUsers();
			accountingSystem = new AccountingSystem(users, providers);
			priorities = new int[users.length];
			for (int i = 0; i < priorities.length; i++) {
				priorities[i] = users[i].getContract().getPriority();
			}
		}
		System.err.println("CHKP LOAD " + simulationInfo);
	}

	public static AccountingSystem loadAccountingSystem() {
		return accountingSystem;
	}
	
	public static int[] loadPriorities() {
		return priorities;
	}
}
