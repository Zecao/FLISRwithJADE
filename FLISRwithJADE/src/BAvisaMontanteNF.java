import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

class BAvisaMontanteNF extends SimpleBehaviour 
{   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean finished = false;
    
    private ArrayList<String> montanteNF = new ArrayList<String>();
    
    private boolean montanteGot = false;

    
    protected ACLMessage msg; 
	
    public BAvisaMontanteNF(Recloser a ) 
    { 
        super(a);
          
    }
    
	
	public BAvisaMontanteNF(Recloser a, ACLMessage msg) 
	{ 
	     super(a);
	     
	     this.msg = msg;
	           
	}
	     
    public void action() 
    {    
		//getMontanteNF rede
		getMontanteNF();
       	
	   //Avisa vizinhos da abertura da chave
	   if (montanteGot)
	   {
		   //comunica MontanteNF
		   comunicaMontanteNF();
		   
		   finished = true;		   
	   } 
    }
    
    public void getMontanteNF()
    {	
    	//chama getMontante
    	Rede r = new Rede();  	
    	Recloser rc = (Recloser)myAgent; 
      	   	
    	montanteNF = r.getMontante( rc.id );
    	
    	//lstMontanteNFs nao esta vazia  
    	if ( !montanteNF.isEmpty() )
    		montanteGot = true;
    	else
    		System.out.println("Erro getMontanteNF");
    	
    }    
    
	private void comunicaMontanteNF()
	{
		/*TODO: VERIFICAR POR QUE ESTE TRECHO NAO FUNCIONA
		
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

		//altera Content da msg
		msg.setContent( "NFInformeCarregamento" );
		String aid =  "Chave" + lstMontanteNFs.get(0);

		//Seta o Sender da msg
		msg.setSender(myAgent.getAID() );
      
		msg.addReceiver( new AID(aid, AID.ISLOCALNAME) );

		myAgent.send(msg);   */
		
		//altera Content da msg
        msg.setContent( "NFInformeCarregamento" );
        String aid =  "Chave" + montanteNF.get(0);
        
        //TODO verificar
        msg.clearAllReceiver();
        
        //Seta o Sender da msg
        msg.setSender(myAgent.getAID() );
        
        msg.addReceiver( new AID(aid, AID.ISLOCALNAME) );
        
        myAgent.send(msg);		
	}
	
       
    public  boolean done() {  return finished;  }     
    
} //End class 

