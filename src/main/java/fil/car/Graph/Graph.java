/**
 * 
 */
package fil.car.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import fil.car.Actor.NodeActor;
import fil.car.Actor.NodeClass;



/**
 * @author Lina RADI
 *
 */
public class Graph {
	ActorSystem system = ActorSystem.create();
	
	//a map for the actors ref of the graph 
	private Map<String, ActorRef> NodesOfGraph = new HashMap<>();
	// list of nodes
	private List<NodeClass> nodes = new ArrayList<>();
	
		public Graph(String configurationFile) {
				this.intialiseNodes(configurationFile);
				this.creatingActorsReferences();
				this.assigningChilds();
		}


		/**
		 * Method to initialise the graph nodes with the configuration file
		 * @param configurationFile
		 */
		private void intialiseNodes(String configurationFile) {
			List<String> lines;
			
	        Path path = Paths.get(configurationFile); 
	        // normalize the path 
	        Path normalizedPath = path.normalize();
			
			try {
				lines = Files.readAllLines(normalizedPath);
				for(String lineContent:lines){
					if (!lineContent.startsWith("Begin")){
						String[] line = lineContent.split(";");
						if (line.length>1) {
							nodes.add(new NodeClass (line[0], line[1].split(",")));
						}else {
							nodes.add(new NodeClass (line[0]));
						}	
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		/**
		 * Method to create the actors references 
		 */
		public void creatingActorsReferences() {
			if (!nodes.isEmpty()) {
				for(NodeClass node : nodes ) {
					ActorRef nodeRef= system.actorOf(Props.create(NodeActor.class),node.getNom());
				    this.NodesOfGraph.put(node.getNom(), nodeRef);
				}
			}else {
				System.out.println("Empty list of nodes");
			}
		
		}
		
		/**
		 * Method to assign childs to each ActorRef Node of the NodesOfGraph map
		 */
		public void assigningChilds() {
		      Set<Entry<String, ActorRef>> setHm = NodesOfGraph.entrySet();
		      Iterator<Entry<String, ActorRef>> it = setHm.iterator();
		      while(it.hasNext()){
		    	  Entry<String, ActorRef> e = it.next();
		    	  // get ActorRef childs to add them to THIS ActorRef 
		    	  List<ActorRef> node_childs = this.getChilds(e.getKey());
		    	  for (ActorRef child: node_childs) {
		    		  //using the tell method to add an ActorRef node to his parent ActorRef
		    		  e.getValue().tell(child, e.getValue()); // e.getValue returns an ActorRef 
		    	  }
		    	  
		      }
		      
		}
		            
		
		/**
		 * Method to get a list of an ActorRef childs
		 * @param key : name of an actor
		 * @return : List<ActorRef> childs
		 */
		private List<ActorRef>  getChilds(String key) {
			List<ActorRef> node_childs = new ArrayList<>();
			List<String> childsNames = new ArrayList<>();
			
			for (NodeClass node: nodes) {
				if(node.getNom()==key) {
					if(node.getNoeuds_fils()!=null) {
						for (int i=0; i< node.getNoeuds_fils().length; i++) {
							childsNames.add(node.getNoeuds_fils()[i]);
						}
					}
				}
			}
			
			for (int i=0; i<childsNames.size(); i++) {
				node_childs.add(NodesOfGraph.get(childsNames.get(i)));	
			}
			return node_childs;
		}
			


		public Map<String, ActorRef> getNodesOfGraph() {
			return NodesOfGraph;
		}


		public void setNodesOfGraph(Map<String, ActorRef> nodesOfGraph) {
			NodesOfGraph = nodesOfGraph;
		}


		public List<NodeClass> getNodes() {
			return nodes;
		}


		public void setNodes(List<NodeClass> nodes) {
			this.nodes = nodes;
		}

				
				
}