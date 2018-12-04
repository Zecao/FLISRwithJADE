import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

class BAbre extends SimpleBehaviour 
{   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean finished = false;
    private boolean manobrado = false;
	
    public BAbre(Recloser a) { 
         super(a);
         
    }
         
    public void action() 
    {    

    	//TODO refatorar Abre Isolar 
    	//Manobra rede
    	manobraChave();
       	
       if (manobrado)
       {
    	   System.out.println( myAgent.getLocalName() + " Aberto!"  );
    	   
    	   //Metodo p/ interromper BMonitoraRede da chave recem aberta.
    	   //Abre chave
    	   Recloser rc = (Recloser)myAgent;
    	   rc.chaveFechada = false;
    	   rc.corrente = 0; 
    	   
    	   //Avisa vizinhos normalmente abertos para tentarem reconfigurar circuito
    	   myAgent.addBehaviour( new BAvisaVizinhosNAs( (Recloser)myAgent) ); 
    	   
    	   //Instancia behaviour AguardaCarregamento que espera as mensagens (chave 3 e 11)  	   
    	   MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM_REF);
    	      	   
    	   myAgent.addBehaviour( new BAguardaCarregamento( (Recloser)myAgent, Tempo.timeOut, mt)
    	   
    	   /*
    	   {
   		   public void handle( ACLMessage msg ) 
		   {  
			   if (msg == null) 
				   System.out.println("Timeout");
			   else 
				   System.out.println("Received: "+ msg);
		   }
    	   }	*/   	   
    			   ); 
    	   
       }
       
   		finished = true;
   		
    }
              
    //executa Programa Manobra.exe   
    private void manobraChave()
    {
		manobrado = true;
    }
       
    public  boolean done() {  return finished;  }
     
    
} //End class 

