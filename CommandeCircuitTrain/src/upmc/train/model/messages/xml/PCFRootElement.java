package upmc.train.model.messages.xml;

import java.util.ArrayList;

import upmc.train.constantes.IConstantes;


public class PCFRootElement  extends PCFElement implements IConstantes
{

	private PCFHelloElement hello = null ;


	private PCFOllehElement olleh = null ;
	private PCFTopographyElement topography = null ;
	private PCFScenarioElement scenario  = null ;
	private PCFInitElement init = null ;
	private ArrayList<PCFUpElement> up = null ;
	private PCFStartElement start = null ;
	private PCFInfoElement info = null ;
	private PCFByeElement bye = null ;
	private PCFSetElement set = null ;
	private PCFLightsElement lights = null ;
	
	
	
	
	public PCFLightsElement getLights() {
		return lights;
	}

	public void setLights(PCFLightsElement lights) {
		this.lights = lights;
	}

	private String reqId ; 
	private ETypeRequest type ;
	
	public PCFRootElement(String reqid, String type) 
	{
		this.reqId = reqid ;
		if (type.equalsIgnoreCase("request"))
			this.type = ETypeRequest.REQUEST ;
		else if (type.equalsIgnoreCase("answer"))
			this.type = ETypeRequest.ANSWER ;
		else
			this.type = ETypeRequest.ADVISE ;
	}
	
	public void setHello(PCFHelloElement hello) {
		this.hello = hello;
	}

	public void setOlleh(PCFOllehElement olleh) {
		this.olleh = olleh;
	}

	public void setTopography(PCFTopographyElement topography) {
		this.topography = topography;
	}

	public void setScenario(PCFScenarioElement scenario) {
		this.scenario = scenario;
	}

	public void setInit(PCFInitElement init) {
		this.init = init;
	}

	public void setInfo(PCFInfoElement advise) {
		this.info = advise;
	}

	public void setBye(PCFByeElement bye) {
		this.bye = bye;
	}
	
	public PCFHelloElement getHello()
	{
		return this.hello ;
	}

	public ArrayList<PCFUpElement> getUp() {
		return up;
	}

	/*public void setUp(PCFUpElement up) {
		this.up = up;
	}*/

	public PCFStartElement getStart() {
		return start;
	}

	public void setStart(PCFStartElement start) {
		this.start = start;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public ETypeRequest getType() {
		return type;
	}

	public void setType(ETypeRequest type) {
		this.type = type;
	}

	public PCFOllehElement getOlleh() {
		return olleh;
	}

	public PCFTopographyElement getTopography() {
		return topography;
	}

	public PCFScenarioElement getScenario() {
		return scenario;
	}

	public PCFInitElement getInit() {
		return init;
	}

	public PCFInfoElement getInfo() {
		return info;
	}

	public PCFByeElement getBye() {
		return bye;
	}
	public String getFragmentXML()
	{
		String result =  "<pcf reqid=\"" + this.reqId + "\" type = \"" + this.getTypeAsString()+ "\">";
		if (this.hello != null)
			result += this.hello.getFragmentXML() ;
		if (this.olleh != null)
			result += this.olleh.getFragmentXML() ;
		if (this.scenario != null)
			result += this.scenario.getFragmentXML() ;
		if (this.topography != null)
			result += this.topography.getFragmentXML() ;
		if (this.init != null)
			result += this.init.getFragmentXML() ;
		if (this.up != null)
			for(PCFUpElement elt : up)
			result += elt.getFragmentXML() ;
		if (this.start != null)
			result += this.start.getFragmentXML() ;
		if (this.info != null)
			result += this.info.getFragmentXML() ;
		if (this.bye != null)
			result += this.bye.getFragmentXML() ;
		if (this.set != null)
			result += this.set.getFragmentXML() ;
		if (this.lights != null)
			result += this.lights.getFragmentXML() ;
		return result + "</pcf>";
	}

	public PCFSetElement getSet() {
		return set;
	}

	public void setSet(PCFSetElement set) {
		this.set = set;
	}

	public String getTypeAsString() {
		if (this.type == ETypeRequest.ANSWER)
			return "answer" ;
		else if (this.type == ETypeRequest.ADVISE)
			return "advise" ;
		else
			return "request" ;
	}
	
	public String getPrefixe()
	{
		String res = "(" ;
		if (this.type == ETypeRequest.ANSWER)
			res += "A-" ;
		else if (this.type == ETypeRequest.REQUEST)
			res += "R-" ;
		else
			res += "Ad-" ;
		return res + this.reqId + ")" ;
		
	}

	public void addUp(PCFUpElement up2) {
		if (this.up == null)
			up = new ArrayList<PCFUpElement>() ;
		up.add(up2); 
		
	}

}
