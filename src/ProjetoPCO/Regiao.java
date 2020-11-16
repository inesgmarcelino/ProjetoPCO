package ProjetoPCO;

import java.util.Calendar;
import java.util.List;


/**
 * Classe que representa as Regioes
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
 * @date Novembro 2020
 */
public class Regiao {
	// O nome da Regiao
	private String nome;
	// Data do ultimo fogo
	private Calendar ultFogo;
	// regiao
	private String[][] regiao; 
	// Lista com as posicoes das casas na regiao
	private List<Par<Integer,Integer>> casas;
	// Lista com as posicoes das estradas na regiao
	private List<Par<Integer,Integer>> estradas;
	// Lista com as posicoes da agua na regiao
	private List<Par<Integer,Integer>> agua;

	/**
	 * Inicializa os atributos do novo objeto Regiao
	 * @param nome O nome
	 * @param ultFogo Data do ultimo fogo
	 * @param largura Largura da regiao
	 * @param altura Altura da regiao
	 * @param casas	Lista com as posicoes das casas
	 * @param estradas Lista com as posicoes das estradas
	 * @param agua Lista com as posicoes da agua
	 * @requires nome != null && ultFogo != null && largura > 0 && altura > 0 
	 */			
	public Regiao (String nome, Calendar ultFogo, int largura, int altura, List<Par<Integer,Integer>> casas,
			List<Par<Integer,Integer>> estradas, List<Par<Integer,Integer>> agua) {
		this.nome = nome;
		this.ultFogo = ultFogo;
		this.casas = casas;
		this.estradas = estradas;
		this.agua = agua;
		this.regiao = new String[largura][altura];
		
		// CONSTRUIR ARRAY BIDIMENSIONAL REGIAO
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
	 * @return O nome da Regiao
	 */
	public String nome() {
		return this.nome;
	}
	
	/**
	 * Conta o numero de elementos ardiveis ha na Regiao
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
	 * Regista um novo fogo
	 * @param data Data do fogo
	 * @param sitios Lista com as posicoes dos sitios ardidos no fogo
	 * @requires data != null && sitios != null
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
	 * @param largura Largura da regiao
	 * @param altura Altura da regiao
	 * @param casas	Lista com as posicoes das casas
	 * @param estradas Lista com as posicoes das estradas
	 * @param agua Lista com as posicoes da agua
	 * @requires largura > 0 && altura > 0
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
	 * A matriz da regiao em que os terrenos e casas nao
	 * ardidos sao representados por LIVRE, e a agua, estradas e
	 * os elementos ja ardidos sao representados por OBSTACULO
	 * @return matriz da regiao representada por LIVRE e OBSTACULO
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
	 * O nivel de perigo da Regiao
	 * @param data Data atual
	 * @param tempoLimites Array que define os riscos de anos que afetaram
	 * o nivel de perigo da regiao
	 * @requires data != null && tempoLimites != null
	 * @return nivel de perigo da regiao
	 */
	public NivelPerigo nivelPerigo(Calendar data, int[] tempoLimites) {
		int perigo = -1;
		int difAno = data.get(Calendar.YEAR) - this.ultFogo.get(Calendar.YEAR);
		
		for (int i = 0; i < tempoLimites.length && perigo == -1; i++) {
			if (difAno <= tempoLimites[i]) {
				perigo = i;
			}
		}
		perigo = (perigo == -1 ? tempoLimites.length : perigo);
		
		int obstaculos = 0;
		for (int i = 0; i < this.regiao.length; i++) {
			for (int j = 0; j < this.regiao[i].length; j++) {
				if (this.regiao[i][j] == "~" || this.regiao[i][j] == "=" || this.regiao[i][j] == "!") {
						obstaculos += 1;
				}
			}
		}
		int quociente = (this.ardiveis() - obstaculos) / (this.regiao.length * this.regiao[0].length);
		perigo = perigo * (1 + quociente);
		
		if (Math.round(perigo) >= NivelPerigo.values().length) {
			perigo = NivelPerigo.values()[NivelPerigo.values().length - 1].ordinal();
		}
		return NivelPerigo.values()[perigo];
	}
	
	/**
	 * Representacao textual da Regiao
	 * @return representacao string da Regiao
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Nome: " + this.nome() + "	Data ult. fogo: " + this.ultFogo.get(Calendar.YEAR) + "/" 
				+ this.ultFogo.get(Calendar.MONTH) + "/" + this.ultFogo.get(Calendar.DAY_OF_MONTH)+ "\n");
		for (int i = 0; i < this.regiao.length; i++) {
			for (int j = 0; j < this.regiao[i].length; j++) {
				result.append(this.regiao[i][j]);
			}
			result.append("\n");
		}
		result.append("--------------------\n");
		return result.toString();
	}
}
