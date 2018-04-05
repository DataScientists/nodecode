package net.datascientists.service;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.datascientists.utilities.JMeterFactory;
import net.datascientists.vo.JMXLogVO;

public class JAXBTest
{
    
    private  JMeterFactory.Builder builder;

    @Before
    public void setUp() throws FileNotFoundException, IOException {
        builder= new JMeterFactory.Builder();
        
        String jmeterHome = "C:\\Users\\lenovo\\Downloads\\apache-jmeter-2.8\\apache-jmeter-2.8"; 
        JMeterUtils.setJMeterHome(jmeterHome); 
        JMeterUtils.loadJMeterProperties(JMeterUtils.getJMeterBinDir() + "\\jmeter.properties"); 
        JMeterUtils.initLocale(); 

        TestPlan testPlan = new TestPlan();
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName()); 
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setComment("This is a test");
//        testPlan.setFunctionalMode(false);
//        testPlan.setUserDefinedVariables(null);
//        testPlan.setTestPlanClasspath("");
        builder.addTestPlan(testPlan);
        
        // HTTP Sampler
//        HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
//        httpSampler.addArgument("name", "value");
//        httpSampler.setDomain("example.com");
//        httpSampler.setPort(80);
//        httpSampler.setPath("/");
//        httpSampler.setMethod("GET");
//        
//        HeaderManager headerManager = new HeaderManager();
//        Header header = new Header();
//        header.setName("Accept");
//        header.setValue("application/json, text/plain, */*");
//        headerManager.add(header);
//        httpSampler.setHeaderManager(headerManager);
//        
//        // Loop Controller
//        LoopController loopController = new LoopController();
//        loopController.setLoops(1);
//        loopController.addTestElement(httpSampler);
//        loopController.setFirst(true);
//        loopController.initialize();
//
//        // Thread Group
//        org.apache.jmeter.threads.ThreadGroup threadGroup = new org.apache.jmeter.threads.ThreadGroup();
//        threadGroup.setNumThreads(1);
//        threadGroup.setRampUp(1);
//        threadGroup.setSamplerController(loopController);
//        
//        builder.addHeader(headerManager);
//        builder.addController(loopController);
//        builder.addHttpSampler(httpSampler);
//        builder.addThreadGroup(threadGroup);
    }
 
    @After
    public void tearDown() {
    }
 
    @Test
    public void testSaveHashTree() {
        JMeterFactory factory = builder.build();
        String result = builder.createTree(factory.getHashTree());
        System.out.println(result);
        assertTrue(!StringUtils.isEmpty(result));
    }
    
    @Test
    public void givenArrayObjects_whenUsingComparing_thenSortedArrayObjects() {
        List<JMXLogVO> jmxLogList = Arrays.asList(new JMXLogVO(1L,"one","","","","","",1),
            new JMXLogVO(3L,"three","","","","","",1),
            new JMXLogVO(2L,"two","","","","","",1)
            );
        Collections.sort(jmxLogList);
        System.out.println(jmxLogList);    
        assertTrue(!jmxLogList.isEmpty());
    }
    
}
