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
//	private int altura;
//	private int largura;
	private List<Par<Integer,Integer>> casas;
	private List<Par<Integer,Integer>> estradas;
	private List<Par<Integer,Integer>> agua;

	public Regiao (String nome, Calendar ultFogo, int largura, int altura, List<Par<Integer,Integer>> casas,
			List<Par<Integer,Integer>> estradas, List<Par<Integer,Integer>> agua) {
		this.nome = nome;
		this.ultFogo = ultFogo;
		
	}
	private String nome() {
		return this.nome;
		
	}
	private int ardiveis() {
	}
	
	private void registaFogo(Calendar data, List<Par<Integer,Integer>> sitios) {
		

	}
	
	private static boolean dadosValidos(int largura, int altura, List<Par<Integer,Integer>> casa,
			List<Par<Integer,Integer>> estradas, List<Par<Integer,Integer>> agua) {
		
	}
	
	private EstadoSimulacao[][] alvoSimulacao() {
		
	}
	
	private NivelPerigo nivelPerigo(Calendar data, int[] tempoLimites) {
		
	}
	
	private String toString() {
		
	}
}
