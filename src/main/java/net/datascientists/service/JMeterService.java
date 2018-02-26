package net.datascientists.service;

import net.datascientists.vo.JMeterTestPlan;

public interface JMeterService
{

    String buildXML(JMeterTestPlan testPlan);
    
}
