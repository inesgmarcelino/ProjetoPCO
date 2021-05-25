import java.util.List;

/**
 * Classe cujas instancias representam ambientes de jogo
 * @author isabel nunes
 * @date Novembro 2020
 */
public class Ambiente implements Acionavel {

	// os elementos do ambiente dispostos em matriz
	private char[][] quadricula;
	// quantas vezes, quando se registam afetados, o
	// numero de elementos livres se mantem o mesmo
	private int vezesLivresIguais;
	// quantas vezes o numero de elementos livres se pode
	// manter igual,entre invocacoes do metodo registaAfetados(),
	// ate' se considerar que ja' nao se pode atuar
	public static final int MAXIMO_LIVRES_IGUAIS = 5;

	/**
	 * Inicializa o novo ambiente
	 * @param nLinhas O numero de linhas do novo ambiente
	 * @param nColunas O numero de colunas do novo ambiente
	 * @param obstaculos As posicoes no ambiente onde se encontram
	 *                   os obstaculos
	 * @requires nLinhas > 0 && nColunas > 0 && obstaculos != null &&
	 *           dadosValidos(nLinhas, nColunas, obstaculos)
	 */
	public Ambiente(int nLinhas, int nColunas, 
			List<Par<Integer,Integer>> obstaculos) {
		this.quadricula = new char[nLinhas][nColunas];
		// Preencher todas as posicoes como livres
		encheQuadricula('.');
		// Preencher obstaculos
		preenchePosicoes(obstaculos, 'X');
	}

	/**
	 * Quantas linhas e colunas tem o ambiente no total?
	 * @return Um par com o numero de linhas e o numero de
	 *         colunas deste ambiente
	 */
	public Par<Integer,Integer> dimensoes(){
		return new Par<Integer,Integer>(this.quadricula.length,
				this.quadricula[0].length);
	}

	/**
	 * Preenche todas as posicoes da quadricula com um dado valor
	 * @param estado O valor em questao
	 */
	private void encheQuadricula(char estado) {
		for(int i = 0 ; i < this.quadricula.length ; i++) {
			for (int j = 0 ; j < this.quadricula[0].length ; j++) {
				this.quadricula[i][j] = estado;
			}
		}		
	}	

	/**
	 * Preencher determinadas posicoes da quadricula com um dado valor
	 * @param posicoes Os pares linha,coluna onde preencher
	 * @param estdao O valor em questao
	 */
	private void preenchePosicoes(List<Par<Integer,Integer>> posicoes, 
			char estado) {
		for (Par<Integer,Integer> p : posicoes) {
			this.quadricula[p.primeiro()][p.segundo()] = estado;
		}
	}

	/**
	 * Quantos elementos da quadricula estao livres numa regiao delimitada
	 * por dados numeros de linhas e colunas
	 * @param primLin Menor linha que delimita a regiao de interesse
	 * @param ultLin Maior linha que delimita a regiao de interesse
	 * @param primCol Menor coluna que delimita a regiao de interesse
	 * @param ultCol Maior coluna que delimita a regiao de interesse
	 * @requires primLin <= ultLin && primCol <= ultCol
	 * @return
	 */
	private int livres(int primLin, int ultLin, int primCol, int ultCol) {
		int result = 0;
		for (int i = primLin ; i <= ultLin ; i++) {
			for (int j = primCol ; j <= ultCol ; j++) {
				if(this.quadricula[i][j] == '.') {
					result++;				                          
				}				
			}			
		}
		return result;
	}

	/**
	 * Determinados valores sao validos para criar um ambiente?
	 * @param nLinhas Quantas linhas tem o ambiente
	 * @param nColunas Quantas colunas tem o ambiente
	 * @param posicoes Posicoes <linha,coluna>
	 * @return true se nLinhas e nColunas sao positivos, e se posicoes 
	 *              contem posicoes legitimas
	 */
	public static boolean dadosValidos(int nLinhas, int nColunas, 
			List<Par<Integer,Integer>> posicoes) {

		boolean validos = nLinhas > 0 && nColunas > 0 && 
				validos(nLinhas, nColunas, posicoes);

		return validos;
	}

	/**
	 * Determinadas posicoes sao validas?
	 * @param d1 Valor maximo para as linhas
	 * @param d2 Valor maximo para as colunas
	 * @param v Posicoes <linha,coluna> para verificar
	 * @return true se os elementos de v sao todos nao null e
	 *         se os seus valores para as linhas e colunas sao
	 *          >= 0 e menores que d1 e d2, respetivamente
	 */
	private static boolean validos(int d1, int d2, 
			List<Par<Integer,Integer>> v) {
		boolean result = v != null;
		for(int i = 0 ; i < v.size() && result ; i++) {
			Par<Integer,Integer> p = v.get(i);
			result = result && p != null;
			result = result && p.primeiro() >= 0 && p.primeiro() < d1 && 
					p.segundo() >= 0 && p.segundo() < d2;				
		}
		return result;
	}

	/**
	 * Uma matriz representando este ambiente para efeitos de 
	 * simulacao de jogadas
	 * @return
	 */
	@Override
	public EstadoSimulacao[][] alvoSimulacao() {
		EstadoSimulacao[][] result = new EstadoSimulacao
				[this.quadricula.length][this.quadricula[0].length];
		for (int i = 0 ; i < this.quadricula.length ; i++) {
			for (int j = 0 ; j < this.quadricula[0].length ; j++) {
				if (this.quadricula[i][j] == 'X') {
					result[i][j] = EstadoSimulacao.OBSTACULO;
				} else if (this.quadricula[i][j] == '.') {
					result[i][j] = EstadoSimulacao.LIVRE;
				} else if (this.quadricula[i][j] == '*') {
					result[i][j] = EstadoSimulacao.AFETADO;
				}
			}			
		}
		return result;
	}

	/**
	 * Existem elementos livres neste ambiente e nao tem havido
	 * sempre o mesmo numero de livres durante MAXIMO_LIVRES_IGUAIS
	 * vezes?
	 */
	@Override
	public boolean podeAtuar() {
		int livresAgora = livres(0, this.quadricula.length - 1, 
				0, this.quadricula[0].length - 1);
		return livresAgora > 0 && 
				this.vezesLivresIguais < MAXIMO_LIVRES_IGUAIS;
	}

	/**
	 * Registar como afetados determinados elementos deste ambiente
	 * @param afetados As posicoes dos elementos que vao ficar afetados
	 */
	public void registaAfetados(List<Par<Integer, Integer>> afetados) {
		preenchePosicoes(afetados, '*');	
		if(afetados.size() == 0) {
			this.vezesLivresIguais++;
		} else {
			this.vezesLivresIguais = 0;
		}
	}

	/**
	 * Representacao textual deste ambiente
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0 ; i < this.quadricula.length ; i++) {
			for (int j = 0 ; j < this.quadricula[0].length ; j++) {
				result.append(this.quadricula[i][j]);
			}
			result.append("\n");
		}
		return result.toString();
	}
}
