package ProjetoPCO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Instituicao {
	
	public static final int[] RISCO_ANOS = {2, 3, 5, 8};
	public static final int[] VENTOS_LIMITES = {0, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21};
	
	private String designacao;

	public Instituicao(String designacao) {
		this.designacao = designacao;
	}
	
	private void adicionaRegiao(String nome, Calendar ultFogo, int largura, int altura, List<Par<Integer,Integer>> casas,
			List<Par<Integer,Integer>> estradas, List<Par<Integer,Integer>> agua) {
		//se não houver nenhuma regiao com este nome nesta instituicao
		Regiao nova =  new Regiao(nome, ultFogo, largura, altura, casas, estradas, agua);
	}
	
	private boolean existeRegiao(String nome) {
		boolean existe;
		//if existe uma regiao nesta instituicao que tenha esse nome
		existe = true;
		
		return existe;
	}
	
	private List<Par<Integer,Integer>> niveisDePerigo() {
		
	}
	
	private EstadoSimulacao[] alvoSimulacao() {
		
	}
	
	private boolean podeAtuar() {
		boolean atuar;
		//if pelo menos uma reagiao da instituicao tem elementos ardiveis
		atuar = true;
		
		return atuar;
	}
	
	private void registaFogo(String regiao, Calendar data, List<Par<Integer,Integer>> sitios) {
		//mesma duvida de Regiao.java
	}
	
	private String toString() {
		return "Designacao: " + this.designacao;
	}
}
