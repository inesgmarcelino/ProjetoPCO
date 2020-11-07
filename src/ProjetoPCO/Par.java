package ProjetoPCO;

/**
 * Representa pares de objetos de tipos quaisquer
 * @author in
 * @date Novembro 2020
 */
public class Par<P,S> {
	private P primeiro;
	private S segundo;
	
	/**
	 * Inicializa os atributos do novo objeto
	 * @param prim O primeiro elemento para o novo par
	 * @param seg O segundo elemento para o novo par
	 * @ensures this.primeiro().equals(prim) && 
	 *          this.segundo() == seg
	 */
	public Par (P prim, S seg) {
		this.primeiro = prim;
		this.segundo = seg;
	}
	
	/**
	 * O primeiro elemento deste par
	 * @return O primeiro elemento deste par
	 */
	public P primeiro() {
		return this.primeiro;
	}
	
	/**
	 * O segundo elemento deste par
	 * @return O segundo elemento deste par
	 */
	public S segundo() {
		return this.segundo;
	}
	
	/**
	 * Representacao textual deste par
	 */
	public String toString() {
		return "(" + this.primeiro + "," + this.segundo + ")";
	}

}