/**
 * 
 */
package fil.car.Actor;

import java.util.ArrayList;
import java.util.List;


import akka.actor.AbstractActor;
import akka.actor.ActorRef;

/**
 * @author Lina RADI
 *
 */
public class NodeActor extends AbstractActor{

	//A boolean to make a difference between Tree & Graph implementation 
	int boolImpl;
	// List of the node's childs
    private List<ActorRef> node_childs = new ArrayList<>();
	
	//Method to propagate message to the node's childs
	public void messagePropagation(String message) {
		for (ActorRef child : node_childs) {
			//System.out.println("Actor " + self().toString()+ " to " + "Actor " + child.toString() +": "+ message);
			System.out.println(self().toString() + " to " + child.toString() +": "+ message);
			child.tell(message, null);
		}
		
	}


	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder()
				.match(String.class, string -> {
					//System.out.println("String: " + string);
					this.messagePropagation(string);
					
					
				})
				.match(Integer.class, i -> {
					System.out.println("int: " + i);
				})
				.match(NodeClass.class, nc -> {
					System.out.println(nc);
				})
				.match(ActorRef.class, aRef -> {
					//System.out.println(aRef);
					this.node_childs.add(aRef);
				})
				.build();
		
		
	}


	public List<ActorRef> getNode_childs() {
		return node_childs;
	}


	public void setNode_childs(List<ActorRef> node_childs) {
		this.node_childs = node_childs;
	}
	
	

}
