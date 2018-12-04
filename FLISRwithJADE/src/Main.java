
import java.util.ArrayList;

import jade.Boot;

public class Main {
	
	private static ArrayList<Integer> vCorRede = new ArrayList<Integer>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Monta String dos Agentes
		String agentes = MontaAgentes();
				
		String[] param = new String[ 2 ];
		param[0] = "-gui";
		param[1] = agentes;
		
		try {
			Boot.main( param );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	//TODO obter corrente do EXE
	private static void dadosCorrente(){
		
		vCorRede.add(0, 0);
		vCorRede.add(1, 100); 	
		vCorRede.add(2, 66); 
		vCorRede.add(3, 33);
		vCorRede.add(4, 0);
		vCorRede.add(5, 100);
		vCorRede.add(6, 66);
		vCorRede.add(7, 33);
		vCorRede.add(8, 0);
		vCorRede.add(9, 100);
		vCorRede.add(10, 66);
		vCorRede.add(11, 33);
		vCorRede.add(12, 0);
		
	}
	
	//Transforma int corrente em String  
	public static String MontaAgentes( )
	{
		
		dadosCorrente();
		
		//Estrutura da String: 
		//"Chave2:Recloser(vCorRede);Chave7:Recloser(66)";
		
		//POC.
		//a6 fecha curto, 
		//a7 abre
		//a4 e a12 (NAs) pesquisam condicao de ajudar
		//a3 e a11 (NFs) informa carregamento		
		
		String a6,a7,a4,a12,a3,a11,a2,a10,a8,param;
		
		a6 = "Chave6:Recloser(6," + vCorRede.get(6).toString() + ",1" + ")"; 
		a7 = "Chave7:Recloser(7," + vCorRede.get(7).toString() + ",1" + ")";
		
		a4 = "Chave4:Recloser(4," + vCorRede.get(4).toString() + ",0" + ")";
		a12 = "Chave12:Recloser(12," + vCorRede.get(12).toString() + ",0" + ")";
		
		a3 = "Chave3:Recloser(3," + vCorRede.get(3).toString() + ",1" + ")";
		a11 = "Chave11:Recloser(11," + vCorRede.get(11).toString() + ",1" + ")";
		
		a2 = "Chave2:Recloser(2," + vCorRede.get(2).toString() + ",1" + ")";
		a10 = "Chave10:Recloser(10," + vCorRede.get(10).toString() + ",1" + ")";
		
		a8 = "Chave8:Recloser(8," + vCorRede.get(8).toString() + ",1" + ")";
		
		param = a6 +";" + a7 +";" + a4 +";" + a12 +";" + a3 +";" + a11 +";" + a2 +";" + a10 +";"+ a8 +";";
		
		return param;
			
	}	
}
