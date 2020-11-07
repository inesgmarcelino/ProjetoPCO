package ProjetoPCO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;


/**
 * Classe que representa as Instituicoes
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
 * @date Novembro 2020
 */
public class Instituicao {
	
	public static final int[] RISCO_ANOS = {2, 3, 5, 8};
	public static final int[] VENTOS_LIMITES = {0, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21};
	
	private String designacao;

	/**
	 * Inicializa os atributos do novo objeto Instituicao
	 * @param designacao
	 */
	public Instituicao(String designacao) {
		this.designacao = designacao;
	}
	
	/**
	 * Cria um novo objeto da classe Regiao
	 * @param nome 
	 * @param ultFogo Data do ultimo fogo que aconteceu
	 * @param largura A largura da regiao
	 * @param altura A altura da regiao
	 * @param casas
	 * @param estradas
	 * @param agua
	 */
	private void adicionaRegiao(String nome, Calendar ultFogo, int largura, int altura, List<Par<Integer,Integer>> casas,
			List<Par<Integer,Integer>> estradas, List<Par<Integer,Integer>> agua) {
		//se não houver nenhuma regiao com este nome nesta instituicao
		Regiao nova =  new Regiao(nome, ultFogo, largura, altura, casas, estradas, agua);
	}
	
	/**
	 * Verifica se existe alguma Regiao com esse nome na Instituicao
	 * @param nome
	 * @return true, se ja existir
	 */
	private boolean existeRegiao(String nome) {
		boolean existe;
		//if existe uma regiao nesta instituicao que tenha esse nome
		existe = true;
		
		return existe;
	}
	
	/**
	 * Devolver os nomes e os niveis de perigo das regioes da Instituicao
	 * @return Informacao pedida numa lista de pares (int,int)
	 */
	private List<Par<Integer,Integer>> niveisDePerigo() {
		
	}
	
	/**
	 * Devolve o alvo da simulacao da Regiao de maior nivel da Instituicao
	 * @return Array ...
	 */
	private EstadoSimulacao[] alvoSimulacao() {
		// (?)
		return this.ambiente;
	}
	
	/**
	 * Verifica se pelo menos uma Regiao da Instituicao tem elementos
	 * ardiveis
	 * @return true, se tiver
	 */
	private boolean podeAtuar() {
		boolean atuar;
		//if pelo menos uma reagiao da instituicao tem elementos ardiveis
		atuar = true;
		
		return atuar;
	}
	
	/**
	 * Regista um (classe/objeto?) Fogo
	 * @param regiao
	 * @param data
	 * @param sitios
	 */
	private void registaFogo(String regiao, Calendar data, List<Par<Integer,Integer>> sitios) {
		//mesma duvida de Regiao.java
	}
	
	/**
	 * Representacao textual desta Instituicao
	 */
	private String toString() {
		return "Designacao: " + this.designacao;
	}
}
