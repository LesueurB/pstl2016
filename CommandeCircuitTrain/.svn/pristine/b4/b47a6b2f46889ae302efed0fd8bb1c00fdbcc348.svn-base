package upmc.train.model.server;

import upmc.train.model.Train;
import upmc.train.model.TrainSimulator;
import upmc.train.model.communication.NetMonitor;

public class CircuitFactorySimulator extends CircuitFactory 
{
	public Train getNewTrain(String adresse, NetMonitor communication)
	{
		return new TrainSimulator(adresse, communication) ;
	}

}
