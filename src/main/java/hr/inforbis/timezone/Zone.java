package hr.inforbis.timezone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="TimeZone", schema="MData")
public class Zone {

	private static final long serialVersionUID = -1;
	
	public Zone() {}
	
	public Zone(String countryCode, String zoneName, String zoneCode, Long utcOffset,
			 Long dstStart,Long dstEnd,Integer dst, String timeZone, String nextTimeZone) {
		
		this.countryCode = countryCode;
		this.zoneName = zoneName;		
		this.utcOffset = utcOffset;
		this.dstStart = dstStart;
		this.dstEnd = dstEnd;
		this.dst = dst;		
		this.timeZone = timeZone;
		this.nextTimeZone = nextTimeZone;		
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	    
	private Integer timeZoneID;
	private String countryCode;
		
	private String zoneName;
	
	private Long dstStart;
	private Long dstEnd;
	private Integer dst;
	private Long utcOffset;
	private String timeZone;
	private String nextTimeZone;		

	public final Integer getTimeZoneID() {
		return timeZoneID;
	}

	public final void setTimeZoneID(Integer timeZoneID) {
		this.timeZoneID = timeZoneID;
	}

	public final String getCountryCode() {
		return countryCode;
	}

	public final void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public final String getZoneName() {
		return zoneName;
	}

	public final void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public final Long getUtcOffset() {
		return utcOffset;
	}

	public final void setUtcOffset(Long utcOffset) {
		this.utcOffset = utcOffset;
	}

	public final Long getDstStart() {
		return dstStart;
	}

	public final void setDstStart(Long dstStart) {
		this.dstStart = dstStart;
	}

	public final Long getDstEnd() {
		return dstEnd;
	}

	public final void setDstEnd(Long dstEnd) {
		this.dstEnd = dstEnd;
	}

	public final Integer getDst() {
		return dst;
	}

	public final void setDst(Integer dst) {
		this.dst = dst;
	}

	public final String getTimeZone() {
		return timeZone;
	}

	public final void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public final String getNextTimeZone() {
		return nextTimeZone;
	}

	public final void setNextTimeZone(String nextTimeZone) {
		this.nextTimeZone = nextTimeZone;
	}
	
	
	

	
	
}
