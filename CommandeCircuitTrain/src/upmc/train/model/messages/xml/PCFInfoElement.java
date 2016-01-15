package upmc.train.model.messages.xml;

import upmc.train.constantes.IConstantes;

public class PCFInfoElement extends PCFElement implements IConstantes
{
	private String contenu = "" ;
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	private EStatus status ;
	public String getContenu() {
		return contenu;
	}

	public EStatus getStatus() {
		return status;
	}


	
	public PCFInfoElement(String contenu, String status)
	{
		this.contenu = contenu ;
		if (status.equalsIgnoreCase("OK"))
			this.status = EStatus.OK;
		else
			this.status = EStatus.KO;
	}
	
	private String getStatusAsString()
	{
		if (status == EStatus.OK)
			return "ok" ;
		else
			return "ko" ;
	}
	
	public String getFragmentXML()
	{
		return "<info status=\"" + this.getStatusAsString() + "\">" + this.contenu + "</info>";
	}
}
