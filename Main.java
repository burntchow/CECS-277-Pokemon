/* 
* Project 2
* Names: 1 Patrick Del Pilar, 2 Aveline Villaganas, 3 Patrice Calagui
* Date: 12/2/2021
* Description: Pokemon - The player is a new pokemon trainer exploring 
* the different areas of the game encountering new places and facing 
* events throughout their travels, collecting and fighting other pokemon.
*/

class Main {
  public static void main(String[] args) {
    // Intro sequence, gets name and starter pokemon
    System.out.println("Prof. Oak: Hello there new trainer, what is your name?");
    String inputName = CheckInput.getString();
    System.out.println("Great to meet you, " + inputName
        + " if you want to be the very best you have to start somewhere.\nNow choose your first pokemon:\n1. Charmander\n2. Bulbasaur\n3. Squirtle");

    int starterPoke = CheckInput.getIntRange(1, 3);
    Pokemon starter = null;

    PokemonGenerator g = PokemonGenerator.getInstance();
    int mapNum = 1;
    Map currentMap = Map.getInstance();
    currentMap.loadMap(mapNum);

    if (starterPoke == 1) {
      starter = g.getPokemon("Charmander");
    } else if (starterPoke == 2) {
      starter = g.getPokemon("Bulbasaur");
    } else if (starterPoke == 3) {
      starter = g.getPokemon("Squirtle");
    }

    Trainer player = new Trainer(inputName, starter);
    int trainerLvl = 1;

    // Game repeats until user quits or when trainer runs out of hp
    boolean trainerAlive = true;
    boolean notQuit = true;
    while (trainerAlive && notQuit) {
      // Print Trainer Stats: Name, HP, Money, Postion#, Poke Ball#, Pokemon List
      System.out.println(player.toString());

      // Print Map + Main Menu (directions) -- account for out of bounds in Map
      System.out.println("Map:");
      System.out.println(currentMap.mapToString(player.getLocation()));

      int userDirection = mainMenu();
      if (userDirection == 1) {
        player.goNorth(); // Have check in each direction function if valid when the returned character is '!'
      } else if (userDirection == 2) {
        player.goSouth();
      } else if (userDirection == 3) {
        player.goEast();
      } else if (userDirection == 4) {
        player.goWest();
      } else if (userDirection == 5) { // quit
        System.out.println("Thank You For Playing!");
        notQuit = false;
        break;
      }

      if (currentMap.getCharAtLoc(player.getLocation()) == 'f') {
        // finish - triggers the loading of the next map
        // loads maps in order

        String[] leaderNames = new String[] {"Doodlebob","Bubble Bass","Kevin","Hash-slinging slasher","Tattletale Strangler","Flying Dutchman","Man Ray","Ave","Patrick","Patrice","Crow","Cleary"};
        int randName = (int)(Math.random()*leaderNames.length);
        String leader = leaderNames[randName];
        System.out.println("Welcome to the Salty Spitoon. How tough are you?");
        System.out.println("Gym Leader " + leader + " appeared");
				
        int gymLvl = trainerLvl + 2;
        Pokemon gymPoke = g.generateRandomPokemon(gymLvl);
				System.out.println(leader + " brings out " + gymPoke.getName() + " !");

        while (player.getHp() > 0 && gymPoke.getHp() != 0) {
          System.out.println(gymPoke.toString());
          System.out.println("What do you want to do?");
          System.out.println("1. Fight\n2. Use Potion\n3. Throw Poké Ball\n4. Run Away");
          int encounterChoice = CheckInput.getIntRange(1, 4);

          if (encounterChoice == 1) {
            if (gymPoke.getHp() > 0) {
              gymPoke = trainerAttack(player, gymPoke);
						} 
          } else if (encounterChoice == 2) {
            	int pokemonToHeal;
            	if (player.hasPotion()) {
              	System.out.println("Choose a pokémon to heal.\n" + player.getPokemonList());
              	pokemonToHeal = CheckInput.getIntRange(1, player.getNumPokemon()) - 1;
              	player.usePotion(pokemonToHeal);
              	starter = player.getPokemon(pokemonToHeal);
            	} else {
              System.out.println("You have no potions.");
            }
          } else if (encounterChoice == 3) { // Throw pokeball
            System.out.println("You can't catch their pokemon");
          } else if (encounterChoice == 4) { // Run
            System.out.println("You can't run from this gym battle");
          }
        }

      	System.out.println("You beat " + leader + " and advanced to the next area!\nYour pokemon feel stronger!");
				mapNum++;
      	trainerLvl++;
      	player.buffAllPokemon();
      	if (mapNum > 3) {
        	mapNum = 1;
        	trainerLvl++;
      	}
      	currentMap.loadMap(mapNum);
      } else if (currentMap.getCharAtLoc(player.getLocation()) == 'n') {
        // nothing - no encounter found
        System.out.println("There's nothing here...");
      } else if (currentMap.getCharAtLoc(player.getLocation()) == 'i') {
        // item - randomly give the train a potion or a poke ball
        // 'i' is removed from the map after the item is found
        int probability = (int) (Math.random() * 2 + 1);
        if (probability == 1) {
          player.receivePokeball();
          System.out.println("You Found a Poke Ball!");
        } else {
          player.receivePotion();
          System.out.println("You Found a Potion!");
        }
        currentMap.removeCharAtLoc(player.getLocation());
      } else if (currentMap.getCharAtLoc(player.getLocation()) == 'w') {
        // wild pokemon
        // Pokemon wildEncounter = chooseRandomPokemon();
        Pokemon wild = g.generateRandomPokemon(trainerLvl);
        System.out.println("A wild " + wild.getName() + " has appeared.");
        System.out.println(wild.toString()); // type cast?
        System.out.println("What do you want to do?");
        System.out.println("1. Fight\n2. Use Potion\n3. Throw Poke Ball\n4. Run Away");
        int encounterChoice = CheckInput.getIntRange(1, 4);
        while (encounterChoice != 4 && wild.getHp() > 0) { // Fight

          // choose a pokemon
          if (encounterChoice == 1) {
            wild = trainerAttack(player, wild);
          } else if (encounterChoice == 2) { // Potion
            int pokemonToHeal;
            if (player.hasPotion()) {
              System.out.println("Choose a pokemon to heal.\n" + player.getPokemonList());
              pokemonToHeal = CheckInput.getIntRange(1, player.getNumPokemon()) - 1;
              player.usePotion(pokemonToHeal);
            } else {
              System.out.println("You have no potions.");
            }
          } else if (encounterChoice == 3) { // Throw pokeball
            boolean caught = player.catchPokemon(wild);
            if (caught == true) {
              // check if trainer has 6 pokemon
              if (player.getNumPokemon() == 7) {
                System.out
                    .println("You can't catch em all. Please choose a pokemon to release.\n" + player.getPokemonList());
                int beFree = CheckInput.getIntRange(1, player.getNumPokemon()) - 1;
                player.removePokemon(beFree);

              }
              break;
            } else if (caught == false) {
              if (player.hasPokeball() == true) {
                if (player.getHp() <= 0) {
                  trainerAlive = false;
                  System.out.println(player.getName() + " fainted!");
                  break;
                }
              }
            }
          }
          if(wild.getHp()>0){
          System.out.println("What do you want to do?");
          System.out.println("1. Fight\n2. Use Potion\n3. Throw Poke Ball\n4. Run Away");
          encounterChoice = CheckInput.getIntRange(1, 4);
          }
        }
        if (encounterChoice == 4) { // Run
          if (player.getLocation().x == 0) { // top of the map
            int runDirection = (int) (Math.random() * 3 + 1);
            if (runDirection == 1) {
              player.goEast();
            } else if (runDirection == 2) {
              player.goSouth();
            } else if (runDirection == 3) {
              player.goWest();
            }
          } else if (player.getLocation().x == 4) { // bottom of the map
            int runDirection = (int) (Math.random() * 3 + 1);
            if (runDirection == 1) {
              player.goEast();
            } else if (runDirection == 2) {
              player.goNorth();
            } else if (runDirection == 3) {
              player.goWest();
            }
          } else if (player.getLocation().y == 0) { // left of the map
            int runDirection = (int) (Math.random() * 3 + 1);
            if (runDirection == 1) {
              player.goEast();
            } else if (runDirection == 2) {
              player.goNorth();
            } else if (runDirection == 3) {
              player.goSouth();
            }
          } else if (player.getLocation().y == 4) { // right of the map
            int runDirection = (int) (Math.random() * 3 + 1);
            if (runDirection == 1) {
              player.goWest();
            } else if (runDirection == 2) {
              player.goNorth();
            } else if (runDirection == 3) {
              player.goSouth();
            }
          } else { // within map boundaries
            int runDirection = (int) (Math.random() * 4 + 1);
            if (runDirection == 1) {
              player.goNorth();
            } else if (runDirection == 2) {
              player.goSouth();
            } else if (runDirection == 3) {
              player.goEast();
            } else if (runDirection == 4) {
              player.goWest();
            }
          }
        }
      } else if (currentMap.getCharAtLoc(player.getLocation()) == 'p') {
        // person - random person gives trainer items, money or can cause them damage
        // 'p' is removed from the map after the item is found
        int randomPerson = (int) (Math.random() * 4 + 1);
        if (randomPerson == 1) {
          int oakInteractions = (int) (Math.random() * 2);
          if (oakInteractions == 0) {
            int bribe = (int) (Math.random() * 6 + 5);
            System.out.println(
                "You encounter Professor Oak.\nOak: Sorry for making you fight a bunch of wild animals with no adult supervision while you're literally just ten years old.\nHere's some money so you won't snitch on me to Officer Jenny.");
            player.receiveMoney(bribe);
            System.out.println("You receive " + bribe + " Poke Bucks. How Shady...");
          } else if (oakInteractions == 1) {
            System.out.println("You encounter Professor Oak.\nOak: Hey " + player.getName()
                + "! It's dangerous to go alone take this.\nYou receive 2 Potions.");
            player.receivePotion();
            player.receivePotion();
          }
        } else if (randomPerson == 2) {
          System.out.println(
              "You run into Brock.\nBrock: I'll use my trusty frying pan as a drying pan!\nHe accidentally smacks you for 3 Damage");
          player.takeDamage(3);
          if (player.getHp() <= 0) {
            trainerAlive = false;
            System.out.println(player.getName() + " fainted!");
            break;
          }
        } else if (randomPerson == 3) {
          int randomItem = (int) (Math.random() * 4 + 1);
          if (randomItem == 1) {
            System.out.println("You encounter... ugh Gary.\nGary: It looks like my rival " + player.getName()
                + " is having a hard time, I guess I'll have to help you out because I'm just SOOooo much better than you.\nGary hands you 2 Potions");
            player.receivePotion();
            player.receivePotion();
          } else if (randomItem == 2) {
            System.out.println("You encounter... ugh Gary.\nGary: It looks like my rival " + player.getName()
                + " is having a hard time, I guess I'll have to help you out because I'm just SOOooo much better than you.\nGary hands you 3 Poke Balls");
            player.receivePokeball();
            player.receivePokeball();
            player.receivePokeball();
          } else if (randomItem == 3) {
            System.out.println("You encounter... ugh Gary.\nGary: It looks like my rival " + player.getName()
                + " is having a hard time, I guess I'll have to help you out because I'm just SOOooo much better than you.\nGary hands you 15 Poke Bucks");
            player.receiveMoney(15);
          } else if (randomItem == 4) {
            System.out.println("You encounter... ugh Gary.\nGary: It looks like my rival " + player.getName()
                + "! Dang you REALLY are ugly.\nThat insult really hurt your feelings\nYou took 5 Damage");
            player.takeDamage(5);
            if (player.getHp() <= 0) {
              trainerAlive = false;
              System.out.println(player.getName() + " fainted!");
              break;
            }
          }
        } else if (randomPerson == 4) {
          System.out.println(
              "You run into Team Rocket.\nJessie: Prepare for trouble!\nJames: And make it double!\nJessie: To protect the world from devastation.\nJames: To unite all peoples within our nation.\nJessie: To denounce the evils of truth and love!\nJames: To extend our reach to the stars above!\nJessie: Jessie!\nJames: James!\nJessie: Team Rocket blasts off at the speed of light!\nJames: Surrender now, or prepare to fight!\nMeowth: Meowth! That's right!\nWobbuffet: Wobbuffet. *salutes*");
          System.out.println("They're too busy monologuing to notice you steal 2 of their Poke Balls");
          player.receivePokeball();
          player.receivePokeball();
        }
        currentMap.removeCharAtLoc(player.getLocation());

      } else if (currentMap.getCharAtLoc(player.getLocation()) == 'c') {
        // city - player can enter a city and choose to go to the store to buy more
        // potions/poke balls OR take their pokemon to the hospital

        System.out.println("You've entered the city.\nWhere would you like to go?");
        System.out.println("1. Poke Mart\n2. Poke Center");
        int cityChoice = CheckInput.getIntRange(1, 2);
        if (cityChoice == 1) {
          store(player);
        } else if (cityChoice == 2) {
          System.out.println(
              "Hello! Welcome to the Poke Center.\nI'll fix your poor pokemon up in a jiffy!\nThere you go! see you again soon.");
          player.healAllPokemon();
        }
      }

      if (player instanceof Entity && player.getHp() <= 0) {
        trainerAlive = false;
      }
    }
    System.out.println("Game Over!");
  }

