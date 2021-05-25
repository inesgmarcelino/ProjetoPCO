import java.util.List;
import java.util.ArrayList;

/**
 * Classe que representa a direcao VerticalHorizontal
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
 * @date Dezembro 2020
 */
public class VerticalHorizontal implements Direcionador {
	
	/**
	 * Devolve uma direcao sugerida para um passo de simulacao
	 * sobre o alvo
	 * @param alvo O alvo da simulacao
	 * @requires alvo != null
	 * @return direcao
	 */
	public String direcao(EstadoSimulacao[][] alvo) {
		
		List<Par<Integer,Integer>> linhas = linhasComAfetados(alvo);
		List<Par<Integer,Integer>> colunas = colunasComAfetados(alvo);
		String dirVertical = direcaoVertical(linhas);
		String dirHorizontal = direcaoHorizontal(colunas);
		
		return difAfetadosLinha(linhas) <= difAfetadosColuna(colunas) ? 
				dirHorizontal : dirVertical;
	}
	
	/**
	 * O custo de fazer a consulta sobre a melhor direcao para
	 * a simulacao no alvo
	 * @param alvo O alvo da simulacao
	 * @requires alvo != null
	 * @return preco
	 */
	public int precoConsulta(EstadoSimulacao[][] alvo ) {
		return (2 * alvo.length) + (2 * alvo[0].length);
	}
	
	/**
	 * Uma lista de pares em que o primeiro elemento e o numero
	 * da linha e o segundo elemento e o numero de afetados dessa
	 * mesma linha
	 * @param alvo O alvo da simulacao
	 * @return Lista com pares da linha e afetados correspondente
	 */
	private List<Par<Integer,Integer>> linhasComAfetados (EstadoSimulacao[][] alvo) {
		
		List<Par<Integer,Integer>> linhas = new ArrayList<Par<Integer,Integer>>();
		for (int i = 0; i < alvo.length; i++) {
			int afetados = 0;
			for (int j = 0; j < alvo[i].length; j++) {
				if (alvo[i][j] == EstadoSimulacao.AFETADO) {
					afetados += 1;
				}
			}
			linhas.add(new Par<Integer,Integer>(i,afetados));
		}
		
		return linhas;
	}
	
	/**
	 * Uma lista de pares em que o primeiro elemento e o numero
	 * da coluna e o segundo elemento e o numero de afetados dessa
	 * mesma coluna
	 * @param alvo O alvo da simulacao
	 * @requires alvo != null
	 * @return Lista com pares da coluna e afetados correspondente
	 */
	private List<Par<Integer,Integer>> colunasComAfetados (EstadoSimulacao[][] alvo) {
		
		List<Par<Integer,Integer>> colunas = new ArrayList<Par<Integer,Integer>>();
		for (int i = 0; i < alvo[0].length; i++) {
			int afetados = 0;
			for (int j = 0; j < alvo.length; j++) {
				if (alvo[j][i] == EstadoSimulacao.AFETADO) {
					afetados += 1;
				}
			}
			colunas.add(new Par<Integer,Integer>(i,afetados));
		}
		
		return colunas;
	}
	
	/**
	 * Devolve a direcao Vertical calculada
	 * @param linhas Lista com pares da linha e afetados correspondente
	 * @requires linhas != null
	 * @return direcao
	 */
	private String direcaoVertical (List<Par<Integer,Integer>> linhas) {
		int primLin = linhas.get(0).segundo();
		int ultLin = linhas.get(linhas.size() - 1).segundo();
		
		return primLin > ultLin ? "N" : "S";
	}
	
	/**
	 * Devolve a direcao Horizontal calculada
	 * @param colunas Lista com pares da coluna e afetados correspondente
	 * @requires colunas != null
	 * @return direcao
	 */
	private String direcaoHorizontal (List<Par<Integer,Integer>> colunas) {
		int primCol = colunas.get(0).segundo();
		int ultCol = colunas.get(colunas.size() - 1).segundo();
		
		return primCol > ultCol ? "O" : "E";
	}
	
	/**
	 * Calcula a diferenca entre a linha com mais afetados e a linha
	 * com menos afetados
	 * @param linhas Lista com pares da linha e afetados correspondente
	 * @return a diferenca
	 */
	private int difAfetadosLinha (List<Par<Integer,Integer>> linhas) {
		int primLin = linhas.get(0).segundo();
		int ultLin = linhas.get(linhas.size() - 1).segundo();
		
		return primLin > ultLin ? primLin - ultLin : ultLin - primLin;
	}
	
	/**
	 * Calcula a diferenca entre a coluna com mais afetados e a coluna
	 * com menos afetados
	 * @param colunas Lista com pares da coluna e afetados correspondente
	 * @return a diferenca
	 */
	private int difAfetadosColuna (List<Par<Integer,Integer>> colunas) {
		int primCol = colunas.get(0).segundo();
		int ultCol = colunas.get(colunas.size() - 1).segundo();

		return primCol > ultCol ? primCol - ultCol : ultCol - primCol;
	}
}

