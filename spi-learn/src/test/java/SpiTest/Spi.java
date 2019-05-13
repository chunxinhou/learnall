package SpiTest;

import org.junit.Test;
import spi.SpiTest;

public class Spi {
    @Test
    public void testSpi(){
        SpiTest spiTest = new SpiTest();
        String s = spiTest.toDo();
        System.out.printf(s);
    }

}
