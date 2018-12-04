
public class Trecho {
	
	private Integer id;
	private Double corrente;
	private double maxCorrente;
	
	private boolean temChave;
	private boolean chaveAberta;	
	
	Trecho(String sCorrente){
		
		corrente = Double.parseDouble( sCorrente );
		
	}

}
