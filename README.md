# Garbage-Collector
Garbage Collector using Q-Learning

Compile:
To run in bluetooth: (Connection from Lego NXT to Computer / Raspberry Pi)
1) Change ./QGarbagePC/GarbageAgentPC.java line number: 31, 
   replace Consts.USB with Consts.BT
2) Change ./QGarbage/GarbageAgentNXT.java line number: 49, 
   replace Button.ID_RIGHT with Button.ID_LEFTasdfsadfaf
3) Change ./QGarbage/makefile line number: 11, 
   replace -u with -b.

To run in USB: (Connection from Lego NXT to Computer / Raspberry Pi)
   Default code is written for USB. No change is required.

To compile the program,
   1) Execute Lego NXJ program first.
      a) Goto QGarbage folder
         *  cd QGarbage
      b) execute make * 
