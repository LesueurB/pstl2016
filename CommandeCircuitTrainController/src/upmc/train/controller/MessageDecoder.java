package upmc.train.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import upmc.train.constantes.IConstantes.EAction;
import upmc.train.constantes.IConstantes.EDirection;
import upmc.train.constantes.IConstantes.EStatus;
import upmc.train.constantes.IConstantes.EType;
import upmc.train.constantes.IConstantes.ETypeRequest;
import upmc.train.controller.model.Controller;
import upmc.train.model.Canton;
import upmc.train.model.Gare;
import upmc.train.model.Light;
import upmc.train.model.Switch;
import upmc.train.model.Topography;
import upmc.train.model.communication.NetMonitor;
import upmc.train.model.exception.AlreadySetException;
import upmc.train.model.messages.IMessageListener;
import upmc.train.model.messages.MessageEvent;
import upmc.train.model.messages.PCFDecoder;
import upmc.train.model.messages.xml.BadPCFFormatException;
import upmc.train.model.messages.xml.PCFCapteurElement;
import upmc.train.model.messages.xml.PCFEdgeElement;
import upmc.train.model.messages.xml.PCFLightElement;
import upmc.train.model.messages.xml.PCFPositionElement;
import upmc.train.model.messages.xml.PCFRootElement;
import upmc.train.model.messages.xml.PCFSwitchEdgeElement;
import upmc.train.model.messages.xml.PCFUpElement;

public class MessageDecoder extends Thread 
{
	
	private ArrayList<String> messages  ;
	private boolean isRunning = true ;
	private NetMonitor com ;
	private String scenario = "1" ;

	public String getScenario() {
		return scenario;
	}
	private Controller controller ;
	public Controller getController() {
		return controller;
	}
	private final Collection<IMessageListener> messagesListeners = new ArrayList<IMessageListener>();
		
	  
	public MessageDecoder(Controller controller,ArrayList<String> messages, NetController network, NetMonitor com)
	{
		this.controller = controller ;
		this.messages = messages ;
		controller.addAbonne(network.getEncoder());
		this.com = com ;
		
	}
	

