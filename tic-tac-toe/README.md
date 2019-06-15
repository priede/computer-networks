## Number Tic-Tac-Toe ##
# Introduction #
Number Tic-Tac-Toe is a variation of the classic with the exception of a little math twist added to the game. This makes the game more challenging, but also practices basic addition skills for those of use who need some refreshing.

# Object of the Game #
Be the first player to complete a row of numbers that equal a total of 15. You will play the game using a standard Tic Tac Toe board  (i.e. a grid with 9 equal spaces). One player uses even numbers between 1 and 9 (2,4,6, and 8) and the other player uses odd numbers to (1,3,5,7, and 9). The player with the odd numbers goes first.

# Playing the Game #
The first player writes an odd number in one of the spaces. The second player then plays an even number in an attempt to get three numbers in a row that equals 15. The rows may be vertical, horizontal, or diagonal. Each number can only be played once.

# Winning the Game #
Play continues until one player wins by totaling 15 in a line or all the spaces are filled.  Players swap even and odd numbers for the next match.

# Implementing the Game #
You will implement the game as a standard Client/Server model. The Server will be one of the players and obviously the Client (the user) will be the other player. For simplicity, you can assume initially that the Server will play the odd numbers and the Client will play the even numbers. Then you can alternate for each match thereafter.

Your implementation will have some constraints.

You will use TCP
You will pass the playing board (the 3x3 grid) as well as the new number and position chosen by the Server or Client. (I recommend using an object).
You will create your application protocol that is persistent, meaning that the client makes only one initial request to the server, and all other transmissions are done over the data socket. When a match concludes, the server will send a packet to ask the client if another match will be played, If so, a new match is started, if not, the session is terminated and a final tally of wins, loses and draws should be sent to the client before wrapping everything up.
Start early and design first before writing the code.
