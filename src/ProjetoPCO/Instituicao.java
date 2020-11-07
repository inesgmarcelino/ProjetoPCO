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
		
	}
	
	private boolean existeRegiao(String nome) {
		
	}
	
	private List<Par<Integer,Integer>> niveisDePerigo() {
		
	}
	
	private EstadoSimulacao[] alvoSimulacao() {
		
	}
	
	private boolean podeAtuar() {
		
	}
	
	private void registaFogo(String regiao, Calendar data, List<Par<Integer,Integer>> sitios) {
		
	}
	
	private String toString() {
		
	}
}
