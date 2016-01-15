package upmc.train.model;

import java.util.ArrayList;

import upmc.train.model.messages.xml.PCFInfoElement;
import upmc.train.model.messages.xml.PCFRootElement;
import upmc.train.model.server.NetMonitorDecoder;

public class ScenarioOneValidator implements IStrategieValidator {

	
	protected void raiseAlarm(String message, NetMonitorDecoder decoder)
	{
		PCFRootElement root = new PCFRootElement("", "advise") ;
		root.setInfo(new PCFInfoElement(message,"ko"));
		decoder.send(root) ;
	}
	
	protected Train getTrainOnExcept(Canton c, ArrayList<Train> trains, Train tr)
	{
		for(Train t : trains)
		{
			if (t != tr)
				if (t.getCanton() == c)
					return t ;

		}
		return null ;
	}
	
	protected Train getTrainOn(Canton c, ArrayList<Train> trains)
	{
		for(Train t : trains)
		{
			if (t.getCanton() == c)
				return t ;

		}
		return null ;
	}
	
	private boolean validateTrainStartAndExistAnotherTrainOnNextCanton(Train t, ArrayList<Train> trains, NetMonitorDecoder decoder)
	{
		Canton g = t.getCanton() ;
		Train t1 = this.getTrainOnExcept(g, trains,t) ;
		if (t1 == null)
			return true ;
		else
		{
			this.raiseAlarm("Two trains are on the same canton : " + g.toString() + "("  + t.toString() + " and " + t1.toString() + ")", decoder);
			return false ;
		}
		
	}
	
	
	@Override
	public boolean validatePosition(NetMonitorDecoder decoder,
			ArrayList<Train> trains) 
	{
		
		for(Train t : trains)
		{
			if (t.getActionsAsString().equals("START"))
			{
				if (!validateTrainStartAndExistAnotherTrainOnNextCanton(t,  trains, decoder))
				{
					return false ;
				}
				if (!validateTrainStartAndExistAnotherTrainOnNextCantonAndLightIsGreen(t,  trains, decoder))
				{
					return false ;
				}
				//validateTrainStartLightIsGreenOnCanton(t,  trains, decoder) ;
				
			}
			if (t.getActionsAsString().equals("STOP"))
			{
				//if (!validateTrainStopAndExistAnotherTrainOnNextCantonAndLightIsGreen(t,  trains, decoder))
				//{
					//return false ;
				//}
			}			
		}
		return true ;
	}


	private boolean validateTrainStartAndExistAnotherTrainOnNextCantonAndLightIsGreen(
		Train t, ArrayList<Train> trains, NetMonitorDecoder decoder) {
		
		if (t.getNextCantonWithLight().getLight().getColorAsString().equals("green"))
		{
			Train t1 = this.getTrainOn(t.getNextCanton(), trains) ;
			if (t1 == null)
				return true ;
			else
			{
				this.raiseAlarm("Train " + t.getAddresse() + " is authorized to pass next canton but train " + t1.getAdresse() + " is already on the canton" , decoder);
				return false ;
			}
		}
		return true ;
		
	}

}
