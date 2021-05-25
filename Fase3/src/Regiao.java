import java.util.Calendar;
import java.util.List;

/**
 * Classe cujas instancias representam regioes populadas com terrenos, casas, 
 * (que podem estar ardidos), estradas e agua  
 * @author isabel nunes
 * @date Novembro 2020
 *
 */
public class Regiao {
	// Simbolos na mesma ordem que os valores do enumerado EstadoAmbiente
	private static final char[] SIMBOLOS = {'H', '.', '=', '~', '!'};

	private String nome;
	private EstadoAmbiente[][] quadricula;
	private Calendar dataUltimoFogo;

	/**
	 * Inicializa uma regiao
	 * @param nome A designacao da nova regiao
	 * @param ultFogo A data em que ocorreu o ultimo fogo
	 * @param linhas Quantas "ruas" horizontais tem a nova regiao
	 * @param colunas Quantas "ruas" verticais tem a nova regiao
	 * @param casas Posicoes <linha,coluna> onde se encontram as casas
	 * @param estradas Posicoes <linha,coluna> onde se encontram estradas
	 * @param agua Posicoes <linha,coluna> onde se encontra agua
	 * @requires  
	 */
	public Regiao(String nome, Calendar ultFogo, int linhas, int colunas, 
			List<Par<Integer,Integer>> casas, 
			List<Par<Integer,Integer>> estradas, 
			List<Par<Integer,Integer>> agua) {
		this.nome = nome;
		this.dataUltimoFogo = ultFogo;
		this.quadricula = new EstadoAmbiente[linhas][colunas];
		// Preencher todas as posicoes como terrenos
		inicializaQuadricula(EstadoAmbiente.TERRENO);
		// Preencher casas, estradas e agua
		preenchePosicoes(casas, EstadoAmbiente.CASA);
		preenchePosicoes(estradas, EstadoAmbiente.ESTRADA);
		preenchePosicoes(agua, EstadoAmbiente.AGUA);
	}

	/**
	 * Preencher todas as posicoes da quadricula com um dado valor
	 * @param estado O valor a preencher
	 */
	private void inicializaQuadricula(EstadoAmbiente estado) {
		for(int i = 0 ; i < this.quadricula.length ; i++) {
			for (int j = 0 ; j < this.quadricula[0].length ; j++) {
				this.quadricula[i][j] = estado;
			}
		}		
	}

	/**
	 * Preencher umas dadas posicoes da quadricula com um dado valor
	 * @param posicoes As posicoes a preencher
	 * @param estado O valor a preencher
	 */
	private void preenchePosicoes(List<Par<Integer,Integer>> posicoes, 
			EstadoAmbiente estado) {
		for (Par<Integer,Integer> p : posicoes) {
			this.quadricula[p.primeiro()][p.segundo()] = estado;
		}
	}

	/**
	 * Registar a ocorrencia de fogo numa dada data, em que arderam 
	 * determinadas posicoes da regiao
	 * @param data Data em que ocorreu o fogo
	 * @param sitios As posicoes <linha,coluna> dos sitios ardidos
	 * @requires data != null && sitios != null
	 */
	public void registaFogo(Calendar data, List<Par<Integer,Integer>> sitios) {
		this.dataUltimoFogo = data;
		preenchePosicoes(sitios, EstadoAmbiente.ARDIDO);
	}

	/**
	 * Quantas posicoes existem na regiao com casas ou terreno?
	 * @return
	 */
	public int ardiveis() {
		int result = 0;
		for (EstadoAmbiente[] linha : this.quadricula) {
			for (EstadoAmbiente ea : linha) {
				if(ea == EstadoAmbiente.CASA || ea == EstadoAmbiente.TERRENO){
					result++;				                          
				}				
			}			
		}
		return result;
	}

	/**
	 * Quantas posicoes existem na regiao que sejam estrada, agua ou 
	 * estejam ardidas?
	 * @return
	 */
	private int obstaculos() {
		return this.quadricula.length * this.quadricula[0].length - 
				this.ardiveis();
	}

