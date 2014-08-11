package org.agito.demo.hr.leaverequest;

// @@begin imports

import org.agito.demo.hr.leaverequest.resources.LeaveRequestTextResource;
import org.agito.demo.hr.leaverequest.resources.LeaveRequestTextResourceUtils;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import de.agito.cps.commons.logging.Logger;
import de.agito.cps.core.bpmo.BPMOState;
import de.agito.cps.core.bpmo.ClientMode;
import de.agito.cps.core.context.ClientContextFactory;
import de.agito.cps.core.process.spi.ProcessAgent;
import de.agito.cps.process.camunda.CamundaProcessAgent;
import de.agito.cps.ui.vaadin.bpmo.BPMOUIController;
import de.agito.cps.ui.vaadin.bpmo.BPMOUi;
import de.agito.cps.ui.vaadin.bpmo.IBPMOUIControllerContext;
import de.agito.cps.ui.vaadin.bpmo.annotation.Navigation;
import de.agito.cps.ui.vaadin.bpmo.annotation.StyleController;
import de.agito.cps.ui.vaadin.bpmo.enums.NavigationType;
import de.agito.cps.ui.vaadin.bpmo.layout.flow.IFlowGroupContent;
import de.agito.cps.ui.vaadin.bpmo.layout.flow.IFlowLayout.Colspan;
import de.agito.cps.ui.vaadin.bpmo.layout.flow.IFlowLayout.MaxColums;
import de.agito.cps.ui.vaadin.bpmo.layout.flow.IFlowLayoutManager;
import de.agito.cps.ui.vaadin.bpmo.navigation.MenuItemId;
import de.agito.cps.ui.vaadin.bpmo.styles.IFlowStyleController;

// @@end

// @@begin head:uicontroller
/**
 * Vaadin UI Controller for RequestForLeave.
 * 
 * @author Jörg Burmeister
 */
// @@end
public class RequestForLeaveUIController
		extends
		BPMOUIController<RequestForLeaveAccess, RequestForLeaveAction, RequestForLeaveLifecycle, RequestForLeaveLanguage, RequestForLeaveProcessActivity, RequestForLeave> {

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
		IFlowLayoutManager layoutManager = styleController.getLayoutManager();
		layoutManager.setMaxCols(MaxColums.COL2);

		IFlowGroupContent groupContent = layoutManager.createAndAddGroupContent().setCaption(
				String.format("%s / %s", bpmoAccess.getBPMODefinition().getBPMOLabel().getText(), bpmoAccess
						.getBPMOHeader().getInitiator().getDisplayName()));
		groupContent.setColspan(Colspan.DIMENSION_FULL);
		groupContent.createAndAddElements(RequestForLeave.Type);
		groupContent.newLine();
		groupContent.addRemainingElements();
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

	@Override
	public void cpsInit(final RequestForLeaveAccess bpmoAccess) {
		super.cpsInit(bpmoAccess);
		if (bpmoAccess.getBPMOHeader().getBPMOState() == BPMOState.RUNNING
				&& bpmoAccess.getBPMO().getTaskInstance() == null
				&& bpmoAccess.getBPMOHeader().getInitiator().getId().equals(ClientContextFactory.getUserId())) {

			CamundaProcessAgent processAgent = (CamundaProcessAgent) ClientContextFactory.getBPMOEngine()
					.getProcessAgent();
			boolean isWaitForFinish = processAgent.getCamundaProcessEngine().getRuntimeService().createExecutionQuery()
					.processInstanceId(bpmoAccess.getBPMOHeader().getProcessInstanceId()).activityId("Eventgateway")
					.singleResult() != null;
			if (isWaitForFinish) {
				styleController.getActionMenu().add(
						styleController.getActionMenu().getMenuItemById(MenuItemId.REQUEST),
						LeaveRequestTextResource.LABEL_REQUEST_REFINE,
						LeaveRequestTextResourceUtils.getText(LeaveRequestTextResource.LABEL_REQUEST_REFINE),
						new Command() {
							private static final long serialVersionUID = 6097393723703009232L;

							@Override
							public void menuSelected(MenuItem selectedItem) {

								ProcessAgent processAgent = ClientContextFactory.getBPMOEngine().getProcessAgent();

								// trigger MessageEvent in Eventgateway
								processAgent.getProcessService().triggerIntermediateMessage(
										bpmoAccess.getBPMOHeader().getProcessInstanceId(), "RefineRequest", null);

								// read BPMO in context of new created TaskInstance
								BPMOUi.getCurrent().rebuild(
										true,
										processAgent.getTaskService().createTaskQuery()
												.bpmoUuid(bpmoAccess.getBPMOHeader().getBPMOUuid()).singleResult());

							}
						}, null, false, ClientMode.READONLY);

			}
		}
	}
	// @@end
}
