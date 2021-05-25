import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa os JogadoresPrudentes
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
 * @date Dezembro 2020
 */
public class JogadorPrudente extends Jogador {
	// Recursos
	private int recursos;
	// Direcao
	private Direcionador dir;
	// Recursos Gastos
	private int recursosGastos;
	// Historico de Jogadas
	private List<Par<Integer,Integer>> historico; 
	
	/**
	 * Inicializa os atributos do novo objeto JogadorPrudente
	 * @param nLinhas Numero de Linhas do Ambiente
	 * @param nCols Numero de Colunas do Ambiente
	 * @param obstaculos Lista com as posicoes dos obstaculos
	 * @param recursos Numero de Recursos do Jogador
	 * @param dir Direcao a usar
	 * @requires nLinhas > 0 && nCols > 0 && obstaculos != null 
	 * && recursos > 0 && dir != null 
	 */
	public JogadorPrudente(int nLinhas, int nCols, List<Par<Integer,Integer>> obstaculos, 
			int recursos, Direcionador dir) {
		super(nLinhas, nCols, obstaculos);
		this.recursos = recursos;
		this.dir = dir;
		this.historico = new ArrayList<Par<Integer,Integer>>();
	}
	
	/**
	 * Devolve os recursos do JogadorPrudente
	 * @return Os recursos
	 */
	public int recursos() {
		return this.recursos;
	}
	
	/**
	 * Representacao textual do JogadorPrudente
	 * @return representacao string do JogadorPrudente
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(super.toString());
		result.append("Direcionador: " + this.dir.getClass() + "\n");
		result.append("Recursos: " + this.recursos + "    Gastos: " + this.recursosGastos + "\n");
		result.append("Jogadas: \n");
		for (int i = 0; i < this.historico.size(); i++) {
			result.append("Gasto: " + this.historico.get(i).primeiro() +"      Pontos Obtidos: " + this.historico.get(i).segundo() +"\n");
		}
		return result.toString();
	}
	
	/**
	 * Devolve a direcao que este JogadorPrudente vai usar na proxima
	 * jogada.
	 * @return direcao
	 */
	@Override
	public String direcao() {
		int preco = dir.precoConsulta(this.alvoSimulacao());
		this.recursos -= preco;
		this.recursosGastos += preco;
		
		return dir.direcao(this.alvoSimulacao());
	}
	
	/**
	 * Devolve como forca o valor maximo entre o numero de linhas
	 * e colunas do Ambiente do JogadorPrudente
	 * @return forca
	 */
	@Override
	public int forca() {
		Par<Integer,Integer> esteAmbiente = this.dimensoesAmbiente();
		
		return Math.max(esteAmbiente.primeiro(), esteAmbiente.segundo());
	} 
	
	/**
	 * O JogadorPrudente ainda pode jogar?
	 * O Ambiente do JogadorPrudente pode atuar?
	 * Os recursos que o JogadorPrudente ainda tem são suficientes para 
	 * pagar a consulta ao direcionador?
	 * Os recursosGastos é inferior ou igual ao total de pontos que ganhou
	 * multiplicando pelo numero de linhas e colunas?
	 * @return true, se pode
	 */
	@Override
	public boolean podeAtuar() {
		boolean valid1, valid2, valid3;
		
		valid1 = super.podeAtuar();
		valid2 = this.recursos >= dir.precoConsulta(this.alvoSimulacao());
		valid3 = this.recursosGastos <= (this.pontuacao() * 
				this.dimensoesAmbiente().primeiro() * 
				this.dimensoesAmbiente().segundo());
		
		return valid1 && valid2 && valid3;
		
	}
	
	/**
	 * Regista uma nova jogada do JogadorPrudente e regista a jogada 
	 * no historico de jogadas
	 * @param afetados Lista com as posicoes dos afetados pela jogada
	 * @param pontos Numero de pontos ganhos
	 * @requires afetados != null && pontos >= 0
	 */
	@Override
	public void registaJogadaComPontuacao(List<Par<Integer,Integer>> afetados, int pontos) {
		super.registaJogadaComPontuacao(afetados, pontos);
		this.historico.add(new Par<Integer,Integer> (dir.precoConsulta(this.alvoSimulacao()), pontos));
		
	}
}
