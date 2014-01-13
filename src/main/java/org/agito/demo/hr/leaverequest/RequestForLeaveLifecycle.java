package org.agito.demo.hr.leaverequest;


import de.agito.cps.core.bpmo.api.enums.ILifecycle;


/**
 * Lifecycle Enum for RequestForLeave.
 *
 * @author Jörg Burmeister
 */
public enum RequestForLeaveLifecycle implements ILifecycle {

	New(false, "HR_RequestForLeave_RequestForLeave");

	private RequestForLeaveLifecycle(boolean supportsOriginalValue, String processDefinitionId) { this.supportsOriginalValue = supportsOriginalValue; this.processDefinitionId = processDefinitionId; }
	private boolean supportsOriginalValue;
	private String processDefinitionId;
	public boolean supportsOriginalValue() { return supportsOriginalValue; }
	public String getProcessDefinitionId() { return processDefinitionId; }

}

