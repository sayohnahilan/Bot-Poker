import java.util.Scanner;

public class Main {
  
  public static void main(String[] args) {
    
    //variables needed to code//
	  Scanner in = new Scanner(System.in);
	  Short userchoice = 1;
	  boolean valid = false;
	  int numBots = 0;
	  String strInput = "";
	  int playerMoney = 0;
	  int startmoney = 0;
	  int countercounter = 0;
	  
	  //getting user inputs for game//
	  System.out.println("How many bot oponents? (1-9)");
	  while (valid==false) {
		  strInput = in.nextLine();
		  try {
			  numBots = Integer.valueOf(strInput);
			  valid = true;
		  }
		  catch (Exception e) {
			  System.out.println("Invalid input. Try again");
			  valid = false;
		  }
	  }
	  if (numBots < 1 || numBots > 9){
	    System.out.println("You are trolling. Goodbye.");
	   return;
	  }
	  System.out.println("How much start money?");
	  valid = false;
	  strInput = "";
	  while (valid==false) {
		  strInput = in.nextLine();
		  try {
			  playerMoney = Integer.valueOf(strInput);
			  valid = true;
		  }
		  catch (Exception e) {
			  System.out.println("Invalid input. Try again");
			  valid = false;
		  }
	  }
	  System.out.println("");
	  startmoney = playerMoney;
	  
	  //main code//
	  while(userchoice == 1){
	  
	  //create a deck//
	  deck mainDeck = new deck();
	  mainDeck.createDeck();
	  mainDeck.shuffleDeck();
	  
	  String[] hand = new String[5];
	  
	  //deal hands//
	  String[] types = new String[numBots + 1];
	  String[][] allHands = new String[numBots + 1][5];
	  for (int j=0;j<types.length;j++) {
		  hand = mainDeck.dealHand();
		  hand = arrangeHand(hand);
		  allHands[j] = hand;
		  if (j == 0){
		  System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nYour hand: " + printHand(hand));
		  }
		  types[j] = evaluateHand(hand);
	  }
	  
	  //getting bet for hand//
	  System.out.println("How much would you like to bet?");
	  valid = false;
	  int betAmount = 0;
	  strInput = "";
	  while (valid==false) {
		  strInput = in.nextLine();
		  try {
			  betAmount = Integer.valueOf(strInput);
			  valid = true;
		  }
		  catch (Exception e) {
			  System.out.println("Invalid input. Try again");
			  valid = false;
		  }
	  }
	  System.out.println("");
	  
	  //printing other bots' hands//
	  for (int i=1;i<types.length;i++) {
		  System.out.println("Bot " + i + ": " + printHand(allHands[i]));
	  }
	  
	  //solve who wins//
	  int[] winners = bestHand(types);
	  if (winners[0]==1) {
	    if (String.valueOf(winners[1]).equals("0")){
		  System.out.println("You win with a " + types[winners[1]].substring(0, types[winners[1]].indexOf(", ")) + " (" + types[winners[1]].substring(types[winners[1]].indexOf(", ")+2) + ")");
	      playerMoney += betAmount;
	    } else {
	      System.out.println("\nBot " + String.valueOf(winners[1]) + " wins with a " + types[winners[1]].substring(0, types[winners[1]].indexOf(", ")) + " (" + types[winners[1]].substring(types[winners[1]].indexOf(", ")+2) + ")");
		  playerMoney -= betAmount;
	    }
	  }
	  else {
	    countercounter = 0;
		  System.out.print("Players ");
		  for (int k=0;k<winners[0]-1;k++) {
			  System.out.print(String.valueOf(winners[k+1] + 1) + ", ");
			countercounter += 1;
		  }
		  System.out.println(String.valueOf(winners[winners[0]] + 1) + " win with a " + types[winners[1]].substring(0, types[winners[1]].indexOf(", ")) + " (" + types[winners[1]].substring(types[winners[1]].indexOf(", ")+2) + ")");
		  playerMoney += (int) betAmount / countercounter;
	  }
	  
	  //giving back money//
	  System.out.println("You now have " + playerMoney + " dollars.\n\nEnter 1 to play again or 0 to leave.");
	  valid = false;
	  userchoice = 0;
	  strInput = "";
	  while (valid==false) {
		  strInput = in.nextLine();
		  try {
			  userchoice = Short.valueOf(strInput);
			  valid = true;
		  }
		  catch (Exception e) {
			  System.out.println("Invalid input. Try again");
			  valid = false;
		  }
	  }
	  System.out.println("");
	  } 
	  
	  //exit message//
	  if (playerMoney >= startmoney){
	    System.out.println("\nYou gained " + (playerMoney - startmoney) + " dollars.");
	  }else{
	    System.out.println("\nYou lost " + (startmoney - playerMoney) + " dollars.");
	  }
  }
  
  //methods for code//
  public static String[] faces = {"Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace"};
  static String[] suits = {"Hearts","Diamonds","Clubs","Spades"};
  static String[] hands = {"High card", "Pair", "Two pair", "Three of a kind", "Straight", "Flush", "Full house", "Four of a kind", "Straight Flush", "Royal Flush"};
  
  public static int findString(String string, String[] array) {
    int count = -1;
    for (int i=0;i<array.length;i++) {
      if (array[i].equals(string)) {
        count = i;
      }
    }
    return count;
  }
  
  public static String getFace(String card) {
    return card.substring(0, card.indexOf(' '));
  }
  
