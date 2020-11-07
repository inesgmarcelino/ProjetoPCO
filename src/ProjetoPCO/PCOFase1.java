package ProjetoPCO;
/**
 * Esta classe permite testar a funcao evolucao da classe 
 * MetodosEvolucao
 * 
 * @author in Grupo 17 - Francisco Ó (53340) & Sofia Lourenço (54950) & Inês Marcelino (54991)
 * @date Outubro 2020
 */
public class PCOFase1 {

	/**
	 * Aqui invoca-se a funcao evolucao da classe MetodosEvolucao para
	 * varios valores dos seus parametros e imprimem-se os resultados 
	 * no standard output
	 * @param args Nao utilizado
	 */
	public static void main(String[] args) {
		
		/*
		 * Exemplo 1
		 * Copia do output no ficheiro output1.txt
		 */
		char[][] terra1 = { 
				{'.','x','.','.','.','.','.','x','.','.'},
				
				{'.','.','.','*','.','.','x','*','.','*'},
				{'.','.','.','x','.','.','*','.','x','.'},
				{'*','.','.','.','.','.','.','.','x','.'}
		};

		int[] ventoLimites1 = {0, 2, 4, 5, 6, 7 };
		mostraTerreno(terra1);

		MetodosEvolucao.evolucao(terra1, ventoLimites1, "O", 4);
		mostraTerreno(terra1);

		MetodosEvolucao.evolucao(terra1, ventoLimites1, "S", 4);
		mostraTerreno(terra1);

		MetodosEvolucao.evolucao(terra1, ventoLimites1, "N", 4);
		mostraTerreno(terra1);

		MetodosEvolucao.evolucao(terra1, ventoLimites1, "S", 2);
		mostraTerreno(terra1);

		MetodosEvolucao.evolucao(terra1, ventoLimites1, "O", 2);
		mostraTerreno(terra1);

		imprimeBarra(60);
		
		/*
		 * Exemplo 2
		 * Copia do output no ficheiro output2.txt
		 */
		char[][] terra2 = { 
				{'.','x','.','.','.','.','.','*','.','.','.','.','.','.','*','.','.','.'},
				{'x','x','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'*','.','.','.','.','.','.','.','.','.','.','.','.','.','x','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'.','.','x','*','.','.','.','.','x','x','x','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','x','.','x','.','.','.','x','.','.','.'},
				{'.','.','.','.','.','.','.','.','x','x','x','.','.','.','.','.','.','.'},
				{'.','x','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','.','x','.','.','.'},
				{'.','x','.','.','.','x','.','.','.','x','.','.','.','.','.','x','x','x'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','x','.','.'},
				{'.','.','*','.','.','.','.','.','.','.','*','.','.','.','.','x','.','.'}
		};

		int[] ventoLimites2 = {0, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21};
		mostraTerreno(terra2);

		MetodosEvolucao.evolucao(terra2, ventoLimites2, "N", 12);
		mostraTerreno(terra2);

		MetodosEvolucao.evolucao(terra2, ventoLimites2, "O", 10);
		mostraTerreno(terra2);

		MetodosEvolucao.evolucao(terra2, ventoLimites2, "S", 4);
		mostraTerreno(terra2);

		MetodosEvolucao.evolucao(terra2, ventoLimites2, "N", 12);
		mostraTerreno(terra2);

		MetodosEvolucao.evolucao(terra2, ventoLimites2, "E", 1);
		mostraTerreno(terra2);

		imprimeBarra(60);

		/*
		 * Exemplo 3
		 * Copia do output no ficheiro output3.txt
		 */
		char[][] terra3 = { 
				{'.','.','.','.','.','*'},
				{'.','.','.','.','x','.'},
				{'.','.','.','x','.','.'},
				{'.','.','x','.','.','.'},
				{'.','x','.','.','.','.'},
				{'*','.','.','.','.','.'}
		};

		int[] ventoLimites3 = {2, 4, 8, 16, 32, 64 };
		mostraTerreno(terra3);

		MetodosEvolucao.evolucao(terra3, ventoLimites3, "S", 33);
		mostraTerreno(terra3);

		MetodosEvolucao.evolucao(terra3, ventoLimites3, "N", 33);
		mostraTerreno(terra3);

		MetodosEvolucao.evolucao(terra3, ventoLimites3, "O", 17);
		mostraTerreno(terra3);

		MetodosEvolucao.evolucao(terra3, ventoLimites3, "E", 9);
		mostraTerreno(terra3);

		imprimeBarra(60);

		/*
		 * Exemplo 4
		 * Copia do output no ficheiro output4.txt
		 */
		char[][] terra4 = { 
				{'.','.','x','*','x','.'},
				{'*','.','.','x','.','.'},
				{'.','.','x','*','x','.'},
				{'.','.','.','x','.','.'},
				{'x','.','.','.','.','.'},
				{'*','x','.','.','.','.'}
		};

		int[] ventoLimites4 = {2, 4, 8, 16, 32, 64 };
		mostraTerreno(terra4);

		MetodosEvolucao.evolucao(terra4, ventoLimites4, "O", 5);
		mostraTerreno(terra4);

		MetodosEvolucao.evolucao(terra4, ventoLimites4, "N", 9);
		mostraTerreno(terra4);

		MetodosEvolucao.evolucao(terra4, ventoLimites4, "O", 17);
		mostraTerreno(terra4);

		MetodosEvolucao.evolucao(terra4, ventoLimites4, "S", 17);
		mostraTerreno(terra4);

		MetodosEvolucao.evolucao(terra4, ventoLimites4, "E", 3);
		mostraTerreno(terra4);

		MetodosEvolucao.evolucao(terra4, ventoLimites4, "N", 3);
		mostraTerreno(terra4);

		imprimeBarra(60);

		/*
		 * Exemplo 5
		 * Copia do output no ficheiro output5.txt
		 */
 		char[][] terra5 = { 
				{'*','x','*'},
				{'x','.','x'},
				{'*','x','*'},
		};

		int[] ventoLimites5 = {2, 4, 8, 16, 32, 64 } ;
		mostraTerreno(terra5);

		MetodosEvolucao.evolucao(terra5, ventoLimites5, "O", 3);
		mostraTerreno(terra5);

		MetodosEvolucao.evolucao(terra5, ventoLimites5, "S", 3);
		mostraTerreno(terra5);

		MetodosEvolucao.evolucao(terra5, ventoLimites5, "N", 3);
		mostraTerreno(terra5);

		MetodosEvolucao.evolucao(terra5, ventoLimites5, "E", 3);
		mostraTerreno(terra5);


	}

	/**
	 * Escreve o terreno no standard output
	 * @param t O terreno a escrever
	 * @requires t != null && t[0] != null
	 */
	private static void mostraTerreno(char [][] t){
		for (int i = 0 ; i < t.length ; i++) {
			for (int j = 0 ; j < t[0].length ; j++)
				System.out.print(t[i][j]);
			System.out.println();
		}
		imprimeBarra(t[0].length);
	}

	/**
	 * Imprime uma linha tracejada dupla com uma dada largura
	 * @param n A largura da linha (em numero de carateres)
	 */
	private static void imprimeBarra(int n) {
		for (int i = 0 ; i < n ; i++) {
			System.out.print("=");
		}
		System.out.println();
	}

}