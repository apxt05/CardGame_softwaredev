*CARD GAME SIMULATION README FILE*

This project is a card game simulation developed in Java language.
This document provides an overview of the project structure and instructions for running the code.


**REQUIREMENTS**
The project requires Java 8 or higher to run.
VSCode / Terminal

**How to run the code**

Using the terminal:
Navigate to the project directory
Run the following command:
java -jar cards.jar

      

**PACK FILE FORMAT**
1. Must contain 8n integers
2. One integer per line only
3. All integers are non-negative

Saved as testfile.txt

**Flow of game when you run the program**

Enter number of players (n): 4
Enter path to pack file: testfile.txt

player 2 initial hand 2 6 2 6
player 1 initial hand 1 5 1 5
player 3 initial hand 3 7 3 7
player 4 initial hand 4 8 4 8
Player 2 draws 2 and discards 6 | hand now: 2 2 6 2
Player 1 draws 1 and discards 5 | hand now: 1 1 5 1
Player 4 draws 4 and discards 8 | hand now: 4 4 8 4
Player 3 draws 3 and discards 7 | hand now: 3 3 7 3
Player 2 draws 6 and discards 6 | hand now: 2 2 2 6
Player 4 draws 8 and discards 8 | hand now: 4 4 4 8
Player 1 draws 5 and discards 5 | hand now: 1 1 1 5
Player 3 draws 7 and discards 7 | hand now: 3 3 3 7
Player 2 draws 2 and discards 6 | hand now: 2 2 2 2
player 2 wins
Player 2 wins
Game end


//ouput files are also generated in the folder

Deck output 
Player output


**Common mistakes to take note of**
1. "PACK FILE INVALID" = happens when test file is not at 8n integers
2. No terminal output after code runs= use a simpler test file to run the code
