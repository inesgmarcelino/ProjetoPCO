import java.util.ArrayList;
import java.util.List;

/**
 * Representa objetos que sabem simular o processo de "contagio" num 
 * dado ambiente, numa dada direcao e com uma dada intensidade.
 * @author isabel nunes
 * @date Novembro 2020
 */
public class Simulador {
	// O ambiente sobre o qual os passos de simulacao sao feitos
	private EstadoSimulacao[][] ambiente;
	// Lista com as posicoes dos elementos que vao sendo afetados
	// durante os varios passos de simulacao
	private List<Par<Integer,Integer>> afetados;

	/**
	 * Inicializa os atributos do novo objeto
	 * @param ambiente O ambiente sobre o qual ocorrerao todas as simulacoes
	 * @requires ambiente != null && ambiente.length > 0 && 
	 *           ambiente[0] != null      
	 */
	public Simulador(EstadoSimulacao[][] ambiente) {
		this.ambiente = 
				new EstadoSimulacao [ambiente.length][ambiente[0].length];
		for(int i = 0 ; i < ambiente.length ; i++) {
			for (int j = 0 ; j < ambiente[i].length ; j++) {
				this.ambiente[i][j] = ambiente[i][j];
			}
		}	
		this.afetados = new ArrayList<Par<Integer,Integer>>();
	}

	/**
	 * O numero de linhas do ambiente do alvo de simulacao
	 */
	public int nLinhas() {
		return ambiente.length;
	}

	/**
	 * O numero de colunas do ambiente do alvo de simulacao
	 */
	public int nColunas() {
		return ambiente[0].length;
	}

	/**
	 * Registar que um elemento numa dada posicao vai ficar afetado
	 * @param linha A linha desse ponto
	 * @param coluna A coluna desse ponto
	 * @requires linha >= 0 && linha < this.nLinhas() &&
	 *           coluna >= 0 && coluna < this.nColunas()
	 */
	public void afetarElemento(int linha, int coluna) {
		this.ambiente[linha][coluna] = EstadoSimulacao.AFETADO;
		this.afetados.add(new Par<Integer,Integer>(linha,coluna));
	}

	/**
	 * Elementos do ambiente que foram afetados durante a simulacao
	 * @return Lista com as posicoes dos elementos que foram afetados 
	 *         durante os passos da simulacao
	 */
	public List<Par<Integer,Integer>> resultadoSimulacao() {
		return new ArrayList<Par<Integer,Integer>>(this.afetados);
	}

	/**
	 * Aplica um passo de simulacao do processo de contagio numa
	 * dada direcao, com uma dada forca
	 * @param direcao A direcao do processo de contagio
	 * @param forca Quantos vizinhos sao afetados na simulacao
	 * @requires direcao != null && direcao in {"N","S","E","O"} && 
	 *           forca >= 0
	 */
	public void passoSimulacao(String direcao, int forca) {
		// Faz uma copia do ambiente inicial do passo de simulacao
		EstadoSimulacao[][] original = copia(this.ambiente);

		// Simular 
		switch (direcao) {
		case "N": 
			sopraDeNorte (original, forca);
			break;
		case "E": 
			sopraDeLeste (original, forca);
			break;
		case "S": 
			sopraDeSul (original, forca);
			break;
		case "O": 
			sopraDeOeste (original, forca);
			break;	    	
		}
	}

	/**
	 * Contagio de um elemento numa dada posicao pelos vizinhos na vertical 
	 * @param original O ambiente inicial
	 * @param nVizinhos Quantos vizinhos podem afetar o elemento em questao
	 * @param lin Linha do elemento em questao
	 * @param col Linha do elemento em questao
	 * @param sentido De Norte (-1) ou de Sul (1)
	 * @requires original != null && nVizinhos >= 0 && 
	 *           (sentido == 1 || sentido == -1)
	 * @return true se o elemento na posicao lin,col do ambiente fica afetado 
	 *         por algum dos seus nVizinhos vizinhos na vertical 
	 */
	private boolean contagioVertical(EstadoSimulacao[][] original, 
			int nVizinhos, int lin, int col, int sentido){
		boolean mudou = false;		
		boolean temBarreira = false;
		for(int k = 1 ; k <= nVizinhos && !mudou && !temBarreira; k++) {
			if((lin + k * sentido) >= 0 && 
					(lin + k * sentido) < this.ambiente.length) {
				if (original[lin + k * sentido][col] == 
						EstadoSimulacao.OBSTACULO) {
					temBarreira = true;
				}
				if(!temBarreira && 
						(original[lin + k * sentido][col] == 
						EstadoSimulacao.AFETADO)) {
					mudou = true;
				}
			}
		}
		return mudou;
	}

