public class Water extends Pokemon{

  /* Water Constructor */
  public Water(String n, int h, int m){
    super(n, h, m); 
  }

  /*
   * Returns the basic and special attack menu string.
   * @param int attack type 
   * @return String 
   */
  @Override 
  public String getAttackMenu(int atkType){
    if(atkType == 1){
      return "1. Slam\n2. Tackle\n3. Punch";
    }else if(atkType == 2){
      return "1. Water Gun\n2. Bubble Beam\n3. Waterfall";
    }
    return "Something went wrong";
  }

  /*
   * Returns number of attack menu items based on attack type.
   * @param int attack type 
   * @return int menu items
   */
  @Override
  public int getNumAttackMenuItems(int atkType){
    if(atkType == 1){
      return 3;
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
    if(atkType == 1){
      return super.getAttackString(atkType, move);
    } else if (atkType == 2){
      if(move == 1){
        return " uses WATER GUN on ";
      } else if(move == 2){
        return " BUBBLE BEAMS ";
      } else if(move == 3){
        return " uses WATERFALL on ";
      }
    }
    return "Something went wrong";
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
      return super.getAttackDamage(atkType, move);
    } else if(atkType == 2){
      if(atkType == 2 && move == 1){
        dmg = (int) (Math.random() * 3 + 2);
      } else if(atkType == 2 && move == 2){
        dmg = (int) (Math.random() * 2 + 1);
      } else if(atkType == 2 && move == 3){
        dmg = (int) (Math.random() * 3 + 1);
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