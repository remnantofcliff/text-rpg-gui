package entity.events;

import entity.Event;
import main.Game;

/**
 * Event for new game.
 */
public class NewGame extends Event {

  public NewGame() {
    name = "New Game";
  }

  @Override
  public void event() {
    var game = Game.getInstance();
    game.addText("You wake up... ");
    game.addText("The last thing you remember is that you were captured by the guards of Nilium. ");
    game.addText("Your memory otherwise feels hazy.");
    game.clear();
    game.addText("You are in a dark room. ");
    game.addText("You can't really see anything.");
    game.clear();
    game.addText("It's cold and damp in the room. ");
    game.addText("You are wearing rags that you don't recognize. ");
    game.addText("None of your possessions are on you. ");
    game.addText("There is a small amount of light coming through one part of the door.");
    game.clear();
    game.addText("As you get closer to the light, ");
    game.addText("you realize that it is shining through the decrepit doorframes. ");
    game.addText("You open the door and see an outline of a person.");
    game.clear();
    game.addText("The person begins talking: ");
    game.addText("\"Oh, you woke up already..? ");
    game.addText("I've been keeping watch on you while you were out. ");
    game.addText("You are just like the others... ");
    game.addText("Thrown in here, and left to die...\"");
    game.clear();
    game.addText("\"Be careful out there\" ");
    game.addText("\nThere's nothing worse than seeing the people I save just die a gruelsome death.., ");
    game.addText("as many have...\"");
    game.clear();
    game.addText("The man lights up a candle. ");
    game.addText("You can now see his face. ");
    game.addText("It is scarred and he has a stoic expression.");
    game.clear();
    game.addText("\"You need to leave. ");
    game.addText("I don't have anything else for you.\"");
    game.clear();
    game.addText("You can't quite tell how old the man is due to the scars and damage he's suffered. ");
    game.addText("He shows you the way to the door that leads you to the outside.");
    game.clear();
    game.addText("He opens the door and says in a calming manner: ");
    game.addText("\"There might be no getting out. ");
    game.addText("Many have tried, ");
    game.addText("and just as many have failed.\"");
    game.clear();
    game.addText("\"This place is where criminals, fugitives, and heretics of Nilium are left to die.\"");
    game.clear();
    game.addText("You see a large cave out of the door. ");
    game.addText("The place is lit up by torches and you see a few people standing around.");
    game.clear();
    game.addText("\"This is our little community here in the western corner of Dereliquerat. ");
    game.addText("All these people are too stubborn to die. ");
    game.addText("However, ");
    game.addText("we can't sustain any more people living here. ");
    game.addText("Crops don't do too well down here...\", he finishes with a smirk.");
    game.clear();
    game.addText("\"See ya.\", He says with a smile and, in one swift motion, ");
    game.addText("waves goodbye, ");
    game.addText("turns around, ");
    game.addText("and closes the door.");
    game.clear();
    game.addText("You can't remember your name... ");
    game.addText("How should you introduce yourself? ");
    new NameChange().event();
    new SelectDifficulty().event();
  }
  
}
