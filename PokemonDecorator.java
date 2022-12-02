public abstract class PokemonDecorator extends Pokemon{
	/* Instance Variables */
  protected Pokemon pokemon;

	/*
	 * Constructor decorating Base form "Pokemon"
	 * updates name with +/- ATK/HP
	 * updates current HP and maxHP with bonus HP 
	 *
	 *
	 * @param Pokemon p
	 * @param String extraName
	 * @param int extraHP
	 *
	 */ 
  public PokemonDecorator(Pokemon p, String extraName, int extraHP){
		super(extraName, p.getHp()+extraHP,p.getMaxHp()+ extraHP);
    pokemon = p;
  }

	/*
	 * Calls base form "Pokemon" getAttackMenu()
	 * @param int atkType
	 * @return String
	 * 
	 */

	public String getAttackMenu(int atkType){
		return pokemon.getAttackMenu(atkType);
	}

	/*
	 * Calls base form "Pokemon" getAttackMenuItems()
	 * @param int atkType
	 * @return int
	 * 
	 */
	public int getNumAttackMenuItems(int atkType){
		return pokemon.getNumAttackMenuItems(atkType);
	}

	/*
	 * Calls base form "Pokemon" getAttackString()
	 * @param int atkType
	 * @param int move
	 * @return String
	 * 
	 */
	public String getAttackString(int atkType, int move){
		return pokemon.getAttackString(atkType, move);
	}

	/*
	 * Calls base form "Pokemon" getAttackDamage()
	 * @param int atkType
	 * @param int move
	 * @return int
	 * 
	 */
	public int getAttackDamage(int atkType, int move){
		return pokemon.getAttackDamage(atkType, move);
	}

	/*
	 * Calls base form "Pokemon" getAttackMultiplier()
	 * @param Pokemon p
	 * @param int atkType
	 * @return double
	 * 
	 */
	public double getAttackMultiplier(Pokemon p, int atkType){
		return pokemon.getAttackMultiplier(p, atkType);
	}

	/*
	 * Calls base form "Pokemon" getAttackBonus()
	 * @param int atkType
	 * @return int
	 * 
	 */
	public int getAttackBonus(int atkType){
		return pokemon.getAttackBonus(atkType);
	}

	/*
	 * Calls base form "Pokemon" getType()
	 * @return int
	 * 
	 */
	public int getType(){
		return pokemon.getType();
	}
}