  public static int mainMenu() {
    System.out.println("Main Menu:\n1. Go North\n2. Go South\n3. Go East\n4. Go West\n5. Quit");
    int choice = CheckInput.getIntRange(1, 5);
    return choice;
  }

  /*
   * Called when user chooses fight from the menu. Chooses between bewteen the
   * basic and special attack to inflict on the wild pokemon
   * 
   * @param t - Trainer object
   * 
   * @param wild - Pokemon object
   */
  public static Pokemon trainerAttack(Trainer t, Pokemon wild) {
		int chosenPokemon, attackType, attackChoice;
		PokemonGenerator g = PokemonGenerator.getInstance();
		Pokemon opponent = wild;

		System.out.println("Choose a Pokemon:\n" + t.getPokemonList());
		
		chosenPokemon = CheckInput.getIntRange(1, t.getNumPokemon()) - 1;
		Pokemon you = t.getPokemon(chosenPokemon);
		if (you.getHp() <= 0) {
			// Wild pokemon will do some random damage to trainer
			int damage = (int) (Math.random() * 3 + 1);
			t.takeDamage(damage);
			System.out.println("This pokemon is knocked out!\nYou take " + damage + " DAMAGE!");
		}
		else if (you.getHp() > 0) {

			System.out.println(you.getName() + ", I choose you!");
			
			System.out.println(you.getAttackTypeMenu());
			attackType = CheckInput.getIntRange(1, you.getNumAttackTypeMenuItems());

			System.out.println(you.getAttackMenu(attackType));
			attackChoice = CheckInput.getIntRange(1, you.getNumAttackMenuItems(attackType));

			System.out.println(you.attack(opponent, attackType, attackChoice));
			int chanceWeDebuff = (int) (Math.random() * 4); // 0-3
			if (chanceWeDebuff == 2) {
				opponent = g.addRandomDebuff(opponent);
			}

			if (opponent.getHp() <= 0){
				System.out.println(opponent.getName() + " has fainted!");
			} else if (opponent.getHp() > 0) {
				int atkChance = (int) (Math.random() * 2 + 1);
				int atkMethod = (int) (Math.random() * 3 + 1);
				System.out.println(opponent.attack(you, atkChance, atkMethod) + "\n");
				int chanceTheyDebuff = (int) (Math.random() * 10); //0-9
				if (chanceTheyDebuff == 5) {
					t.debuffPokemon(chosenPokemon);
				}
			}


		}
		System.out.println(you.toString());
		System.out.println(opponent.toString() + "\n");
		return opponent;
	}

