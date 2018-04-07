package net.datascientists.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.extractor.json.jsonpath.JSONPostProcessor;
import org.apache.jmeter.extractor.json.jsonpath.gui.JSONPostProcessorGui;
import org.apache.jmeter.protocol.http.config.gui.HttpDefaultsGui;
import org.apache.jmeter.protocol.http.control.CacheManager;
import org.apache.jmeter.protocol.http.control.CookieManager;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.CacheManagerGui;
import org.apache.jmeter.protocol.http.gui.CookiePanel;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

public class JMeterFactory
{
    
    private HashTree hashTree;
    private org.apache.jmeter.threads.ThreadGroup threadGroup;
    private List<HTTPSamplerProxy> httpSampler;
    private HeaderManager header;
    private GenericController controller;
    private TestPlan testPlan;
    private List<HashTree> childHashTree;
    private JSONPostProcessor jsonPostProcesser;

    
    
    public JMeterFactory(
        HashTree hashTree, ThreadGroup threadGroup, List<HTTPSamplerProxy> httpSampler, HeaderManager header, GenericController controller, TestPlan testPlan,
        List<HashTree> childHashTree, JSONPostProcessor jsonPostProcesser)
    {
        super();
        this.hashTree = hashTree;
        this.threadGroup = threadGroup;
        this.httpSampler = httpSampler;
        this.header = header;
        this.controller = controller;
        this.testPlan = testPlan;
        this.childHashTree = childHashTree;
        this.jsonPostProcesser = jsonPostProcesser;
    }


    public HashTree getHashTree()
    {
        return hashTree;
    }


    public org.apache.jmeter.threads.ThreadGroup getThreadGroup()
    {
        return threadGroup;
    }


    public List<HTTPSamplerProxy> getHttpSampler()
    {
        return httpSampler;
    }


    public HeaderManager getHeader()
    {
        return header;
    }


    public GenericController getController()
    {
        return controller;
    }


    public TestPlan getTestPlan()
    {
        return testPlan;
    }


    public List<HashTree> getChildHashTree()
    {
        return childHashTree;
    }


    public JSONPostProcessor getJsonPostProcesser()
    {
        return jsonPostProcesser;
    }


    public static class Builder
    {

        private HashTree hashTree = new HashTree();
        private org.apache.jmeter.threads.ThreadGroup threadGroup;
        private List<HTTPSamplerProxy> httpSampler = new ArrayList<>();
        private HeaderManager header;
        private LoopController controller;
        private TestPlan testPlan;
        private List<HashTree> childHashTree = new ArrayList<>();
        private JSONPostProcessor jsonPostProcesser;


        public Builder addTestPlan(final TestPlan testPlan)
        {
            this.testPlan = testPlan;
            return this;
        }


        public Builder addChildHashTree(final HashTree hashTree)
        {
            this.childHashTree.add(hashTree);
            return this;
        }


        public Builder addHeader(final HeaderManager header)
        {
            this.header = header;
            return this;
        }


        public Builder addController(final LoopController controller)
        {
            this.controller = controller;
            return this;
        }


        public Builder addThreadGroup(final org.apache.jmeter.threads.ThreadGroup threadGroup)
        {
            this.threadGroup = threadGroup;
            return this;
        }


        public Builder addHttpSampler(final HTTPSamplerProxy httpSampler)
        {
            this.httpSampler.add(httpSampler);
            return this;
        }
        
        public Builder addJSONPostProcessor(final JSONPostProcessor jsonPostProcessor)
        {
            this.jsonPostProcesser = jsonPostProcessor;
            return this;
        }


        public JMeterFactory build()
        {
            String jmeterHome = "C:\\Users\\lenovo\\Downloads\\apache-jmeter-3.3\\apache-jmeter-3.3"; 
            JMeterUtils.setJMeterHome(jmeterHome); 
            JMeterUtils.loadJMeterProperties(JMeterUtils.getJMeterBinDir() + "\\jmeter.properties"); 
            JMeterUtils.initLocale();
            if (controller != null)
            {
                setDefaultLoopControllerProperty(this.controller);
                this.hashTree.add("loopController", this.controller);
            }
            if (testPlan != null)
            {
                setDefaultPropertyForTestPlan(testPlan);
                this.hashTree.add(testPlan);
            }
            if (threadGroup != null)
            {
                setDefaultPropertyForThreadGroup(threadGroup);
                this.hashTree.add(threadGroup);
            }
            if (jsonPostProcesser != null)
            {
                setDefaultPropertyForJSONPostProcessor(jsonPostProcesser);
                this.hashTree.add(jsonPostProcesser);
            }
            if (!this.httpSampler.isEmpty())
            {
                setDefaultHeaderProperty(this.header);
                for (HTTPSamplerProxy sampler : this.httpSampler)
                {
                    setDefaultHTTPSamplerProperty(sampler);
                    sampler.setHeaderManager(this.header);
                    this.hashTree.add("httpSampler", sampler);
                }
            }
            if(!childHashTree.isEmpty()){
                for(HashTree ht:childHashTree){
                    this.hashTree.add(ht);
                }
            }
            return new JMeterFactory(this.hashTree,threadGroup,httpSampler,header,controller,testPlan,childHashTree,jsonPostProcesser);
        }


