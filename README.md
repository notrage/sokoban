# Sokoban
This is an university project (in collaboration with [Thomas Civade](https://github.com/Luminosaa/)) that aim to implement a sokoban game using our skills in object-oriented programming, human-machine interface and artificial intelligence.
## Installation
1. Clone this git repository :
```bash
git clone git@github.com:notrage/sokoban.git
```
2. Navigate in the project directory :
```bash
cd sokoban
```
3. Compile the project :
```bash
javac -cp :./src/:./res/ ./src/Sokoban.java
```
4. Run the Sokoban :
```bash
java -cp :./src/:./res/ Sokoban
```
## In Game Keybinds
- Quit : **A** / **Q**
- Fullsceen mode : **Esc**
- Cancel the last move : **Delete** / **Backspace**
- Redo the last canceled move : **Enter**
- Toggle animation : **W**
- Toggle naive Depth-First Search (DFS) AI : **T**
- Toggle AI minimizing the number of time a box moves (A* algorithm) : **Y**
- Toggle AI minimizing the number of times the player moves (A* algorithm) : **U**
- Mark the current case : **M**
- Remove all field marks : **R**
- Display all the boxs positions in the console : **C**
- Toggle teleportation of the player when using AI mode : **J**
- Go to the next level : **N**
## Notes
- An AI does not automatically move to the next level so that you can observe the result, so use N
- When you change levels, remember to toggle off/on the AI to restart it
- Once an AI is launched, you can't interrupt it until it finds a solution (or lack thereof), you can toggle the display of all moves while they are being executed by the AI
