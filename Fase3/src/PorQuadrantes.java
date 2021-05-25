import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa a direcao PorQuadrantes
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
 * @date Dezembro 2020
 */
public class PorQuadrantes implements Direcionador {
	
	/**
	 * Devolve uma direcao sugerida para um passo de simulacao
	 * sobre o alvo
	 * @param alvo O alvo da simulacao
	 * @requires alvo != null
	 * @return direcao
	 */
	public String direcao(EstadoSimulacao[][] alvo) {
		List<Par<List<Par<Integer,Integer>>,Integer>> quadrantes = quadrantesComAfetados(alvo);
		int qMenosAfetado = qMenosAfetado(quadrantes);
		int ataque = qAtaque(quadrantes, qMenosAfetado);
		
		return sentidoAtaque(qMenosAfetado, ataque);
	}
	
	/**
	 * O custo de fazer a consulta sobre a melhor direcao para
	 * a simulacao no alvo
	 * @param o alvo O alvo da simulacao
	 * @requires alvo != null
	 * @return preco
	 */
	public int precoConsulta(EstadoSimulacao[][] alvo) {
		return alvo.length * alvo[0].length;
	}
	
	/**
	 * Uma lista de pares dos elementos pertencentes ao 1o quadrante
	 * @param alvo
	 * @requires alvo != null
	 * @return quadrante1
	 */
	private List<Par<Integer,Integer>> q1 (EstadoSimulacao[][] alvo) {
		List<Par<Integer,Integer>> q1 = new ArrayList<Par<Integer,Integer>>();
		
		int linhas = (alvo.length % 2 == 0) ? (alvo.length / 2) : (alvo.length / 2 ) + 1;
		int colunas = (alvo[0].length % 2 == 0) ? (alvo[0].length / 2) : (alvo[0].length / 2) + 1;
		
		for (int i = 0; i < linhas; i++) {
			for (int j = colunas; j < alvo[0].length; j++) {
				q1.add(new Par<Integer,Integer>(i,j));
			}
		}
		return q1;
	}
	
