package hr.inforbis.timezone;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ZoneService {

//final static String url = "http://api.timezonedb.com/v2/get-time-zone?key=%s&lat=%s&lng=%s&by=zone&format=json";
final static String url = "http://api.timezonedb.com/v2/get-time-zone?key=%s&zone=%s&by=zone&format=json";
final static String zone_url = "http://api.timezonedb.com/v2/list-time-zone?key=%s&format=json";
final static String API_KEY = "DGO4X3MXY0MN";
final static String zone_hql = "select z from Zone z where z.zoneName = :zoneName";
	
private static Date createDate(Long time) {			
	
	SimpleDateFormat frm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");	
	frm.setTimeZone(TimeZone.getTimeZone("UTC"));
	
	Date d = new Date();
	d.setTime(time);	
	String dateStr = frm.format(d);
			
	try {
		return frm.parse(dateStr);
	} catch (ParseException e) {
		e.printStackTrace();
	}
	return null;
	
}

public static void getZones() {
	
	String apiUrl = String.format(zone_url, API_KEY);
	
	String json = ClientBuilder.newClient().target(apiUrl).request().accept(MediaType.APPLICATION_JSON).get(String.class);
	
	String countryCode = null;
	String countryName = null;
	String zoneName = null; 
	String zoneCode = null;
	Long utcOffset = null;
	Long dstStart = null;;
	Long dstEnd = null;;
	Integer dst = null;;
	String timeZone = null;;
	String nextTimeZone = null;;		
			
	Gson gson = new Gson();
	
	JsonParser gparser = new JsonParser();	
	JsonElement jel = gparser.parse(json);
	
	JsonObject obj = jel.getAsJsonObject();
	
	JsonElement zonesElement = obj.get("zones");
				
	JsonArray list = zonesElement.getAsJsonArray();
		
	for (JsonElement element: list) {
				
		JsonObject zone = element.getAsJsonObject();
			
		Set<Map.Entry<String, JsonElement>> entries = zone.entrySet();
					
		for (Map.Entry<String, JsonElement> entry: entries) {
			
			switch(entry.getKey()) {
				case "zoneName":
					zoneName = entry.getValue().getAsString();
					break;
				case "countryCode":
					countryCode = entry.getValue().getAsString();
					break;
				case "countryName":			
					countryName = entry.getValue().getAsString();			
					break;								
			}
			
		}
			
		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			apiUrl = String.format(url, API_KEY, zoneName);
						
			json = ClientBuilder.newClient().target(apiUrl).request().accept(MediaType.APPLICATION_JSON).get(String.class);
											
			jel = gparser.parse(json);
			
			obj = jel.getAsJsonObject();
			
			entries = obj.entrySet();
						
			for (Map.Entry<String, JsonElement> zoneData: entries) {
				
				switch(zoneData.getKey()) {				
					case "dstStart":			
						dstStart = (Long)zoneData.getValue().getAsLong()*1000;			
						break;
					case "dstEnd":
						dstEnd = (Long)zoneData.getValue().getAsLong()*1000;		
						break;			
					case "dst":						
						dst = (Integer)zoneData.getValue().getAsInt();
						break;
					case "gmtOffset":
						utcOffset = (zoneData.getValue().getAsLong()*1000);
						break;
					case "abbreviation":
						timeZone = zoneData.getValue().getAsString();
						break;
					case "nextAbbreviation":
						nextTimeZone = zoneData.getValue().getAsString();
						break;						
				}				
			}
			
			if(nextTimeZone.isEmpty()) dst = 0;
			
			Zone tzone = null;
			
			Session session = App.sf.openSession();	
						
			Query q = session.createQuery(zone_hql);
			q.setParameter("zoneName", zoneName);
			List<Zone> zoneList = q.list();
			
			if(!zoneList.isEmpty()) {
				
				tzone = zoneList.get(0);
				tzone.setCountryCode(countryCode);
				
								
				tzone.setDst(dst);
				
				tzone.setNextTimeZone(nextTimeZone);
				tzone.setTimeZone(nextTimeZone);
				tzone.setUtcOffset(utcOffset);
				tzone.setZoneName(zoneName);
				
			}
			else
				tzone = new Zone(countryCode, zoneName, zoneCode, utcOffset, dstStart, dstEnd, dst, timeZone, nextTimeZone);	


			if(dst==0) {
				tzone.setDstEnd(null);
				tzone.setDstStart(null);	
			}
			else
			{
				tzone.setDstEnd(dstEnd);
				tzone.setDstStart(dstStart);	
			}

			
			Transaction tx = session.beginTransaction();		
			session.persist(tzone);		
			tx.commit();
			
			System.out.println(tzone.getZoneName());
			
			
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
}



public static void getZone1(Float lat, Float lng, Country country) {
		
	DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
	otherSymbols.setDecimalSeparator('.');
	otherSymbols.setGroupingSeparator(',');
	
	DecimalFormat fmt = new DecimalFormat("###.###############",otherSymbols);		
	String latitude =fmt.format(lat);	
	String longitude =fmt.format(lng);
	
	String apiUrl = String.format(url, API_KEY, latitude, longitude);
	
	String json = "";
	
	Client client = ClientBuilder.newClient();	
	WebTarget target = client.target(apiUrl);
	
	Future<String> response = target.request(MediaType.APPLICATION_JSON).async().get(String.class);
	
	try {
		json = response.get();		
	} catch (InterruptedException | ExecutionException e1) {	
		e1.printStackTrace();
	}

	
	//String json = ClientBuilder.newClient().target(apiUrl).request().accept(MediaType.APPLICATION_JSON).get(String.class);
	
	Gson gson = new Gson();
	
	System.out.println(json);
	
	JsonParser gparser = new JsonParser();	
	JsonElement jel = gparser.parse(json);
	
	JsonObject obj = jel.getAsJsonObject();
	
	Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
				
	for (Map.Entry<String, JsonElement> entry: entries) {
		
		switch(entry.getKey()) {
		case "countryName":
			System.out.println(entry.getValue().getAsString());
			break;
		case "dstStart":			
			country.setDstStart((Long)entry.getValue().getAsLong()*1000);			
			break;
		case "dstEnd":
			country.setDstEnd((Long)entry.getValue().getAsLong()*1000);		
		break;			
		case "dst":
			country.setDst((Integer)entry.getValue().getAsInt());
			break;
		case "gmtOffset":
			country.setUtcOffset((Integer)entry.getValue().getAsInt()*1000);
			break;
		case "abbreviation":
			country.setTimeZone(entry.getValue().getAsString());
			break;
		case "nextAbbreviation":
			country.setNextTimeZone(entry.getValue().getAsString());
			break;
		case "zoneName":
			country.setZoneName(entry.getValue().getAsString());
			break;	
		
		}
					    
	}
		
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	if(country.getDst()==0) {
		country.setDstEnd(null);
		country.setDstStart(null);			
	}
	
	/*countryCode
countryName
zoneName
abbreviation
gmtOffset
dst
dstStart
dstEnd
nextAbbreviation
timestamp
formatted*/
}
	
	
}
