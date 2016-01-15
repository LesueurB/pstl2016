package upmc.train.model.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import upmc.train.constantes.IConstantes;
import upmc.train.constantes.IConstantes.ETypeRequest;
import upmc.train.model.messages.xml.PCFRootElement;

public class NetMonitorDecoder extends Thread
{
	private PrintWriter out;
	private BufferedReader in;
	private Socket socket = null ;
	private ArrayList<String> messages = new ArrayList<String>() ;
	private boolean isRunning = true ;
	private int numMessage = 0 ;

	public NetMonitorDecoder(Socket socket, String scenario)
	{
		try {
			SimpleDateFormat dateformatter = new SimpleDateFormat("dd MM yyyy : HH:mm:ss");
			String date ;
			this.socket = socket ;
			date = dateformatter.format(Calendar.getInstance().getTime()) ;
			System.out.println(date + "(" + this.socket.getInetAddress().getHostAddress() +  ") New connection accepted") ;
			
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true );
			in  = new BufferedReader(new  InputStreamReader(socket.getInputStream ()));
			//true ou false pour mode simuler ou non 
			//Attention le cas reel il FAUT connaitre le port de la carte arduino
			new MessageDecoder(scenario,messages,this, IConstantes.simulator).start(); 
		} 
		catch (IOException e) 
		{
			try 
			{
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		this.start();
	}
	
	public void send (PCFRootElement sendMessage)
	{
		System.out.println("Sent : " + sendMessage.getFragmentXML()) ;
		if (sendMessage.getType() == ETypeRequest.REQUEST)
		{
			sendMessage.setReqId("" + this.getNumMessage());
			this.setNumMessage(this.getNumMessage() + 1) ;
		}
		if (out != null)
		{
			out.println(sendMessage.getFragmentXML());
			out.flush();
			
		}
	}
	public void run()
	{
		String tmp="" ;
		String accu = "";
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd MM yyyy : HH:mm:ss");
		String date ;
		while(isRunning)
		{
			try {
				
				tmp = in.readLine() ;
				accu += tmp ;
				if (accu.toLowerCase().contains("</pcf>"))
				{
					synchronized(messages)
					{
						this.messages.add(accu) ;
						date = dateformatter.format(Calendar.getInstance().getTime()) ;
						System.out.println(date + "(" + this.socket.getInetAddress().getHostAddress() +  ") : Message recu :" + accu);
					}
					accu = "" ;
				}
			}//try
			catch (IOException a ) {
				System.out.println("Error transmitting data") ;
				a.printStackTrace() ; 
				isRunning = false ;
			}//catch IO
		}
		try
		{
			in.close();
			out.close();
			socket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setIsRunning(boolean b) {
		this.isRunning = false ;
		
	}

	public int getNumMessage() {
		return numMessage;
	}

	public void setNumMessage(int numMessage) {
		this.numMessage = numMessage;
	}

}
