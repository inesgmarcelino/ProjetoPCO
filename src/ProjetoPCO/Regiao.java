package ProjetoPCO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Regiao {
	
	private String nome;
	private Calendar ultFogo;
	private EstadoSimulacao[][] ambiente; //not sure
	private List<Par<Integer,Integer>> casas;
	private List<Par<Integer,Integer>> estradas;
	private List<Par<Integer,Integer>> agua;

	public Regiao (String nome, Calendar ultFogo, int largura, int altura, List<Par<Integer,Integer>> casas,
			List<Par<Integer,Integer>> estradas, List<Par<Integer,Integer>> agua) {
		this.nome = nome;
		this.ultFogo = ultFogo;
		this.ambiente = new Regiao[altura][largura]; //not sure
		this.casas = casas;
		this.estradas = estradas;
		this.agua = agua;
		
		for (int i = 0; i < this.ambiente.length; i++) {
			for (int j = 0; j < this.ambiente[i].length; j++) {
				// meter as casas, estradas e agua nas suas posicoes em ambiente (?)
			}
		}
	}
	
	private String nome() {
		return this.nome;
	}
	
	private int ardiveis() {
		int count = 0;
		//if casas ou terrenos ! EstadoAmbiente.ARDIDOS
		// count += 1
		
		return count;
	}
	
	private void registaFogo(Calendar data, List<Par<Integer,Integer>> sitios) {
		this.ultFogo = data; //<- duvidoso... criar classe Fogo?
		// adicionar ao ambientes sitios ardidos (?)

	}
	
	private static boolean dadosValidos(int largura, int altura, List<Par<Integer,Integer>> casa,
			List<Par<Integer,Integer>> estradas, List<Par<Integer,Integer>> agua) {
		boolean valid;
		//if posições das casas, estradas, agua forem compativeis com a dimensão do ambiente
		valid = true;
		
		return valid;
	}
	
	private EstadoSimulacao[][] alvoSimulacao() {
		// (?)
		return this.ambiente;
	}
	
	private NivelPerigo nivelPerigo(Calendar data, int[] tempoLimites) {
		int perigo; 
		int difAno =  data.get(Calendar.YEAR) - this.ultFogo.get(Calendar.YEAR);
		for (int i = 0; i < tempoLimites.length; i++) {
			if (tempoLimites[i - 1] < difAno && difAno <= tempoLimites[i]) {
				perigo = i;
			} else if (tempoLimites[tempoLimites.length - 1] < difAno) {
				perigo = tempoLimites.length;
			}
		}
		//código acima pode ainda ter erros de interpretacao
		//continuar
	}
	
	private String toString() {
		return "Nome: " + this.nome + " Data ult. fogo: " + this.ultFogo;
		//falta a representacao da regiao
	}
}
