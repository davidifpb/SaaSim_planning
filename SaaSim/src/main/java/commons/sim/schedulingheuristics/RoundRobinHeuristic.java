/**
 * 
 */
package commons.sim.schedulingheuristics;

import java.util.List;

import commons.cloud.Request;
import commons.sim.components.Machine;

/**
 * Simple {@link SchedulingHeuristic} to choose servers in a Round Robin fashion.
 * 
 * @author David Candeia Medeiros Maia - davidcmm@lsd.ufcg.edu.br
 * @author Ricardo Araújo Santos - ricardo@lsd.ufcg.edu.br
 */
public class RoundRobinHeuristic implements SchedulingHeuristic {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4279289006456262500L;
	private int nextToUse;
	
	/**
	 * Default constructor
	 */
	public RoundRobinHeuristic() {
		this.nextToUse = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Machine getNextServer(Request request, List<Machine> servers) {
		
		nextToUse = (nextToUse) % servers.size();
		return servers.get(nextToUse++);
	}

	@Override
	public long getRequestsArrivalCounter() {
		return 0;
	}

	@Override
	public long getFinishedRequestsCounter() {
		return 0;
	}

	@Override
	public void resetCounters() {
	}

	@Override
	public void reportRequestFinished() {
	}

	@Override
	public void finishServer(Machine server, int index, List<Machine> servers){
		if(nextToUse > index){
			nextToUse = nextToUse - 1;
		}
	}
	
	@Override
	public void updateServers(List<Machine> servers) {
	}
}
