import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jade.core.behaviours.SimpleBehaviour;

/* B2. Requisita situacao (carregamento) de Agente a montante */

class BAbreCurto extends SimpleBehaviour 
{   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean finished = false;
    private boolean manobrado = false;
	
    public BAbreCurto(Recloser a) { 
         super(a);    
    }
         
    public void action() 
    {
    	// DEBUG	
    	//System.out.println( "Abrindo chave " + myAgent.getLocalName() );
        
		//Manobra rede
		manobraChave();
       	
	   //Avisa vizinhos da abertura da chave
	   if (manobrado)
	   {
		   System.out.println( "Abertura: " + myAgent.getLocalName() + " !"  );
	   
		   //Abre chave
		   Recloser rc = (Recloser)myAgent;
		   rc.chaveFechada = false;
		   rc.corrente = 0; 
		   
		   myAgent.addBehaviour( new BAvisaJusantesNFs( (Recloser)myAgent) ); //TODO verificar se esta ok
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

