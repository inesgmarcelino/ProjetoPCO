import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
/**
 * Programa para testar os interfaces e classes feitos
 * pelos alunos na Fase 3 do trabalho de PCO
 * @author Isabel Nunes
 * @date Novembro 2020
 */
public class TestarFase3 {

	public static final String[] DIRECOES = {"N","S","E","O"};

	/**
	 * Comeca por ler a informacao de 5 ficheiros de texto dados:
	 * - umaInstituicao.txt contendo informacao sobre uma instituicao
	 * - ambienteA.txt e ambienteB.txt contendo cada um informacao sobre
	 *   um Ambiente;
	 * - JogadorConfiante.txt e JogadorPrudente.txt contendo cada um 
	 *   informacao sobre um Jogador;
	 * 
	 * Com essas informacoes, sao criados: um objeto do tipo Instituicao,
	 * dois do tipo Ambiente, um do tipo JogadorConfiante e um do tipo
	 * JogadorPrudente.
	 * 
	 * Estes objetos sao guardados em variaveis do tipo Acionavel 
	 * (os 5 elementos de um array). 
	 * 
	 * E' criada uma instancia de Simulador e e´invocado o seu metodo 
	 * passoSimulacao sobre os alvos de cada um dos 5 objetos Acionavel
	 * e imprimem-se os resultados no ecra
	 * 
	 * Por duas vezes e' feito o seguinte:
	 * - E´ pedido ao utilizador o nome de uma classe representando um tipo
	 *   especifico de Direcionador para o jogador prudente usar; e' usado  
	 *   carregamento dinamico de classes para criar um objeto dessa classe
	 *   que vai ser usado para a seguir criar um JogadorPrudente.
	 * 
	 * - Sao criados dois objetos dos tipos JogadorConfiante e JogadorPrudente,
	 *   com ambientes iguais, e de seguida sao simulados varios passos de
	 *   contagio para os dois jogadores, ate' que um deles ja' nao possa atuar
	 * 
	 * @param args Nao utilizado
	 * @throws FileNotFoundException no caso de nao encontrar algum dos 
	 *                               ficheiros indicados
	 */
	public static void main(String[] args) throws FileNotFoundException {

		// O array de elementos do tipo Acionavel
		Acionavel[] conjunto = new Acionavel[5];

		// Ler informacao para criacao de uma instancia de Instituicao
		// (a classe Instituicao implementa Acionavel)
		conjunto[0] = lerCriarInstituicao("umaInstituicao.txt");
		// Construir dois ambientes
		// (a classe Ambiente implementa Acionavel)
		conjunto[1] = lerCriarAmbiente("ambienteA.txt");
		conjunto[2] = lerCriarAmbiente("ambienteB.txt");

		// Construir dois jogadores
		// (a classe Jogador implementa Acionavel)
		conjunto[3] = lerCriarJogadorConfiante("jogadorConfiante.txt");
		conjunto[4] = lerCriarJogadorPrudente("jogadorPrudente.txt", 
				new VerticalHorizontal());

		/* Se quiser imprimir o resultado do metodo toString sobre os   
		 * objetos acabados de criar para verificar que tudo correu bem,
		 * retire o comentario da proxima instrucao (ver resultado no
		 * ficheiro OutputToStrings.txt)
		 */
		// imprimirComToString(conjunto);	

		// Dar um passo de simulacao sobre os varios Acionaveis, com 
		// direcao e forca aleatorias
		simularAcionaveis(conjunto, new Random(1));

		// Por duas vezes:
		// criar 2 jogadores (um confiante e um prudente) com ambientes 
		// iguais e po-los a jogar ate' que um deles ja' nao possa atuar
		experimentaJogadores("jogadorConfiante.txt", 
				"jogadorPrudente.txt",0,0);
		experimentaJogadores("jogadorConfiante2.txt", 
				"jogadorPrudente2.txt",0,9);	

	}// Fim do metodo main

	/***********************************************************************/
	/************************   OUTROS METODOS   ***************************/
	/***********************************************************************/

	/**
	 * Imprimir varios objetos usando o resultado do seu metodo toString
	 * @param aImprimir Os objetos a imprimir
	 * @requires aImprimir != null
	 */
	@SuppressWarnings("unused")
	private static void imprimirComToString(Acionavel[] aImprimir) {
		for (Acionavel quant : aImprimir) {
			System.out.println("===============  toString de: " + 
					quant.getClass() + " ============");
			System.out.println(quant.toString());			
		}
	}

