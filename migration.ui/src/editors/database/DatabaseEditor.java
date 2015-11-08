package editors.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.gef4.fx.nodes.ScrollPaneEx;
import org.eclipse.gef4.graph.Edge;
import org.eclipse.gef4.graph.Edge.Builder;
import org.eclipse.gef4.graph.Graph;
import org.eclipse.gef4.graph.Node;
import org.eclipse.gef4.layout.algorithms.GridLayoutAlgorithm;
import org.eclipse.gef4.layout.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef4.mvc.fx.domain.FXDomain;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.ContentModel;
import org.eclipse.gef4.mvc.viewer.IViewer;
import org.eclipse.gef4.zest.fx.ZestFxModule;
import org.eclipse.gef4.zest.fx.ZestProperties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import javafx.scene.layout.AnchorPane;

public class DatabaseEditor {
	
	protected FXDomain domain;
	protected IViewer viewer;
	protected Graph graph;
	
	@SuppressWarnings("restriction")
	public DatabaseEditor(AnchorPane pane){
		Injector injector = Guice.createInjector(createModule());
		domain = injector.getInstance(FXDomain.class);
		viewer = domain.getAdapter(IViewer.class);
		
		ScrollPaneEx fxpane = ((FXViewer)viewer).getScrollPane();
		pane.getChildren().add(fxpane);
		AnchorPane.setBottomAnchor(fxpane, 0.0);
		AnchorPane.setLeftAnchor(fxpane, 0.0);
	    AnchorPane.setRightAnchor(fxpane, 0.0);
	    AnchorPane.setTopAnchor(fxpane, 0.0);
	}
	
	public ScrollPaneEx getPane()
	{
		return ((FXViewer)viewer).getScrollPane();
	}
	
	public void activate()
	{ 
		domain.activate();
		graph = createDefaultGraph();
		viewer.getAdapter(ContentModel.class)
				.setContents(Collections.singletonList(graph));
	}
	
	private static Graph buildAC(String id) {
		// create nodes "A" to "C"
		List<org.eclipse.gef4.graph.Node> nodes = new ArrayList<org.eclipse.gef4.graph.Node>();
		nodes.addAll(Arrays.asList(
				n(ZestProperties.ELEMENT_LABEL, "A", ZestProperties.NODE_TOOLTIP, "Alpha",
						ZestProperties.ELEMENT_CSS_ID, id + "A"),
				n(ZestProperties.ELEMENT_LABEL, "B", ZestProperties.NODE_TOOLTIP, "Beta", ZestProperties.ELEMENT_CSS_ID,
						id + "B"),
				n(ZestProperties.ELEMENT_LABEL, "C", ZestProperties.NODE_TOOLTIP, "Gamma",
						ZestProperties.ELEMENT_CSS_ID, id + "C")));
	
		// create some edges between those nodes
		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(Arrays.asList(e(nodes.get(0), nodes.get(1)), e(nodes.get(1), nodes.get(2)),
				e(nodes.get(2), nodes.get(0))));
	
		// directed connections
		HashMap<String, Object> attrs = new HashMap<String, Object>();
		attrs.put(ZestProperties.GRAPH_TYPE, ZestProperties.GRAPH_TYPE_DIRECTED);
		attrs.put(ZestProperties.GRAPH_LAYOUT, new SpringLayoutAlgorithm());
		return new Graph(attrs, nodes, edges);
	}
	
	private static Graph buildAE(String id) {
		// create nodes "A" to "C"
		List<org.eclipse.gef4.graph.Node> nodes = new ArrayList<org.eclipse.gef4.graph.Node>();
		nodes.addAll(Arrays.asList(
				n(ZestProperties.ELEMENT_LABEL, "A", ZestProperties.NODE_TOOLTIP, "Alpha",
						ZestProperties.ELEMENT_CSS_ID, id + "A"),
				n(ZestProperties.ELEMENT_LABEL, "B", ZestProperties.NODE_TOOLTIP, "Beta", ZestProperties.ELEMENT_CSS_ID,
						id + "B"),
				n(ZestProperties.ELEMENT_LABEL, "C", ZestProperties.NODE_TOOLTIP, "Gamma",
						ZestProperties.ELEMENT_CSS_ID, id + "C"),
				n(ZestProperties.ELEMENT_LABEL, "D", ZestProperties.NODE_TOOLTIP, "Delta",
						ZestProperties.ELEMENT_CSS_ID, id + "D"),
				n(ZestProperties.ELEMENT_LABEL, "E", ZestProperties.NODE_TOOLTIP, "Epsilon",
						ZestProperties.ELEMENT_CSS_ID, id + "E")));
	
		// add nested graphs
		nodes.get(4).setNestedGraph(buildAC("c"));
	
		// create some edges between those nodes
		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(Arrays.asList(e(nodes.get(0), nodes.get(1)), e(nodes.get(1), nodes.get(2)),
				e(nodes.get(2), nodes.get(3)), e(nodes.get(3), nodes.get(4)), e(nodes.get(4), nodes.get(0))));
	
		// directed connections
		HashMap<String, Object> attrs = new HashMap<String, Object>();
		attrs.put(ZestProperties.GRAPH_TYPE, ZestProperties.GRAPH_TYPE_DIRECTED);
		attrs.put(ZestProperties.GRAPH_LAYOUT, new SpringLayoutAlgorithm());
		return new Graph(attrs, nodes, edges);
	}
	
