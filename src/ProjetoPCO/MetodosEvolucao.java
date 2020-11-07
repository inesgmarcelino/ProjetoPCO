package ProjetoPCO;
/**
 * A classe que permite realizar o "espalhamento das sementes"
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
 * @date Outubro 2020
 */
public class MetodosEvolucao {
	 
	/*Os simbolos que representam cada espaco no terreno*/
	public static final String SITIOS = ".*x";
	
	/**
	 * Provocar um passo do processo de espalhamento de sementes num dado
	 * terreno, com determinadas direcao e forca do vento.
	 * @param terreno O terreno onde vai ser aplicado o passo de
	 * espalhamento
	 * @param ventoLimites Tabela para calcular quantos vizinhos sao afetados
	 * com o vento
	 * @param ventoDir A direcao do vento
	 * @param ventoForca A forca do vento
	 * @requires terreno != null && ventoLimites != null &&
	 * ventoDir != null && ventoDir in {"N","S","E","O"} &&
	 * ventoForca >= 0
	 */
	public static void evolucao(char [][] terreno, int[] ventoLimites, String ventoDir, int ventoForca) {
		
		// CRIAR UMA COPIA DO TERRENO ORIGINAL
		char [][] copiaTerreno = copiarTerreno(terreno);
	
		// CONTAGEM VIZINHOS
		int vizinhos = contagemVizinhos(ventoLimites,ventoForca);
		
		//DIRECOES
		switch(ventoDir) {
		case "N":
			dirNorte(terreno,copiaTerreno,vizinhos);
			break;
		case "O":
			dirOeste(terreno,copiaTerreno,vizinhos);
			break;
		case "E":
			dirEste(terreno,copiaTerreno,vizinhos);
			break;
		case "S":
			dirSul(terreno,copiaTerreno,vizinhos);
			break;
		}
	}
	/**
	 * Copia o terreno para outra variavel
	 * @param terreno O terreno que queremos copiar
	 * @requires terreno != null 
	 * @return A copia do terreno original
	 */
	private static char[][] copiarTerreno (char[][] terreno) {
		
		char [][] copiaTerreno = new char[terreno.length][terreno[0].length];
		for (int i = 0; i < terreno.length; i++) {
			for (int j = 0; j < terreno[i].length; j++) {
				copiaTerreno[i][j] = terreno[i][j];
			}
		}
		return copiaTerreno;
	}
	/**
	 * Conta o numero de vizinhos afetados
	 * @param ventoLimites Tabela para calcular 
	 * quantos vizinhos sao afetados
	 * com o vento
	 * @param ventoForca A forca do vento
	 * @requires ventoLimites != null && 
	 * ventoForca >= 0
	 * @return Numero de vizinhos afetados pelo
	 * vento
	 */
	private static int contagemVizinhos(int [] ventoLimites, int ventoForca) {
		
		int vizinhos = 0;
		if (ventoForca <= ventoLimites[0]) {
			vizinhos = 0;
		} else {
			for (int i = 1; i < ventoLimites.length;i++) {
				if (ventoLimites[i - 1] < ventoForca && ventoForca <= ventoLimites[i]) {
					vizinhos = i;
				} else if (ventoLimites[ventoLimites.length - 1] < ventoForca) {
					vizinhos = ventoLimites.length;
				}
			}
		}
		return vizinhos;
	}
	/**
	 * Espalhamento das sementes com o vento de 
	 * origem Norte
	 * @param terreno O terreno onde vai ser aplicado o passo de
	 * espalhamento
	 * @param copiaTerreno A copia do terreno orginal
	 * @param vizinhos Numero de espacos vazios perto da planta
	 * que serao afetados pelo vento e recebem semente
	 * @requires terreno != null && copiaTerreno != null &&
	 * vizinhos >= null 
	 */
	private static void dirNorte (char[][] terreno, char[][] copiaTerreno, int vizinhos) {
		
		for (int i = 0; i < terreno.length; i++) {
			for (int j = 0; j < terreno[i].length; j++) {
				for (int k = 1; k <= vizinhos && (i + k) < terreno.length && copiaTerreno[i + k][j] != SITIOS.charAt(2); k++) {
					if (copiaTerreno[i][j] == SITIOS.charAt(1) && copiaTerreno[i + k][j] == SITIOS.charAt(0)) {
							terreno[i + k][j] = SITIOS.charAt(1);
					}
				}
			}
		}
	}
	/**
	 * Espalhamento das sementes com o vento de 
	 * origem Sul
	 * @param terreno O terreno onde vai ser aplicado o passo de
	 * espalhamento
	 * @param copiaTerreno A copia do terreno orginal
	 * @param vizinhos Numero de espacos vazios perto da planta
	 * que serao afetados pelo vento e recebem semente
	 * @requires terreno != null && copiaTerreno != null &&
	 * vizinhos >= null 
	 */
	private static void dirSul (char[][] terreno, char[][] copiaTerreno, int vizinhos) {
		
		for (int i = terreno.length - 1; i >= 0; i--) {
			for (int j = 0; j < terreno[i].length; j++) {
				for (int k = 1; k <= vizinhos && (i - k) >= 0 && copiaTerreno[i - k][j] != SITIOS.charAt(2); k++) {
					if (copiaTerreno[i][j] == SITIOS.charAt(1) && copiaTerreno[i - k][j] == SITIOS.charAt(0)) {
						terreno[i - k][j] = SITIOS.charAt(1);
					}
				}
			}
		}
	}
	/**
	 * Espalhamento das sementes com o vento de 
	 * origem Este
	 * @param terreno O terreno onde vai ser aplicado o passo de
	 * espalhamento
	 * @param copiaTerreno A copia do terreno orginal
	 * @param vizinhos Numero de espacos vazios perto da planta
	 * que serao afetados pelo vento e recebem semente
	 * @requires terreno != null && copiaTerreno != null &&
	 * vizinhos >= null 
	 */
	private static void dirEste (char[][] terreno, char[][] copiaTerreno, int vizinhos) {
		
		for (int i = 0; i < terreno.length; i++) {
			for (int j = terreno[i].length - 1; j >= 0; j--) {
				for (int k = 1; k <= vizinhos && (j - k) >= 0 && copiaTerreno[i][j - k] != SITIOS.charAt(2); k++) {
					if (copiaTerreno[i][j] == SITIOS.charAt(1) && copiaTerreno[i][j - k] == SITIOS.charAt(0)) {
						terreno[i][j - k] = SITIOS.charAt(1);
					}
				}
			}
		}
	}
	/**
	 * Espalhamento das sementes com o vento de 
	 * origem Oeste
	 * @param terreno O terreno onde vai ser aplicado o passo de
	 * espalhamento
	 * @param copiaTerreno A copia do terreno orginal
	 * @param vizinhos Numero de espacos vazios perto da planta
	 * que serao afetados pelo vento e recebem semente
	 * @requires terreno != null && copiaTerreno != null &&
	 * vizinhos >= null 
	 */
	private static void dirOeste (char[][] terreno, char[][] copiaTerreno, int vizinhos) {
		
		for (int i = 0; i < terreno.length; i++) {
			for (int j = 0; j < terreno[i].length; j++) {
				for (int k = 1; k <= vizinhos && (j + k) < terreno[i].length && copiaTerreno[i][j + k] != SITIOS.charAt(2); k++) {
					if (copiaTerreno[i][j] == SITIOS.charAt(1) && copiaTerreno[i][j + k] == SITIOS.charAt(0)) {
						terreno[i][j + k] = SITIOS.charAt(1);
					}
				}
			}
		}
	}
}