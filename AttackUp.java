public class AttackUp extends PokemonDecorator{
  
  /* Constructor: adds ‘+ATK’ to their name */
  public AttackUp(Pokemon p){
    super(p, p.getName()+" +ATK", 0);
  }

  /*
   * Increases a pokémon’s damage by 1-2.
   * @param int pokemon type 
   * @return int attack bonus
   */
	@Override
  public int getAttackBonus(int atkType){
  	int randAtk = (int)(Math.random()*2 + 1);
		return pokemon.getAttackBonus(atkType) + randAtk;
  }
}