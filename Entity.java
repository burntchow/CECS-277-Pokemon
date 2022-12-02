public abstract class Entity {
  /* Instance variables */
  private String name;
  private int hp;
  private int maxHp;

  /* Constructor */
  public Entity(String n, int h, int m){
    this.name = n;
    this.hp = h;
    this.maxHp = m;
  }

  /*
   * Getter for Pokemon entity HP
   * 
   * @return int hp
   */
  public int getHp() {
    return hp;
  }

  /*
   * Getter for the Maximum Health
   * 
   * @return int
   */
  public int getMaxHp() {
    return maxHp;
  }

  /*
   * Damage taken subtracted by health. If health goes below 0 we cap at 0 to
   * avoid negative health.
   * 
   * @param int damage dealt
   */
  public void takeDamage(int d) {
    hp = hp - d;
    if (hp < 0) {
      hp = 0;
    }
  }

  /*
   * Health is set back to the max health.
   */
  public void heal() {
    hp = maxHp;
  }

  /*
   * Getter for Entity name
   * 
   * @return String name
   */
  public String getName() {
    return name;
  }

  /*
   * Takes name and health of entity and makes it into a string
   * 
   * @return String EX: "1. Charmander HP: 24/24"
   */
  public String toString() {
    return name + " HP: " + hp + "/" + maxHp;
  }
}