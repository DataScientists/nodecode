package net.datascientists.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.extractor.json.jsonpath.JSONPostProcessor;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

public class JMeterFactory
{

    public static class Builder
    {

        private HashTree hashTree;
        private org.apache.jmeter.threads.ThreadGroup threadGroup;
        private List<HTTPSamplerBase> httpSampler = new ArrayList<>();
        private HeaderManager header;
        private GenericController controller;
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


        public Builder addController(final GenericController controller)
        {
            this.controller = controller;
            return this;
        }


        public Builder addThreadGroup(final org.apache.jmeter.threads.ThreadGroup threadGroup)
        {
            this.threadGroup = threadGroup;
            return this;
        }


        public Builder addHttpSampler(final HTTPSamplerBase httpSampler)
        {
            this.httpSampler.add(httpSampler);
            return this;
        }
        
        public Builder addJSONPostProcessor(final JSONPostProcessor jsonPostProcessor)
        {
            this.jsonPostProcesser = jsonPostProcessor;
            return this;
        }


        public HashTree build()
        {
            String jmeterHome = "C:\\Users\\lenovo\\Downloads\\apache-jmeter-3.3\\apache-jmeter-3.3"; 
            JMeterUtils.setJMeterHome(jmeterHome); 
            JMeterUtils.loadJMeterProperties(JMeterUtils.getJMeterBinDir() + "\\jmeter.properties"); 
            JMeterUtils.initLocale();
            
            hashTree = new HashTree();
            if (controller != null)
            {
                hashTree.add("loopController", this.controller);
            }
            if (testPlan != null)
            {
                this.hashTree.add("testPlan", testPlan);
            }
            if (!childHashTree.isEmpty())
            {
                for (HashTree ht : childHashTree)
                {
                    hashTree.add(ht);
                }
            }
            if (threadGroup != null)
            {
                hashTree.add(threadGroup);
            }
            if (jsonPostProcesser != null)
            {
                hashTree.add(jsonPostProcesser);
            }
            if (!this.httpSampler.isEmpty())
            {
                for (HTTPSamplerBase sampler : this.httpSampler)
                {
                    sampler.setHeaderManager(this.header);
                    hashTree.add("httpSampler", sampler);
                }
            }
            return hashTree;
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

    }

}
