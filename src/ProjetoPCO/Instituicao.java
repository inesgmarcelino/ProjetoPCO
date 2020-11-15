package ProjetoPCO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Classe que representa as Instituicoes
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
 * @date Novembro 2020
 */
public class Instituicao {
	
	public static final int[] RISCO_ANOS = {2, 3, 5, 8};
	public static final int[] VENTOS_LIMITES = {0, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21};
	
	private String designacao;
	private ArrayList<Regiao> regioes = new ArrayList<Regiao>();

	/**
	 * Inicializa os atributos do novo objeto Instituicao
	 * @param designacao
	 */
	public Instituicao(String designacao) {
		this.designacao = designacao;
	}
	
	/**
	 * Cria um novo objeto da classe Regiao
	 * @param nome 
	 * @param ultFogo Data do ultimo fogo que aconteceu
	 * @param largura A largura da regiao
	 * @param altura A altura da regiao
	 * @param casas
	 * @param estradas
	 * @param agua
	 */
	public void adicionaRegiao(String nome, Calendar ultFogo, int largura, int altura, List<Par<Integer,Integer>> casas,
			List<Par<Integer,Integer>> estradas, List<Par<Integer,Integer>> agua) {

		this.regioes.add(new Regiao(nome, ultFogo, largura, altura, casas, estradas, agua));
	}
	
	/**
	 * Verifica se existe alguma Regiao com esse nome na Instituicao
	 * @param nome
	 * @return true, se ja existir
	 */
	public boolean existeRegiao(String nome) {
		for (Regiao r: this.regioes) {
			if (r.nome().equals(nome)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Devolver os nomes e os niveis de perigo das regioes da Instituicao
	 * @return Informacao pedida numa lista de pares (int,int)
	 */
	public List<Par<String,NivelPerigo>> niveisDePerigo() {
		List<Par<String,NivelPerigo>> listaNiveis = new ArrayList<Par<String,NivelPerigo>>();
		Calendar now = Calendar.getInstance();
		for (Regiao r: this.regioes) {
			String p = r.nome();
			NivelPerigo s = r.nivelPerigo(now, RISCO_ANOS);
			Par<String,NivelPerigo> nivelRegiao = new Par<String,NivelPerigo>(p,s);
			
			listaNiveis.add(nivelRegiao);
		}
		return listaNiveis;
	}
		
	//ATENCAO! AQUI TEM QUE SER DEVOLVIDO A REGIAO COM MAIOR NIVEL DE PERIGO
	/**
	 * Devolve o alvo da simulacao da Regiao de maior nivel da Instituicao
	 * @return Array ...
	 */
	public EstadoSimulacao[][] alvoSimulacao() {
		List<Par<String,NivelPerigo>> listaPerigo = niveisDePerigo();
		String nomeRegMaiorPerigo = null;
		int indexMaiorPerigo = 0;
		int indexReg = 0;
		for (int i = 0; i < listaPerigo.size(); i++) {
			if (listaPerigo.get(i).segundo().ordinal() > indexMaiorPerigo) {
				indexMaiorPerigo = listaPerigo.get(i).segundo().ordinal();
				nomeRegMaiorPerigo = listaPerigo.get(i).primeiro();
			}
		}
		for (int i = 0; i < this.regioes.size(); i++) {
			if (this.regioes.get(i).nome() == nomeRegMaiorPerigo) {
				indexReg = i;
			}
		}
		return this.regioes.get(indexReg).alvoSimulacao();
	}
	
	/**
	 * Verifica se pelo menos uma Regiao da Instituicao tem elementos
	 * ardiveis
	 * @return true, se tiver
	 */
	public boolean podeAtuar() {
		for (Regiao r: this.regioes) {
			if (r.ardiveis() > 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Regista um Fogo
	 * @param regiao
	 * @param data
	 * @param sitios
	 */
	public void registaFogo(String regiao, Calendar data, List<Par<Integer,Integer>> sitios) {
		for (Regiao r: this.regioes) {
			if (r.nome().equals(regiao)) {
				r.registaFogo(data, sitios);
			}
		}
	}
	
	/**
	 * Representacao textual desta Instituicao
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("*****************\n");
		result.append("Designacao: " + this.designacao + "\n");
		result.append("Regiao maior perigo: " + "\n");
		result.append("-------- REGIOES -------\n");
		for (Regiao r: this.regioes) {
			for (Par<String,NivelPerigo> p : niveisDePerigo()) {
				if (r.nome().equals(p.primeiro())) {
					result.append("Nivel perigo de fogo: " + p.segundo() + "\n");
				}
			}
			result.append(r.toString());
		}
		result.append("***************************\n");
		return result.toString();
	}
}
