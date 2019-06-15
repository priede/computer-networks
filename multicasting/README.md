# Multicasting Fun #
Overview: Now that you have mastered basic Java client/server networking, it’s time to design and implement your first (simple) peer to peer system. In this programming assignment, you will be writing a Multicast game for two players. You will also include a "chat room" for people who want to "kibitz" (i.e. chat about) how the game is progressing. However, the one catch is that there will be a "middleware" server that has the role of passing the group number for the game room and a separate group number for the chat room.

Details: This project philosophically is very straightforward. The application you are creating will have two components: the first is the game component itself and the second is the "chat" component.

# Part 1: The Game (15 points) #

The game must be played by two participants. The game you are implementing will be simple HIGH/LOW game that you implemented previously, but with some twists. You will have ONLY two players in the game. The first player will randomly choose a number to guess (between 1 and 50), then wait for the second player to join the game room. The second player will try to guess the random number with hints from the first player (too high or too low).  So in essence, when the game starts, the first player will take the role of the high/low server you implemented in the prior assignment and the second player will take the role of the client. You need to determine the protocol for transferring information from player 1 to player 2 via the multicast group. I will only run two players, so that will make is easier for you.

Your program will use a fixed multicast address and port number for the game room. Both participants must join the group in order to play the game. Remember, the first player to join the group should choose a random number that the second player will guess and then wait for the second player to join the group.  Then the game commences. After one of the players  guesses the correct answer, the players switch sides and the player that randomly chooses the number will now try to guess the mystery number while the guessing player will now randomly choose the number to be guessed. Do this until one or both decide to "quit", then both players leave the group. The key here is to properly design how the role play changes.

Now, the middleware server will actually be a broker of multicast group addresses and port numbers for clients that want to play the game. That means, the server will send a specific multicast address and port number to each pair of would be players, then waits for the next pair to connect. The players use this address to play the game.

Every IP multicast group has a group address. IP multicast provides only open groups: That is, it is not necessary for special conditions to be a member of a group in order to send/receive datagrams to the group. Multicast address are like IP addresses used for single hosts, and is written in the same way: A.B.C.D. Multicast addresses will never clash with host addresses because a portion of the IP address space is specifically reserved for multicast. This reserved range consists of addresses from 224.0.0.0 to 239.255.255.255. However, the multicast addresses from 224.0.0.0 to 224.0.0.255 are reserved for multicast routing information; Application programs should use multicast addresses outside this range. Your program will use a fixed multicast address and port number for the game.

You will need to run several threads for each player and the middleware server.  To run this, I will run one server and several clients/players (always in pairs). The server has obvious threads (think about this). You will need threads for the client/player: (1) a thread to read the guess from the terminal, (2) a  thread to read data from the other player via the multicast group and (3) a  thread to send to the other player via the multicast thread. With some cleverness, you can actually combine these very effectively, go for it.

# Part 2: The Game Chat Room (15 points) #

The second component, the "kibitz room" is open to any number of participants. It has its own Multicast IP address and its own port number. Anyone can join the group, leave the group, write to the group, etc. This group will also be able to "listen", but not "write" to the game multicast group. So, these participants will be receiving packets from two groups, but sending packets to only one group.

You will need threads for the chatter: (1) a  thread to read the comments from the terminal, (2) a receiver thread to read comments from the other chatters via the chat room multicast group, (3) a thread to send comments to the chatters via the chat room multicast group, Again, with some cleverness, you can actually combine these very effectively.

# Part 3: Multiple Game rooms (10 points) #

In this part, you will have multiple game room IP and port numbers (let's say 5 in total). In this way, you can actually permit up to 5 simultaneous games. This is not really hard to do (it's a simple adjustment to the middleware server). However, you need to handle how to pair the a chat room to the appropriate game room.

# Part 4: Allow the chatters to change chat rooms (10 points) #

In this part, a chatter may be bored with her current chat room/game room pair, so you will let her change rooms (if there is another room). Remember, the chat rooms are paired with game rooms.

For either part, you do not need to implement a GUI (Jframe), reading/writing to the command line is fine.
