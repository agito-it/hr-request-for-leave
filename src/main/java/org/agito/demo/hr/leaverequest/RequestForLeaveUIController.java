package org.agito.demo.hr.leaverequest;

// @@begin imports

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import de.agito.cps.core.bpmo.BPMOState;
import de.agito.cps.core.bpmo.ClientMode;
import de.agito.cps.core.context.ClientContextFactory;
import de.agito.cps.core.logger.Logger;
import de.agito.cps.core.process.spi.ProcessAgent;
import de.agito.cps.process.activiti.ActivitiProcessAgent;
import de.agito.cps.ui.vaadin.bpmo.BPMOUIController;
import de.agito.cps.ui.vaadin.bpmo.IBPMOUIControllerContext;
import de.agito.cps.ui.vaadin.bpmo.annotation.Navigation;
import de.agito.cps.ui.vaadin.bpmo.annotation.StyleController;
import de.agito.cps.ui.vaadin.bpmo.context.UIClientContextAccessor;
import de.agito.cps.ui.vaadin.bpmo.enums.NavigationType;
import de.agito.cps.ui.vaadin.bpmo.enums.SeparatorStyle;
import de.agito.cps.ui.vaadin.bpmo.enums.UNIT;
import de.agito.cps.ui.vaadin.bpmo.layout.flow.IFlowLayoutManager;
import de.agito.cps.ui.vaadin.bpmo.navigation.MenuItemId;
import de.agito.cps.ui.vaadin.bpmo.styles.IFlowStyleController;
import org.agito.demo.hr.leaverequest.RequestForLeave;
import org.agito.demo.hr.leaverequest.RequestForLeaveAccess;
import org.agito.demo.hr.leaverequest.RequestForLeaveAction;
import org.agito.demo.hr.leaverequest.RequestForLeaveLanguage;
import org.agito.demo.hr.leaverequest.RequestForLeaveLifecycle;
import org.agito.demo.hr.leaverequest.RequestForLeaveProcessActivity;
import org.agito.demo.hr.leaverequest.resources.LeaveRequestTextResource;
import org.agito.demo.hr.leaverequest.resources.LeaveRequestTextResourceUtils;

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
		IFlowLayoutManager layoutManager = styleController.getLayoutManager();
		layoutManager.setWidth(700, UNIT.PIXEL);
		layoutManager
				.createAndAddSeparator()
				.setTitle(
						String.format("%s / %s", bpmoAccess.getBPMODefinition().getBPMOLabel().getText(), bpmoAccess
								.getBPMOHeader().getInitiator())).setTitleStyleName(SeparatorStyle.H1)
				.setContentWidth(600, UNIT.PIXEL).addTitleStyleName(SeparatorStyle.HR);
		layoutManager.createAndAddElements(RequestForLeave.Type);
		layoutManager.addLineBreak();
		layoutManager.addRemainingElements(RequestForLeave.Details);
		layoutManager.addLineBreak();
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

			ActivitiProcessAgent processAgent = (ActivitiProcessAgent) ClientContextFactory.getBPMOEngine()
					.getProcessAgent();
			boolean isWaitForFinish = processAgent.getActivitiProcessEngine().getRuntimeService()
					.createExecutionQuery().processInstanceId(bpmoAccess.getBPMOHeader().getProcessInstanceId())
					.activityId("Eventgateway").singleResult() != null;
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

								// read and claim TaskInstance
								processAgent.getTaskService().claim(
										processAgent.getTaskService().createTaskQuery()
												.bpmoUuid(bpmoAccess.getBPMOHeader().getBPMOUuid()).singleResult()
												.getId(), ClientContextFactory.getUserId());

								// rebuild IBPMOComponent
								UIClientContextAccessor
										.getInstance()
										.getContext(bpmoAccess.getBPMO())
										.getBPMOComponent()
										.rebuild(
												true,
												processAgent.getTaskService().createTaskQuery()
														.bpmoUuid(bpmoAccess.getBPMOHeader().getBPMOUuid())
														.singleResult());

							}
						}, null, false, ClientMode.READONLY);

			}
		}
	}
	// @@end
}
