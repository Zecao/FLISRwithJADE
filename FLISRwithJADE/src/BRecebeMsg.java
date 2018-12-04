import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class BRecebeMsg extends CyclicBehaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BRecebeMsg(Recloser a) 
	{ 
        super(a);
         
	}
	
	public void action() 
    {
		ACLMessage msg = myAgent.receive();
        
    	if (msg!=null) {

	    	//chamado por BAvisaJusantesNFs 
	    	if ( msg.getContent().equals("JusantesNFs!IsolarRede") )
	    	{	
	    		myAgent.addBehaviour( new BAbre( (Recloser)myAgent) );
	            System.out.println( myAgent.getLocalName() + " Realizou Ação: " +
	                    msg.getContent() + " done");
	   	
	    	}
	    	//chamado por BAvisaVizinhosNAs
	    	//na Poc a4 e a12 recebem esta msg (enviada por a7)
	    	else if ( msg.getContent().equals("VizinhosNAsAvisemMontanteNF") )
	        	{     			
	        		myAgent.addBehaviour( new BAvisaMontanteNF ( (Recloser)myAgent, msg ));
	            System.out.println( myAgent.getLocalName() + " Realizou Ação: VizinhosNAsAvisemMontanteNF done");
	      		          
	    	} 
	    	//chamado por BAvisaMontanteNF
	    	//na POC a3 e a11 recebem esta msg (enviada por a4 e a12) 
	    	else if ( msg.getContent().equals("NFInformeCarregamento") )
	    	{	
	    		//Pega a corrente do Agent
	    		Recloser rc = (Recloser)myAgent;
	    		Integer corrente = new Integer(rc.corrente);   	
	    		String sCor = corrente.toString();
	
	            //Responde Consultar carregamento com a corrente 
	    		//na POC responde direto p/ a A7;
	        		respondeInformandoCarregamento(msg,sCor);
	 
	                System.out.println( myAgent.getLocalName() + " Realizou Ação: NF InformeCarregamento done. " + sCor); 
	            
	    	} 	
	    	//chamado por esta classe mesmo. 
	    	//na POC agentes a7 recebe esta msg (enviada por a3 e a11) 
	    	else if ( msg.getContent().equals("NFRespondeCarregamento") )
	    	{
	    		
	    		//get corrente
	    		String sCor = msg.getUserDefinedParameter(ParMsg.corrente);		       		
	    		Integer corrente = Integer.parseInt(sCor);
	    		
	    		//get agente
	    		String sAgente = msg.getUserDefinedParameter(ParMsg.chaveOrigem);       		
	    		       		
	    		Recloser rc = (Recloser)myAgent;
	    		
	            //Armazena valor de corrente e id do sender no map do agente.     
	    		rc.mapAgentCorrente.put(sAgente, corrente);
	    		               
	            System.out.println( myAgent.getLocalName() + " Realizou Ação: NF RespondeCarregamento done " + sCor); 
	                 
	    	} 	
	    	//chamado por BTomadaDecisao
	     	else if ( msg.getContent().equals("Fechar!NAs") )
	    	{              
	    		Recloser rc = (Recloser)myAgent;
	    		
	    		rc.chaveFechada = true;
	    		
	            System.out.println( myAgent.getLocalName() + " Realizou Ação: Fechar!NAs done - chamado por: " + msg.getSender().getLocalName() ); 
	            
	    	}
	    	//chamado por BAvisaVizinhosNAs
	     	else if ( msg.getContent().equals("getStatus") )
	    	{              
	        	ACLMessage reply = msg.createReply();
	        	
	    		Recloser rc = (Recloser)myAgent;
	    		Boolean chaveFechadaBoolean = new Boolean(rc.chaveFechada);   		
	    		String chaveFechadaString = chaveFechadaBoolean.toString(); 
	                 	
	         	reply.addUserDefinedParameter(ParMsg.chaveFechada, chaveFechadaString);
	         	reply.setContent("replyStatus");
	         	
	         	myAgent.send(reply); 
	    		
	            //System.out.println( myAgent.getLocalName() + " Realizou Ação: Fechar!NAs done - chamado por: " + msg.getSender().getLocalName() ); 
	                   	
	    	}
	    	else 
	    		System.out.println( myAgent.getLocalName() + " Ação: " + msg.getContent() + " desconhecida");
        	
        }
        block();
     }

     //Responde Consultar carregamento com a corrente
     private void respondeInformandoCarregamento(ACLMessage msg, String corrente)
     {
     	//Pega chaveIsolada (destino da corrente)
      	String idChaveIsolada = msg.getUserDefinedParameter(ParMsg.chaveIsolada);
      	
      	//Limpo a msg.
      	msg.clearAllReceiver();
      	msg.clearUserDefinedParameter(ParMsg.chaveIsolada);
      	
      	// CRIA MSG NOVA
      	
    	 //Altera Content
    	msg.setContent( "NFRespondeCarregamento" );
    	
     	//Adiciona a corrente na mensagem
     	msg.addUserDefinedParameter(ParMsg.corrente, corrente);
     	
     	//Adiciona o agente desta corrente
     	msg.addUserDefinedParameter(ParMsg.chaveOrigem, msg.getSender().getLocalName() ); //na POC A4 e A12
        System.out.println( " respondi carregamento: " + msg.getSender().getLocalName() ); 

     	msg.addUserDefinedParameter(ParMsg.chaveDaCorrente, myAgent.getLocalName() ); //na POC A3 e A11
     	
     	//Destinatario
     	msg.addReceiver( new AID( "Chave" + idChaveIsolada, AID.ISLOCALNAME) );    	

     	myAgent.send(msg);
         
     }     
             
}
