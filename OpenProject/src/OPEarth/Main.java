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
        	/*
                Parser parser = ProjectFactory.getParser();
                parser.test();
                */
        	
        	PostgreSQL database = new PostgreSQL();
        	
        	database.InsertData((float)5.5, (float)3.3, (float)1.1, new Date(1356998400000L), "Testtest");
        }
        
        
        
        
}
