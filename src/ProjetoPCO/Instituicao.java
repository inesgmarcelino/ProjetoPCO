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
	
	// A designacao da Instituicao
	private String designacao;
	// ArrayList das regioes da Instituicao
	private ArrayList<Regiao> regioes = new ArrayList<Regiao>();

	/**
	 * Inicializa os atributos do novo objeto Instituicao
	 * @param designacao A designacao
	 * @requires designacao != null
	 */
	public Instituicao(String designacao) {
		this.designacao = designacao;
	}
	
	/**
	 * Cria um novo objeto da classe Regiao
	 * @param nome O nome
	 * @param ultFogo Data do ultimo fogo 
	 * @param largura A largura da regiao
	 * @param altura A altura da regiao
	 * @param casas Lista com as posicoes das casas
	 * @param estradas Lista com as posicoes das estradas
	 * @param agua Lista com as posicoes da agua
	 * @requires nome != null && ultFogo != null && largura > 0 && altura > 0 
	 */
	public void adicionaRegiao(String nome, Calendar ultFogo, int largura, int altura, List<Par<Integer,Integer>> casas,
			List<Par<Integer,Integer>> estradas, List<Par<Integer,Integer>> agua) {

		this.regioes.add(new Regiao(nome, ultFogo, largura, altura, casas, estradas, agua));
	}
	
	/**
	 * Verifica se existe alguma Regiao com esse nome na Instituicao
	 * @param nome O nome
	 * @requires nome != null
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
	 * Nome da Regiao e nivel de perigo correspondente
	 * @return Lista com pares da regiao e nivel de perigo correspondente
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
		
	/**
	 * A matriz da regiao da instituicao que tem maior nivel de perigo
	 * @return matriz da regiao da instituicao que tem maior nivel de perigo
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
	 * Regista um novo fogo
	 * @param regiao O nome
	 * @param data Data do fogo
	 * @param sitios Lista com as posicoes dos sitios ardidos pelo fogo
	 * @requires regiao != null && data != null && sitios != null
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
	 * @return representacao string da Instituicao
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
