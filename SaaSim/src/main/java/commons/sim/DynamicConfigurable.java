package commons.sim;

import java.util.List;

import provisioning.DPS;
import provisioning.Monitor;

import commons.cloud.Request;
import commons.io.WorkloadParser;
import commons.sim.components.MachineDescriptor;

/**
 * Interface for applications which underlying infrastructure can be dynamically configurable.
 * 
 * @author Ricardo Araújo Santos - ricardo@lsd.ufcg.edu.br
 * @author David Candeia Medeiros Maia - davidcmm@lsd.ufcg.edu.br
 */
public interface DynamicConfigurable {
	
	/**
	 * Add a new server to the infrastructure
	 * @param tier The application tier which the new machine will serve.
	 * @param machineDescriptor {@link MachineDescriptor} of the new server.
	 * @param useStartUpDelay <code>true</code> to use machine start up delay.
	 */
	void addServer(int tier, MachineDescriptor machineDescriptor, boolean useStartUpDelay);
	
	/**
	 * @param tier
	 * @param serverID
	 * @param force TODO
	 */
	@Deprecated
	void removeServer(int tier, MachineDescriptor machineDescriptor, boolean force);

	/**
	 * @param parser {@link WorkloadParser} implementation for workload reading.
	 */
	void setWorkloadParser(WorkloadParser<List<Request>> parser);

	/**
	 * Removes a server from specified tier. The removal policy is determined by something we still don't know.
	 * TODO Who is responsible for determine which machine is removed?
	 * @param tier The tier whose machine will be removed.
	 * @param force <code>true</code> to remove immediately, and <code>false</code> to stop scheduling and wait
	 * until machine becomes idle to remove.
	 */
	void removeServer(int tier, boolean force);

	/**
	 * @param monitor Monitoring system to collect information needed by {@link DPS}.
	 */
	void setMonitor(Monitor monitor);
	
}