	/**
	 * Pedir ao utilizador qual o tipo de Direcionador que quer usar
	 * e construir e devolver um objeto desse tipo
	 * @return Um objeto do tipo Direcionador cujo subtipo eh decidido
	 *         pelo utilizador
	 */
	private static Direcionador umDirecionador() {

		System.out.println("Tipos de Direcionador acessíveis:");
		// invoca metodo que retorna lista de Direcionadores existentes
		List<String> direcionadores = tiposDirecionadorExistentes();
		for(String s : direcionadores) {
			System.out.println(s);
		}
		@SuppressWarnings("resource")
		Scanner leitor = new Scanner(System.in);		
		System.out.println("Qual é o que que deseja?");
		String nome = leitor.nextLine();
		System.out.println("Tipo escolhido: " + nome);
		return obtemDirecionador(nome);
	}

	/**
	 * Lista dos nomes das subclasses de Direcionador existentes, que
	 * estao referidas no ficheiro configuracao.properties
	 * @return
	 */
	private static List<String> tiposDirecionadorExistentes() {

		String classesDirecionador = "";
		// Array que vai conter os nomes dos direcionadores. E' inicializado
		// ja' com um elemento para o caso de nao ser encontrado o fich de 
		// configuracao
		String[] nomes = {"Aleatorio"};
		Properties prop = new Properties ();	
		try {
			prop.load(new FileInputStream("configuracao.properties") );
			classesDirecionador = prop.getProperty("direcionadores");	
			nomes = classesDirecionador.split(";");
		} catch (IOException e1) {
			System.out.println(classesDirecionador);
		}	
		return Arrays.asList(nomes);
	}

	/**
	 * Constroi um Direcionador a partir do nome da classe
	 * (usando carregamento dinamico de classes)
	 * @param tipoDesejado O tipo do Direcionador a criar
	 * @requires tipoDesejado != null
	 * @return Um Direcionador do tipo indicado por tipoDesejado, caso 
	 *         exista; caso contrario eh retornado um do tipo Aleatorio
	 */
	private static Direcionador obtemDirecionador(String tipoDesejado) {
		Direcionador result;
		try {
			// Quando as classes estao no default package.
			// Se nao estiverem, terah que se acrescentar o path.
			// Exemplo: "projeto3." + tipoDesejado
			@SuppressWarnings("rawtypes")
			Constructor construtor = 
					Class.forName(tipoDesejado).getDeclaredConstructor();
			result = (Direcionador) construtor.newInstance();
		} catch (Exception e) {	// caso nao encontre a classe desejada
			System.out.println("Exception " + tipoDesejado);
			result = new Aleatorio();		
		}
		return result;
	}

	/************************************************************************
	 ***********   METODOS PARA LER INFO DE UMA INSTITUICAO  ****************
	 ************************************************************************/

	/**
	 * Ler informacao de uma instituicao a partir de um ficheiro e criar
	 * uma instituicao com essa informacao
	 * @param nomeFich Nome do ficheiro a ser lido
	 * @throws FileNotFoundException se nao existir com o nome nomeFich
	 */
	private static Instituicao lerCriarInstituicao(String nomeFich) 
			throws FileNotFoundException {
		Scanner leitor = new Scanner(new FileReader(nomeFich));

		String nome = leitor.nextLine();
		Instituicao result = new Instituicao(nome);

		while(leitor.hasNextLine()) {
			nome = leitor.nextLine();
			Calendar dataUltFogo = lerData(leitor.nextLine());
			int nLinhas = leitor.nextInt();
			int nColunas = leitor.nextInt();
			// Consumir o fim de linha que ficou por consumir
			leitor.nextLine();
			ArrayList<Par<Integer,Integer>> casas = lerInfoPares(leitor);
			ArrayList<Par<Integer,Integer>> estradas = lerInfoPares(leitor);
			ArrayList<Par<Integer,Integer>> agua = lerInfoPares(leitor);				        

			result.adicionaRegiao(nome, dataUltFogo, nLinhas, nColunas, 
					casas, estradas, agua);
		}
		leitor.close();		
		return result;
	}

