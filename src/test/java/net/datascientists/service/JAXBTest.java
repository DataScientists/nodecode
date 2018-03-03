package net.datascientists.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.util.JMeterUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.datascientists.utilities.JMeterFactory;

import static org.junit.Assert.*;

public class JAXBTest
{
    
    private  JMeterFactory.Builder builder;

    @Before
    public void setUp() throws FileNotFoundException, IOException {
        builder= new JMeterFactory.Builder();
        
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
        
        builder.addHeader(headerManager);
        builder.addController(loopController);
        builder.addHttpSampler(httpSampler);
        builder.addThreadGroup(threadGroup);
    }
 
    @After
    public void tearDown() {
    }
 
    @Test
    public void testSaveHashTree() {
        String result = builder.build();
        System.out.println(result);
        assertTrue(!StringUtils.isEmpty(result));
    }
    
}
