import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Rede {
	
	private HashMap<String, Barra > hashIDBarra = new HashMap<String, Barra>();
	
	private ArrayList<Trecho> lstTrecho = new ArrayList<Trecho>();
	
	private HashMap<String, ArrayList<Barra> > grafoDireto = new HashMap<String, ArrayList<Barra>>(); 
	private HashMap<String, Barra > grafoReverso = new HashMap<String, Barra>();
	
	private ArrayList<String> lstLinhaArquivo = new ArrayList<String>();
	
	Rede(){
		
		leArquivo();
		
		preencheVariaveis();
				
	}
	
	private void preencheLstTrecho(Trecho trecho) {

		if ( ! lstTrecho.contains(trecho) )
			lstTrecho.add(trecho); 

	}

	private void preencheLstBarra(Barra barra1, Barra barra2) {
		
		if ( ! hashIDBarra.containsKey(barra1.id) )
			hashIDBarra.put(barra1.id, barra1); 
		if ( ! hashIDBarra.containsKey(barra2.id) )
			hashIDBarra.put(barra2.id, barra2); 
		
	}

	private void leArquivo(){
		
	    BufferedReader br = null;
	    
		try {
			br = new BufferedReader(new FileReader(".\\res\\v3.tgf"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    try {

	        String line = br.readLine();

	        boolean tralha = false;

	        // percorre arquivo 
	        while (line != null) {
	        	
		        // adiciona linha na lista de linhas
		        if (tralha) 
		        	lstLinhaArquivo.add(line);
	        	
		        // se linha igual '#', seta variavel tralha igual true   
		        if (line.equals("#"))
		        	tralha = true;
               
	            line = br.readLine();
	        }

	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    finally {
	        try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		
	}
	
	
	private void preencheVariaveis() {
		
		
		for (String linha:lstLinhaArquivo) {
			
			String [] arrayString = linha.split(" ");
			
			Barra barra1 = new Barra( arrayString[0] );			
			Barra barra2 = new Barra( arrayString[1] );
			Trecho trecho  = new Trecho ( arrayString[2] );
			
			preencheLstBarra(barra1,barra2);
			
			preencheLstTrecho(trecho);			
			
			preencheGrafoDireto(barra1,barra2);
			
			preencheGrafoReverso(barra1,barra2);
				
		}

	}
	
	private void preencheGrafoDireto(Barra barra1, Barra barra2){
		
		//Preenche grafoDireto 
		if ( ! grafoDireto.containsKey(barra1.id) ) {

			ArrayList<Barra> lstBarra2 = new ArrayList<Barra>();
			lstBarra2.add(barra2);

			grafoDireto.put(barra1.id, lstBarra2);

		} else { //grafoDireto ja existe 

			ArrayList<Barra> lstBarra2 = grafoDireto.get(barra1.id);
			lstBarra2.add(barra2);

			grafoDireto.put(barra1.id, lstBarra2);

		}


	}
	
	
	private void preencheGrafoReverso(Barra barra1, Barra barra2) {

		//Preenche grafoReverso
		if ( ! grafoReverso.containsKey(barra2.id) ){

			grafoReverso.put(barra2.id, barra1);

		}

	}
	
	// get Vizinhos Jusante
	public ArrayList<String> getVizinhos(String id) {

		ArrayList<Barra> lstBarrasJusante = grafoDireto.get(id);
		
		ArrayList<String> lstJusanteNFs = new ArrayList<String>();
		
    	for (Barra barra:lstBarrasJusante){
    		
    		lstJusanteNFs.add(barra.id);
    		
    	}
		
		return lstJusanteNFs;
		
	}
	
	// get Montante 
	public ArrayList<String> getMontante(String id) {

		Barra barrasMontante = grafoReverso.get(id);
		
		ArrayList<String> lstIDMontante = new ArrayList<String>();
		
		lstIDMontante.add( barrasMontante.id );

		return lstIDMontante;
		
	}
	
	
}
