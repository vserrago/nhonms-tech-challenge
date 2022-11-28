package net.serrago.ntc.filter;

class SimplePrefixFilterTest implements PrefixFilterTest {

    @Override
    public PrefixFilter getInstance() {
        return new SimplePrefixFilter();
    }
}