  /*
   * Trainer can choose to buy more potions ($5) or poke balls ($3) or leave .Will
   * subtract amount from trainer's money if possible transaction
   * 
   * @param Trainer object
   */
  public static void store(Trainer t) {
    System.out.println("Hello! What can I help you with?\n1. Buy Potions - $5\n2. Buy Poke Balls - $3\n3. Exit");
    int choice = CheckInput.getIntRange(1, 3);
    while (choice != 3) {
      if (choice == 1 && t.spendMoney(5)) { // Potions
        System.out.println("Here's your potion.");
        t.receivePotion();
      } else if (choice == 2 && t.spendMoney(3)) { // Pokeball
        System.out.println("Here's your poke ball.");
        t.receivePokeball();
      } else { // Broke
        System.out.println("You don't have enough money for that.");
      }
      System.out.println("Hello! What can I help you with?\n1. Buy Potions - $5\n2. Buy Poke Balls - $3\n3. Exit");
      choice = CheckInput.getIntRange(1, 3);
    }
    System.out.println("Thank you, come again soon!\n");
    // exit
  }
}

/*
 * ────────▄███████████▄────────I wanna be the very best
 * ─────▄███▓▓▓▓▓▓▓▓▓▓▓███▄─────Like no one ever was
 * ────███▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓███────To catch them is my real test
 * ───██▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██───To train them is my cause
 * ──██▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██──I will travel across the land
 * ─██▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██─Searching far and wide
 * ██▓▓▓▓▓▓▓▓▓███████▓▓▓▓▓▓▓▓▓██Teach Pokemon to understand
 * ██▓▓▓▓▓▓▓▓██░░░░░██▓▓▓▓▓▓▓▓██The power that's inside
 * ██▓▓▓▓▓▓▓██░░███░░██▓▓▓▓▓▓▓██Pokemon! 
   ███████████░░███░░███████████gotta catch 'em all 
 * ██░░░░░░░██░░███░░██░░░░░░░██It's you and me
 * ██░░░░░░░░██░░░░░██░░░░░░░░██I know it's my destiny
 * ██░░░░░░░░░███████░░░░░░░░░██Pokemon! 
 * ─██░░░░░░░░░░░░░░░░░░░░░░░██─Oh, you're my best friend
 * ──██░░░░░░░░░░░░░░░░░░░░░██──In a world we must defend
 * ───██░░░░░░░░░░░░░░░░░░░██─── 
 * ────███░░░░░░░░░░░░░░░███────
 * ─────▀███░░░░░░░░░░░███▀─────
 * ────────▀███████████▀────────
 */