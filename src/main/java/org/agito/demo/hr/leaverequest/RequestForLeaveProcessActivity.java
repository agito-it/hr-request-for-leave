package org.agito.demo.hr.leaverequest;


import de.agito.cps.core.bpmo.api.enums.IProcessActivity;


/**
 * Process Activity Enum for RequestForLeave.
 *
 * @author Jörg Burmeister
 */
public enum RequestForLeaveProcessActivity implements IProcessActivity {

	$DRAFT,
	Approver,
	Requester;

}

