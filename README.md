# simple-Robot-Game
This is a simple Robot Game with Java
Given a starting position [x,y] (0<x,y<9), initial direction faced (W, S, N, E) on 8 x 8 square board and sequence of actions for a robot, print the outcome; direction faced and position on the board.

# How to use?
1. open world class with intelliJ IDE
2. execute world class
3. input x in 1~9 (ex: 3) 
4. input y in 1~9 (ex: 2)
5. input direction in 'E', 'N', 'W', 'S' (ex: N)
6. input actions in the string only containing 'M', 'R', 'L' (ex: "M,M,M,R,L,M")
7. click the button
8. it will show the process of moving and the result position and direction in the table.

# The core function
The core function is in class planner and given x, y, direction and actions, it will plan the target position and direction.

