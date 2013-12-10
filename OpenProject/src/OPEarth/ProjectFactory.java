package OPEarth;

public class ProjectFactory {
	public static Fetch getFetch(){
		Fetch fetch = new Fetch();
		return fetch;
	}
	public static Parser getParser(){
		Parser parser = new Parser();
		return parser;
	}
}
