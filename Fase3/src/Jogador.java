import java.util.List;

/**
 * Classe abstrata cujos objetos representam jogadores
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
 * @date Dezembro 2020
 */
public abstract class Jogador implements Acionavel {
	// Ambiente
	private Ambiente ambiente;
	// Pontuacao
	private int pontuacao;
	
	/**
	 * Inicializa os atributos do novo objeto Jogador
	 * @param nLinhas Numero de Linhas do Ambiente
	 * @param nCols Numero de Colunas do Ambiente
	 * @param obstaculos Lista com as posicoes dos obstaculos
	 * @requires nLinhas > 0 && nCols > 0 && obstaculos != null
	 */
	public Jogador(int nLinhas, int nCols, List<Par<Integer,Integer>> obstaculos) {
		this.ambiente = new Ambiente(nLinhas, nCols, obstaculos);
		this.pontuacao = 0;
	}
	
	/**
	 * Devolve a pontuacao do Jogador
	 * @return A pontuacao
	 */
	public int pontuacao() {
		return this.pontuacao;
	}
	
	/**
	 * Devolve um Par cujo primeiro elemento é o numero de linhas
	 * e o segundo elemento é o numero de colunas, do Ambiente do Jogador
	 * @return dimensoes do Ambiente
	 */
	public Par<Integer,Integer> dimensoesAmbiente() {
		return this.ambiente.dimensoes();
	}
	
	/**
	 * Regista uma nova jogada do Jogador
	 * @param afetados Lista com as posicoes dos afetados pela jogada
	 * @param pontos Numero de pontos ganhos
	 * @requires afetados != null && pontos >= 0
	 */
	public void registaJogadaComPontuacao(List<Par<Integer,Integer>> afetados, int pontos) {
		this.ambiente.registaAfetados(afetados);
		this.pontuacao += pontos;
	}
	
	/**
	 * Representacao textual do Jogador
	 * @return representacao string do Jogador
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Pontuacao: " + this.pontuacao + "\n");
		result.append(this.ambiente.toString());
		
		return result.toString();
	}
	
	/**
	 * Devolve a matriz que representa o Ambiente deste Jogador 
	 * para efeitos de simulacao
	 * @return A matriz
	 */
	public EstadoSimulacao[][] alvoSimulacao() {
		return this.ambiente.alvoSimulacao();
	}
	
	/**
	 * O ambiente deste jogador pode atuar?
	 * @return true, se pode
	 */
	public boolean podeAtuar() {
		return this.ambiente.podeAtuar();
	}
	
	/**
	 * Devolve a direcao que este Jogador quer usar na proxima
	 * jogada
	 * @return direcao
	 */
	public abstract String direcao();
	
	/**
	 * Devolve a forca que este Jogador quer usar na proxima
	 * jogada
	 * @return forca
	 */
	public abstract int forca();
}