        private void setDefaultHTTPSamplerProperty(HTTPSamplerProxy sampler)
        {
            sampler.setProperty(TestElement.GUI_CLASS,HttpTestSampleGui.class.getName());
            sampler.setProperty(TestElement.TEST_CLASS,HTTPSamplerProxy.class.getName());
            sampler.setProperty(TestElement.ENABLED,true);
        }


        private void setDefaultPropertyForJSONPostProcessor(JSONPostProcessor jsonPostProcesser)
        {
            jsonPostProcesser.setProperty(TestElement.GUI_CLASS,JSONPostProcessorGui.class.getName());
            jsonPostProcesser.setProperty(TestElement.TEST_CLASS,JSONPostProcessor.class.getName());
            jsonPostProcesser.setProperty(TestElement.ENABLED,true);
            jsonPostProcesser.setRefNames("jsontoken");
            jsonPostProcesser.setJsonPathExpressions("$..*");
            jsonPostProcesser.setMatchNumbers("1");
        }


        private void setDefaultPropertyForThreadGroup(ThreadGroup threadGroup)
        {
            threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
            threadGroup.setProperty(TestElement.TEST_CLASS,ThreadGroup.class.getName());
            threadGroup.setProperty(TestElement.ENABLED,true);
            threadGroup.setNumThreads(1);
            LoopController controller = new LoopController();
            setDefaultLoopControllerProperty(controller);
            threadGroup.setSamplerController(controller);
            threadGroup.setRampUp(1);
            threadGroup.setStartTime(1363247040000L);
            threadGroup.setEndTime(1363247040000L);
            threadGroup.setScheduler(false);
            threadGroup.setDuration(0);
            threadGroup.setDelay(0);
        }
        
        


        private void setDefaultLoopControllerProperty(LoopController controller)
        {
            controller.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
            controller.setProperty(TestElement.TEST_CLASS,LoopController.class.getName());
            controller.setProperty(TestElement.ENABLED,true);
            controller.setContinueForever(false);
            controller.setLoops(1);
        }


        private void setDefaultHeaderProperty(HeaderManager header)
        {
            header.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());
            header.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        }


        private void setDefaultPropertyForTestPlan(TestPlan testPlan)
        {
            testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName()); 
            testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
            testPlan.setProperty(TestElement.ENABLED, true);
        }


        public String createTree(HashTree hashTree)
        {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try
            {
                SaveService.saveTree(hashTree, os);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return os.toString();
        }


        public void setDefaultElements(HashTree hashTree)
        {
            addDefaultArguments(hashTree);
            addDefaultConfigTestElement(hashTree);
//            addDefaultDnsCache(hashTree);
            addDefaultCookieManager(hashTree);
            addCacheManager(hashTree);
        }


//        private void addDefaultDnsCache(HashTree hashTree)
//        {
//               //@TODO no DNSCacheManager in this API
//        }


        private void addCacheManager(HashTree hashTree)
        {
            CacheManager cacheManager = new CacheManager();
            cacheManager.setProperty(TestElement.GUI_CLASS,CacheManagerGui.class.getName());
            cacheManager.setProperty(TestElement.TEST_CLASS,CacheManager.class.getName());
            cacheManager.setProperty(TestElement.ENABLED,true);
            hashTree.add(cacheManager);
        }


        private void addDefaultCookieManager(HashTree hashTree)
        {
            CookieManager cookieManager = new CookieManager();
            cookieManager.setProperty(TestElement.GUI_CLASS, CookiePanel.class.getName());
            cookieManager.setProperty(TestElement.TEST_CLASS,CookieManager.class.getName());
            cookieManager.setProperty(TestElement.ENABLED,true);
            cookieManager.setClearEachIteration(true);
            hashTree.add(cookieManager);
        }


        private void addDefaultConfigTestElement(HashTree hashTree)
        {
            ConfigTestElement configTestElement = new ConfigTestElement();
            configTestElement.setProperty(TestElement.GUI_CLASS, HttpDefaultsGui.class.getName());
            configTestElement.setProperty(TestElement.TEST_CLASS,ConfigTestElement.class.getName());
            configTestElement.setProperty(TestElement.ENABLED,true);
            configTestElement.setProperty("HTTPSampler.image_parser", true);
            configTestElement.setProperty("HTTPSampler.concurrentDwn", true);
            configTestElement.setProperty("HTTPSampler.concurrentPool", "6");
            hashTree.add(configTestElement);
        }


        private void addDefaultArguments(HashTree hashTree)
        {
            Arguments arguments = new Arguments();
            arguments.setProperty(TestElement.GUI_CLASS,ArgumentsPanel.class.getName());
            arguments.setProperty(TestElement.TEST_CLASS,Arguments.class.getName());
            arguments.setProperty(TestElement.ENABLED,true);
            Argument argument1 = new Argument();
            argument1.setProperty("BASE_URL_1", "localhost");
            argument1.setMetaData("=");
            arguments.addArgument(argument1);
            hashTree.add(arguments);
        }


    }

}
