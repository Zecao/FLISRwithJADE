//
//BAguardaCarregamento:   a more user friendly ReceiverBehaviour
//
//Creation: new BAguardaCarregamento(Agent, Timeout (or -1), MessageTemplate )
//
//	- terminates when 1) desired message is received OR timeout expires
//- on termination, handle(msg) is called
//                  ( J.Vaucher sept. 7 2003 )
//---------------------------------------------------------------------------

import java.util.ArrayList;
import java.util.Map;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class BAguardaCarregamento extends SimpleBehaviour
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,Integer> mapAgCor = null;
    private ArrayList <String> lstAg = null;
	
	private MessageTemplate template;
	private long    timeOut, 	wakeupTime;
	private boolean finished;

	private ACLMessage msg;

	public ACLMessage getMessage() { return msg; }


	public BAguardaCarregamento(Recloser a, int tmp, MessageTemplate mt) {
		super(a);
		timeOut = tmp;
		template = mt;
	}

	public void onStart() {
		wakeupTime = (timeOut<0 ? Long.MAX_VALUE
				:System.currentTimeMillis() + timeOut);
	}

	public boolean done () {
		
		return finished;
	}

	public void action() 
	{
		//TODO  verificar se estou recebendo msg c/ o template.
		if(template == null)
//			msg = myAgent.receive();
			return;
		else
			msg = myAgent.receive(template);

		//TODO verificar a msg enviada pela BAbre. 
		if( msg != null) {
			
			handle( msg );
			finished = true;
			return;
		}
		
		long dt = wakeupTime - System.currentTimeMillis();
		
		if ( dt > 0 ) 
			
			block(dt);
		
		else {

			handleTimeOut( msg );
			finished = true;
		}
	}
	
	//Esperando...
	private void handle( ACLMessage m) { 
		
		System.out.println("Esperando... e Received: "+ msg);	
       	
	}
	
	//TimeOut...
	private void handleTimeOut( ACLMessage m) { 

		if (msg == null) 
		{
			System.out.println("Timeout");

			//TODO verificar se o map esta preenchido e etc.		
			Recloser rc = (Recloser)myAgent;
			mapAgCor = rc.mapAgentCorrente; 

			//lista de Ag
			lstAg  = new ArrayList <String>(); 
			lstAg.addAll(mapAgCor.keySet());		

			ComunicaFechamento(); 

		}/*//comentado pq tenho handle diferentes
    	else 
       		System.out.println("Received: "+ msg);*/
            	
	}	
	
	private void ComunicaFechamento() {

		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

		msg.setContent( "Fechar!NAs" );

		for(String ag : lstAg)
		{     	
			msg.addReceiver( new AID( ag, AID.ISLOCALNAME) );

			myAgent.send(msg);
		}
	}

	public void reset() 
	{
		msg = null;
		finished = false;
		super.reset();
	}

	public void reset(int dt) 
	{
		timeOut= dt;
		reset();
	}
}


