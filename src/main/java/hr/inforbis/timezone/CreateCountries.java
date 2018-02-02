package hr.inforbis.timezone;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class CreateCountries {

	private int cnt = 0;

	Session sess = App.sf.openSession();
	
	List<Country> countryList;
	
	public void createCountries() {
		
	countryList = new ArrayList<>();
	
	ClassLoader classLoader = getClass().getClassLoader();
	
	File fl = new File(classLoader.getResource("country.csv").getFile());
		 	
     FileInputStream fs;
     
     StringBuffer sb = new StringBuffer();
     
     String[] asc;
     
 	try {
			 fs = new FileInputStream(fl);
			
			int ch;
			
			while((ch=fs.read())!=-1) {
			
				if(ch!=13) {					
					sb.append((char)ch);		
				}
				else {					
					asc = sb.toString().split(";");
					
					createCountry(asc);
															
					sb.delete(0, sb.length());
				}
			
			}
			
			asc = sb.toString().split(";");		
			createCountry(asc);	
			
			fs.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	finally {
 		
 	}
 	
 	App.sf.close();
 	               
}
 
 private void createCountry(String[] asc) {
 	
	 Country c = new Country( asc[0],  asc[3]);
			
	 ZoneService.getZone1(Float.parseFloat(asc[1]), Float.parseFloat(asc[2]), c);
	 
	 Session session = App.sf.openSession();
	 
	 Transaction tran = session.beginTransaction();	 	 
	 
	 session.persist(c);		
	 
	 tran.commit();	 
		
	System.out.println(c.getName());
	
	//countryList.add(c);
 	
 }
	
		
}
