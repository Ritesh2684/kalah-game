# kalah-service

This application implements Kalah Board Game for two players namely NORTH and SOUTH. The board has 6 small pits, called houses, on each side; and a big pit, called an end zone, at each end. The object of the game is to capture more seeds than one's opponent.

# Architecture

This is simple spring boot application. The application leverages Spring's Chain of Responsibility Framework using @Ordered annotation with the help of interface KalahRule. Each rule of the game has been implemented as a separate class.

Each turn of the player goes through the set of classes in the order marked with @Ordered annotation fulfilling each condition of the game.

Application also persists the state of the Kalah in Cache to enable interaction using different REST Endpoints ( due to time constraint, caching is implemented with simple HashMap, but this could be replaced with distributed Caching framework like Hazelcast , EhCache or persistence storage to enable application running on multiple machines )


# Technology Stack
* Java 8
* Spring Boot
* Lombok
* JUnit
* Tomcat 8
* Swagger


# How to build
mvn clean install


# How to run
java -jar target/kalah-service-0.0.1-SNAPSHOT.jar

Alternatively, if start git bash inside the folder kalah-service and execute below command
mvn spring-boot:run


# How To Play

Game is played by two players named as NORTH ( Pit 1-7 ) and SOUTH ( Pit 8-14 ).

Application exposes two REST End points to play the game, 

- /games :- to initialize the game with 6 stones in each pit except players home. Response for this Endpoint contains the id of the initiated game, using which   players can play turns.

- /games/{gameId}/pits/{pitId} :- Using this url, Players can take turn with the pitId they want to distribute stones from and game Id is the id received in  								      the url while initiating game above. Response of this url contains the state of the pits as Json along with gameId.

Application also logs the state of Kalah after each turn, on console, which also determines, 
- Which player should take the next turn
- State of the Game ( FINISHED or INPROGRESS )
- Winner of the Game

# Validations - Error Code : Error Message

Invalid input or turn taken results in bad request i.e. status code 400. Below are the validations,

1. Invalid pit id selected i.e. > 14 -  KSE-001 : Invalid Pit Selected
2. Turn not taken by expected player - KSE-002 : Invalid Player has taken the turn
3. Move made after game has finished - KSE-002 : Game has already Finished
4. Invalid Game Id provided - KSE-004 : Game does not exist


# kalah-Rules
1. At the beginning of the game, 6 stones are placed in each house.
2. Each player controls the six houses and their seeds on the player's side of the board. The player's score is the number of seeds in the store to their right.
3. Players take turns sowing their seeds. On a turn, the player removes all seeds from one of the houses under their control. Moving counter-clockwise, the player drops one seed in each house in turn, including the player's own store but not their opponent's.
4. If the last sown seed lands in an empty house owned by the player, and the opposite house contains seeds, both the last seed and the opposite seeds are captured and placed into the player's store.
5. If the last sown seed lands in the player's store, the player gets an additional move. There is no limit on the number of moves a player can make in their turn.
6. When one player no longer has any seeds in any of their houses, the game ends. The other player moves all remaining seeds to their store, and the player with the most seeds in their store wins.

# Improvements Possible

* Ideally the api to play the game should also accept playerId, to identify and validate whether
	- is the next url triggered by correct player
	- is the right pit id selected by right player
	
* Caching with simple Map could be replaced with distributed caching i.e. Hazelcast , EhCache or persistent storage to analyse the previous games.

	
	