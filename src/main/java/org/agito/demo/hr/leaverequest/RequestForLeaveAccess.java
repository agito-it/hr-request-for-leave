package org.agito.demo.hr.leaverequest;


import de.agito.cps.core.bpmo.IKeywordEntry;
import de.agito.cps.core.bpmo.INodeElement;
import de.agito.cps.core.bpmo.api.access.BPMOAccess;
import de.agito.cps.core.bpmo.api.access.CharacteristicAccess;
import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.XMLGregorianCalendar;
import org.agito.demo.hr.leaverequest.RequestForLeaveLanguage;


/**
 * BPMOAccess for RequestForLeave.
 * 
 * @author Jörg Burmeister
 */
public final class RequestForLeaveAccess extends BPMOAccess<RequestForLeaveAccess> {

	public RequestForLeaveAccess(INodeElement context) { super(context); }

	/**
	 * <b>Type</b>
	 * <p>
	 * <i>{@link DefinitionArtifactType CHARACTERISTIC_DEFINITION}<i><br>
	 * <i>{@link DataType ENUM}<i><br>
	 * <i>{@link ControlType INTERACTIVE}<i><br>
	 */
	public Type getType() { return super.<Type>getCharacteristicAccess(Type.class, "RequestForLeave$Type"); }
	/**
	 * <b>Leave from</b>
	 * <p>
	 * <i>{@link DefinitionArtifactType CHARACTERISTIC_DEFINITION}<i><br>
	 * <i>{@link DataType DATE}<i><br>
	 * <i>{@link ControlType INTERACTIVE}<i><br>
	 */
	public LeaveFrom getLeaveFrom() { return super.<LeaveFrom>getCharacteristicAccess(LeaveFrom.class, "RequestForLeave$LeaveFrom"); }
	/**
	 * <b>Leave to</b>
	 * <p>
	 * <i>{@link DefinitionArtifactType CHARACTERISTIC_DEFINITION}<i><br>
	 * <i>{@link DataType DATE}<i><br>
	 * <i>{@link ControlType INTERACTIVE}<i><br>
	 */
	public LeaveTo getLeaveTo() { return super.<LeaveTo>getCharacteristicAccess(LeaveTo.class, "RequestForLeave$LeaveTo"); }
	/**
	 * <b>Business days</b>
	 * <p>
	 * <i>{@link DefinitionArtifactType CHARACTERISTIC_DEFINITION}<i><br>
	 * <i>{@link DataType NUMERIC}<i><br>
	 * <i>{@link ControlType DEFAULT}<i><br>
	 */
	public LeaveDays getLeaveDays() { return super.<LeaveDays>getCharacteristicAccess(LeaveDays.class, "RequestForLeave$LeaveDays"); }
	/**
	 * <b>Calendar days</b>
	 * <p>
	 * <i>{@link DefinitionArtifactType CHARACTERISTIC_DEFINITION}<i><br>
	 * <i>{@link DataType NUMERIC}<i><br>
	 * <i>{@link ControlType DEFAULT}<i><br>
	 */
	public LeaveDaysTotal getLeaveDaysTotal() { return super.<LeaveDaysTotal>getCharacteristicAccess(LeaveDaysTotal.class, "RequestForLeave$LeaveDaysTotal"); }
	/**
	 * <b>Details</b>
	 * <p>
	 * <i>{@link DefinitionArtifactType CHARACTERISTIC_DEFINITION}<i><br>
	 * <i>{@link DataType STRING}<i><br>
	 * <i>{@link ControlType INTERACTIVE}<i><br>
	 */
	public Details getDetails() { return super.<Details>getCharacteristicAccess(Details.class, "RequestForLeave$Details"); }

	public final static class Type extends CharacteristicAccess {
		protected Type(BPMOAccess<?> bpmoAccess, String path) { super(bpmoAccess, path); }
		public IKeywordEntry getValue() { return super.<IKeywordEntry>getCurrentValue(); }
		public IKeywordEntry getValue(RequestForLeaveLanguage language) { return super.<IKeywordEntry>getCurrentValue(language); }
		public String getValueKey() { return super.getCurrentValueKey(); }
		public void setValue(String value) { super.setCurrentValue(value); }
	}

	public final static class LeaveFrom extends CharacteristicAccess {
		protected LeaveFrom(BPMOAccess<?> bpmoAccess, String path) { super(bpmoAccess, path); }
		public XMLGregorianCalendar getValue() { return super.<XMLGregorianCalendar>getCurrentValue(); }
		public Date getValueAsDate() { return super.getCurrentValueDate(Date.class); }
		public GregorianCalendar getValueAsGregorianCalendar() { return super.getCurrentValueDate(GregorianCalendar.class); }
		public void setValue(XMLGregorianCalendar value) { super.setCurrentValue(value); }
		public void setValue(Date value) { super.setCurrentValueDate(value); }
		public void setValue(GregorianCalendar value) { super.setCurrentValueDate(value); }
	}

	public final static class LeaveTo extends CharacteristicAccess {
		protected LeaveTo(BPMOAccess<?> bpmoAccess, String path) { super(bpmoAccess, path); }
		public XMLGregorianCalendar getValue() { return super.<XMLGregorianCalendar>getCurrentValue(); }
		public Date getValueAsDate() { return super.getCurrentValueDate(Date.class); }
		public GregorianCalendar getValueAsGregorianCalendar() { return super.getCurrentValueDate(GregorianCalendar.class); }
		public void setValue(XMLGregorianCalendar value) { super.setCurrentValue(value); }
		public void setValue(Date value) { super.setCurrentValueDate(value); }
		public void setValue(GregorianCalendar value) { super.setCurrentValueDate(value); }
	}

	public final static class LeaveDays extends CharacteristicAccess {
		protected LeaveDays(BPMOAccess<?> bpmoAccess, String path) { super(bpmoAccess, path); }
		public BigDecimal getValue() { return super.<BigDecimal>getCurrentValue(); }
		public void setValue(BigDecimal value) { super.setCurrentValue(value); }
	}

	public final static class LeaveDaysTotal extends CharacteristicAccess {
		protected LeaveDaysTotal(BPMOAccess<?> bpmoAccess, String path) { super(bpmoAccess, path); }
		public BigDecimal getValue() { return super.<BigDecimal>getCurrentValue(); }
		public void setValue(BigDecimal value) { super.setCurrentValue(value); }
	}

	public final static class Details extends CharacteristicAccess {
		protected Details(BPMOAccess<?> bpmoAccess, String path) { super(bpmoAccess, path); }
		public String getValue() { return super.<String>getCurrentValue(); }
		public void setValue(String value) { super.setCurrentValue(value); }
	}

}
