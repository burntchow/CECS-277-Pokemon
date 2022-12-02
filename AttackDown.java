public class AttackDown extends PokemonDecorator{
  
  /* Constructor: adds ‘-ATK’ to their name */
  public AttackDown(Pokemon p){
    super(p, p.getName() + " -ATK", 0);
  }

  /*
   * Decreases a pokémon’s damage by 1.
   * @param int pokemon type 
   * @return int attack bonus
   */
	@Override
  public int getAttackBonus(int atkType){
    return pokemon.getAttackBonus(atkType) - 1;
  }
}