

import java.util.Map;

import jade.core.Agent;

public class Recloser extends Agent 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Usado por BAvisavizinhosNAs e BTomadaDecisao e BAguardaCarregamento
	protected Map<String,Integer> mapAgentCorrente = null;
	
	protected String id;
	
	public int corrente = 0;	
	public boolean chaveFechada = false; 
	
    protected void setup()
    {         
    	trataArgumentos();
    	
    	addBehaviour(  new BMonitoraCorrente(this) );
    	
    	addBehaviour( new BRecebeMsg(this) );
    	
   }
    
    private void trataArgumentos()
    {
        Object[] args = getArguments();
        
        if (args != null) 
        {
            
            // Extracting the integer.
            this.id = (String) args[0];
            this.corrente = Integer.parseInt( (String) args[1] );
            this.chaveFechada = ((String) args[2]).equals("1");
            
        }
    }
   
}



