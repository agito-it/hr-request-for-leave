package org.agito.demo.hr.leaverequest;


import de.agito.cps.core.bpmo.ControlType;
import de.agito.cps.core.bpmo.DataTypeFactory;
import de.agito.cps.core.bpmo.IEnumInspector;
import de.agito.cps.core.bpmo.api.enums.IBODataElement;
import de.agito.cps.core.bpmo.api.enums.IBOId;
import de.agito.cps.core.bpmo.api.enums.IBONode;


/**
 * Enum for RequestForLeave.
 *
 * @author Jörg Burmeister
 */
public enum RequestForLeave implements IBODataElement {

	/**
	 * <b>Type</b>
	 * <p>
	 * <i>{@link DefinitionArtifactType CHARACTERISTIC_DEFINITION}<i><br>
	 * <i>{@link DataType ENUM}<i><br>
	 * <i>{@link ControlType INTERACTIVE}<i><br>
	 */
	Type("Type", "RequestForLeave$Type", ControlType.INTERACTIVE),

	/**
	 * <b>Leave from</b>
	 * <p>
	 * <i>{@link DefinitionArtifactType CHARACTERISTIC_DEFINITION}<i><br>
	 * <i>{@link DataType DATE}<i><br>
	 * <i>{@link ControlType INTERACTIVE}<i><br>
	 */
	LeaveFrom("LeaveFrom", "RequestForLeave$LeaveFrom", ControlType.INTERACTIVE),

	/**
	 * <b>Leave to</b>
	 * <p>
	 * <i>{@link DefinitionArtifactType CHARACTERISTIC_DEFINITION}<i><br>
	 * <i>{@link DataType DATE}<i><br>
	 * <i>{@link ControlType INTERACTIVE}<i><br>
	 */
	LeaveTo("LeaveTo", "RequestForLeave$LeaveTo", ControlType.INTERACTIVE),

	/**
	 * <b>Business days</b>
	 * <p>
	 * <i>{@link DefinitionArtifactType CHARACTERISTIC_DEFINITION}<i><br>
	 * <i>{@link DataType NUMERIC}<i><br>
	 * <i>{@link ControlType DEFAULT}<i><br>
	 */
	LeaveDays("LeaveDays", "RequestForLeave$LeaveDays", ControlType.DEFAULT),

	/**
	 * <b>Calendar days</b>
	 * <p>
	 * <i>{@link DefinitionArtifactType CHARACTERISTIC_DEFINITION}<i><br>
	 * <i>{@link DataType NUMERIC}<i><br>
	 * <i>{@link ControlType DEFAULT}<i><br>
	 */
	LeaveDaysTotal("LeaveDaysTotal", "RequestForLeave$LeaveDaysTotal", ControlType.DEFAULT),

	/**
	 * <b>Details</b>
	 * <p>
	 * <i>{@link DefinitionArtifactType CHARACTERISTIC_DEFINITION}<i><br>
	 * <i>{@link DataType STRING}<i><br>
	 * <i>{@link ControlType INTERACTIVE}<i><br>
	 */
	Details("Details", "RequestForLeave$Details", ControlType.INTERACTIVE);

	/**
	 * <b>BPMO Identifier</b>
	 */
	public final static IBOId $BPMO = new IBOId.BOId("RequestForLeave");

	/**
	 * <b>Node Identifier</b>
	 */
	public final static IBONode $ID = new IBONode.BONode("RequestForLeave", "RequestForLeave", ControlType.DEFAULT);

	private final static IEnumInspector ENUM_INSPECTOR = DataTypeFactory.getInstance().createEnumInspector(RequestForLeave.class);
	private RequestForLeave(String id, String path, ControlType controlType) { this.id = id; this.path = path; this.controlType = controlType; }
	private String id;
	public String getId() { return id; }
	private String path;
	public String getPath() { return path; }
	private ControlType controlType;
	public ControlType getControlType() { return controlType; }

	/**
	 * Retrieve a content element for a given path.
	 * 
	 * @return
	 *             Element requested or {@link IEnumInspector}.UNDEFINED_DATA if element does not exist. 
	 * @throws ClassCastException
	 *             when the corresponding element for given path is not a content element.
	 */
	public static IBODataElement getIBODataElementByPath(String path) throws ClassCastException { return ENUM_INSPECTOR.getIBODataElementByPath(path); }

	/**
	 * Retrieve a node for a given path.
	 * 
	 * @return
	 *             Node requested or {@link IEnumInspector}.UNDEFINED_NODE if node does not exist. 
	 * @throws ClassCastException
	 *             when the corresponding element for given path is not a node.
	 */ 
	public static IBONode getIBONodeByPath(String path) throws ClassCastException { return ENUM_INSPECTOR.getIBONodeByPath(path); }

}

