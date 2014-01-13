package org.agito.demo.hr.leaverequest;

// @@begin imports

import de.agito.cps.core.annotations.BPMO;
import de.agito.cps.core.bpmo.api.controller.BPMOController;
import de.agito.cps.core.bpmo.api.controller.IBPMOControllerContext;
import de.agito.cps.core.logger.Logger;
import org.agito.demo.hr.leaverequest.RequestForLeave;
import org.agito.demo.hr.leaverequest.RequestForLeaveAccess;
import org.agito.demo.hr.leaverequest.RequestForLeaveAction;
import org.agito.demo.hr.leaverequest.RequestForLeaveLanguage;
import org.agito.demo.hr.leaverequest.RequestForLeaveLifecycle;
import org.agito.demo.hr.leaverequest.RequestForLeaveProcessActivity;

// @@end

// @@begin head:controller
/**
 * BPMOController for RequestForLeave.
 * 
 * @author Jörg Burmeister
 */
// @@end
@BPMO(id = "RequestForLeave", version = "1.0.0", xml = "org/agito/demo/hr/leaverequest/RequestForLeave.bpmo")
public class RequestForLeaveController extends BPMOController<RequestForLeaveAccess, RequestForLeaveAction, RequestForLeaveLifecycle, RequestForLeaveLanguage, RequestForLeaveProcessActivity, RequestForLeave> {

	public RequestForLeaveController(final IBPMOControllerContext context) {
		super(context);
	}

	// @@begin others

	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(RequestForLeaveController.class);

	// @@end
}
