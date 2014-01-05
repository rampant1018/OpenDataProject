package OPEarth;

/** Try to use 'Simple Factory' design pattern. Used to generate instance.
 * 
 * @author rampant
 *
 */
public class ProjectFactory {
	/** Get instance of Fetch
	 * 
	 * @return instance of Fetch
	 */
	public static Fetch getFetch() {
		Fetch fetch = new Fetch();
		
		return fetch;
	}
	
	/** Get instance of Parser
	 * 
	 * @return instance of Parser
	 */
	public static Parser getParser() {
		Parser parser = new Parser();
		
		return parser;
	}
	
	/** Get instance of PostgreSQL
	 * 
	 * @return instance of PostgreSQL
	 */
	public static PostgreSQL getDatabase() {
		PostgreSQL database = new PostgreSQL();
		
		return database;
	}
	
	/** Get instance of Chart
	 * 
	 * @return instance of Chart
	 */
	public static Chart getChart() {
		Chart chart = new Chart();
		
		return chart;
	}
}
