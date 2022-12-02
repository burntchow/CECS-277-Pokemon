public class HpDown extends PokemonDecorator{
  /* Constructor: adds ‘-HP’ to their name and decreases Pokemon Hp and maxHp by 1*/
  public HpDown(Pokemon p){
    super(p,p.getName() + " -HP",-1);
  }
}