package net.datascientists.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JAXBTest
{

    private HashTree hashTree;
    
    @Before
    public void setUp() throws FileNotFoundException, IOException {
        String jmeterHome = "C:\\Users\\lenovo\\Downloads\\apache-jmeter-3.3\\apache-jmeter-3.3"; 
        JMeterUtils.setJMeterHome(jmeterHome); 
        JMeterUtils.loadJMeterProperties(JMeterUtils.getJMeterBinDir() + "\\jmeter.properties"); 
        JMeterUtils.initLocale(); 

        
        // HTTP Sampler
        HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
        httpSampler.addArgument("name", "value");
        httpSampler.setDomain("example.com");
        httpSampler.setPort(80);
        httpSampler.setPath("/");
        httpSampler.setMethod("GET");
        
        HeaderManager headerManager = new HeaderManager();
        Header header = new Header();
        header.setName("Accept");
        header.setValue("application/json, text/plain, */*");
        headerManager.add(header);
        httpSampler.setHeaderManager(headerManager);
        
        // Loop Controller
        LoopController loopController = new LoopController();
        loopController.setLoops(1);
        loopController.addTestElement(httpSampler);
        loopController.setFirst(true);
        loopController.initialize();

        // Thread Group
        org.apache.jmeter.threads.ThreadGroup threadGroup = new org.apache.jmeter.threads.ThreadGroup();
        threadGroup.setNumThreads(1);
        threadGroup.setRampUp(1);
        threadGroup.setSamplerController(loopController);

        // Test Plan
        TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
        
        hashTree = new HashTree();
        // Construct Test Plan from previously initialized elements
        hashTree.add("testPlan", testPlan);
        hashTree.add("loopController", loopController);
        hashTree.add("threadGroup", threadGroup);
        hashTree.add("httpSampler", httpSampler);
    }
 
    @After
    public void tearDown() {
    }
 
    @Test
    public void testSaveHashTree() throws FileNotFoundException, IOException {
        SaveService.saveTree(hashTree, new FileOutputStream("test.xml"));
        SaveService.saveTree(hashTree, System.out);
    }
    
}
