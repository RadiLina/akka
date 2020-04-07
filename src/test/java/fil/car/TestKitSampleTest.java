package fil.car;

import akka.testkit.javadsl.TestKit;
import fil.car.Actor.NodeClass;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.AbstractActor.Receive;
import akka.actor.AbstractActor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TestKitSampleTest {

  public static class NodeActor extends AbstractActor {
   // ActorRef target = null;

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
  }

  static ActorSystem system;

  @BeforeClass
  public static void setup() {
    system = ActorSystem.create();
  }

  @AfterClass
  public static void teardown() {
    TestKit.shutdownActorSystem(system);
    system = null;
  }

  @Test
  public void testIt() {
    /*
     * Wrap the whole test procedure within a testkit constructor
     * if you want to receive actor replies or use Within(), etc.
     */
    new TestKit(system) {
      {
    	  
    	  
    	  final ActorRef aRefNode1 = system.actorOf(Props.create(NodeActor.class));
    	  final TestKit child = new TestKit(system);
    	 
    	  //ajout du fils
    	  aRefNode1.tell(child.getRef(), null);
    	  
    	  //envoi du message
    	  aRefNode1.tell("hello",null);
    	  
    	  
    	  child.expectMsg("hello");
        

      }
    };
       
  }
}