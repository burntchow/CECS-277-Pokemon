public class HpUp extends PokemonDecorator{
  private static int randHp = (int)(Math.random()*2+1);
  /* Constructor: increases a pokémon’s hp and maxHp by 1-2 and adds ‘+HP’ to their name */
  public HpUp(Pokemon p){
    super(p,p.getName() + " +HP",randHp);
  }
}