package upmc.train.model.communication;

import upmc.train.model.Train;
import upmc.train.model.communication.exception.CommunicationUSBException;
import upmc.train.model.communication.exception.TrainCommandException;

public class NetMonitorWithoutWiThrottle extends NetMonitor
{
	public void addNewTrain(String adresseTrain)
	{  
	}
	//mise en place des elements de communication
	public void addNewArduino(String port)
	{
		CommunicationArduino com = new CommunicationArduinoTotale() ;
		try {
			com.connect(port) ;
			com.initIOStream() ;
		    com.initListener() ;
			this.comUSB.add(com) ;
			com.addMessageListener(this);
		} 
		catch (CommunicationUSBException e) {
			System.out.println(e.getMessage());
		}

	}
	   public void emergencyStop(Train train)
	   {	
			for(CommunicationArduino ca : this.comUSB)
			{
				ca.emergencyStop(train);
			}	    	
	   }
	 
	    public void setSpeed(Train train, int speed) throws TrainCommandException
	    {
			for(CommunicationArduino ca : this.comUSB)
			{
				ca.setSpeed(train, speed);
			}	    	
	    }
	    
	    public void reverse(Train train) throws TrainCommandException
	    {
			for(CommunicationArduino ca : this.comUSB)
			{
				ca.reverse(train);
			}
	    }
	    
	    public void forward(Train train) throws TrainCommandException
	    {
			for(CommunicationArduino ca : this.comUSB)
			{
				ca.forward(train);
			}
	    }
	    	
}
