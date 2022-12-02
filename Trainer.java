import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

/**
 * 
 *
 *
 * @author Patrick Del Pilar 2021
 * @ghostwriter Patrice Calagui: later 2021
 */
public class Trainer extends Entity{
	/* Instance Variables */
	private int money = 25;
	private int potions = 1; 
	private int pokeballs = 5;
	private Point loc = new Point(2,0);
	//private Map map;
	private ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();
	private static Random r = new Random();
	private static int randHP = 22 + r.nextInt(3); // random hp 22-24

	/* Trainer Constructor */
	public Trainer(String n, Pokemon p) {
    super(n, randHP, randHP);
		this.pokemon.add(p);
	}

	/*
	 * Getter for Money
	 * 
	 * @return int
	 */
	public int getMoney() {
		return money;
	}

	/*
	 * If user has enough money, subtract amount spent, return true if user doesn't
	 * have enough, return false
	 * 
	 * @param int amount spent
	 * 
	 * @return boolean
	 */
	public boolean spendMoney(int amt) {
		boolean canBuy = false;
		if (money >= amt) {
			money = money - amt;
			canBuy = true;
		} else if (money < amt) {
			canBuy = false;
		}
		return canBuy;
	}

	/*
	 * Adds amount received to trainer money
	 * 
	 * @param int amount received
	 */
	public void receiveMoney(int amt) {
		money = money + amt;
	}

  /*
	 * If user has a potion to use, return true if the user has NO potion, return
	 * false
	 * 
	 * @return boolean
	 */
	public boolean hasPotion() {
		boolean availPotion = false;
		if (potions > 0) {
			availPotion = true;
		} else {
			availPotion = false;
		}
		return availPotion;
	}

  /*
	 * adds one potion to trainer potions
	 */
	public void receivePotion() {
		potions++;
	}

  /*
	 * Gets the Pokemon specified at the pokeIndex and then uses the potion and
	 * heals the pokemon
	 */
	public void usePotion(int pokeIndex) {
    PokemonGenerator g = PokemonGenerator.getInstance();
		Pokemon p = pokemon.get(pokeIndex);
		potions--;
		p.heal();
    p = g.addRandomBuff(p);
		this.removePokemon(pokeIndex);
		pokemon.add(p);
	}

	/*
	 * Getter for Potions
	 * 
	 * @return int
	 */
	public int getPotions() {
		return potions;
	}

  /*
	 * If user has a pokeball to use, return true if the user has NO pokeball,
	 * return false
	 * 
	 * @return boolean
	 */
	public boolean hasPokeball() {
		boolean availPokeball = false;
		if (pokeballs > 0) {
			availPokeball = true;
		} else {
			availPokeball = false;
		}
		return availPokeball;
	}

  /*
	 * adds one pokeball to trainer pokeballs
	 */
	public void receivePokeball() {
		pokeballs++;
	}

	/*
	 * Getter for Pokeballs
	 * 
	 * @return int
	 */
	public int getPokeballs() {
		return pokeballs;
	}

	/*
	 * If user has a pokeball, run to catch the Pokemon p, add to user's list of
	 * pokemon and return true If the user didn't catch the Pokemon p, return false
	 * ex. 5/20 = 75% | 13/20 = 35% | 20/20 = 0% if rdm = (6-20) caught| if rdm =
	 * (14-20) caught | cannot be caught success range = (hp + 1) - maxHP
	 * 
	 * @param object Pokemon p
	 * 
	 * @return boolean
	 */
	public boolean catchPokemon(Pokemon p) {
    Map m = Map.getInstance();

		boolean caught = false;
		if (hasPokeball() == true) {
			pokeballs--; //uses a pokeball
			System.out.println("Shake...Shake...Shake..."); 
			int rdm = ((r.nextInt(p.getMaxHp())) + 1); // rolls 1-maxHP
			caught = (((p.getHp() + 1) <= rdm) && (rdm <= p.getMaxHp())); // if inside the range (hp + 1) <= rdm <= maxHP | Pokemon is caught
			if (caught == true) {
				System.out.println("You caught " + p.getName());
				pokemon.add(p);
				m.removeCharAtLoc(loc.getLocation());
			} else if (caught == false){
				System.out.println(p.getName() + " broke free!\n");
				System.out.println("Damn, almost had it!");
				System.out.println(p.getName() + " didn't like that and attacks");
				int damage = (int) (Math.random() * 3 + 1);
				this.takeDamage(damage);
				System.out.println("You take " + damage + " DAMAGE!");
			}
		} else if (hasPokeball() == false) {
			System.out.println("You have no more Poke Balls.");
		}
		return caught;
	}

	/*
	 * Getter for Point object loc
	 * 
	 * @return Point object
	 */
	public Point getLocation() {
    Map m = Map.getInstance();
		m.reveal(loc.getLocation());
		return loc.getLocation();
	}

