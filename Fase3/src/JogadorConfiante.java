import java.util.List;
import java.util.Random;

/**
 * Classe que representa os JogadoresConfiantes
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
 * @date Dezembro 2020
 */
public class JogadorConfiante extends Jogador {
	// O gerador de aleatorios
	private Random rand;
	private static final String[] OPCOES = {"N","S","E","O"};
	
	/**
	 * Inicializa os atributos do novo objeto JogadorConfiante
	 * @param nLinhas Numero de Linhas do Ambiente
	 * @param nCols Numero de Colunas do Ambiente
	 * @param obstaculos Lista com as posicoes dos obstaculos
	 * @requires nLinhas > 0 && nCols > 0 && obstaculos != null
	 */
	public JogadorConfiante(int nLinhas, int nCols, List<Par<Integer,Integer>> obstaculos) {
		super(nLinhas, nCols, obstaculos);
		this.rand = new Random(1);
	}
	

	/**
	 * A direcao para fazer a simulacao num dado alvo e
	 * uma direcao aleatoria
	 * @return um elemento de {"N","S","E","O"} escolhido 
	 * aleatoriamente
	 */
	@Override
	public String direcao() {
		return OPCOES[rand.nextInt(OPCOES.length)];
		
	}

	/**
	 * Devolve a forca que este Jogador quer usar na proxima
	 * jogada
	 * @return forca
	 */
	@Override
	public int forca() {
		return rand.nextInt(this.dimensoesAmbiente().primeiro());
	}
}

