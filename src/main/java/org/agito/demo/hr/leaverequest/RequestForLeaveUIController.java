package org.agito.demo.hr.leaverequest;

// @@begin imports

import de.agito.cps.core.logger.Logger;
import de.agito.cps.ui.vaadin.bpmo.BPMOUIController;
import de.agito.cps.ui.vaadin.bpmo.IBPMOUIControllerContext;
import de.agito.cps.ui.vaadin.bpmo.annotation.Navigation;
import de.agito.cps.ui.vaadin.bpmo.annotation.StyleController;
import de.agito.cps.ui.vaadin.bpmo.enums.NavigationType;
import de.agito.cps.ui.vaadin.bpmo.styles.IFlowStyleController;
import org.agito.demo.hr.leaverequest.RequestForLeave;
import org.agito.demo.hr.leaverequest.RequestForLeaveAccess;
import org.agito.demo.hr.leaverequest.RequestForLeaveAction;
import org.agito.demo.hr.leaverequest.RequestForLeaveLanguage;
import org.agito.demo.hr.leaverequest.RequestForLeaveLifecycle;
import org.agito.demo.hr.leaverequest.RequestForLeaveProcessActivity;

// @@end

// @@begin head:uicontroller
/**
 * Vaadin UI Controller for RequestForLeave.
 * 
 * @author Jörg Burmeister
 */
// @@end
public class RequestForLeaveUIController extends BPMOUIController<RequestForLeaveAccess, RequestForLeaveAction, RequestForLeaveLifecycle, RequestForLeaveLanguage, RequestForLeaveProcessActivity, RequestForLeave> {

	public RequestForLeaveUIController(final IBPMOUIControllerContext context) {
		super(context);
	}

	// @@begin head:init:RequestForLeave
	/**
	 * Hook for node element init of RequestForLeave
	 */
	// @@end
	@Navigation(artifact = "RequestForLeave", type = NavigationType.NODE_ELEMENT_INIT)
	public void cpsInitRequestForLeave(final RequestForLeaveAccess bpmoAccess) {
		// @@begin body:init:RequestForLeave

		// @@end
	}

	// @@begin head:destroy:RequestForLeave
	/**
	 * Hook for node element destroy of RequestForLeave
	 */
	// @@end
	@Navigation(artifact = "RequestForLeave", type = NavigationType.NODE_ELEMENT_DESTROY)
	public void cpsDestroyRequestForLeave(final RequestForLeaveAccess bpmoAccess) {
		// @@begin body:destroy:RequestForLeave

		// @@end
	}

	// @@begin others

	@StyleController
	public IFlowStyleController styleController;

	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(RequestForLeaveUIController.class);

	// @@end
}
