/**
 * Esta interface define um tipo de dados.
 * @author in Grupo 17 - Francisco O (53340) & Sofia Lourenco (54950) & Ines Marcelino (54991)
 * @date Dezembro 2020
 */
public interface Acionavel {
	
	/**
	 * A matriz de elementos deste Acionavel para simulacao
	 * @return A matriz
	 */
	EstadoSimulacao[][] alvoSimulacao();
	
	/**
	 * Pode ser usado numa simulacao?
	 * @return true se este acionael pode ser usado na simulacao
	 */
	boolean podeAtuar();
}