	/**
	 * 
	 * @param linha A string contendo ano, mes e dia 
	 * @return Uma instancia de Calendar correspondente a'
	 *         data contida em linha
	 * @requires linha != null
	 */
	private static Calendar lerData(String linha) {
		String[] valores = linha.split(" ");		
		Calendar result = Calendar.getInstance(); 
		// ano, mes, dia
		result.set(Integer.parseInt(valores[0]), 
				Integer.parseInt(valores[1]) - 1,
				Integer.parseInt(valores[2]));
		return result;
	}

	/**
	 * Lista de pares (int, int) lidos a partir de um scanner
	 * @param leitor O objeto scanner
	 * @return Informacao lida com a ajuda do leitor, numa lista de 
	 *         pares (int, int)
	 * @requires leitor != null
	 */
	private static ArrayList<Par<Integer,Integer>> lerInfoPares(Scanner leitor){
		ArrayList<Par<Integer,Integer>> resultado = 
				new ArrayList<Par<Integer,Integer>>();

		String[] valores = leitor.nextLine().split(" ");
		for(int j = 0 ; j < valores.length - 1 ; j += 2) {
			Par<Integer,Integer> p = new Par<Integer,Integer>(
					Integer.parseInt(valores[j]), 
					Integer.parseInt(valores[j + 1]));
			resultado.add(p);
		}
		
		return resultado;
	}

	/************************************************************************
	 *************   METODOS PARA LER INFO DE UM AMBIENTE   *****************
	 ************************************************************************/

	/**
	 * Ler informacao de um ambiente a partir de um ficheiro e criar
	 * um ambiente com essa informacao
	 * @param nomeFich Nome do ficheiro a ser lido
	 * @throws FileNotFoundException se nao existir com o nome nomeFich
	 */
	private static Ambiente lerCriarAmbiente(String nomeFich) 
			throws FileNotFoundException {
		Scanner leitor = new Scanner(new FileReader(nomeFich));

		int nLinhas = leitor.nextInt();
		int nColunas = leitor.nextInt();
		// Consumir o fim de linha que ficou por consumir
		leitor.nextLine();
		ArrayList<Par<Integer,Integer>> obstaculos = lerInfoPares(leitor);

		leitor.close();		
		return new Ambiente(nLinhas, nColunas, obstaculos);
	}

	/************************************************************************
	 **************   METODOS PARA LER INFO DE JOGADORES   ******************
	 ************************************************************************/

	private static JogadorPrudente lerCriarJogadorPrudente(String nomeFich, 
			Direcionador dir) throws FileNotFoundException {

		Scanner leitor = new Scanner(new FileReader(nomeFich));

		int nLinhas = leitor.nextInt();
		int nColunas = leitor.nextInt();
		// Consumir o fim de linha que ficou por consumir
		leitor.nextLine();
		ArrayList<Par<Integer,Integer>> obstaculos = lerInfoPares(leitor);
		int recursos = leitor.nextInt();

		leitor.close();		
		return new JogadorPrudente(nLinhas, nColunas, 
				obstaculos, recursos, dir);
	}

	private static JogadorConfiante lerCriarJogadorConfiante(String nomeFich)
			throws FileNotFoundException {

		Scanner leitor = new Scanner(new FileReader(nomeFich));

		int nLinhas = leitor.nextInt();
		int nColunas = leitor.nextInt();
		// Consumir o fim de linha que ficou por consumir
		leitor.nextLine();
		ArrayList<Par<Integer,Integer>> obstaculos = lerInfoPares(leitor);

		leitor.close();		
		return new JogadorConfiante(nLinhas, nColunas, obstaculos);
	}


	/************************************************************************
	 **   METODO PARA APLICAR UM PASSO DE SIMULACAO A VARIOS ACIONAVEIS  ****
	 ************************************************************************/