	private void traiteMessage(String message)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc = null ;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(new InputSource(new StringReader(message))) ;
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if (doc != null)
		{
			PCFDecoder decoder = new PCFDecoder(doc) ;
			try {
				decoder.startDecoding();
				PCFRootElement racine = decoder.getRacine() ;
				if (racine.getType() == ETypeRequest.ANSWER)
				{
					if (racine.getScenario() != null)
						scenario = racine.getScenario().getId() ;
					if (racine.getBye() != null)
					{
						this.traiteBye() ;
					}
					if (racine.getOlleh() != null)
					{
						this.getController().setTopography(null);
						this.getController().resetTrains() ;
						this.traiteOlleh(racine);
					}	
					if (racine.getTopography() != null)
						this.traiteTopography(racine);
					if (racine.getInit() != null)
						this.traiteInit(racine) ;
					if (racine.getLights() != null)
					{
						this.traiteLights(racine) ;
					}					
					if (racine.getScenario() != null)
					{
						this.scenario = racine.getScenario().getId() ;
					}
					if (racine.getInfo() != null)
					{
						this.traiteAdvice(racine);
					}	
				}
				else if (racine.getType() == ETypeRequest.REQUEST)
				{
					try
					{
						this.controller.getEncoder().getNetwork().setNumMessage(1 + Integer.parseInt(racine.getReqId()));
					}
					catch(Exception e)
					{
						
					}
					if (racine.getUp() != null)
						this.traiteUp(racine);
				}
				else
				{
					if (racine.getInfo() != null)
					{
						this.traiteAdvice(racine);
					}					
				}
				
			} catch (BadPCFFormatException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void traiteLights(PCFRootElement racine) {
		for (PCFLightElement l : racine.getLights().getLights())
		{
			Light light = new Light(l.getId(),this.getCommunication()) ;
			try {
				light.setColorWithoutCommunication(l.getColor());
			} catch (AlreadySetException e) {
				
			}
			this.getController().getTopography().addLight(light);
			Canton canton = this.getController().getTopography().getCantonByName(light.getNumero()) ;
			canton.setLight(light); 
			
		}
		this.controller.changed(null);
	}
	
	
	private void traiteInit(PCFRootElement racine) {
		EDirection d ;
		EAction a ;
		for(PCFPositionElement pos : racine.getInit().getPositions())
		{
			Canton after = this.getController().getTopography().getCantonByName(pos.getDebut().getId());
			Canton before = this.getController().getTopography().getCantonByName(pos.getFin().getId());
			if ((after == null) || (before == null))
			{
				System.out.println("Init failed, canton does not exist") ;
				return ;
			}
			if (pos.getTrain().getDirection() == null)
				d = EDirection.FORWARD ;
			else
				d = pos.getTrain().getDirection() ;
			if (pos.getTrain().getAction() == null)
				a = EAction.STOP;
			else
				a = pos.getTrain().getAction();			
			this.getController().addTrainWithoutCommunication(
					pos.getTrain().getId(),
					after, before, 
					com, 
					d,
					a
					);
			}
	}
	
	private void traiteTopography(PCFRootElement racine) {
		Topography topo = new Topography() ;
		Canton c  = null;
		//on cr�e tout d'abord l'ensemble des cantons
		//pour les relations on verra dans un second temps
		
		//ATTENTION : POUR LE MOMENT A CHAQUE CANTON JE RAJOUTE UN FEU
		for (PCFEdgeElement edge : racine.getTopography().getEdges())
		{
			if (topo.getCantonByName(edge.getCapteur().getId()) == null)
			{
				if (edge.getCapteur().getType() == EType.CANTON)
				{
					 c = new Canton(edge.getCapteur().getId(),edge.getCapteur().getId(), this.getCommunication()) ;
					
				}
				else
					c = new Gare(edge.getCapteur().getId(), edge.getCapteur().getId(), this.getCommunication()) ;
				topo.addToGlobalList(c);
			}
			
		}
		for (PCFSwitchEdgeElement edge : racine.getTopography().getSwitchEdges()) 
		{
			Switch s = new Switch(edge.getId()) ;
			s.setBranch0(topo.getCantonByName(edge.getNum_capteur_branch0()));
			s.setBranch1(topo.getCantonByName(edge.getNum_capteur_branch1()));
			s.setTrunk(topo.getCantonByName(edge.getNum_capteur_trunk()));
			s.setType(edge.getType()) ;
			
		}
		//maintenant on peut s'attaquer aux liens de dependances entres
		//les differents capteurs
		for (PCFEdgeElement edge : racine.getTopography().getEdges())
		{
			c = topo.getCantonByName(edge.getCapteur().getId())  ;
			for(PCFCapteurElement capteur : edge.getIn().getIn())
			{
				c.addPreviousCanton(topo.getCantonByName(capteur.getId()));
			}
			
			for(PCFCapteurElement capteur : edge.getOut().getOut())
			{
				c.addNextCanton(topo.getCantonByName(capteur.getId()));
			}
		}
		this.controller.setTopography(topo) ;
	}
	private void traiteAdvice(PCFRootElement racine) 
	{
		String contenu =  this.decodeAdvise(racine.getInfo().getContenu()) ; ;
		String msg = "" ;
		if (racine.getInfo().getStatus() == EStatus.KO)
		{
			if (contenu.equals(""))
				contenu = "ACK" ;
			msg = "ERROR " + racine.getPrefixe() + " : "  +  contenu + "\n" ;

		}
		else
		{
			
			if (contenu.equals(""))
				contenu = "ACK" ;
			msg ="INFO " + racine.getPrefixe() + " : " + contenu + "\n" ;
		}
		this.firedNewMessageEvent(msg);
		System.out.println(msg) ;			
	}
	
	private void traiteUp(PCFRootElement racine) 
	{
		String msg = "" ;
		for(PCFUpElement elt : racine.getUp())
		{
			for (PCFCapteurElement c : elt.getCapteurs())
			{
				msg = "INFO " + racine.getPrefixe() + " : monitor signal \""  + c.getId() + "\"\n" ;
				this.firedNewMessageEvent(msg);
				System.out.println(msg) ;	
				this.controller.reaction(c.getId(), c.getType()) ;
			}
		}
				
	}	
	
	private void traiteBye() 
	{
		String msg = "" ;
		msg = "INFO : bye acknoledge\n" ;
		this.firedNewMessageEvent(msg);
		System.out.println(msg) ;			
	}
				
	private String decodeAdvise(String contenu) {
		
		return contenu.replace("@@@", "\n");
	}


	private void traiteOlleh(PCFRootElement racine) 
	{
		String msg = "INFO " + racine.getPrefixe() + " : Olleh received from server" ;
		this.firedNewMessageEvent(msg);
		System.out.println(msg) ;		
	}
	public void run()
	{
		String message ;
		while(isRunning)
		{
			synchronized(messages)
			{
				if (!messages.isEmpty())
				{
					message = messages.remove(0) ;
				}
				else
					message = "" ;
			}
			if (message.equals(""))
			{
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else
				this.traiteMessage(message) ;
		}
	}
	  //traitement de l'ecoute des messages series
    public void addMessageListener(IMessageListener listener) {
    	messagesListeners.add(listener);
    }
 
    public void removeMessageListener(IMessageListener listener) {
    	messagesListeners.remove(listener);
    }
 
    public IMessageListener[] getMessageListeners() {
        return messagesListeners.toArray(new IMessageListener[0]);
    }
    
    private void firedNewMessageEvent(String msg)
    {
    	MessageEvent evt = new MessageEvent(msg) ;
    	for(IMessageListener listener : this.messagesListeners)
    		listener.newMessage(evt) ;
    }
	public void newMessage(MessageEvent evt) 
	{
		this.firedNewMessageEvent(evt.getMessage());
		
	}


	public NetMonitor getCommunication() {
		return this.com ;
	}
	

}
