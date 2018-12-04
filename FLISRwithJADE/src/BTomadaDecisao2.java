

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;


public class BTomadaDecisao2 extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean finished = false;

	private Map<String,Integer> mapAgCor = null;
	private ArrayList <Integer> lstCor = null;
	private ArrayList <String> lstAg = null;

	public BTomadaDecisao2(Recloser a) { 
		super(a);

		this.mapAgCor = a.mapAgentCorrente;

	}

	/*
	    addBehaviour( new myReceiver(this, 40000, 
	            MessageTemplate.MatchPerformative(ACLMessage.INFORM_REF)
	         {


	            }
	         });*/

	public void handle( ACLMessage msg ) 
	{  
		if (msg == null) 
			System.out.println("Timeout");
		else 
			System.out.println("Received: "+ msg);

	}


	@Override
	public void action() {
		// TODO Auto-generated method stub

		System.out.println( myAgent.getLocalName() + " Tomando Decisao!"  );

		int numChaves = getNumOpcoesChaveamento();

		//Se ha mais de uma opcao de reconfiguracao
		if ( numChaves == 2 )  System.out.println( "OK OK" );

		//relacao carregamento/capacidade linha
		rankeiaOpcoes();

		//analisao opcoes
		analisaOpcoes();

		/*
			} else {

				System.out.println( myAgent.getLocalName() + " Numero de opcoes: " + numChaves );

			} */

		//Reconfigurar
		ComunicaFechamento();

		finished = true;

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

	private void analisaOpcoes() {

		// TODO Criar fator de capacidade da linha

		// TODO Passar como argumento deste metodo corrente necessaria (nova carga).

		/*
			//1.1 Pondera a lista de correntes
			Integer soma = new Integer(0);   

			for (Integer i:lstCor){

				soma = soma + i;			
			}

			//Divide lista pela soma
			ArrayList <Double> lstCorAux = new ArrayList <Double>();

			for (Integer i:lstCor){

				double id = (double)i;
				double sd = (double)soma;

				id = id/sd;

				lstCorAux.add(id);

			}

			//Analisa OPcoes 

			//Chaves a serem manobradas 

		 */


	}

	private void rankeiaOpcoes() {

		//lista de Ag
		lstAg  = new ArrayList <String>(); 
		lstAg.addAll(mapAgCor.keySet());		

		//lista de Corrente
		lstCor = new ArrayList <Integer>();
		lstCor.addAll(mapAgCor.values());

		// TODO Auto-generated method stub
		//Collections.sort(lstCor);
	}

	private int getNumOpcoesChaveamento() {

		return mapAgCor.size();

	}

	@Override
	public boolean done() {

		//Apaga mapAgCor
		mapAgCor.clear();

		return finished;
	}

}
