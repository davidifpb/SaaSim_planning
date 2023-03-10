package commons.io;

import java.io.IOException;

/**
 * Common set of features for workload parsers.
 * 
 * @author Ricardo Araújo Santos - ricardo@lsd.ufcg.edu.br
 * @author David Candeia Medeiros Maia - davidcmm@lsd.ufcg.edu.br
 * 
 * @param <T> To represent the granularity you
 */
public interface WorkloadParser<T> {

	/**
	 * Reads and returns the next portion of data from the workload. Note that
	 * the read and return process depends on the implementation.
	 * 
	 * @return
	 * @throws RuntimeException Encapsulation of {@link IOException}. 
	 */
	public T next();

	/**
	 * @return
	 */
	public boolean hasNext();

	public void clear();

	public void setDaysAlreadyRead(int simulatedDays);

	public void close();
	
	public int size();
}
