/**
 * Esta interface define um tipo de dados.
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
 * @date Dezembro 2020
 */
public interface Direcionador {

	/**
	 * Direcao sugerida para um passo da simulacao
	 * @param alvo
	 * @requires alvo != null
	 * @return direcao
	 */
	String direcao (EstadoSimulacao[][] alvo);
	
	/**
	 * Preco de uma consulta sobre a direcao a usar num
	 * passo da simulacao
	 * @param alvo
	 * @requires alvo != null
	 * @return preco
	 */
	int precoConsulta (EstadoSimulacao[][] alvo);
}