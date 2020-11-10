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
	private Regiao[] regioes;
	private EstadoSimulacao[][] ambiente;
	private int quantasRegioes;

	/**
	 * Inicializa os atributos do novo objeto Instituicao
	 * @param designacao
	 */
	public Instituicao(String designacao) {
		this.designacao = designacao;
		this.regioes = new Regiao [4]; //not sure
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
	public void adicionaRegiao(String nome, Calendar ultFogo, int largura, int altura, List<Par<Integer,Integer>> casas,
			List<Par<Integer,Integer>> estradas, List<Par<Integer,Integer>> agua) {
		//se não houver nenhuma regiao com este nome nesta instituicao
		
//		for (int i = 0; i < this.regioes.length; i++) {
//			if (this.regioes[i] == null) {
//				this.regioes[i] =  new Regiao(nome, ultFogo, largura, altura, casas, estradas, agua);
//			}
//		} -> está a adicionar a todos os lugares da lista de regioes
		
		
	}
	
	/**
	 * Verifica se existe alguma Regiao com esse nome na Instituicao
	 * @param nome
	 * @return true, se ja existir
	 */
	public boolean existeRegiao(String nome) {
		boolean existe = false;
		for(int i = 0; i < this.regioes.length; i++) {
			if(this.regioes[i].nome() == nome) {
				existe = true;
			} else {
				existe = false;
			}
		}
		return existe; // não está correto
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
//	public EstadoSimulacao[][] alvoSimulacao() {
//		// (?)
//		return this.ambiente;
//	}
	
//	/**
//	 * Verifica se pelo menos uma Regiao da Instituicao tem elementos
//	 * ardiveis
//	 * @return true, se tiver
//	 */
//	public boolean podeAtuar() {
//		boolean atuar = false;
//		
//		return atuar;
//	}
	
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
	public String toString() {
		return "Designacao: " + this.designacao;
	}
}
