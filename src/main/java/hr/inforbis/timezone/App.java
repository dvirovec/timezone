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
	
	public static void main( String[] args )
    {
    	 
        sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
                
        
    	//CreateCountries cc = new CreateCountries();
    	
    	//cc.createCountries();
        
        Session session = App.sf.openSession();	
		     
        ZoneService.getZones();
        
    	    
    }
    
}
