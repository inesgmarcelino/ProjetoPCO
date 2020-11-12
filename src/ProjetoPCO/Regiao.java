package ProjetoPCO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * Classe que representa as Regioes
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
 * @date Novembro 2020
 */
public class Regiao {
	
	private String nome;
	private Calendar ultFogo;
	private String[][] regiao; //not sure
	private List<Par<Integer,Integer>> casas;
	private List<Par<Integer,Integer>> estradas;
	private List<Par<Integer,Integer>> agua;

	/**
	 * Inicializa os atributos do novo objeto Regiao
	 * @param nome
	 * @param ultFogo
	 * @param largura
	 * @param altura
	 * @param casas
	 * @param estradas
	 * @param agua
	 */
	public Regiao (String nome, Calendar ultFogo, int largura, int altura, List<Par<Integer,Integer>> casas,
			List<Par<Integer,Integer>> estradas, List<Par<Integer,Integer>> agua) {
		this.nome = nome;
		this.ultFogo = ultFogo;
		this.casas = casas;
		this.estradas = estradas;
		this.agua = agua;
		this.regiao = new String[largura][altura];
		
		for (int i = 0; i < this.regiao.length; i++) {
			for (int j = 0; j < this.regiao[i].length; j++) {
				
				for (int c = 0; c < this.casas.size(); c++) {
					if (i == this.casas.get(c).primeiro() && j == this.casas.get(c).segundo()) {
						this.regiao[i][j] = "H" ;
					}
				}
				
				for (int e = 0; e < this.estradas.size(); e++) {
					if (i == this.estradas.get(e).primeiro() && j == this.estradas.get(e).segundo()) {
						this.regiao[i][j] = "=";
					}
				}
				
				for (int a = 0; a < this.agua.size(); a++) {
					if (i == this.agua.get(a).primeiro() && j == this.agua.get(a).segundo()) {
						this.regiao[i][j] = "~";
					}
				}
				
				if (this.regiao[i][j] == null) {
					this.regiao[i][j] = ".";
				}
			}
		}
		
	}
	
	/**
	 * Devolve o nome da Regiao
	 */
	public String nome() {
		return this.nome;
	}
	
	/**
	 * Devolve o numero de elementos ardiveis da Regiao
	 * @return count >= 0
	 */
	public int ardiveis() {
		int count = 0;
		for (int i = 0; i < this.regiao.length; i++) {
			for (int j = 0; j < this.regiao[i].length; j++) {
				if (this.regiao[i][j] == "." || this.regiao[i][j] == "H") {
					count += 1;
				}
			}
		}
		return count;
	}
	
	/**
	 * Regista um (classe/objeto?) Fogo
	 * @param data
	 * @param sitios
	 */
	public void registaFogo(Calendar data, List<Par<Integer,Integer>> sitios) {
		this.ultFogo = data;
		
		for (int i = 0; i < this.regiao.length; i++) {
			for (int j = 0; j < this.regiao[i].length; j++) {
				for (int k = 0; k < sitios.size(); k++) {
					if (i == sitios.get(k).primeiro() && j == sitios.get(k).segundo()) {
						this.regiao[i][j] = "!";
					}
				}
			}
		}

	}
	
	/**
	 * Verifica se os dados da Regiao sao validos
	 * @param largura
	 * @param altura
	 * @param casas
	 * @param estradas
	 * @param agua
	 * @return true, se posicoes das casas, estradas e agua sao
	 * corretas para um ambiente com a largura e altura dadas
	 */
	public static boolean dadosValidos(int largura, int altura, List<Par<Integer,Integer>> casas,
			List<Par<Integer,Integer>> estradas, List<Par<Integer,Integer>> agua) {
		
		boolean validCasas = true, validEstradas = true, validAgua = true;
		
		for(int i = 0; i < casas.size(); i++) {
			if(casas.get(i).primeiro() < largura && casas.get(i).segundo() < altura) {
				validCasas = true;
			} else {
				validCasas = false;
			}
		}
		
		for(int i = 0; i < estradas.size(); i++) {
			if(estradas.get(i).primeiro() < largura && estradas.get(i).segundo() < altura) {
				validEstradas = true;
			} else {
				validEstradas = false;
			}
		}
		
		for(int i = 0; i < agua.size(); i++) {
			if(agua.get(i).primeiro() < largura && agua.get(i).segundo() < altura) {
				validAgua = true;
			} else {
				validAgua = false;
			}
		}
		return validCasas && validEstradas && validAgua;
	}
	
	/**
	 * Devolve a matriz da regiao em que os terrenos e casas nao
	 * ardidos sao representados por LIVRE, e a agua, estradas e
	 * os elementos ja ardidos sao representados por OBSTACULO
	 * @return Array ...
	 */
	public EstadoSimulacao[][] alvoSimulacao() {
		EstadoSimulacao[][] alvo = new EstadoSimulacao[this.regiao.length][this.regiao[0].length];
		
		for (int i = 0; i < this.regiao.length; i++) {
			for (int j = 0; j < this.regiao[i].length; j++) {
				for (int c = 0; c < this.casas.size(); c++) {
					if (i == this.casas.get(c).primeiro() && j == this.casas.get(c).segundo()) {
						alvo[i][j] = EstadoSimulacao.LIVRE;
					}
				}
				for (int e = 0; e < this.estradas.size(); e++) {
					if (i == this.estradas.get(e).primeiro() && j == this.estradas.get(e).segundo()) {
						alvo[i][j] = EstadoSimulacao.OBSTACULO;
					}
				}
				for (int a = 0; a < this.agua.size(); a++) {
					if (i == this.agua.get(a).primeiro() && j == this.agua.get(a).segundo()) {
						alvo[i][j] = EstadoSimulacao.OBSTACULO;
					}
				}
				if (alvo[i][j] == null) {
					alvo[i][j] = EstadoSimulacao.LIVRE;
				}
			}
		}
		return alvo;
	}
	
	/**
	 * Devolve o nivel de perigo da Regiao
	 * @param data
	 * @param tempoLimites
	 * @return
	 */
	public NivelPerigo nivelPerigo(Calendar data, int[] tempoLimites) {
		NivelPerigo result = null;
		int difAno =  data.get(Calendar.YEAR) - this.ultFogo.get(Calendar.YEAR);
		int nivel = 0;
		for (int i = 0; i < tempoLimites.length; i++) {
			if (tempoLimites[i - 1] < difAno && difAno <= tempoLimites[i]) {
				nivel = i;
			} else if (tempoLimites[tempoLimites.length - 1] < difAno) {
				nivel = tempoLimites.length;
			}
		}
		int obstaculos = 0;
		for (int i = 0; i < this.regiao.length; i++) {
			for (int j = 0; j < this.regiao[i].length; j++) {
				if (this.regiao[i][j] == "~" || this.regiao[i][j] == "=" || this.regiao[i][j] == "!") {
						obstaculos += 1;
				}
			}
		}
		
		int quociente = (this.ardiveis() - obstaculos) / (this.regiao.length * this.regiao[0].length);
		nivel *= (1 + quociente);
		
		if (Math.round(nivel) >= NivelPerigo.values().length) {
			result = NivelPerigo.values()[NivelPerigo.values().length - 1];
		}
		
		return result;
	}
	
	/**
	 * Representacao textual da Regiao
	 * @return
	 */
	private String toString() {
		return "Nome: " + this.nome + " Data ult. fogo: " + this.ultFogo;
		//falta a representacao da regiao
	}
}
