public abstract class Pokemon extends Entity {
  public static final double[][] battleTable = { { 1, .5, 2 }, { 2, 1, .5 }, { .5, 2, 1 } };

  /*
   * Constructor: name is inputed string and hp is a random number from 22 to 24
   */
  public Pokemon(String n, int h, int m) {
    super(n, h, m);

  }

  /*
   * Lists options between basic and special attack
   * 
   * @return String attack menu
   */
  public String getAttackTypeMenu(){
    return "1. Basic Attack\n2. Special Attack";
  }

  /*
   * Returns the number of options in the attack menu
   * 
   * @return int number of items
   */
  public int getNumAttackTypeMenuItems(){
    return 2;
  }

  /*
   * Returns the menu based on the attack type (basic or special)
   *
   * @return String menu
   */
  public String getAttackMenu(int atkType){
    String menu = "";
    if(atkType == 1){
      menu = "1. Slam\n2. Tackle\n3. Punch";
    }
    return menu;
  }

  /*
   * Returns the number of options in the attack menu
   * 
   * @return int number of items
   */
  public int getNumAttackMenuItems(int atkType){
    return 3;
  }

  /*
   * Passes in pokemon being attacked. Damage is calculated via method
   *Pokemon takes damage. String is return showing move was taken
   *
   * @return String attack
   */
  public String attack(Pokemon p, int atkType, int move){
    int dmg = this.getAttackDamage(atkType, move);
    double mult = this.getAttackMultiplier(p, atkType);
    int bonus = this.getAttackBonus(atkType);
    int finalDmg = (int) (dmg * mult) + bonus;
    //System.out.println("ATTACK BONUS IS: "+ bonus);
		
		if (finalDmg < 0){
			finalDmg = 0;
		}
    p.takeDamage(finalDmg);

    return this.getName() + this.getAttackString(atkType, move) + p.getName() + " and does " + finalDmg + " damage.";
  }

  /*
   * Passes in attack type and move.
   * Returns specific move used
   *
   * @return String move
   */
  public String getAttackString(int atkType, int move){
    String chosenMove = "";
    if(atkType == 1 && move == 1){
      chosenMove = " SLAMS ";
    } else if(atkType == 1 && move == 2){
      chosenMove = " TACKLES ";
    } else if(atkType == 1 && move ==3){
      chosenMove = " PUNCHES ";
    }
    return chosenMove;
  }

  /*
   * Damage is determined on wheter or not the basic move is slam, tackle, or punch
   *
   * @return int damage
   */
  public int getAttackDamage(int atkType, int move){
    int dmg = 0;
    if(atkType == 1 && move == 1){
      dmg = (int) (Math.random() * 6); //0-5 dmg
    } else if(atkType == 1 && move == 2){
      dmg = (int) (Math.random() * 2 + 2); //2-3 dmg
    } else if(atkType == 1 && move == 3){
      dmg = (int) (Math.random() * 4 + 1); //1-4 dmg
    }
    return dmg;
  }

  /*
   * Returns an attack multiplier based on type advantage
   *Since these are basic attacks, multiplier is one
   *
   * @return double multiplier
   */
  public double getAttackMultiplier(Pokemon p, int atkType){
    return 1;
  }

  /*
   * Returns an attack bonus
   *
   * @return int bonus
   */
  public int getAttackBonus(int atkType){
    return 0;
  }

  /*
  * Checks to see if the pokemon object is implementing the Fire, Water, or Grass class 
  * Returns an integer corresponding to what type it is
  */
  public int getType(){
    int pokeType = -1;
    if (this instanceof Fire) {
      pokeType = 0;
    }
    if (this instanceof Water) {
      pokeType = 1;
    }
    if (this instanceof Grass) {
      pokeType = 2;
    }
    return pokeType;
  }
}