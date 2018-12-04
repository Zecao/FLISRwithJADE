import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

class BAvisaVizinhosNAs extends SimpleBehaviour 
{   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean finished = false;
    private boolean vizinhosGot = false; 
    private ArrayList<String> lstVizinhosNAs = new ArrayList<String>();
    private String id;
    
    public BAvisaVizinhosNAs(Recloser a) { 
         super(a);
         
         a.mapAgentCorrente = new HashMap<String,Integer>();
         
         this.id = a.id;
     
    }
         
    public void action() 
    {
       System.out.println( "Avisando vizinhos da " + 
              myAgent.getLocalName() );
       
       //get Vizinhos NAS e NFs
       getVizinhosNAs();
              
       //comunica Vizinhos
       if (vizinhosGot)
       {
    	   //Cria msg e comunicar vizinhos NAs
    	   comunicaVizinhosNAs();
    	       
       }

       finished = true;
   		
    }
    
    //
    private void getVizinhosNAs()
    {    	
    	Rede r = new Rede();  	
    	Recloser rc = (Recloser)myAgent; 
        
    	//Obtem lista de vizinhos NAs e NFs da estrutura Rede.
    	ArrayList<String> lstVizinhos = r.getVizinhos( rc.id );
    	
    	//Comunica com vizinhos para saber seu status (aberto ou fechado) 
    	getStatusVizinhos(lstVizinhos);
    	
    	//lstMontanteNFs nao esta vazia  
    	if ( !lstVizinhosNAs.isEmpty() )
    		vizinhosGot = true;
    	else
    		System.out.println("Erro getVizinhosNAs"); 

    }
    
    //comunica com vizinhos, para pegar somente os vizinhos NAs
    private void getStatusVizinhos(ArrayList<String> lstVizinhos){
    	

    	for (String vizinho:lstVizinhos){
    		
        	ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        	msg.setContent( "getStatus" );
    		
    		msg.addReceiver( new AID( "Chave" + vizinho, AID.ISLOCALNAME) );
    		
    		myAgent.send(msg);
    		
    		
    		ACLMessage resposta = myAgent.blockingReceive();
    		
    		//TODO verificar possibilidade de refatorar e colocar este trecho na classe BRecebeMsg
        	if ( resposta.getContent().equals("replyStatus") )
        	{              
         		String chaveFechadaString = resposta.getUserDefinedParameter(ParMsg.chaveFechada);
         		
         		//Se status da chave for aberta, adiciona vizinho na lstVizinhosNAs
         		if (chaveFechadaString == "false")
         		
         			lstVizinhosNAs.add(vizinho);

        	}

    	}
    	
    }
    
    
    //na POC ag 7.
    private void comunicaVizinhosNAs()
    {
    	ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

    	msg.setContent( "VizinhosNAsAvisemMontanteNF" );
    	
    	for(String vizinho : lstVizinhosNAs)
    	{     	
        	msg.addReceiver( new AID( "Chave" + vizinho, AID.ISLOCALNAME) );
    	}
    	//Coloca id do agente como parametro na msg, pois precisarei desta informacao
    	//qnd for retornar a msg.
    	msg.addUserDefinedParameter(ParMsg.chaveIsolada, this.id);
    	
    	myAgent.send(msg);
    	 	
    }
 
    public  boolean done() { return finished;  }
  
} //End class 
