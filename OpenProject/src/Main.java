
public class Main {

        /**
         * @param args
         */
        public static void main(String[] args) {
                // TODO Auto-generated method stub
                Parser parser = ProjectFactory.getParser();
                Fetch fetch = ProjectFactory.getFetch();
                parser.test(fetch.getJson("2012-09-05", "2012-09-06"));
        }
        
        
}