	/**
	 * Uma lista de pares dos elementos pertencentes ao 2o quadrante
	 * @param alvo
	 * @requires alvo != null
	 * @return quadrante2
	 */
	private List<Par<Integer,Integer>> q2 (EstadoSimulacao[][] alvo) {
		List<Par<Integer,Integer>> q2 = new ArrayList<Par<Integer,Integer>>();
		
		int linhas = (alvo.length % 2 == 0) ? (alvo.length / 2) : (alvo.length / 2) + 1;
		int colunas = alvo[0].length / 2;
		
		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				q2.add(new Par<Integer,Integer>(i,j));
			}
		}
		return q2;
	}
	
	/**
	 * Uma lista de pares dos elementos pertencentes ao 3o quadrante
	 * @param alvo
	 * @requires alvo != null
	 * @return quadrante3
	 */
	private List<Par<Integer,Integer>> q3 (EstadoSimulacao[][] alvo) {
		List<Par<Integer,Integer>> q3 = new ArrayList<Par<Integer,Integer>>();
		
		int linhas = alvo.length / 2;
		int colunas = alvo[0].length / 2;
		
		if (alvo.length % 2 != 0) {
			for (int i = linhas + 1; i < alvo.length; i++) {
				for (int j = 0; j < colunas; j++) {
					q3.add(new Par<Integer,Integer>(i,j));
				}
			}
		} else {
			for (int i = linhas; i < alvo.length; i++) {
				for (int j = 0; j < colunas; j++) {
					q3.add(new Par<Integer,Integer>(i,j));
				}
			}
		}
		return q3;
	}
	
	/**
	 * Uma lista de pares dos elementos pertencentes ao 4o quadrante
	 * @param alvo
	 * @requires alvo != null
	 * @return quadrante4
	 */
	private List<Par<Integer,Integer>> q4 (EstadoSimulacao[][] alvo) {
		List<Par<Integer,Integer>> q4 = new ArrayList<Par<Integer,Integer>>();
		
		int linhas = alvo.length / 2;
		int colunas = (alvo[0].length % 2 == 0) ? (alvo[0].length / 2) : (alvo[0].length / 2) + 1;
		
		if (alvo.length % 2 != 0) {
			for (int i = linhas + 1; i < alvo.length; i++) {
				for (int j = colunas; j < alvo[0].length; j++) {
					q4.add(new Par<Integer,Integer>(i,j));
				}
			}
		} else {
			for (int i = linhas; i < alvo.length; i++) {
				for (int j = colunas; j < alvo[0].length; j++) {
					q4.add(new Par<Integer,Integer>(i,j));
				}
			}
		}
		return q4;
	}
	
	/**
	 * Calcula o numero de afetados do quadrante
	 * @param q O quadrante
	 * @param alvo O alvo da simulacao
	 * @requires q != null && alvo != null
	 * @return Numero de afetafos
	 */
	private int afetadosQ(List<Par<Integer,Integer>> q, EstadoSimulacao[][] alvo) {
		int afetados = 0;
		for (Par<Integer,Integer> p : q) {
			if (alvo[p.primeiro()][p.segundo()] == EstadoSimulacao.AFETADO) {
				afetados += 1;
			}
		}
		return afetados;
	}
	
	/**
	 * Uma lista de pares em que o primeiro elemento e a lista 
	 * referente ao quadrante e o segundo elemento e o  numero
	 * de afetados do respetivo quadrante
	 * @param alvo O alvo de simulacao
	 * @requires alvo != null
	 * @return Lista com pares do quadrante e afetados correspondente
	 */
	private List<Par<List<Par<Integer,Integer>>,Integer>> quadrantesComAfetados (EstadoSimulacao[][] alvo) {
		List<Par<List<Par<Integer,Integer>>,Integer>> quadrantes = new ArrayList<Par<List<Par<Integer,Integer>>,Integer>>();
		
		List<Par<Integer,Integer>> q = q1(alvo);
		quadrantes.add(new Par<List<Par<Integer,Integer>>,Integer>(q, afetadosQ(q, alvo)));
		
		q = q2(alvo);
		quadrantes.add(new Par<List<Par<Integer,Integer>>,Integer>(q, afetadosQ(q, alvo)));
		
		q = q3(alvo);
		quadrantes.add(new Par<List<Par<Integer,Integer>>,Integer>(q, afetadosQ(q, alvo)));
		
		q = q4(alvo);
		quadrantes.add(new Par<List<Par<Integer,Integer>>,Integer>(q, afetadosQ(q, alvo)));
		
		return quadrantes;
	}
	
	/**
	 * Qual e o quadrante com menos afetados?
	 * @param quadrantes A Lista com pares do quadrante e afetados correspondente
	 * @requires quadrantes != null
	 * @return index do quadrante com menos afetados
	 */
	private int qMenosAfetado(List<Par<List<Par<Integer,Integer>>,Integer>> quadrantes) {
		int index = 0;
		int afetados = 50;
	
		for (Par<List<Par<Integer,Integer>>,Integer> q : quadrantes) {
			if (q.segundo() < afetados) {
				afetados = q.segundo();
				index = quadrantes.indexOf(q);
			}
		}
		return index;
	}
	
	/**
	 * Qual e o quadrante com mais afetados?
	 * @param qa1 Index do 1o quadrante de ataque possivel
	 * @param qa2 Index do 2o quadrante de ataque possivel
	 * @param quadrantes A Lista com pares do quadrante e afetados correspondente
	 * @requires qa1 >= 0 && qa2 >= 0 && quadrantes != null
	 * @return index do quadrante com mais afetados
	 */
	private int qMaisAfetado (int qa1, int qa2, List<Par<List<Par<Integer,Integer>>,Integer>> quadrantes) {
		
		Par<List<Par<Integer, Integer>>, Integer> qA1 = quadrantes.get(qa1);
		int afetadosQA1 = qA1.segundo();
		
		Par<List<Par<Integer, Integer>>, Integer> qA2 = quadrantes.get(qa2);
		int afetadosQA2 = qA2.segundo();
		
		return afetadosQA1 < afetadosQA2 ? qa2 : qa1;
	}
	
	/**
	 * Qual e o quadrante de ataque?
	 * @param quadrantes A Lista com pares do quadrante e afetados correspondente
	 * @param qMenosAfetado Index do quadrante com menos afetados
	 * @requires quadrantes != null && qMenosAfetado >= 0
	 * @return index do quadrante de ataque
	 */
	private int qAtaque (List<Par<List<Par<Integer,Integer>>,Integer>> quadrantes, int qMenosAfetado) {
		int ataque = 0;
		
		switch (qMenosAfetado) {
		case 0:
			ataque = qMaisAfetado(1, 3, quadrantes);
			break;
		case 1:
			ataque = qMaisAfetado(0, 2, quadrantes);
			break;
		case 2:
			ataque = qMaisAfetado(1, 3, quadrantes);
			break;
		case 3:
			ataque = qMaisAfetado(0, 2, quadrantes);
			break;
		}
		return ataque;
	}
	
	/**
	 * Qual o sentido de ataque?
	 * @param qMenosAfetado Index do quadrante com menos afetados
	 * @param qAtaque Index do quadrante de ataque
	 * @requires qMenosAfetado >= 0 && qAtaque >= 0
	 * @return direcao
	 */
	private String sentidoAtaque (int qMenosAfetado, int qAtaque) {
		String dir = null;
		switch (qMenosAfetado) {
		case 0:
			dir = (qAtaque == 1) ? "O" : "S";
			break;
		case 1:
			dir = (qAtaque == 0) ? "E" : "S";
			break;
		case 2:
			dir = (qAtaque == 1) ? "N" : "E";
			break;
		case 3:
			dir = (qAtaque == 0) ? "N" : "O";
			break;
		}
		return dir;
	}
}
















