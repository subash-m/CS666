# Q-Learning based Behavior Adaptation in Multi-Robot Systems

## Abstract 

 - An approach to the problem of autonomous mobile garbage collecting and sorting robot using reinforcement learning.
 - Q-learning is one kind of the reinforcement learning algorithm, which we used for learning the actions based on rewards.

## Inspiration

 - <b>Google DeepMind</b> created an artificial intelligence program using deep reinforcement learning that plays Atari games and improves itself to a superhuman level.
 - The most important thing to know is that all that agent is given is sensory inputs(what we see on the screen) and it was ordered to maximize the score on the screen.
 - <b>No Domain Knowledge</b> is involved! This means that the algorithm doesn't know the concept of ball or what the controls exactly do.

| [![Google Deepmind's Deep Q-learning playing Atari Breakout](/Images/8.PNG "Click to watch video!")](https://www.youtube.com/watch?v=V1eYniJ0Rnk) |
|:---:|
| Google DeepMind's Deep Q-learning playing Atari Breakout |

## Problem Statement

Autonomous garbage collection and garbage segregation, where the environment has different garbages scattered around, the robot should move around this environment and pick this objects(garbage) and drop it into appropriate bins, while keeping energy in check, if energy is getting lower than threshold, the robot should go to charing station and recharge its energy.

## Robot Design

| <img src="/Images/1.jpg" width="250px" alt="Raspberry Pi mounted on Legokit (Front_View)">  | <img src="/Images/2.jpg" width="622px" alt="Raspberry Pi mounted on Legokit (Side_View)"> |
|:---:|:---:|
| Raspberry Pi mounted on Legokit (Front_View) | Raspberry Pi mounted on Legokit (Side_View) |

## Areana Design

| <img src="/Images/6.jpg" width="436px" alt="Arena with garbage scattered in the arena">  | <img src="/Images/5.jpg" width="436px" alt="Arena with few garbage sorted to appropriate bins"> |
|:---:|:---:|
| Arena with garbage scattered in the arena | Arena with few garbage sorted to appropriate bins |

## Project Demo Video

| [![Project Demo](/Images/7.PNG "Click to watch video!")](https://www.youtube.com/watch?v=rCjFV8R2Cls) |
|:---:|
| Project Demo - Garbage Collector using Q-learning |

## Compile and execute:

### To run in bluetooth: (Connection from Lego NXT to Computer / Raspberry Pi)

1) Change ./QGarbagePC/GarbageAgentPC.java line number: 31,  
   replace Consts.USB with Consts.BT
2) Change ./QGarbage/GarbageAgentNXT.java line number: 49,  
   replace Button.ID_RIGHT with Button.ID_LEFTasdfsadfaf
3) Change ./QGarbage/makefile line number: 11,  
   replace -u with -b.

### To run in USB: (Connection from Lego NXT to Computer / Raspberry Pi)
   Default code is written for USB. No change is required.

### To compile the program

Execute Lego NXJ program first.  
```
# Goto QGarbage folder  
cd QGarbage
```
Position the robot towards any side of the arena to calibrate the directions.
```
# Execute makefile or execute the commands inside the makefile.  
make  
```
Execute Lego NXT PC program  
```
# Goto QGarbagePC folder
cd QGarbagePC

#Execute the makefile or execute the commands inside the makefile.
make
```


To run Multi Robot system.
  * Copy the same code to all the computers.
  * Edit the ./QGarbagePC/Consts.java file
    * Line number: 23
    * Give unique numbers to each system in the range (1-254)
  * All the computers should be connected to the same network.
  * I would recommend to test with a dedicated router.
    * Since, we use multicast broadcast service, the connection is not reliable for communication, if it is already managing some traffic.
