package upmc.train.model.messages.xml;

public class PCFPositionElement  extends PCFElement
{
	private PCFCapteurElement  debut,fin ;
	public PCFCapteurElement getDebut() {
		return debut;
	}

	public void setDebut(PCFCapteurElement debut) {
		this.debut = debut;
	}

	public PCFCapteurElement getFin() {
		return fin;
	}

	public void setFin(PCFCapteurElement fin) {
		this.fin = fin;
	}

	public PCFTrainElement getTrain() {
		return train;
	}

	public void setTrain(PCFTrainElement train) {
		this.train = train;
	}

	private PCFTrainElement train;
	
	public String getFragmentXML()
	{
		String result = "<position>" ;
		result += "<before>" + fin.getFragmentXML() + "</before>";
		result += train.getFragmentXML() ;
		result += "<after>" + debut.getFragmentXML() + "</after>";	
		result += "</position>" ;
		return result ;
	}
}
