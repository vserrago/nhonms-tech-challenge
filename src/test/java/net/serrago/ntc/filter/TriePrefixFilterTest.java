package net.serrago.ntc.filter;

class TriePrefixFilterTest implements PrefixFilterTest {

    @Override
    public PrefixFilter getInstance() {
        return new TriePrefixFilter();
    }
}