	public static Graph createDefaultGraph() {
		// create nodes "0" to "9"
		List<org.eclipse.gef4.graph.Node> nodes = new ArrayList<org.eclipse.gef4.graph.Node>();
		nodes.addAll(Arrays.asList(n(ZestProperties.ELEMENT_LABEL, "0", ZestProperties.NODE_TOOLTIP, "zero"),
				n(ZestProperties.ELEMENT_LABEL, "node1", ZestProperties.NODE_TOOLTIP, "one"),
				n(ZestProperties.ELEMENT_LABEL, "2", ZestProperties.NODE_TOOLTIP, "two"),
				n(ZestProperties.ELEMENT_LABEL, "3", ZestProperties.NODE_TOOLTIP, "three"),
				n(ZestProperties.ELEMENT_LABEL, "4", ZestProperties.NODE_TOOLTIP, "four"),
				n(ZestProperties.ELEMENT_LABEL, "5", ZestProperties.NODE_TOOLTIP, "five"),
				n(ZestProperties.ELEMENT_LABEL, "6", ZestProperties.NODE_TOOLTIP, "six"),
				n(ZestProperties.ELEMENT_LABEL, "7", ZestProperties.NODE_TOOLTIP, "seven"),
				n(ZestProperties.ELEMENT_LABEL, "8", ZestProperties.NODE_TOOLTIP, "eight"),
				n(ZestProperties.ELEMENT_LABEL, "9", ZestProperties.NODE_TOOLTIP, "nine")));
	
		// set nested graphs
		nodes.get(0).setNestedGraph(buildAC("a"));
		nodes.get(5).setNestedGraph(buildAE("b"));
	
		// create some edges between those nodes
		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(Arrays.asList(e(nodes.get(0), nodes.get(9)), e(nodes.get(1), nodes.get(8)),
				e(nodes.get(2), nodes.get(7)), e(nodes.get(3), nodes.get(6)), e(nodes.get(4), nodes.get(5)),
				e(nodes.get(0), nodes.get(4)), e(nodes.get(1), nodes.get(6)), e(nodes.get(2), nodes.get(8)),
				e(nodes.get(3), nodes.get(5)), e(nodes.get(4), nodes.get(7)), e(nodes.get(5), nodes.get(1))));
	
		// directed connections
		HashMap<String, Object> attrs = new HashMap<String, Object>();
		attrs.put(ZestProperties.GRAPH_TYPE, ZestProperties.GRAPH_TYPE_DIRECTED);
		attrs.put(ZestProperties.GRAPH_LAYOUT, new GridLayoutAlgorithm());
		return new Graph(attrs, nodes, edges);
	
	}
	
	private static int id = 0;
	protected static final String ID = ZestProperties.ELEMENT_CSS_ID;
	protected static final String LABEL = ZestProperties.ELEMENT_LABEL;
	protected static final String CSS_CLASS = ZestProperties.ELEMENT_CSS_CLASS;
	protected static final String LAYOUT_IRRELEVANT = ZestProperties.ELEMENT_LAYOUT_IRRELEVANT;

	protected static String genId() {
		return Integer.toString(id++);
	}

	protected static Edge e(org.eclipse.gef4.graph.Node n,
			org.eclipse.gef4.graph.Node m, Object... attr) {
		String label = (String) n.getAttrs().get(LABEL)
				+ (String) m.getAttrs().get(LABEL);
		Builder builder = new Edge.Builder(n, m).attr(LABEL, label).attr(ID,
				genId());
		for (int i = 0; i < attr.length; i += 2) {
			builder.attr(attr[i].toString(), attr[i + 1]);
		}
		return builder.buildEdge();
	}

	protected static Edge e(Graph graph, org.eclipse.gef4.graph.Node n,
			org.eclipse.gef4.graph.Node m, Object... attr) {
		Edge edge = e(n, m, attr);
		edge.setGraph(graph);
		graph.getEdges().add(edge);
		return edge;
	}

	protected static org.eclipse.gef4.graph.Node n(Object... attr) {
		org.eclipse.gef4.graph.Node.Builder builder = new org.eclipse.gef4.graph.Node.Builder();
		String id = genId();
		builder.attr(ID, id).attr(LABEL, id);
		for (int i = 0; i < attr.length; i += 2) {
			builder.attr(attr[i].toString(), attr[i + 1]);
		}
		return builder.buildNode();
	}

	protected static org.eclipse.gef4.graph.Node n(Graph graph,
			Object... attr) {
		Node node = n(attr);
		node.setGraph(graph);
		graph.getNodes().add(node);
		return node;
	}
	
	protected Module createModule() {
		return new ZestFxModule();
	}
}

