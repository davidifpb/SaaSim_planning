package commons.cloud;

import commons.io.Checkpointer;

/**
 * Singleton for ID generation.
 * @author Ricardo Araújo Santos - ricardo@lsd.ufcg.edu.br
 * @author David Candeia Medeiros Maia - davidcmm@lsd.ufcg.edu.br
 */
public enum IDGenerator {
	
	/**
	 * Singleton 
	 */
	GENERATOR;
	
	private long nextID;
	
	/**
	 * Private constructor
	 */
	private IDGenerator(){
		nextID = Checkpointer.loadSimulationInfo().getCurrentDayInMillis();
	}
	
	/**
	 * @return The next available ID
	 */
	public long next(){
		return nextID++;
	}
	
}
