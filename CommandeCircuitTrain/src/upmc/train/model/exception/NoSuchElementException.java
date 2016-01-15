package upmc.train.model.exception;

public class NoSuchElementException extends Exception {
	private String message ;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2215297231328470920L;
	
	public NoSuchElementException(String message)
	{
		this.message = message ;
	}
	public NoSuchElementException()
	{
		this.message = "No such element" ;
	}
}
