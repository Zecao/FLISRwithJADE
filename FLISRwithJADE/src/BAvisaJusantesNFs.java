import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

class BAvisaJusantesNFs extends SimpleBehaviour 
{   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean finished = false;
    private boolean jusantesGot = false; 
    
    private ArrayList<String> lstJusanteNFs = new ArrayList<String>();
    
    public BAvisaJusantesNFs(Recloser a) { 
         super(a);     
    }
         
    public void action() 
    {
       //getJusanteNFs
       getJusantesNFs();
       
       //comunica Vizinhos
       if (jusantesGot)
       {
    	   comunicaJusantesNFs();
       }
             
   		finished = true;
   		
    }
    
    //executa Programa getJusanteNFs
    private void getJusantesNFs()
    {  	
    	//chama getvizinhos
    	Rede r = new Rede();
    	Recloser rc = (Recloser)myAgent; 
      	   	
    	lstJusanteNFs = r.getVizinhos( rc.id );
    	
    	//TODO filtrar lstJusanteNFs retirando as chaves NAs
    	
    	//TODO Caso onde religador abre c/ curto. E temos 2 religadores a jusante NF (que tem que abrir p/ isolar)

    	//lstJusanteNFs nao esta vazia  
    	if ( !lstJusanteNFs.isEmpty() )
    		jusantesGot = true;
    	else
    		System.out.println("Erro getJusantesNFs");
    		
    }
    
    private void comunicaJusantesNFs()
    {    	   	
    	//TODO: testar caso com 2chaves NF a jusante!
    	
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        msg.setContent( "JusantesNFs!IsolarRede" );
        
        for (String ag:lstJusanteNFs )
        {
        	
        	String aid =  "Chave" + ag;
            msg.addReceiver( new AID(aid, AID.ISLOCALNAME) );
            myAgent.send(msg);       	
            
        }
    	
    }
       
    public  boolean done() {  return finished;  }
     
    
} //End class 