  public static String getSuit(String card) {
    String a = card.substring(card.indexOf(' ') + 1);
    return a.substring(a.indexOf(' ') + 1);
  }
  
  public static class deck {
    String[] allcards = new String[52];
    int firstCard = 0;
    
  public void createDeck() {
      int count = 0;
      for (int i=0; i<4; i++) {
        for (int k=0; k<13; k++) {
          allcards[count] = faces[k] + " of " + suits[i];
          count++;
        }
      }
    }
    
  public void shuffleDeck() {
      for (int i=0; i<52; i++) {
        int n = (int) (Math.random()*allcards.length);
        String temp = allcards[i];
        allcards[i] = allcards[n];
        allcards[n] = temp;
      }
    }
    
  public String[] dealHand() {
    	String[] hand = {allcards[firstCard], allcards[firstCard+1], allcards[firstCard+2], allcards[firstCard+3], allcards[firstCard+4]};
        firstCard+=5;
        return hand;
    }
  }
  
  public static String[] arrangeHand(String[] hand) {
    int count = 0;
    String[] temp = new String[hand.length];
    for (int i=0; i<13; i++) {
      for (int k=0; k<hand.length; k++) {
        if (hand[k].indexOf(faces[i])!=-1) {
          temp[count] = hand[k];
          count++;
        }
      }
    }
    return temp;
  }
  
  public static String evaluateHand(String[] hand) {
	  String type = "";
	  
	  boolean flush = true, straight = true;
	  for (int i=1;i<hand.length;i++) {
		  if (!getSuit(hand[i]).equals(getSuit(hand[i-1]))) {
			  flush = false;
		  }
		  if (findString(getFace(hand[i]), faces)!=(findString(getFace(hand[i-1]), faces) + 1)) {
			  straight = false;
		  }
	  }
	  if (getFace(hand[0]).equals("Two")&&getFace(hand[1]).equals("Three")&&getFace(hand[2]).equals("Four")&&getFace(hand[3]).equals("Five")&&getFace(hand[4]).equals("Ace")) {
		  straight = true;
		  hand[0] = "Ace";
		  hand[1] = "Two";
		  hand[2] = "Three";
		  hand[3] = "Four";
		  hand[4] = "Five";
	  }
	  
	  int[] groups = new int[3];
	  String highestDouble = "";
	  int count = 0;
	  for (int i=1;i<hand.length;i++) {
		  if (getFace(hand[i]).equals(getFace(hand[i-1]))) {
			  count ++;
			  if (i==hand.length-1) {
				  groups[count-1] ++;
				  if (count==1) {
					  highestDouble = getFace(hand[i-1]);
				  }  
			  }
		  }
		  else {
			  if (count>0) {
				  groups[count-1] ++;
				  if (count==1) {
					  highestDouble = getFace(hand[i-1]);
				  }
			  }
			  count = 0;
		  }
	  }
	  if (straight&&flush) {
		  if (getFace(hand[0]).equals("Ten")) {
			  type = "Royal Flush, Ace";
		  }
		  else {
			  type = "Straight Flush, " + getFace(hand[4]);
		  }
	  }
	  else if (flush) {
		  type = "Flush, " + getFace(hand[4]);
	  }
	  else if (straight) {
		  type = "Straight, " + getFace(hand[4]);
	  }
	  else if (groups[2]==1){
		  type = "Four of a kind, " + getFace(hand[3]);
	  }
	  else if (groups[1]==1) {
		  if (groups[0]==1) {
			  type = "Full House, " + getFace(hand[2]);
		  }
		  else {
			  type = "Three of a kind, " + getFace(hand[2]);
		  }
	  }
	  else if (groups[0]==2) {
		  type = "Two pair, " + highestDouble;
	  }
	  else if (groups[0]==1) {
		  type = "Pair, " + highestDouble;
	  }
	  else {
		  type = "High card, " + getFace(hand[4]);
	  }
	  
	  return type;
  }
  
  public static String printHand(String[] hand) {
	  String handString = "";
	  for (int i=0;i<hand.length-1;i++) {
		  handString = handString + hand[i] + ", ";
	  }
	  return handString + hand[hand.length-1];
  }
  
  public static int[] bestHand(String[] handsList) {
	  String highest = handsList[0];
	  int[] players = new int[handsList.length + 1];
	  players[1] = 0;
	  players[0] = 1;
	  for (int i=1;i<handsList.length;i++) {
		  if (findString(handsList[i].substring(0,handsList[i].indexOf(", ")), hands) > findString(highest.substring(0,highest.indexOf(", ")), hands)) {
			  highest = handsList[i];
			  players[1] = i;
			  players[0] = 1;
		  }
		  else if (findString(handsList[i].substring(0,handsList[i].indexOf(", ")), hands) == findString(highest.substring(0,highest.indexOf(", ")), hands)){
			  if (findString(handsList[i].substring(handsList[i].indexOf(", ") + 2), faces) > findString(highest.substring(highest.indexOf(", ") + 2), faces)) {
				  highest = handsList[i];
				  players[1] = i;
				  players[0] = 1;
			  }
			  else if (findString(handsList[i].substring(handsList[i].indexOf(", ") + 2), faces) == findString(highest.substring(highest.indexOf(", ") + 2), faces)){
				  players[0] = players[0] + 1;
				  players[players[0]] = i;
			  }
		  }
	  }
	  return players;
  }
  
}
