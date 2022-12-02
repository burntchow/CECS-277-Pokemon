public class Grass extends Pokemon{
  /* Grass Constructor */
  public Grass(String n, int h, int m){
    super(n,h,m);
  }

  /*
   * Returns the basic and special attack menu string.
   * @param int attack type 
   * @return String 
   */
  @Override
  public String getAttackMenu(int atkType){
    String menu = "";
    if(atkType == 1){
      menu = super.getAttackMenu(atkType);
    } else if(atkType == 2){
      menu = "1. Vine Whip\n2. Razor Leaf\n3. Solar Beam";
    }
    return menu;
  }

  /*
   * Returns number of attack menu items based on attack type.
   * @param int attack type 
   * @return int menu items
   */
  @Override
  public int getNumAttackMenuItems(int atkType){
    if(atkType == 1){
      return super.getNumAttackMenuItems(atkType);
    }else if(atkType == 2){
      return 3; 
    }
    return 3; 
  }

  /*
   * Returns the partial string for the chosen move.
   * @param int attack type 
   * @param int move 
   * @return String
   */
  @Override
  public String getAttackString(int atkType, int move){
    String chosenMove = "";
    if(atkType == 1){
      return super.getAttackString(atkType, move);
    } else if (atkType == 2){
      if(move == 1){
        chosenMove = " VINE WHIPS ";
      } else if(move == 2){
        chosenMove = " RAZOR LEAFS ";
      } else if(move == 3){
        chosenMove = " SOLAR BEAMS ";
      }
    }
    return chosenMove;
  }

  /*
   * Returns the randomized damage for the chosen move.
   * @param int attack type 
   * @param int move 
   * @return int damage 
   */
  @Override
  public int getAttackDamage(int atkType, int move){
    int dmg = 0;
    if(atkType == 1){
      dmg = super.getAttackDamage(atkType, move);
    } else if(atkType == 2){
      if(atkType == 2 && move == 1){
        dmg = (int) (Math.random() * 3 + 1); //1-3
      } else if(atkType == 2 && move == 2){
        dmg = (int) (Math.random() * 3 + 2); //2-4
      } else if(atkType == 2 && move == 3){
        dmg = (int) (Math.random() * 5); //0-5
      }
    }
    return dmg;
  }

  /*
   * Returns the attack multiplier for that class's moves.
   * @param int attack type 
   * @param int move 
   * @return int damage 
   */
  @Override
  public double getAttackMultiplier(Pokemon p, int atkType){
    int t1 = this.getType();
    int t2 = p.getType();

    double typeAdvantage = battleTable[t1][t2];
    if(atkType == 1){
      return super.getAttackMultiplier(p, atkType);
    } else if(atkType == 2){
      typeAdvantage = battleTable[t1][t2];
      return typeAdvantage;
    }
    return typeAdvantage;
  }
}