	/**
	 * Aplicar um passo de simulacao com valores aleatorios para o primeiro
	 * elemento a afetar e para a direcao e forca a aplicar
	 * Imprimir no standard output as posicoes dos elementos afetados na
	 * simulacao
	 * @param conjunto Os acionaveis sobre os quais se aplica a simulacao
	 * @param gerador O gerador de aleatorios a ser usado
	 */
	private static void simularAcionaveis(Acionavel[] conjunto, Random gerador){

		for (Acionavel acion : conjunto) {

			System.out.println("=========  Resultado de um passo de simulacao de: " + 
					acion.getClass());

			if(acion.podeAtuar()) {
				EstadoSimulacao[][] alvo = acion.alvoSimulacao();
				Simulador meuSimulador = new Simulador(alvo);
				int linhaAleatoria = gerador.nextInt(alvo.length);
				int colunaAleatoria = gerador.nextInt(alvo[0].length);
				System.out.println("Afetacao inicial: Linha - " + linhaAleatoria + 
						"    Coluna - " + colunaAleatoria);
				meuSimulador.afetarElemento(linhaAleatoria, colunaAleatoria);
				String direcaoAleatoria = 
						DIRECOES[gerador.nextInt(DIRECOES.length)];
				int forcaAleatoria = 
						gerador.nextInt(Math.max(alvo.length, alvo[0].length));
				System.out.println("Parametros da simulacao: Direcao - " + 
						direcaoAleatoria + "    Forca - " + forcaAleatoria);
				meuSimulador.passoSimulacao(direcaoAleatoria, forcaAleatoria);
				List<Par<Integer,Integer>> result = 
						meuSimulador.resultadoSimulacao();
				System.out.println("Afetados na simulacao: ");
				for(Par<Integer,Integer> p : result) {
					System.out.print("(" + p.primeiro() + "," + 
				                      p.segundo() + ")   ");
				}
				System.out.println();			
			} else {
				System.out.println("Ja' nao pode atuar");
			}
		}
	}

	/************************************************************************
	 *********   METODO PARA EXPERIMENTAR JOGAR COM JOGADORES   *************
	 ************************************************************************/

	/**
	 * Criar dois jogadores (um confiante e outro prudente) a partir de dois 
	 * ficheiros de texto, pedir ao utilizador um tipo de direcionador para
	 * o jogador prudente usar e simular jogadas ate' que algum deles ja'
	 * nao possa atuar
	 * @param fichJogConf O ficheiro contendo a info sobre o jogador confiante
	 * @param fichJogPrud O ficheiro contendo a info sobre o jogador prudente
	 * @param linPrimAfetado A linha do primeiro elemento a afetar
	 * @param colPrimAfetado A coluna do primeiro elemento a afetar
	 * @throws FileNotFoundException
	 */
	private static void experimentaJogadores(
			String fichJogConf, String fichJogPrud,
			int linPrimAfetado,int colPrimAfetado)
					throws FileNotFoundException {

		System.out.println("================================="
				+ "=======================================");

		// Pedir ao utilizador qual o tipo de Direcionador que quer usar
		// para um jogador prudente usar quando precisa da direcao de 
		// simulacao, e construir um objeto desse tipo
		Direcionador representador = umDirecionador();	

		// Criar dois jogadores (um confiante e um prudente), com 
		// ambientes iniciais iguais e fazer varias jogadas usando
		// os resultados dados por simuladores
		JogadorConfiante jConf = 
				lerCriarJogadorConfiante(fichJogConf);
		JogadorPrudente jPrud = 
				lerCriarJogadorPrudente(fichJogPrud, representador);

		Simulador simConfiante = new Simulador(jConf.alvoSimulacao());
		Simulador simPrudente = new Simulador(jPrud.alvoSimulacao());

		simConfiante.afetarElemento(linPrimAfetado, colPrimAfetado);
		simPrudente.afetarElemento(linPrimAfetado, colPrimAfetado);

		while(jConf.podeAtuar() && jPrud.podeAtuar()) {

			String dir = jConf.direcao();
			int forca = jConf.forca();
			System.out.println("CONF - Dir: " + dir + "    Forca: " + forca);
			simConfiante.passoSimulacao(dir, forca);

			dir = jPrud.direcao();
			forca = jPrud.forca();
			System.out.println("PRUD - Dir: " + dir + "    Forca: " + forca);
			simPrudente.passoSimulacao(dir, forca);

			List<Par<Integer,Integer>> afetados = 
					simConfiante.resultadoSimulacao();
			System.out.println("CONF - Quantos afetados: " + afetados.size());
			jConf.registaJogadaComPontuacao(afetados, afetados.size());

			afetados = simPrudente.resultadoSimulacao();
			System.out.println("PRUD - Quantos afetados: " + afetados.size());
			jPrud.registaJogadaComPontuacao(afetados, afetados.size());			

			simConfiante = new Simulador(jConf.alvoSimulacao());
			simPrudente = new Simulador(jPrud.alvoSimulacao());			
		}

		// Imprime no standard output a representacao textual dos jogadores
		System.out.println("==============================");
		System.out.println("Jogador confiante:");
		System.out.println(jConf.toString());

		System.out.println("Jogador prudente:");
		System.out.println(jPrud.toString());
	}

}
