package hr.inforbis.timezone;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 *
 */
public class App 
{	
	
	//http://api.timezonedb.com/v2/get-time-zone?key=DGO4X3MXY0MN&by=position&lat=45.7989476&lng=15.9836974&zone=CET
	//http://api.timezonedb.com/v2/list-time-zone?key=DGO4X3MXY0MN&format=json
	
	public static SessionFactory sf;
	public static String dataMode = null;
	public static String filePath;

	public static void main( String[] args ){

        if (args.length < 1) {
            System.out.println("No parameters defined! \n DB - remote database \n CSV <file_name> - csv file \n SQL <file_name> - sql merge script \n");
            System.exit(0);
        }
        else{

            switch (args[0]){
                case "CSV":
                case "SQL":
                case "DB": dataMode = args[0]; break;
            }

            if (dataMode == null){
                System.out.println(String.format("%s parameter not allowed!", args[0]));
                System.exit(0);
            }

            if (!dataMode.equals("DB")){            	            
            	if (args.length==1) { 
	                System.out.println("Filename parameter not entered!");
	                System.exit(0);
            	} 
            	
            	filePath = args[1] + "." + (dataMode.equals("SQL") ? "sql" : "csv");            	            	
            }            
        }


        if (dataMode.equals("DB")) {
            sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            Session session = App.sf.openSession();
        }

    	//CreateCountries cc = new CreateCountries();
    	
    	//cc.createCountries();
		     
        ZoneService.getZones();

    }
    
}
