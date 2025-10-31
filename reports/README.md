# Software Development Card Game

***Candidate Names:***
Pheng Xuan Ting
Rumi Mansoubi

Multi-threaded card game where each player collects four cards of their preferred value.
Game uses threads to run concurrent player moves, and synchronisation to ensure thread-safe interactions.

**SPECIFICATION**

Requirements:
1. n = number of players (input)
2. Pack file with 8n non-negative integers
3. Draw card from left deck, discard on right deck (atomic)
4. Each player must have 4 cards in their hand at all times
5. Game ends when player collects 4 cards with the same value

**CODE DESIGN**

Classes
1. Card.java = immutable class that represents a card value
2. Deck.java = Represents a deck of cards, drawing and discarding of cards are programmed here
3. Player.java = Runnable class, player holds a deck with 4 cards, draws to left deck and discards to the right deck
4. GameController.java = Manages the game status and declares winning status
5. CardGame.java = Main class, initialise game, read input file, create player and deck, deal cards and threads.

File Input / Output
Pack file is read -> each player writes actions to their output file -> deck writes final state to output file


**GAME DESIGN**
1. User inputs number of players of their choice, and the file path to the pack file
2. Pack is validated 
3. Initial hand is dealt in round robin 
4. Player starts the threads (Check winning hand, draw card from left deck, discard card to right deck, log actions to output file)
5. Game stops only when player has all preferred cards (same values)
6. Terminate threads, winner status is written

**TESTS**


**CHALLENGES DURING THE PROJECT**


**CONCLUSION**