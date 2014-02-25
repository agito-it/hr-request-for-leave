package org.agito.bpmo.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.agito.demo.hr.leaverequest.RequestForLeave;
import org.agito.demo.hr.leaverequest.RequestForLeaveAccess;
import org.agito.demo.hr.leaverequest.RequestForLeaveLifecycle;
import org.agito.demo.hr.leaverequest.RequestForLeaveProcessActivity;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import de.agito.cps.core.bpmo.IBPMO;
import de.agito.cps.core.context.ClientContextFactory;
import de.agito.cps.core.process.spi.eventing.ProcessAgentEventType;
import de.agito.cps.process.camunda.test.BPMODeployment;
import de.agito.cps.process.camunda.test.BPMOTestUserId;
import de.agito.cps.test.camunda.BPMOProcessEngineRule;

public class RequestForLeaveTest {

	@Rule
	public BPMOProcessEngineRule bpmoRule = new BPMOProcessEngineRule();

	@Deployment(resources = "LeaveRequestProcess.bpmn")
	@BPMODeployment(resources = "org/agito/demo/hr/leaverequest/RequestForLeave.bpmo")
	@BPMOTestUserId("bob")
	@Test
	public void testRequestForLeaveNew() {

		// create bpmo
		IBPMO bpmo = bpmoRule.getBpmoEngine().getRuntimeService()
				.createBPMO(RequestForLeave.$BPMO, RequestForLeaveLifecycle.New, "001");
		RequestForLeaveAccess requestForLeaveAccess = new RequestForLeaveAccess(bpmo.getBPMOData());

		requestForLeaveAccess.getType().setValue("999");

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		requestForLeaveAccess.getLeaveFrom().setValue(calendar);

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		requestForLeaveAccess.getLeaveTo().setValue(calendar);

		// start process
		bpmo.startProcess();
		Assert.assertNotNull(bpmoRule.getBpmoEngine().getRuntimeService().createProcessHistoryQuery()
				.processInstanceId(bpmo.getBPMOHeader().getProcessInstanceId())
				.eventType(ProcessAgentEventType.PROCESS_START).singleResult());

		// checkout and complete Approver task
		bpmo.claimTaskInstance(RequestForLeaveProcessActivity.Approver);
		bpmo.completeTaskInstance("APPROVED", null);
		Assert.assertNotNull(bpmoRule.getBpmoEngine().getRuntimeService().createProcessHistoryQuery()
				.processInstanceId(bpmo.getBPMOHeader().getProcessInstanceId())
				.eventType(ProcessAgentEventType.TASK_COMPLETE).singleResult());

		// wait for async jobs
		bpmoRule.waitForJobExecutorToProcessAllJobs(30000l, 200l);

		// check Report leave time service task
		Assert.assertNotNull(bpmoRule.getBpmoEngine().getRuntimeService().createProcessHistoryQuery()
				.processInstanceId(bpmo.getBPMOHeader().getProcessInstanceId())
				.eventType(ProcessAgentEventType.SERVICE_TASK_END).singleResult());

		// refine request
		ClientContextFactory
				.getBPMOEngine()
				.getProcessAgent()
				.getProcessService()
				.triggerIntermediateMessage(requestForLeaveAccess.getBPMOHeader().getProcessInstanceId(),
						"RefineRequest", null);

		// check Report Message event catch
		Assert.assertNotNull(bpmoRule.getBpmoEngine().getRuntimeService().createProcessHistoryQuery()
				.processInstanceId(bpmo.getBPMOHeader().getProcessInstanceId())
				.eventType(ProcessAgentEventType.INTERMEDIATE_MESSAGE_CATCH).singleResult());

		// wait for async jobs
		bpmoRule.waitForJobExecutorToProcessAllJobs(30000l, 200l);

		// check Report purge leave time service task
		Assert.assertEquals(
				bpmoRule.getBpmoEngine().getRuntimeService().createProcessHistoryQuery()
						.processInstanceId(bpmo.getBPMOHeader().getProcessInstanceId())
						.eventType(ProcessAgentEventType.SERVICE_TASK_END).list().size(), 2);

		// checkout and complete Requester task
		bpmo.claimTaskInstance(RequestForLeaveProcessActivity.Requester);
		bpmo.completeTaskInstance("CANCEL", "CANCEL");

		// assert process end
		Assert.assertNotNull(bpmoRule.getBpmoEngine().getRuntimeService().createProcessHistoryQuery()
				.bpmoUuid(bpmo.getBPMOHeader().getBPMOUuid()).eventType(ProcessAgentEventType.PROCESS_END)
				.singleResult());

	}

	@Deployment(resources = "LeaveRequestProcess.bpmn")
	@BPMODeployment(resources = "org/agito/demo/hr/leaverequest/RequestForLeave.bpmo")
	@BPMOTestUserId("bob")
	@Test
	public void testRequestForLeaveNewTimerEvent() {

		// create bpmo
		IBPMO bpmo = bpmoRule.getBpmoEngine().getRuntimeService()
				.createBPMO(RequestForLeave.$BPMO, RequestForLeaveLifecycle.New, "001");
		RequestForLeaveAccess requestForLeaveAccess = new RequestForLeaveAccess(bpmo.getBPMOData());

		requestForLeaveAccess.getType().setValue("999");
		requestForLeaveAccess.getLeaveFrom().setValue(new Date());
		requestForLeaveAccess.getLeaveTo().setValue(new Date());

		// start process
		bpmo.startProcess();
		Assert.assertNotNull(bpmoRule.getBpmoEngine().getRuntimeService().createProcessHistoryQuery()
				.processInstanceId(bpmo.getBPMOHeader().getProcessInstanceId())
				.eventType(ProcessAgentEventType.PROCESS_START).singleResult());

		// checkout and complete Approver task
		bpmo.claimTaskInstance(RequestForLeaveProcessActivity.Approver);
		bpmo.completeTaskInstance("APPROVED", null);
		Assert.assertNotNull(bpmoRule.getBpmoEngine().getRuntimeService().createProcessHistoryQuery()
				.processInstanceId(bpmo.getBPMOHeader().getProcessInstanceId())
				.eventType(ProcessAgentEventType.TASK_COMPLETE).singleResult());

		// wait for async jobs
		bpmoRule.waitForJobExecutorToProcessAllJobs(30000l, 1000l);

		// check Report timer catch
		Assert.assertNotNull(bpmoRule.getBpmoEngine().getRuntimeService().createProcessHistoryQuery()
				.processInstanceId(bpmo.getBPMOHeader().getProcessInstanceId())
				.eventType(ProcessAgentEventType.INTERMEDIATE_TIMER_CATCH).singleResult());

		// check Report Commit leave time service task
		Assert.assertEquals(
				bpmoRule.getBpmoEngine().getRuntimeService().createProcessHistoryQuery()
						.processInstanceId(bpmo.getBPMOHeader().getProcessInstanceId())
						.eventType(ProcessAgentEventType.SERVICE_TASK_END).list().size(), 2);

		// assert process end
		Assert.assertNotNull(bpmoRule.getBpmoEngine().getRuntimeService().createProcessHistoryQuery()
				.bpmoUuid(bpmo.getBPMOHeader().getBPMOUuid()).eventType(ProcessAgentEventType.PROCESS_END)
				.singleResult());

	}

}