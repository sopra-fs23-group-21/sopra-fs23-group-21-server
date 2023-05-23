# SoPra FS23 - Group 21 Server
## Introduction
Dou Dizhu (Fighting the Landlord) is one of the most popular card games in China. It is about the class struggle in the old society, which is known as Land Reform Movement. The game was originally popular in Hanyang District, Wuhan City, Hubei Province, China, and has gradually become popular all over the world. Due to the different historical and cultural backgrounds of various provinces and cities in China, different types of Dou Dizhu have been derived according to local characteristics, such as Jiujiang Dou Dizhu, Wuhan Dou Dizhu, and Ningbo Dou Dizhu, etc.

The art of Dou Dizhu may be easy to learn, but it requires a lot of strategic planning and mathematical reasoning skill to master it. Also, to win the game, the players also need to be collaborative and memorize the cards which have been put on the deck. The game is played by at least 3 players, using a deck of 54 cards (even the Joker cards), one of which is the landlord, and the other two are the farmers. The two sides play against each other, and the one who runs out of cards first wins.

Our poker game is a three-player game. To start the game, all three players must be online at the same time, enter the same game room, and click ready. If only one or two players are present, or if all three players are not online at the same time, the game cannot be played.

After finishing a game, it is best for all three players to either choose to play another round together or all choose to exit the room, instead of one player continuing while the other two choose to exit.

The rules of the game "Doudizhu" are relatively complicated: In short, this is a team-based game, with two farmers as one team and the landlord as the other team. Whoever plays all their cards first wins, and as long as one of the farmers plays all the cards first, the farmers win.

At the beginning of the game, the three players can compete to become the landlord. Each player is randomly dealt 17 cards, and an additional three cards are given to the player who successfully becomes the landlord. Therefore, each farmer has 17 cards, while the landlord has 20 cards and can play first.

The types of cards include single cards (A), double cards (AA), three cards with one (AAA+6), three cards with two (AAA+66), four cards (AAAA), consecutive cards (4-5-6-7-8), consecutive double cards (44-55-66), consecutive three cards (444-555), consecutive three cards with one (444-555-7-9), and the pair of jokers (red joker + black joker).

In "Doudizhu", the card values are as follows: 3<4<5<6<7<8<9<10<J<Q<K<A<2<RED JOKER<BLACK JOKER. The size relationship between card combinations is as follows: single cards(A), double cards(AA), three cards(AAA), three cards with one(222+6), three cards with two(JJJ+K), consecutive single cards(10-J-Q-K-A, note:at least 5 cards), consecutive double cards(JJ-QQ-KK,at least 3 cards combination), consecutive three cards(888-999, at least 2 card combination), consecutive three cards with one(QQQ-KKK-2-5) are all at the same level. This means that single cards can only be played against single cards, double cards can only be played against double cards, and other card combinations can only be compared within their respective card combinations. For example, if the landlord plays a 3, the farmers can play a 6, but not a 66. If the landlord plays 3-4-5-6-7, the farmers can play 4-5-6-7-8, but not any other card combinations.

Four cards is a kind is a special card combination that was not mentioned earlier, as it is higher than any other card combination. Four cards can be played against any other card combination. For example, 3333 is the lowest value of four cards, but it can beat 4-5-6-7-8 and KKK-AAA-4-7.

The most special card combination is the pair of jokers. The red joker and black joker can beat any card combination, including the highest four cards, which is 2222.

For more details on "Landlord," you can search for it on Wikipedia at https://en.wikipedia.org/wiki/Dou_dizhu.

In this group project, we hope to develop a website to let users play the Dou Dizhu together. The first step is to register an account. A registered user can join in the game with two other registered users. Also, the user can invite people to join the game. It would be a way for people to expand their social circle and enrich their amateur cultural life.


## Technologies
- SpringBoot
- WebSocket
- GitHub Actions
- Spring Data JPA
- H2 Database
- JUnit

## High-level Components
Most important components:
- [CardsController](https://github.com/sopra-fs23-group-21/sopra-fs23-group-21-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/controller/CardsController.java)
- [GameContext](https://github.com/sopra-fs23-group-21/sopra-fs23-group-21-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/core/GameContext.java)
- [PokerCombination](https://github.com/sopra-fs23-group-21/sopra-fs23-group-21-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/core/PokerCombination.java)
- [RoomSync](https://github.com/sopra-fs23-group-21/sopra-fs23-group-21-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/controller/RoomSync.java)

### CardsController
It is responsible for creating game rooms, adding users to game rooms, managing game actions such as playing cards, passing turns, and continuing the game. Its functionalities include:
- creates a new GameContext object and assigns a unique room code to it
- add a User to a Game Room
- removes the user from the specified game room identified by the roomCode
- call the continueGame() method of the game context, indicating that the current user wants to continue the game after passing a turn

### GameContext
The GameContext class is a data class that holds the state of the game. It contains fields such as userList (a list of users participating in the game), pokers (the deck of cards), code (a code associated with the game room), isGameOver (a flag indicating whether the game is over), now (the index of the current player), start (the index of the starting player), and gameStatus (the status of the game). Its functionalities include: 
- initializes the deck of cards by creating a list of Poker objects representing the cards
- synchronizes the game state with the connected users by sending the updated game context via WebSocket communication
- make it possible when a player wants to continue the game after skipping a turn
- prepare a player for the game by adding them to the user list. It checks if the game has already started or if the room is already full before adding the player

### PokerCombination
It represents a combination of poker cards with various types, such as one card, two cards, three cards, four cards, etc. Its functionalities include:
- initialize the combination type based on the number and arrangement of cards in the list
- compare two PokerCombination objects based on their combination types and card values
- check specific conditions for different combination types

### RoomSync
is responsible for handling WebSocket connections for room synchronization. It provides functionality to send and receive data related to game contexts between the server and connected clients. Its functionalities include:
- keep track of active WebSocket sessions
- responsible for synchronizing the room code
- handle WebSocket connections at the specified URL pattern

## Launch & Development


### Launch
Open the project and run the main function of `Application` class


You can use the local Gradle Wrapper to build the application.
-   macOS: `./gradlew`
-   Linux: `./gradlew`
-   Windows: `./gradlew.bat`

More Information about [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and [Gradle](https://gradle.org/docs/).

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

You can verify that the server is running by visiting `localhost:8080` in your browser.

### Test

```bash
./gradlew test
```

### Development Mode
You can start the backend in development mode, this will automatically trigger a new build and reload the application
once the content of a file has been changed.

Start two terminal windows and run:

`./gradlew build --continuous`

and in the other one:

`./gradlew bootRun`

If you want to avoid running all tests with every change, use the following command instead:

`./gradlew build --continuous -xtest`


## Roadmap
Potential improvements or extensions may include:
- In the game room, users can change the music and choose their favorite music as the background music
- The user can log in with the email. After passing the email verification, if the password is forgotten, the user can retrieve the password or change the password through the email.
- The game ranking feature can be added


## Authors & Acknowledgement
> Jing Cao, Weimin Yang, Zhi Wang, Xiong Li

## License
Licensed under Apache License Version 2.0
- See [License](LICENSE)
