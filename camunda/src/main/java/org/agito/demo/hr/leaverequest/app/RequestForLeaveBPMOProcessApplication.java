package org.agito.demo.hr.leaverequest.app;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.PreUndeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;

import de.agito.cps.core.annotations.BPMOApplication;
import de.agito.cps.process.camunda.app.BPMOApplicationHelper;

@BPMOApplication
@ProcessApplication("HR_RequestForLeave")
public class RequestForLeaveBPMOProcessApplication extends ServletProcessApplication {

	@PostDeploy
	public void deployBPMO() {
		BPMOApplicationHelper.INSTANCE.deploy(this);
	}

	@PreUndeploy
	public void undeployBPMO() {
		BPMOApplicationHelper.INSTANCE.undeploy(this);
	}

}
