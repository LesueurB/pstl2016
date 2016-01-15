package upmc.train.model;

import java.util.ArrayList;

import upmc.train.model.exception.NoSuchElementException;


public class Topography 
{
	private ArrayList<Canton> cantonsDepart = new ArrayList<Canton>() ;


	public void setCantonsDepart(ArrayList<Canton> cantonsDepart) {
		this.cantonsDepart = cantonsDepart;
	}

	private ArrayList<Canton> globalListCanton = new ArrayList<Canton>() ;
	private ArrayList<Light> lights = new ArrayList<Light>() ;
	private ArrayList<Switch> switches = new ArrayList<Switch>() ;
	
	public ArrayList<Light> getLights() {
		return lights;
	}
	public ArrayList<Switch> getSwitches() {
		return switches;
	}
	public Switch getSwitchByTrunkId(String num) throws NoSuchElementException
	{
		for(Switch s : this.switches)
		{
			if (s.getTrunk().getNom().equals(num))
				return s ;
		}
		throw new NoSuchElementException("Switch " + num + " does not exist") ;		
	}
	
	public Switch getSwitchById(String num) throws NoSuchElementException
	{
		for(Switch s : this.switches)
		{
			if (s.getNumero().equals(num))
				return s ;
		}
		throw new NoSuchElementException("Switch " + num + " does not exist") ;
	}	
	public void addSwitch(Switch s)
	{
		this.switches.add(s) ;
	}		
	public void addCanton(Canton c)
	{
		this.cantonsDepart.add(c) ;
	}
	
	public void addLight(Light l)
	{
		this.lights.add(l) ;
	}
	
	public void addToGlobalList(Canton c)
	{
		if (this.cantonsDepart.isEmpty())
			this.cantonsDepart.add(c) ;
		this.globalListCanton.add(c) ;
	}	
	
	public void removeCanton(Canton c)
	{
		this.cantonsDepart.remove(c) ;
	}	
	
	public ArrayList<Canton> getCantonsDepart()
	{
		return this.cantonsDepart ;
	}
	

	public Light getLightByid(String num) throws NoSuchElementException
	{
		for(Light l : this.lights)
		{
			if (l.getNumero().equals(num))
				return l ;
		}
		throw new NoSuchElementException("Light " + num + " does not exist") ;
	}	
	
	public Canton getCantonByid(int num)
	{
		for(Canton c : this.globalListCanton)
		{
			if (c.getNumero() == num)
				return c ;
		}
		return null ;
	}
	
	public Canton getCantonByName(String name)
	{
		for(Canton c : this.globalListCanton)
		{
			if (c.getNom().equals(name))
				return c ;
		}
		return null ;
	}
	public ArrayList<Canton> getGlobalListCanton() {
		return globalListCanton;
	}

	public Light getLightByName(String capteur) throws NoSuchElementException {
		for(Canton c : this.globalListCanton)
		{
			if (c.getNom().equals(capteur))
				return c.getLight() ;
		}
		throw new NoSuchElementException("Light attached to sensor" + capteur + " does not exist") ;
	}
	
}