	/*
	 * Checks if user is moving in a valid direction, first creates the new
	 * location/point based off current location gets the character at the new
	 * location, if it is a valid location then move, if it is not valid then dont
	 * move and return the location's character
	 * 
	 * @return character at location
	 */
	public char goNorth() {
    Map m = Map.getInstance();

		Point nextPoint = getLocation();
		char pointChar = '!';
		nextPoint.x = nextPoint.x - 1;
		pointChar = m.getCharAtLoc(nextPoint);
		if (pointChar == '!') {
			System.out.println("You cannot go that way.");
		} else if (pointChar != '!') {
			loc = nextPoint;
		}
		return m.getCharAtLoc(loc);
	}

	/*
	 * Checks if user is moving in a valid direction, first creates the new
	 * location/point based off current location gets the character at the new
	 * location, if it is a valid location then move, if it is not valid then dont
	 * move and return the location's character
	 * 
	 * @return character at location
	 */
	public char goSouth() {
    Map m = Map.getInstance();
		Point nextPoint = getLocation();
		char pointChar = '!';
		nextPoint.x = nextPoint.x + 1;
		pointChar = m.getCharAtLoc(nextPoint);
		if (pointChar == '!') {
			System.out.println("You cannot go that way.");
		} else if (pointChar != '!') {
			loc = nextPoint;
		}
		return m.getCharAtLoc(loc);
	}

	/*
	 * Checks if user is moving in a valid direction, first creates the new
	 * location/point based off current location gets the character at the new
	 * location, if it is a valid location then move, if it is not valid then dont
	 * move and return the location's character
	 * 
	 * @return character at location
	 */
	public char goEast() {
    Map m = Map.getInstance();
		Point nextPoint = getLocation();
		char pointChar = '!';
		nextPoint.y = nextPoint.y + 1;
		pointChar = m.getCharAtLoc(nextPoint);
		if (pointChar == '!') {
			System.out.println("You cannot go that way.");
		} else if (pointChar != '!') {
			loc = nextPoint;
		}
		return m.getCharAtLoc(loc);
	}

	/*
	 * Checks if user is moving in a valid direction, first creates the new
	 * location/point based off current location gets the character at the new
	 * location, if it is a valid location then move, if it is not valid then dont
	 * move and return the location's character
	 * 
	 * @return character at location
	 */
	public char goWest() {
    Map m = Map.getInstance();

		Point nextPoint = getLocation();
		char pointChar = '!';
		nextPoint.y = nextPoint.y - 1;
		pointChar = m.getCharAtLoc(nextPoint);
		if (pointChar == '!') {
			System.out.println("You cannot go that way.");
		} else if (pointChar != '!') {
			loc = nextPoint;
		}
		return m.getCharAtLoc(loc);
	}

	/*
	 * Gets how many pokemon the user has
	 * 
	 * @return int
	 */
	public int getNumPokemon() {
		return pokemon.size();
	}

	/*
	 * Heals all of the user's pokemon
	 */
	public void healAllPokemon() {
		for (int i = 0; i < pokemon.size(); i++) {
			Pokemon p = getPokemon(i);
			p.heal();
		}
	}

  /*
	 * Adds a random buff to all of the user's pokemon
	 */
  public void buffAllPokemon(){
    PokemonGenerator g = PokemonGenerator.getInstance();
    for(int i = 0; i < pokemon.size(); i++){
      Pokemon p = getPokemon(i);
      p = g.addRandomBuff(p);
      this.removePokemon(i);
		  pokemon.add(i,p);
    }
  }

  /*
	 * Adds a random debuff to user's pokemon
	 */
  public void debuffPokemon(int index){
    PokemonGenerator g = PokemonGenerator.getInstance();
    Pokemon p = getPokemon(index);
    p = g.addRandomDebuff(p);
    this.removePokemon(index);
		pokemon.add(index,p);
  }

  /*
	 * Getter for the specific Pokemon at the given index
	 * 
	 * @param int
	 * 
	 * @return object Pokemon
	 */
  public Pokemon getPokemon(int index){
    Pokemon p = pokemon.get(index);
		return p;
  }

	/*
	 * Gets the trainers pokemon, and outputs it into a list
	 * 
	 * @return String
	 */
	public String getPokemonList() {
		String pokemonList = "";
		for (int i = 0; i < pokemon.size(); i++) {
			Pokemon p = getPokemon(i);
			pokemonList = pokemonList + (i + 1) + ". " + p.getName() + " HP: " + p.getHp() + "/" + p.getMaxHp() + "\n";
		}
		return pokemonList;
	}

  /*
  *Removes the pokemon at the given index
  *
  *@param int
  *
  *@return object Pokemon
  */
  public Pokemon removePokemon(int index){
    Pokemon p = pokemon.remove(index);
    return p;
  }

	/*
	 * Takes name and health of entity and makes it into a string
	 * 
	 * @return String EX: "Ash HP: 25/25"
	 */
	public String toString() {
		Trainer t = this;
		String tName = t.getName() + " HP: " + t.getHp() + "/" + t.getMaxHp() + "\n";
		String tMoney = "Money: " + getMoney() + "\n";
		String tPotions = "Potions: " + getPotions() + "\n";
		String tPokeballs = "Poke Balls: " + getPokeballs() + "\n";
		String trainerString = tName + tMoney + tPotions + tPokeballs + "Pokemon\n-------\n" +getPokemonList();
		return trainerString;
	}
}