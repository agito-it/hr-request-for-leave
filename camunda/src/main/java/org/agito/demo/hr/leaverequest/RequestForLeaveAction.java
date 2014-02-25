package org.agito.demo.hr.leaverequest;


import de.agito.cps.core.bpmo.api.enums.IAction;


/**
 * BPMO Actions for RequestForLeave.
 *
 * @author andreas.weise
 */
public enum RequestForLeaveAction implements IAction {

	REPORT_LEAVE_TIME,
	COMMIT_LEAVE_TIME,
	PURGE_LEAVETIME;

}

