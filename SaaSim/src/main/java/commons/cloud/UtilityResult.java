package commons.cloud;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Contains information entries for each charge period (typically a month)
 * @author Ricardo Araújo Santos - ricardo@lsd.ufcg.edu.br
 * @author David Candeia Medeiros Maia - davidcmm@lsd.ufcg.edu.br
 */
public class UtilityResult implements Serializable{
	
	private static final char FIELD_SEPARATOR = '\t';
	/**
	 * 
	 */
	private static final long serialVersionUID = -7954582098382863519L;
	private TreeSet<UtilityResultEntry> entries;
	private double[] usersUniqueFee;
	private double[][] providersUniqueFee;
	
	private double uniqueReceipt;
	private double uniqueCost;
	private double finalProfit;
	
	/**
	 * Default constructor.
	 * @param numberOfUsers 
	 * @param numberOfProviders 
	 */
	public UtilityResult(int numberOfUsers, int numberOfProviders) {
		entries = new TreeSet<UtilityResultEntry>();
		usersUniqueFee = new double[numberOfUsers];
		providersUniqueFee = new double[numberOfProviders][MachineType.values().length];
		uniqueCost = 0;
		uniqueReceipt = 0;
		finalProfit = 0;
	}

	/**
	 * @return The total utility value.
	 */
	public double getUtility() {
		finalProfit = 0d;
		for ( UtilityResultEntry entry : entries) {
			finalProfit += entry.getUtility();
		}
		finalProfit += (uniqueReceipt - uniqueCost);
		return finalProfit;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getUtility());
		sb.append(FIELD_SEPARATOR);
		sb.append(uniqueReceipt);
		sb.append(FIELD_SEPARATOR);
		sb.append(uniqueCost);
		
		sb.append('\n');
		sb.append(entries.first().getEntryDescriptor());
		
		for (UtilityResultEntry entry : entries) {
			sb.append('\n');
			sb.append(entry);
		}
		return sb.toString();
	}

	/**
	 * @param entry
	 */
	public void addEntry(UtilityResultEntry entry) {
		entries.add(entry);
	}
	
	/**
	 * @param entry
	 */
	public void addUserUniqueFee(int userID, double fee) {
		usersUniqueFee[userID] = fee;
		uniqueReceipt += fee;
	}

	/**
	 * @param providerID
	 * @param type
	 * @param cost
	 */
	public void addProviderUniqueCost(int providerID, MachineType type, double cost) {
		providersUniqueFee[providerID][type.ordinal()] = cost;
		uniqueCost += cost;
	}

	public Iterator<UtilityResultEntry> iterator() {
		return entries.iterator();
	}
}
