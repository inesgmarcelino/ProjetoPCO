import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe cujas instancias representam instituicoes 
 * @author isabel nunes
 * @date Novembro 2020
 *
 */
public class Instituicao implements Acionavel {
	// Valores limite de anos após ultimo fogo que permitem determinar
	// o nivel de risco de uma regiao
	public static final int[] RISCO_ANOS = {2, 3, 5, 8};

	private String designacao;
	private Map<String,Regiao> regioes;
	private String regiaoMaiorPerigo;

	/**
	 * Inicializa uma nova instituicao
	 * @param designacao O nome para a nova instituicao
	 * @requires designacao !=  null
	 */
	public Instituicao (String designacao) {
		this.designacao = designacao;
		this.regioes = new HashMap<String,Regiao>();
		this.regiaoMaiorPerigo = null;
	}

	/**
	 * Adiciona uma nova regiao a este DGF
	 * @param nome O nome da regiao
	 * @param linhas Quantas "ruas" horizontais tem a regiao
	 * @param colunas Quantas "ruas" verticais tem a regiao
	 * @param casas Posicoes <linha,coluna> onde se encontram as casas
	 * @param estradas Posicoes <linha,coluna> onde se encontram estradas
	 * @requires !existeRegiao(nome) && 
	 *           Regiao.dadosValidos(linhas, colunas, casas, estradas, agua)
	 */
	public void adicionaRegiao(String nome, Calendar ultFogo, 
			int linhas, int colunas,
			List<Par<Integer,Integer>> casas, 
			List<Par<Integer,Integer>> estradas, 
			List<Par<Integer,Integer>> agua) {

		Regiao r = new Regiao(nome, ultFogo, linhas, colunas, 
				casas, estradas, agua);
		this.regioes.put(nome, r);
		this.regiaoMaiorPerigo = novaRegiaoMaiorPerigo();
	}

	/**
	 * Existe alguma regiao nesta instituicao?
	 * @return
	 */
	public boolean temRegioes() {
		return this.regioes.keySet().size() > 0;
	}	

	/**
	 * A regiao com um dado nome, ou null caso nao exista
	 * @param nome O nome da regiao desejada
	 * @return
	 */
	private Regiao obterRegiao(String nome) {
		return this.regioes.get(nome);	
	}

	/**
	 * Existe uma regiao com um dado nome?
	 * @param regiao O nome
	 * @return
	 */
	public boolean existeRegiao(String regiao) {
		return obterRegiao(regiao) != null;
	}

	/**
	 * Registar a ocorrencia de fogo numa dada regiao, numa dada data,
	 * em que arderam determinadas posicoes da regiao
	 * @param regiao A regiao em que ocorreu o fogo
	 * @param data Data em que ocorreu o fogo
	 * @param sitios As posicoes <linha,coluna> dos sitios ardidos
	 * @requires existeRegiao(regiao) && data != null && sitios != null
	 */
	public void registaFogo(String regiao, Calendar data, 
			List<Par<Integer,Integer>> sitios) {

		obterRegiao(regiao).registaFogo(data, sitios);
		this.regiaoMaiorPerigo = novaRegiaoMaiorPerigo();
	}

	/**
	 * Qual a regiao de maior perigo?
	 * @return
	 */
	private String novaRegiaoMaiorPerigo() {
		NivelPerigo maxPerigo = NivelPerigo.VERDE;
		String regiaoMax = null;
		for(Regiao r : this.regioes.values()) {
			NivelPerigo perigoR = r.nivelPerigo(
					Calendar.getInstance(), RISCO_ANOS);
			if(perigoR.ordinal() >= maxPerigo.ordinal()) {
				maxPerigo = perigoR;
				regiaoMax = r.nome();
			}
		}
		return regiaoMax;
	}

	/**
	 * Quais os niveis de perigo das varias regioes desta instituicao?
	 * @return
	 * @requires temRegioes()
	 */
	public List<Par<String,NivelPerigo>> niveisDePerigo(){

		List<Par<String,NivelPerigo>> result = 
				new ArrayList<Par<String,NivelPerigo>>();

		for(Regiao r : this.regioes.values()) {
			result.add(new Par<String,NivelPerigo>(
					r.nome(),
					r.nivelPerigo(Calendar.getInstance(), RISCO_ANOS)));
		}
		return result;
	}

	/**
	 * Uma matriz representando a regiao com maior nivel de perigo,
	 * para efeitos de simulacao de fogos
	 * @requires temRegioes()
	 */
	@Override
	public EstadoSimulacao[][] alvoSimulacao(){
		return this.regioes.get(this.regiaoMaiorPerigo).alvoSimulacao();
	}

	/**
	 * Existe pelo menos uma regiao nesta instituicao que tenha pontos
	 * ardiveis?
	 */
	@Override
	public boolean podeAtuar() {

		for(Regiao r : this.regioes.values()) {
			if(r.ardiveis() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Representacao textual desta instituicao
	 */
	public String toString() {
		StringBuilder result = 
				new StringBuilder("***************************\n");
		result.append("Designacao: " + this.designacao + "\n");
		result.append("Regiao maior perigo: " + 
				this.regiaoMaiorPerigo + "\n");
		result.append("-------- REGIOES -------\n");

		for (Regiao r : this.regioes.values()) {
			result.append("Nivel perigo de fogo: " + 
					r.nivelPerigo(Calendar.getInstance(), RISCO_ANOS) + "\n");
			result.append(r.toString());
			result.append("--------------------\n");
		}
		result.append("***************************\n");
		return result.toString();

	}

}

