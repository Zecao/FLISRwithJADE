
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jade.core.behaviours.*;


class BMonitoraCorrente extends CyclicBehaviour 
{	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int corrente;
    private boolean curto = false;
    private String id;
 
    //Construtor  
    public BMonitoraCorrente(Recloser a) { 
         super(a);
         
         this.corrente = a.corrente;
         this.id = a.id;   
    }
                
    public void action() 
    {
       //pega estado da chave, pois so monitora se a chave estiver fechada
 	   Recloser rc = (Recloser)myAgent;
 	   
 	   if (rc.chaveFechada)
 	   {
			//monitora rede a cada intervaloMonitoramento
			block(Tempo.intervaloMonitoramento);

			// getCorrenteRede
			getCorrenteRede();
			
			System.out.println(myAgent.getLocalName() + " monitorando a Rede " + this.corrente + "A");
			
			//detecta curto
			detectaCurto();				  
 	   }   
    } 
    
    private void detectaCurto(){
    	
		//verifica curto
    	//codigo p/ distinguir curto de simples abertura da chave
    	if ( this.corrente > 4500){
    		this.curto = true;
    	}

		// Detectou curto
		if (curto)
		{
			System.out.println("Curto " + myAgent.getLocalName() + " !");

			myAgent.addBehaviour(new BAbreCurto((Recloser) myAgent)); 
		}  
    }

    //executa Programa GetCorrenteRede.exe   
    private void getCorrenteRede(){

    	Process process = null;
    	
    	ArrayList<String> args = new ArrayList<String> (); 
    	
  //  	String currentDir = System.getProperty("user.dir");
    	
    	args.add(".\\res\\get_corrente.exe");    	
    	args.add("-r");    	
    	args.add("\".\\res\\v3.tgf\"");    	
    	args.add( "-c");    	
    	args.add( "\"" + id + "\"");
	  	       	  	
		try {
			process = new ProcessBuilder(args).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InputStream is = process.getInputStream();	

    	InputStreamReader isr = new InputStreamReader(is);
    	BufferedReader br = new BufferedReader(isr);

    	String line = null;
    	
    	Recloser rc = (Recloser)myAgent;
    	
    	try {
			while ((line = br.readLine()) != null) {
					
				rc.corrente = Integer.parseInt( (String) line ); 

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//TODO: eliminar this
    	this.corrente = rc.corrente;
    	   	 	
    }
    
} //End class 


