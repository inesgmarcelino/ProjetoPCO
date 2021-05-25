import java.util.Random;

/**
 * Classe cujas instancias representam direcionadores que indicam
 * direcoes aleatorias a custo zero
 * @author isabel nunes
 * @date Novembro 2020
 */
public class Aleatorio implements Direcionador {

	private static final String[] OPCOES = {"N","S","E","O"};

	// O gerador de aleatorios
	private Random gerador;
	
	/**
	 * Inicializa um novo objeto
	 */
	public Aleatorio() {
		super();
		this.gerador = new Random(0);
	}

	/**
	 * A direcao para fazer a simulacao num dado alvo e'
	 * uma direcao aleatoria
	 * @param alvo O alvo da simulacao
	 * @requires alvo != null
	 * @return um elemento de {"N","S","E","O"} escolhido 
	 *         aleatoriamente
	 */
	@Override
	public String direcao(EstadoSimulacao[][] alvo) {		
		return OPCOES[gerador.nextInt(OPCOES.length)];
	}

	/**
	 * O custo de fazer a consulta sobre a melhor direcao para 
	 * a simulacao num dado alvo e' zero 
	 * @param alvo O alvo da simulacao
	 * @requires alvo != null
	 * @return zero
	 */
	@Override
	public int precoConsulta(EstadoSimulacao[][] alvo) {
		return 0;
	}

}
