import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.io.FileNotFoundException;

public class PokemonGenerator{
  /* Instance Variables */
  private HashMap<String,String> pokemon = new HashMap<String,String>();
  private static PokemonGenerator instance = null;

  /* Constructor */
  private PokemonGenerator(){
    Scanner read = null;
    try{
      File list = new File("PokemonList.txt");
      read = new Scanner(list);
    }catch(FileNotFoundException e){
      System.out.println("File not found");
    }

    while(read.hasNextLine()){
      try{
        String line = read.nextLine();
        line = line.replaceAll("\\s","");
        
        pokemon.put(line.substring(0,line.indexOf(",")), line.substring(line.indexOf(",") + 1, line.length()));
        
      }catch(NoSuchElementException nse){
        System.out.println("No such element exists"); 
      }
    }
  }
  
  /*
   * Checks if the PokemonGenerator has already been created, 
   * if it hasn't, then it will make one. It is already has, then
   * it will return the original instance.
   */
  public static PokemonGenerator getInstance(){
    if(instance == null){
      instance = new PokemonGenerator();
    }
    return instance;
  }

  /*
   * Randomly picks a pokemon from the HashMap, and then
   * constructs a pokemon of the corresponding elemental 
   * base type. 
   * @param int level
   * @return Pokemon 
   */
  public Pokemon generateRandomPokemon(int level){
    
    int pokeHp = (int)(Math.random() * 3 + 22); 

    // Array of Keys (names) 
    Object[] pokeKeys = pokemon.keySet().toArray(); 
    int randInt = (int)(Math.random() * pokeKeys.length); 
    String randPoke = (String)pokeKeys[randInt]; 
    Pokemon p = null;

    if(pokemon.get(randPoke).equalsIgnoreCase("Fire")){
      p = new Fire(randPoke, pokeHp, pokeHp); 
    }else if(pokemon.get(randPoke).equalsIgnoreCase("Water")){
      p = new Water(randPoke, pokeHp, pokeHp); 
    }else if(pokemon.get(randPoke).equalsIgnoreCase("Grass")){
      p = new Grass(randPoke, pokeHp, pokeHp); 
    }
    
    if(level > 1){
      for(int i = 1; i < level; i++){
        p = addRandomBuff(p);
      }
    } 
    return p; 
  }

  /*
   * Passes in a string with the name of a pokemon and 
   * constructs an object of the correct corresponding type.
   * @param String pokemon name
   * @return Pokemon  
   */
  public Pokemon getPokemon(String name){
    // Health is random number from 22 to 24?
    int pokeHp = (int)(Math.random() * 3 + 22);
    Pokemon p = null;
    if(pokemon.get(name).equalsIgnoreCase("Fire")){
      p = new Fire(name, pokeHp, pokeHp); 
    }else if(pokemon.get(name).equalsIgnoreCase("Water")){
      p = new Water(name, pokeHp, pokeHp); 
    }else if(pokemon.get(name).equalsIgnoreCase("Grass")){
      p = new Grass(name, pokeHp, pokeHp); 
    }
    return p;
  }

  /*
   * Randomly chooses a buff to apply to the pokemon 
   * @param Pokemon 
   * @return Pokemon 
   */
  public Pokemon addRandomBuff(Pokemon p){
    int randBuff = (int)(Math.random()*2 + 1);
    if(randBuff == 1){
      p = new AttackUp(p);
    }else if(randBuff == 2){
      p = new HpUp(p);
    }
    return p; 
  } 

  /*
   * Randomly chooses a debuff to apply to the pokemon 
   * @param Pokemon 
   * @return Pokemon 
   */
  public Pokemon addRandomDebuff(Pokemon p){
    int randDebuff = (int)(Math.random()*2 + 1);
    if(randDebuff == 1){
      p = new AttackDown(p);
    }else if(randDebuff == 2){
      p = new HpDown(p);
    }
    return p; 
  } 
}