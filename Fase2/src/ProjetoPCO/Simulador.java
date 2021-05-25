package ProjetoPCO;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa objetos que sabem simular o processo de "contagio" num 
 * dado ambiente, numa dada direcao e com uma dada intensidade.
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
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
	 * @requires ambiente != null && ambiente.length > 0 && ambiente[0] != null
	 *           
	 */
	public Simulador(EstadoSimulacao[][] ambiente) {
		this.ambiente = new EstadoSimulacao [ambiente.length][ambiente[0].length];
		for(int i = 0 ; i < ambiente.length ; i++) {
			for (int j = 0 ; j < ambiente[i].length ; j++) {
				this.ambiente[i][j] = ambiente[i][j];
			}
		}	
		this.afetados = new ArrayList<Par<Integer,Integer>>();
	}
	
	/**
	 * A largura do ambiente do alvo de simulacao
	 */
	public int largura() {
		return ambiente.length;
	}
	
	/**
	 * A altura do ambiente do alvo de simulacao
	 */
	public int altura() {
		return ambiente[0].length;
	}

	/**
	 * Registar que um elemento numa dada posicao vai ficar afetado
	 * @param linha A linha desse ponto
	 * @param coluna A coluna desse ponto
	 * @requires linha >= 0 && linha < this.largura() &&
	 *           coluna >= 0 && coluna < this.altura()
	 */
	public void afetarElemento(int linha, int coluna) {
		this.ambiente[linha][coluna] = EstadoSimulacao.AFETADO;
		this.afetados.add(new Par<Integer,Integer>(linha,coluna));
	}
	
	/**
	 * Elementos do ambiente que foram afetados durante a simulacao
	 * @return Lista com as posicoes dos elementos que foram afetados 
	 *         durante todos os passos da simulacao
	 */
	public List<Par<Integer,Integer>> resultadoSimulacao() {
		return this.afetados;
	}

	
	/**
	 * Aplica um passo de simulacao do processo de contagio numa
	 * dada direcao, com uma dada forca
	 * @param direcao A direcao do processo de contagio
	 * @param forca A forca do processo de contagio
	 * @param limites Tabela para saber quantos vizinhos sao afetados 
	 *                na simulacao
	 * @requires direcao != null && direcao in {"N","S","E","O"} && 
	 *           forca >= 0 e limites != null && limites.length > 0
	 */
	public void passoSimulacao(String direcao, int forca, int[] limites) {
		// Quantos vizinhos de um dado elemento vao afeta'-lo?
	    int afeta = afetaQuantos(limites, forca);
	    // Faz uma copia do ambiente inicial do passo de simulacao
	    EstadoSimulacao[][] original = copia(this.ambiente);

	    // Simular 
		switch (direcao) {
		case "N": 
			sopraDeNorte (original, afeta);
			break;
		case "E": 
			sopraDeLeste (original, afeta);
			break;
		case "S": 
			sopraDeSul (original, afeta);
			break;
		case "O": 
			sopraDeOeste (original, afeta);
			break;	    	
		}
	}

	/**
	 * Representacao textual do ambiente de simulacao
	 * @return Representacao textual do ambiente de simulacao
	 */
	public String representacaoAmbiente() {
		StringBuilder result = new StringBuilder();
		for(int i = 0 ; i < ambiente.length ; i++) {
			for (int j = 0 ; j < ambiente[i].length ; j++) {
				result.append(this.ambiente[i][j] + "  ");
			}
			result.append("\n");
		}	
		result.append("======\n");
		return result.toString();
	}

	/**
	 * Quantos vizinhos podem afetar cada elemento?
	 * @param limites Array que define a relacao entre a forca do
	 *        vento e o numero de vizinhos que afetam cada elemento
	 *        do ambiente
	 * @param forca A forca do vento
	 * @return O numero de vizinhos que podem afetar cada elemento de
	 *         acordo com:
	 *         Dada uma tabela de n+1 valores v0, v1, ..., vn, e a 
	 *         força f do vento num dado instante, 
     *         - se f <= v0 então o número de vizinhos afetados é zero;
     *         - se vi-1 < f <= vi então o número de vizinhos afetados é i; 
     *         - se vn < f então o número de vizinhos afetados é n+1.

	 */
	private static int afetaQuantos(int[] limites, int forca) {
		int result = -1;
		for(int i = 0 ; i < limites.length && result == -1 ; i++) {
			if(forca <= limites[i]) {
				result = i;
			}
		}
		return result == -1 ? limites.length : result;
	}


	/**
	 * Contagio de um elemento numa dada posicao pelos vizinhos na vertical 
	 * @param nVizinhos Quantos vizinhos podem afetar o elemento em questao
	 * @param sentido De Norte (-1) ou de Sul (1)
	 * @requires original != null && nVizinhos >= 0 && 
	 *           (sentido == 1 || sentido == -1)
	 * @return true se o elemento na posicao i,j do ambiente fica afetado por
	 *         algum dos seus nVizinhos vizinhos na vertical 
	 */
	private boolean contagioVertical(EstadoSimulacao[][] original, int nVizinhos,
			int i, int j, int sentido){
		boolean mudou = false;		
		boolean temBarreira = false;
		for(int k = 1 ; k <= nVizinhos && !mudou && !temBarreira; k++) {
			if((i + k * sentido) >= 0 && (i + k * sentido) < this.ambiente.length) {
				if (original[i + k * sentido][j] == EstadoSimulacao.OBSTACULO) {
					temBarreira = true;
				}
				if(!temBarreira && 
						(original[i + k * sentido][j] == EstadoSimulacao.AFETADO)) {
					mudou = true;
				}
			}
		}
		return mudou;
	}
	
	/**
	 * Simulacao quando a direcao do contagio e' de Norte para Sul
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
	 * @param nVizinhos Quantos vizinhos podem afetar o elemento em questao
	 * @param sentido De Oeste (-1) ou de Este (1)
	 * @requires original != null && nVizinhos >= 0 && 
	 *           (sentido == 1 || sentido == -1)
	 * @return true se o elemento na posicao i,j do ambiente fica afetado por
	 *         algum dos seus nVizinhos vizinhos na horizontal 
	 */
	private boolean contagioHorizontal(EstadoSimulacao[][] original, int nVizinhos,
			int i, int j, int sentido){
		boolean mudou = false;		
		boolean temBarreira = false;
		for(int k = 1 ; k <= nVizinhos && !mudou && !temBarreira; k++) {
			if((j + k * sentido) >= 0 && (j + k * sentido) < this.ambiente[0].length) {
				if (original[i][j + k * sentido] == EstadoSimulacao.OBSTACULO) {
					temBarreira = true;
				}
				if(!temBarreira && 
						(original[i][j + k * sentido] == EstadoSimulacao.AFETADO)) {
					mudou = true;
				}
			}
		}
		return mudou;
	}

	/**
	 * Simulacao quando a direcao do contagio e' de Este para Oeste
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
		EstadoSimulacao[][] result = new EstadoSimulacao [t.length][t[0].length];
        for (int i = 0 ; i < t.length ; i++)
        	for (int j = 0 ; j < t[0].length ; j++)
        			result[i][j] = t[i][j];
        return result;
	}

}