	/**
	 * Simulacao quando a direcao do contagio e' de Norte para Sul
	 * @param original O ambiente inicial
	 * @param nVizinhos Quantos vizinhos podem afetar cada elemento
	 * @requires original != null && nVizinhos >= 0 
	 */
	private void sopraDeNorte(EstadoSimulacao[][] original, int nVizinhos){
		for (int j = 0 ; j < this.ambiente[0].length ; j++) {
			for (int i = 1 ; i < this.ambiente.length ; i++) {
				if (original[i][j] == EstadoSimulacao.LIVRE && 
						contagioVertical(original, nVizinhos, i, j, -1)) {
					this.ambiente[i][j] = EstadoSimulacao.AFETADO;
					this.afetados.add(new Par<Integer,Integer>(i,j));
				}
			}
		}
	}

	/**
	 * Simulacao quando a direcao do contagio e' de Sul para Norte
	 * @param original O ambiente inicial
	 * @param nVizinhos Quantos vizinhos podem afetar cada elemento
	 * @requires original != null && nVizinhos >= 0 
	 */
	private void sopraDeSul(EstadoSimulacao[][] original, int nVizinhos){
		for (int j = 0 ; j < this.ambiente[0].length ; j++) {
			for (int i = this.ambiente.length - 2 ; i >= 0 ; i--) {
				if (original[i][j] == EstadoSimulacao.LIVRE && 
						contagioVertical(original, nVizinhos, i, j, 1)) {
					this.ambiente[i][j] = EstadoSimulacao.AFETADO;
					this.afetados.add(new Par<Integer,Integer>(i,j));
				}
			}
		}
	}

	/**
	 * Contagio de um elemento numa dada posicao pelos vizinhos na horizontal 
	 * @param original O ambiente inicial
	 * @param nVizinhos Quantos vizinhos podem afetar o elemento em questao
	 * @param lin Linha do elemento em questao
	 * @param col Linha do elemento em questao
	 * @param sentido De Oeste (-1) ou de Este (1)
	 * @requires original != null && nVizinhos >= 0 && 
	 *           (sentido == 1 || sentido == -1)
	 * @return true se o elemento na posicao lin,col do ambiente fica afetado por
	 *         algum dos seus nVizinhos vizinhos na horizontal 
	 */
	private boolean contagioHorizontal(EstadoSimulacao[][] original,
			int nVizinhos, int lin, int col, int sentido){
		boolean mudou = false;		
		boolean temBarreira = false;
		for(int k = 1 ; k <= nVizinhos && !mudou && !temBarreira; k++) {
			if((col + k * sentido) >= 0 && 
					(col + k * sentido) < this.ambiente[0].length) {
				if (original[lin][col + k * sentido] == 
						EstadoSimulacao.OBSTACULO) {
					temBarreira = true;
				}
				if(!temBarreira && 
						(original[lin][col + k * sentido] == 
						EstadoSimulacao.AFETADO)) {
					mudou = true;
				}
			}
		}
		return mudou;
	}

	/**
	 * Simulacao quando a direcao do contagio e' de Este para Oeste
	 * @param original O ambiente inicial
	 * @param nVizinhos Quantos vizinhos podem afetar cada elemento
	 * @requires original != null && nVizinhos >= 0 
	 */
	private void sopraDeLeste(EstadoSimulacao[][] original, int nVizinhos){
		for (int i = 0 ; i < this.ambiente.length ; i++) {
			for (int j = this.ambiente[0].length - 2 ; j >= 0  ; j--) {
				if (original[i][j] == EstadoSimulacao.LIVRE && 
						contagioHorizontal(original, nVizinhos, i, j, 1)) {
					this.ambiente[i][j] = EstadoSimulacao.AFETADO;
					this.afetados.add(new Par<Integer,Integer>(i,j));
				}
			}
		}
	}

	/**
	 * Simulacao quando a direcao do contagio e' de Oeste para Este
	 * @param original O ambiente inicial
	 * @param nVizinhos Quantos vizinhos podem afetar cada elemento
	 * @requires original != null && nVizinhos >= 0 
	 */
	private void sopraDeOeste(EstadoSimulacao[][] original, int nVizinhos){
		for (int i = 0 ; i < this.ambiente.length ; i++) {
			for (int j = 1 ; j < this.ambiente[0].length ; j++) {
				if (original[i][j] == EstadoSimulacao.LIVRE && 
						contagioHorizontal(original, nVizinhos, i, j, -1)) {
					this.ambiente[i][j] = EstadoSimulacao.AFETADO;
					this.afetados.add(new Par<Integer,Integer>(i, j));
				}
			}
		}
	}

	/**
	 * Copia de uma matriz
	 * @param t A matriz
	 * @requires t != null 
	 * @return Uma copia de t
	 */
	private static EstadoSimulacao[][] copia(EstadoSimulacao [][] t){
		EstadoSimulacao[][] result = 
				new EstadoSimulacao [t.length][t[0].length];
		for (int i = 0 ; i < t.length ; i++)
			for (int j = 0 ; j < t[0].length ; j++)
				result[i][j] = t[i][j];
		return result;
	}

}

