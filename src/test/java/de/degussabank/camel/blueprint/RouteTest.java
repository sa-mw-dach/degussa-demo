package de.degussabank.camel.blueprint;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

public class RouteTest extends CamelBlueprintTestSupport {
			
	@Produce(uri = "mock:file:inbox")
    protected ProducerTemplate producer;
	
    @Override
    protected String getBlueprintDescriptor() {
        return "/OSGI-INF/blueprint/blueprint.xml";
    }
    

    @Override
	public String isMockEndpointsAndSkip() {
		return "*";
	}


	@Test
    public void testRoute() throws Exception {
		MockEndpoint inbox = getMockEndpoint("mock:file:inbox");
		inbox.expectedMessageCount(1);
			
    	producer.sendBody("<set><entry id=\"1\"><key>key1</key><value>value1</value></entry><entry id=\"2\"><key>key2</key><value>value2</value></entry></set>");
    	
        // assert expectations
    	inbox.assertIsSatisfied();
    }

}
