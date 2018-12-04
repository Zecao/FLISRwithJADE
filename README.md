# FLISRwithJADE
A Proof of Concept fo a FLISR (Fault Localization, Isolation and Service Restoration) using JADE (JAVA Agent DEvelopment Framework)

Each Recloser will implement an agent which will be able to monitor the environment, communicate with others Reclosers agents and take decisions together. 

*Brief description of the main agents behaviours*

*Open:* simply behavior that’s simply opens or close a recloser. File: BAbre.class

*Open Short Circuit:* opens a recloser due a short circuit. File: BAbreCurto.class 

*Monitoring Grid:* a cyclic behavior that checks the current of the closed Reclosers. File: BMonitoraCorrente.class

*Monitoring Messages:* a cyclic behavior that checks the messages between the Agents and changes behaviors accordingly. 
File: BRecebeMsg.class

*Warn Neighbors:*  There are 3 behaviors that are used to warn the neighbors when a Recloser open and needs to have this load supplied
by others Reclosers normally opened. Class files: BAvisaJusanteNFs, BAvisaMontanteNF and BAvisaVizinhosNAs

*Decision Making:* This is the decision making behavior that chooses how many reclosers will close to solve the fault. 
As the decision making theory is beyond the objective of this work, just a simple one was implemented: the behavior waits for
messages from all Agents that can share the load of the recloser that just opened. If one or more reclosers can share the load,
this behavior commands all the Reclosers to close. Class file: BTomadaDecisao.class
