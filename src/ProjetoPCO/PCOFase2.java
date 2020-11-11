package ProjetoPCO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * Programa para testar as classes Instituicao e Regiao feitas
 * pelos alunos na Fase 2 do trabalho de PCO
 * @author in
 * @date Novembro 2020
 */
public class PCOFase2 {

	/**
	 * Comeca por ler a informacao de 4 ficheiros de texto dados e
	 * verificar se contem informacao sobre regioes validas;
	 * Com a informacao lida de dois desses ficheiros constroi duas 
	 * instancias da classe Instituicao e simula evolucoes de fogo. 
	 * Vai imprimindo informacao sobre as regiões.
	 * @param args Nao utilizado
	 * @throws FileNotFoundException no caso de nao encontrar algum dos 
	 *                               ficheiros indicados
	 */
	public static void main(String[] args) throws FileNotFoundException {

		// Ler 4 ficheiros e verificar validade da informacao neles contida
		testarInfoValida();

		// Constroi uma instancia de Instituicao
		Instituicao meuDGF = new Instituicao("Direcao Geral de Fogos");
		//Ler informacao de uma regiao e acrescenta-la ao meuDGF
		lerCriarInserirRegiao("in1.txt", meuDGF);
		System.out.println(meuDGF.toString());		

		if(!meuDGF.existeRegiao("Amareleja")) {
			System.out.println("Erro inserir regiao");
		}
		
		// metodo ardiveis da classe Regiao está com count = 0 dá false
		if(!meuDGF.podeAtuar()) {
			System.out.println("Erro pode atuar");
		}

		// Pede ao meuDGF o alvo para a simulacao (que sera' a 
		// regiao com maior perigo de fogo
		EstadoSimulacao[][] alvo = meuDGF.alvoSimulacao();
		
		// Cria um simulador dando-lhe esse alvo para trabalhar
//		// define o ponto de ignicao e faz 3 passos de simulacao
//		// No fim usa o resultado da simulacao para registar um fogo
//		// no meuDGF
//		Simulador meuSimulador1 = new Simulador(alvo);
//		System.out.println(meuSimulador1.representacaoAmbiente());
//		meuSimulador1.afetarElemento(0, 0);
//		System.out.println(meuSimulador1.representacaoAmbiente());
//
//		meuSimulador1.passoSimulacao("N", 3, Instituicao.VENTOS_LIMITES);
//		System.out.println(meuSimulador1.representacaoAmbiente());
//		meuSimulador1.passoSimulacao("O", 5, Instituicao.VENTOS_LIMITES);
//		System.out.println(meuSimulador1.representacaoAmbiente());
//		meuSimulador1.passoSimulacao("S", 3, Instituicao.VENTOS_LIMITES);
//		System.out.println(meuSimulador1.representacaoAmbiente());
//
//		List<Par<Integer,Integer>> afetados = meuSimulador1.resultadoSimulacao();
//		meuDGF.registaFogo("Amareleja", Calendar.getInstance(), afetados);
//		System.out.println(meuDGF.toString());
//
//		// Acrescentar uma segunda regiao e simular um fogo sobre
//		// o novo alvo. No fim regista o resultado da simulacao como
//		// fogo no meuDGF
//		lerCriarInserirRegiao("in2.txt", meuDGF);
//		System.out.println(meuDGF.toString());
//
//		// Quais os niveis de perigo das regioes?
//		List<Par<String,NivelPerigo>> niveisDePerigo = meuDGF.niveisDePerigo();
//		System.out.println("------- NIVEIS DE PERIGO --------");
//		for(Par<String,NivelPerigo> p : niveisDePerigo) {
//			System.out.println(p.primeiro() + " ---> " + p.segundo());
//		}
//
//		// Pede o novo alvo de simulacao 'a instituicao
//		alvo = meuDGF.alvoSimulacao();
//
//		// Cria um novo simulador, afeta um elemento onde vai começar 
//		// o fogo e faz simulacao 
//		Simulador meuSimulador2 = new Simulador(alvo);
//		meuSimulador2.afetarElemento(9, 19);
//
//		meuSimulador2.passoSimulacao("S", 17, Instituicao.VENTOS_LIMITES);
//		meuSimulador2.passoSimulacao("E", 21, Instituicao.VENTOS_LIMITES);
//		meuSimulador2.passoSimulacao("E", 21, Instituicao.VENTOS_LIMITES);
//		meuSimulador2.passoSimulacao("N", 17, Instituicao.VENTOS_LIMITES);
//
//		// Regista um fogo usando os resultados da simulacao
//		afetados = meuSimulador2.resultadoSimulacao();
//		meuDGF.registaFogo("Regiao sem casas", Calendar.getInstance(), afetados);
//		System.out.println(meuDGF.toString());
//
//
	} // Fim do metodo main
//
	/**
	 * Ler informacao de uma regiao a partir de um ficheiro, criar
	 * uma regiao com essa informacao e adiciona-la 'a instituicao dada
	 * @param nomeFich Nome do ficheiro a ser lido
	 * @param dirGeral A instancia de Instituicao
	 * @throws FileNotFoundException se nao existir com o nome nomeFich
	 */
	private static void lerCriarInserirRegiao(String nomeFich, Instituicao dirGeral) 
			throws FileNotFoundException {
		Scanner leitor = new Scanner(new FileReader(nomeFich));

		String nome = leitor.nextLine();
		Calendar dataUltFogo = lerData(leitor.nextLine());
		int largura = leitor.nextInt();
		int altura = leitor.nextInt();
		// Consumir o fim de linha que ficou por consumir
		leitor.nextLine();
		ArrayList<Par<Integer,Integer>> casas = lerInfoPares(leitor);
		ArrayList<Par<Integer,Integer>> estradas = lerInfoPares(leitor);
		ArrayList<Par<Integer,Integer>> agua = lerInfoPares(leitor);				        
		leitor.close();

		dirGeral.adicionaRegiao(nome, dataUltFogo, largura, altura, 
				casas, estradas, agua);
		
	}
//
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
	private static ArrayList<Par<Integer,Integer>> lerInfoPares(Scanner leitor) {
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

	/**
	 *  Ler varios ficheiros e verificar validade da informacao neles contida
	 * @throws FileNotFoundException no caso de nao encontrar os 
	 *                               ficheiros indicado 
	 */
	private static void testarInfoValida() throws FileNotFoundException {
		// para cada um dos 4 ficheiros in1.txt ate' in4.txt
		for(int i = 1 ; i < 2 ; i++) {
			Scanner leitor = new Scanner(new FileReader("in" + i + ".txt"));

			// Consome a linha correspondente ao nome
			leitor.nextLine();

			// Consome a linha correspondente a' data e verifica
			String[] dadosData = leitor.nextLine().split(" ");		
			boolean valida = dataValida(Integer.parseInt(dadosData[0]), 
					Integer.parseInt(dadosData[1]),
					Integer.parseInt(dadosData[2]));

			// Consome as linhas seguintes e verifica
			int linha1 = leitor.nextInt();
			int linha2 = leitor.nextInt();
			leitor.nextLine();
			ArrayList<Par<Integer,Integer>> linha3 = lerInfoPares(leitor);
			ArrayList<Par<Integer,Integer>> linha4 = lerInfoPares(leitor);
			ArrayList<Par<Integer,Integer>> linha5 = lerInfoPares(leitor);				        
			leitor.close();
			valida = valida && Regiao.dadosValidos(linha1, linha2, linha3, linha4, linha5);

			System.out.println("Dados do ficheiro in" + i + ".txt " + 
					(valida? "" : "IN") + "VALIDOS");	
		}
	}

	/**
	 * Um dado trio de ano, mes e dia define uma data valida?
	 * @param ano
	 * @param mes
	 * @param dia
	 * @return true se ano, mes e dia definem uma data valida
	 */
	private static boolean dataValida(int ano, int mes, int dia) {
		return mes >= 1 && mes <= 12 && 
				dia >= 1 && dia <= quantosDias(mes, ano) ;
	}

    /**
     * Quantos dias tem um dado mes de um dado ano
     * @param mes O mes em questao
     * @param ano O ano em questao
     * @return O numero de dias que mes tem
     * @requires mes >=1 && mes <= 12
     */
	private static int quantosDias (int mes, int ano){
		int result = 0;
		switch (mes) {
		case 1 : case 3 : case 5 : case 7 : case 8 : case 10 : case 12 :
			result = 31;
			break;
		case 4 : case 6 : case 9 : case 11 :
			result = 30;
			break;
		case 2 : if (bissexto(ano))
			result = 29;
		else
			result = 28;
		break;
		default : 
			break;
		}

		return result;
	}
    
    /**
     * Um dado ano e' bissexto?
     * @param n O ano
     * @return true se n eh ano bissexto ; false c.c.
     */
    private static boolean bissexto (int n){
       	return n % 400 == 0 || n % 100 != 0 && n % 4 == 0;
    }
}
