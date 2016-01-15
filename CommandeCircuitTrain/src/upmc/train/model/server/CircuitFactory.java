package upmc.train.model.server;

import upmc.train.model.Train;
import upmc.train.model.communication.NetMonitor;

public class CircuitFactory 
{
	public Train getNewTrain(String adresse, NetMonitor communication)
	{
		return new Train(adresse, communication) ;
	}

}
