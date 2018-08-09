package br.com.igorrodrigues.cattlefarm.config;

import java.time.Period;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PeriodAdapter 
extends XmlAdapter<String, Period> {

public Period unmarshal(String v) throws Exception {
    return Period.parse(v);
}

public String marshal(Period v) throws Exception {
    return v.toString();
}

}
