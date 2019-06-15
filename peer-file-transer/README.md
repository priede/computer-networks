# Peer 2 Peer File Transfer #

# Introduction: #

Now that you have mastered peer2peer gaming, you will tackle peer2peer file transfers. This project will be based on the operation of a Simplified version of Napster we discussed in class. Peers will register with a broker files that they wish to share with members of the community, the broker will provide requesting peers the IP:Port# of the such peers. NO FILES will actually be located with the broker, only IP:port#:filename tuples.

# Broker: #

The broker is responsible for registering peers (their IP:PORT#:Filename tuple), and providing this information to a requesting peer. If a peer requests a file that is not in the data structure holding the tuples, then the broker must inform the requesting peer that the file does not exist in the community.

# Peers: #

Peers register a filename(s) and its IP and port number with the broker. Then, when  peer searches for a file, it contacts the broker for the host peer's IP and Port number. After receiving this pair, the requesting peer directly (not using multicasting) the host peer to transfer the file.

