package org.agito.demo.hr.leaverequest;

// @@begin imports

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConstants;

import org.agito.demo.hr.leaverequest.RequestForLeaveAccess.LeaveFrom;
import org.agito.demo.hr.leaverequest.RequestForLeaveAccess.LeaveTo;
import org.agito.demo.hr.leaverequest.RequestForLeaveAccess.Type;
import org.agito.demo.hr.leaverequest.resources.LeaveRequestTextResource;
import org.agito.demo.hr.leaverequest.resources.LeaveRequestTextResourceUtils;

import de.agito.cps.core.annotations.BPMO;
import de.agito.cps.core.annotations.Expression;
import de.agito.cps.core.annotations.ExpressionDependency;
import de.agito.cps.core.bpmo.ExpressionType;
import de.agito.cps.core.bpmo.MessageSeverity;
import de.agito.cps.core.bpmo.api.controller.BPMOController;
import de.agito.cps.core.bpmo.api.controller.IBPMOControllerContext;
import de.agito.cps.core.logger.Logger;
import de.agito.cps.core.utils.ConvertUtils;

// @@end

// @@begin head:controller
/**
 * BPMOController for RequestForLeave.
 * 
 * @author agito
 */
// @@end
@BPMO(id = "RequestForLeave", version = "1.0.0", xml = "org/agito/demo/hr/leaverequest/RequestForLeave.bpmo")
public class RequestForLeaveController
		extends
		BPMOController<RequestForLeaveAccess, RequestForLeaveAction, RequestForLeaveLifecycle, RequestForLeaveLanguage, RequestForLeaveProcessActivity, RequestForLeave> {

	public RequestForLeaveController(final IBPMOControllerContext context) {
		super(context);
	}

	// @@begin head:validate:LeaveTo
	/**
	 * Hook for validate expression of LeaveTo
	 */
	// @@end
	@Expression(artifact = "RequestForLeave$LeaveTo", type = ExpressionType.VALIDATION)
	@ExpressionDependency({ "RequestForLeave$Type", "RequestForLeave$LeaveFrom" })
	public boolean cpsValidateLeaveTo(final RequestForLeaveAccess bpmoAccess) {
		final LeaveTo leaveTo = bpmoAccess.getLeaveTo();
		final Type type = bpmoAccess.getType();
		final LeaveFrom leaveFrom = bpmoAccess.getLeaveFrom();
		/*
		 * date must not before LeaveFrom, and max working days depends on type
		 */
		// @@begin body:validate:LeaveTo

		if (leaveFrom.getValue() != null && leaveTo.getValue() != null) {
			if (leaveFrom.getValue().compare(leaveTo.getValue()) == DatatypeConstants.GREATER) {
				leaveTo.addMessage(MessageSeverity.ERROR, "000", LeaveRequestTextResourceUtils.getText(
						LeaveRequestTextResource.MESSAGE_DATE_MUST_NOT_BEFORE,
						ConvertUtils.toString(leaveFrom.getValue(), null)));
				return false;
			}
			if (type.getValueKey() != null) {
				if (calculateDays(leaveFrom.getValueAsGregorianCalendar(), leaveTo.getValueAsGregorianCalendar(),
						new HashSet<Calendar>(), Calendar.SUNDAY, Calendar.SATURDAY) > Integer.valueOf(
						type.getValueKey()).intValue()) {
					leaveTo.addMessage(MessageSeverity.ERROR, "000", LeaveRequestTextResourceUtils.getText(
							LeaveRequestTextResource.MESSAGE_MAX_WORKINGDAYS_EXCEEDED, type.getValue().getValue(),
							type.getValueKey()));
					return false;
				}
			}
		}
		return true;

		// @@end
	}

	// @@begin head:calculate:LeaveDays
	/**
	 * Hook for calculate expression of LeaveDays
	 */
	// @@end
	@Expression(artifact = "RequestForLeave$LeaveDays", type = ExpressionType.CALCULATE)
	@ExpressionDependency({ "RequestForLeave$LeaveTo", "RequestForLeave$LeaveFrom" })
	public BigDecimal cpsCalculateLeaveDays(final RequestForLeaveAccess bpmoAccess) {
		final LeaveTo leaveTo = bpmoAccess.getLeaveTo();
		final LeaveFrom leaveFrom = bpmoAccess.getLeaveFrom();
		// @@begin body:calculate:LeaveDays

		return new BigDecimal(calculateDays(leaveFrom.getValueAsGregorianCalendar(),
				leaveTo.getValueAsGregorianCalendar(), new HashSet<Calendar>(), Calendar.SUNDAY, Calendar.SATURDAY));

		// @@end
	}

	// @@begin head:calculate:LeaveDaysTotal
	/**
	 * Hook for calculate expression of LeaveDaysTotal
	 */
	// @@end
	@Expression(artifact = "RequestForLeave$LeaveDaysTotal", type = ExpressionType.CALCULATE)
	@ExpressionDependency({ "RequestForLeave$LeaveTo", "RequestForLeave$LeaveFrom" })
	public BigDecimal cpsCalculateLeaveDaysTotal(final RequestForLeaveAccess bpmoAccess) {
		final LeaveTo leaveTo = bpmoAccess.getLeaveTo();
		final LeaveFrom leaveFrom = bpmoAccess.getLeaveFrom();
		// @@begin body:calculate:LeaveDaysTotal
		return new BigDecimal(calculateDays(leaveFrom.getValueAsGregorianCalendar(),
				leaveTo.getValueAsGregorianCalendar(), new HashSet<Calendar>()));
		// @@end
	}

	// @@begin others

	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(RequestForLeaveController.class);

	@Override
	public Object cpsExecuteBPMOAction(RequestForLeaveAccess bpmoAccess, RequestForLeaveAction action,
			Map<String, Object> parameters) {

		switch (action) {
		case COMMIT_LEAVE_TIME:
			// finalize days of leave in HR System
			break;
		case PURGE_LEAVETIME:
			// purge days of leave in HR System
			break;
		case REPORT_LEAVE_TIME:
			// report days of leave in HR System

			// convert LeaveFrom to ISO-8601
			return DatatypeConverter.printDateTime(bpmoAccess.getLeaveFrom().getValue().toGregorianCalendar());
		}
		return super.cpsExecuteBPMOAction(bpmoAccess, action, parameters);
	}

	@Override
	public void cpsBeforeSaveBPMO(RequestForLeaveAccess bpmoAccess) {

		// calculate the title
		Map<RequestForLeaveLanguage, String> titles = new HashMap<RequestForLeaveLanguage, String>();
		for (RequestForLeaveLanguage language : RequestForLeaveLanguage.values())
			titles.put(language, String.format(
					"%s: %s - %s",
					bpmoAccess.getType().getValue() == null ? "" : bpmoAccess.getType().getValue(language).getValue(),
					bpmoAccess.getLeaveFrom().getValue() == null ? "" : ConvertUtils.toString(bpmoAccess.getLeaveFrom()
							.getValue(), language.getLocale()),
					bpmoAccess.getLeaveTo().getValue() == null ? "" : ConvertUtils.toString(bpmoAccess.getLeaveTo()
							.getValue(), language.getLocale())));
		bpmoAccess.getBPMO().setTitle(titles);

	}

	private static int calculateDays(Calendar start, Calendar end, Collection<Calendar> holidays,
			int... ignoreDayOfWeek) {
		int days = 0;
		if (start != null && end != null)
			while (start.compareTo(end) != DatatypeConstants.GREATER) {
				int dayOfWeek = start.get(Calendar.DAY_OF_WEEK);
				boolean isCountDay = true;
				for (int i : ignoreDayOfWeek) {
					if (dayOfWeek == i) {// TODO check holidays
						isCountDay = false;
						break;
					}
				}
				if (isCountDay)
					days++;
				start.add(Calendar.DAY_OF_WEEK, 1);
			}
		return days;
	}

	// @@end
}
