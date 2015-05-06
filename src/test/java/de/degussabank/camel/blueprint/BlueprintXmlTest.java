package de.degussabank.camel.blueprint;

import static org.junit.Assert.*;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

public class BlueprintXmlTest extends CamelBlueprintTestSupport {

	// TODO Create test message bodies that work for the route(s) being tested
	// Expected message bodies
	protected Object[] expectedBodies = {"<something id='1'>expectedBody1</something>",
	    "<something id='2'>expectedBody2</something>" };
	// Templates to send to input endpoints
	@Produce(uri = "file://inbox?include=(.*)\.xml")
	protected ProducerTemplate inputEndpoint;
	// Mock endpoints used to consume messages from the output endpoints and then perform assertions
	@EndpointInject(uri = "mock:output")
	protected MockEndpoint outputEndpoint;

	@Test
	public void testCamelRoute() throws Exception{
	// Create routes from the output endpoints to our mock endpoints so we can assert expectations
	context.addRoutes(new RouteBuilder() {
	    @Override
	    public void configure() throws Exception {
	        from("activemq:queue:demo.in").to(outputEndpoint);
	    }
	});
	
	
	// Define some expectations
	
	// TODO Ensure expectations make sense for the route(s) we're testing
	outputEndpoint.expectedBodiesReceivedInAnyOrder(expectedBodies);
	
	// Send some messages to input endpoints
	for (Object expectedBody : expectedBodies) {
	    inputEndpoint.sendBody(expectedBody);
	}
	
	// Validate our expectations
	assertMockEndpointsSatisfied();
	}

	@Override
	protected String getBlueprintDescriptor() {
	return "OSGI-INF/blueprint/blueprint.xml";
	}

}
