package upmc.train.model;

import upmc.train.model.communication.NetMonitor;

public class Switch {
	private String numero = "" ;
	private String type = "1-2" ;
	private Canton position ;
	public Canton getPosition() {
		return position;
	}

	public void setPosition(Canton position) {
		this.position = position;
	}

	public Canton getBranch0() {
		return branch0;
	}

	public void setBranch0(Canton branch0) {
		this.branch0 = branch0;
		this.position = this.branch0 ;
	}

	public Canton getBranch1() {
		return branch1;
	}

	public void setBranch1(Canton branch1) {
		this.branch1 = branch1;
	}

	public Canton getTrunk() {
		return trunk;
	}

	public void setTrunk(Canton trunk) {
		this.trunk = trunk;
		trunk.setSwitch(this);
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	private Canton branch0 ;
	private Canton branch1 ;
	private Canton trunk ;
	
	
	public Switch(String num)
	{
		this.numero = num ;
	}
	
	public String getNumero()
	{
		return this.numero ;
	}

	private void sendChange( NetMonitor com)
	{
		if (com != null)
		{
			if (this.position == this.branch0)
				com.changeAiguillage(this.getNumero(), "0");
			else
				com.changeAiguillage(this.getNumero(), "1");
		}
	}
	
	public void setBranch(String nomCanton, NetMonitor com) 
	{
		if (!this.position.getNom().equals(nomCanton))
		{
			if (this.getBranch0().getNom().equals(nomCanton))
			{
				this.position = this.getBranch0() ;
				this.sendChange(com);
			}
			if (this.getBranch1().getNom().equals(nomCanton))
			{
				this.position = this.getBranch1() ;
				this.sendChange(com);
			}			
		}
	}

	public void switchAiguillage(NetMonitor com) {
		if (this.position == this.getBranch0())
		{
			this.position = this.getBranch1() ;
			this.sendChange(com);
		}
		else
		{
			this.position = this.getBranch0() ;
			this.sendChange(com);
		}
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		
	}

}