	/**
	 * Determinados valores sao validos para criar uma regiao?
	 * @param linhas Quantas "ruas" horizontais tem a regiao
	 * @param colunas Quantas "ruas" verticais tem a regiao
	 * @param casas Posicoes <linha,coluna> onde se encontram casas
	 * @param estradas Posicoes <linha,coluna> onde se encontram estradas
	 * @param agua Posicoes <linha,coluna> onde se encontra agua
	 * @return true se linhas e colunas sao positivos, e se as listas 
	 *              contem posicoes legitimas
	 */
	public static boolean dadosValidos(int linhas, int colunas, 
			List<Par<Integer,Integer>> casas, 
			List<Par<Integer,Integer>> estradas, 
			List<Par<Integer,Integer>> agua) {

		boolean validos = linhas > 0 && colunas > 0;
		validos = validos && validos(linhas, colunas, casas) && 
				validos(linhas, colunas, estradas) && 
				validos(linhas, colunas, agua);

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
			result = result && 
					p.primeiro() >= 0 && p.primeiro() < d1 && 
					p.segundo() >= 0 && p.segundo() < d2;
		}
		return result;
	}

	/**
	 * O nome desta regiao
	 * @return
	 */
	public String nome() {
		return this.nome;
	}

	/**
	 * Uma matriz representando esta regiao para efeitos de 
	 * simulacao de fogos
	 * @return
	 */
	public EstadoSimulacao[][] alvoSimulacao() {

		EstadoSimulacao[][] result = new EstadoSimulacao
				[this.quadricula.length][this.quadricula[0].length];
		for (int i = 0 ; i < this.quadricula.length ; i++) {
			for (int j = 0 ; j < this.quadricula[0].length ; j++) {
				switch(this.quadricula[i][j]) {
				case ESTRADA: case AGUA : case ARDIDO : 
					result[i][j] = EstadoSimulacao.OBSTACULO;
					break;
				case CASA: case TERRENO : 
					result[i][j] = EstadoSimulacao.LIVRE;
					break;				                          
				}				
			}			
		}
		return result;
	}

	/**
	 * Qual o nivel de perigo desta regiao numa dada data e relativamente
	 * a dados valores que associam o numero de anos desde o ultimo fogo
	 * com niveis de perigo
	 * @param data A data em relacao a' qual se deve calcular o nivel de perigo
	 * @param tempoLimites Valores de numero de anos passados desde ultimo
	 *                     fogo que definem niveis de perigo (atraves da 
	 *                     posicao que ocupam no array)
	 * @return Nivel de perigo desta regiao (ver forma de calculo no enunciado
	 *         da Fase 2)
	 */
	public NivelPerigo nivelPerigo(Calendar data, int[] tempoLimites) {
		// Toma em conta os ardiveis (terrenos e casas nao ardidos) e os
		// obstaculos (agua, estradas e ardidos) e a data do ultimo fogo.
		// Nivel de perigo depende do numero de anos que já passaram desde 
		// ultimo fogo e do array tempoLimites.
		// Este nivel ainda pode ser agravado pelo racio 
		// (ardiveis-obstaculos)/linhas*colunas
		int anosDesdeUltFogo = data.get(Calendar.YEAR) - 
				this.dataUltimoFogo.get(Calendar.YEAR);

		double racio = (double)(this.ardiveis() - this.obstaculos()) / 
				(this.quadricula.length * this.quadricula[0].length);

		double nivel = gravidadeTempo(tempoLimites, anosDesdeUltFogo);

		nivel *= (1 + racio);
		if (nivel >= NivelPerigo.values().length) {
			nivel = NivelPerigo.values().length - 1;
		}
		return NivelPerigo.values()[(int)Math.round(nivel)];
	}

	/**
	 * Nivel de perigo quando tomado em conta o numero de anos desde
	 * o ultimo fogo
	 * @param limites Valores de numero de anos passados desde ultimo
	 *                fogo que definem niveis de perigo (atraves da 
	 *                posicao que ocupam no array)
	 * @param valor O valor a comparar com os varios elementos de limites
	 * @return (ver forma de calculo no enunciado da Fase 2)
	 */
	private static int gravidadeTempo(int[] limites, int valor) {
		int result = -1;
		for(int i = 0 ; i < limites.length && result == -1 ; i++) {
			if(valor <= limites[i]) {
				result = i;
			}
		}

		return result == -1 ? limites.length : result;
	}

	/**
	 * Representacao textual desta regiao
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Nome: " + this.nome + "  Data ult. fogo: " + 
				this.dataUltimoFogo.get(Calendar.YEAR) + "/" + 
				this.dataUltimoFogo.get(Calendar.MONTH) + "/" + 
				this.dataUltimoFogo.get(Calendar.DAY_OF_MONTH) + "\n");
		for (int i = 0 ; i < this.quadricula.length ; i++) {
			for (int j = 0 ; j < this.quadricula[0].length ; j++) {
				result.append(SIMBOLOS[this.quadricula[i][j].ordinal()]);
			}
			result.append("\n");
		}
		return result.toString();
	}
}


