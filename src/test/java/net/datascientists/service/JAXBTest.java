package net.datascientists.service;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.save.SaveService;
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

    private HashTree hashTreeMain;
    
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
//        builder.addTestPlan(testPlan);
        hashTreeMain = new HashTree(testPlan);
        
        // HTTP Sampler
//        HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
//        httpSampler.addArgument("name", "value");
//        httpSampler.setDomain("example.com");
//        httpSampler.setPort(80);
//        httpSampler.setPath("/");
//        httpSampler.setMethod("GET");
//
        HeaderManager headerManager = new HeaderManager();
        Header header = new Header();
        header.setName("Accept");
        header.setValue("application/json, text/plain, */*");
        headerManager.add(header);
        HashTree hashTree = hashTreeMain.add(testPlan, headerManager);
        HashTree configTestElement = hashTreeMain.add(testPlan, JMeterFactory.addDefaultConfigTestElement());
//        builder.addChildHashTree(hashTree);
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
    }
 
    @After
    public void tearDown() {
    }
 
    @Test
    public void testSaveHashTree() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try
        {
            SaveService.saveTree(hashTreeMain, os);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(os.toString());
        assertTrue(!StringUtils.isEmpty(os.toString()));
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
