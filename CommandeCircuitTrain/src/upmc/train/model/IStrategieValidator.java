package upmc.train.model;

import java.util.ArrayList;

import upmc.train.model.server.NetMonitorDecoder;

public interface IStrategieValidator 
{
	public boolean validatePosition(NetMonitorDecoder decoder, ArrayList<Train> trains) ;

}
