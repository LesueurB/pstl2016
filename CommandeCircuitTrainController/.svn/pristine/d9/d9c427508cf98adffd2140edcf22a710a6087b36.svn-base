package upmc.train.controller.ihm;

import java.util.ArrayList;

import upmc.train.model.Canton;

public class Node {
	private Canton canton ;
	private ArrayList<Link> in = new ArrayList<Link>() ;
	private ArrayList<Link> out = new ArrayList<Link>() ;
	public Node(Canton c)
	{
		this.setCanton(c) ;
	}
	public Canton getCanton() {
		return canton;
	}
	private void setCanton(Canton canton) {
		this.canton = canton;
	}
	
	public void addIn(Node n)
	{
		this.getIn().add(new Link(n, this)) ;
	}
	public void addOut(Node n)
	{
		this.getOut().add(new Link(this, n)) ;
	}
	/**
	 * @return the in
	 */
	public ArrayList<Link> getIn() {
		return in;
	}

	/**
	 * @return the out
	 */
	public ArrayList<Link> getOut() {
		return out;
	}

}
