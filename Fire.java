public class Fire extends Pokemon{
  /* Fire Constructor */
  public Fire(String n, int h, int m){
    super(n, h, m); 
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
    }else if(atkType == 2){
      menu = "1. Ember\n2. Fire Blast\n3. Fire Punch";
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
		int numMenu = -1;
    if(atkType == 1){
      numMenu = super.getNumAttackMenuItems(atkType);
    }else if(atkType == 2){
      numMenu = 3; 
    }
    return numMenu;
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
        return " uses EMBER on ";
      } else if(move == 2){
        return " FIRE BLASTS ";
      } else if(move ==3){
        return " FIRE PUNCHES ";
      }
    }
    return " something went wrong lmao ";
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
        dmg = (int) (Math.random() * 3 + 2); //2-4 dmg
      } else if(atkType == 2 && move == 2){
        dmg = (int) (Math.random() * 4 + 2); //2-5 dmg
      } else if(atkType == 2 && move == 3){
        dmg = (int) (Math.random() * 4 + 1); //1-4 dmg
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