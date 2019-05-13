package service.impl;

import service.SpiServicer;

public class SpiServicerA implements SpiServicer {
    @Override
    public String doSomeThing() {
        return "SpiServicerA";
    }
}
