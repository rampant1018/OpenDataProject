package OPEarth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
        /**
         * @param args
         */
        public static void main(String[] args) {
            // TODO Auto-generated method stub

        	List<Map<String, String>> eventList;
        	
        	System.out.println("Main Thread");
        	Parser parser = ProjectFactory.getParser();
        	eventList = parser.parse();
        }
        
        
        
        
}
