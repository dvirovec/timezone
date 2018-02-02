package hr.inforbis.timezone;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Country", schema = "MData")
public class Country   implements Serializable {
	
	private static final long serialVersionUID = -1;
	
	public Country() {}
	
	public Country(String countryCode, String name) {
		this.CountryCode = countryCode;		
		this.Name = name;		
	}
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	    
	private Integer countryID;
	private String CountryCode;	
	private String Name;
	private Long DstStart;
	private Long DstEnd;
	private Integer Dst;
	private Integer UtcOffset;
	private String TimeZone;
	private String NextTimeZone;
	private String ZoneName; 
	
	public final String getCountryCode() {
		return CountryCode;
	}
	public final void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}
	
	public final String getName() {
		return Name;
	}
	public final void setName(String name) {
		Name = name;
	}

	public final Integer getCountryID() {
		return countryID;
	}

	public final void setCountryID(Integer countryID) {
		this.countryID = countryID;
	}

	public final Long getDstStart() {
		return DstStart;
	}

	public final void setDstStart(Long dstStart) {
		DstStart = dstStart;
	}

	public final Long getDstEnd() {
		return DstEnd;
	}

	public final void setDstEnd(Long dstEnd) {
		DstEnd = dstEnd;
	}

	public final Integer getDst() {
		return Dst;
	}

	public final void setDst(Integer dst) {
		Dst = dst;
	}

	public final Integer getUtcOffset() {
		return UtcOffset;
	}

	public final void setUtcOffset(Integer utcOffset) {
		UtcOffset = utcOffset;
	}

	public final String getTimeZone() {
		return TimeZone;
	}

	public final void setTimeZone(String timeZone) {
		TimeZone = timeZone;
	}

	public final String getNextTimeZone() {
		return NextTimeZone;
	}

	public final void setNextTimeZone(String nextTimeZone) {
		NextTimeZone = nextTimeZone;
	}

	public final String getZoneName() {
		return ZoneName;
	}

	public final void setZoneName(String zoneName) {
		ZoneName = zoneName;
	}
	
	
	
	
	

}
