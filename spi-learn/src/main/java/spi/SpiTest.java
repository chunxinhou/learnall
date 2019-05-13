package spi;

import service.SpiServicer;

import java.util.ServiceLoader;

public class SpiTest {



    public String toDo(){
        ServiceLoader<SpiServicer> spiServicers = ServiceLoader.load(SpiServicer.class);
        String result ="";
        for (SpiServicer servicer: spiServicers) {
            result =result +  servicer.doSomeThing() +"-";
        }
        return result;
    }
}
