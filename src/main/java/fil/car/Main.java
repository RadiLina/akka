/**
 * 
 */
package fil.car;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import fil.car.Actor.NodeActor;
import fil.car.Actor.NodeClass;
import fil.car.Graph.Graph;



/**
 * @author Lina RADI
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ActorSystem system = ActorSystem.create();
		
		/**  Tree implementation **/
		System.out.println("Tree implementation");
		/**
		 * Creating nodes
		 */
		ActorRef aRefNode1= system.actorOf(Props.create(NodeActor.class));
		
		ActorRef aRefNode2= system.actorOf(Props.create(NodeActor.class));
		ActorRef aRefNode5= system.actorOf(Props.create(NodeActor.class));
		
		ActorRef aRefNode3= system.actorOf(Props.create(NodeActor.class));
		ActorRef aRefNode4= system.actorOf(Props.create(NodeActor.class));
		
		ActorRef aRefNode6= system.actorOf(Props.create(NodeActor.class));
		
		/**
		 * Assigning childs 
		 **/
		//assigning childs to node 1
			aRefNode1.tell(aRefNode2, aRefNode1);
			aRefNode1.tell(aRefNode5, aRefNode1);
		    
		//assigning childs to node 2
			aRefNode2.tell(aRefNode3, aRefNode2);
			aRefNode2.tell(aRefNode4, aRefNode2);
		
		//assigning childs to node 5
			aRefNode5.tell(aRefNode6, aRefNode5);
			
		 //send message 
		 aRefNode1.tell("Je vais bien", aRefNode1);
		 
		
		 try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
				/**  Graph implementation **/
		System.out.println("Graph implementation");
		Graph graph = new Graph("src/main/java/fil/car/Configuration/configuration.txt");
		//send message 
		//graph.getNodesOfGraph().get("1").tell("Je vais bcp mieux", null);
		
		
		String input = null;
		System.out.println("**** Please enter 'stop' to stop the implementation **** ");
		String returned =null;
		
		do {
			System.out.print("Please choose an actor number : ");
			Scanner sc = new Scanner(System.in);
			input = sc.nextLine();
		    returned =analyseInput(input); 
			switch(returned) {
			  case "isInteger":
				  graph.getNodesOfGraph().get(input).tell("Je vais bcp mieux", null);
				  try {
					  Thread.sleep(10);
				  } catch (InterruptedException e) {
					  
				  }
			    break;
			  case "stop":
				  sc.close();
				  System.exit(0);
			    break;
			  default:
			    // code block
				  	  
			}
				
	     } while(!returned.equals("stop"));
		
	}

	/**
	 * method to interpret the user's input command
	 * @param input
	 * @return : command 
	 */
	private static  String analyseInput(String input) {
		switch(input) {
		case "stop":
			return "stop";
		case "delete":
			//a function or other ...
			break;
		case "modify":
			//a function or other ...
			break;
		default:
			try {
				Integer.parseInt(input);
				return "isInteger";
			} catch (NumberFormatException e) {
				return "notAnInteger";
			}
		}
		return input;	
	}	
		
}
