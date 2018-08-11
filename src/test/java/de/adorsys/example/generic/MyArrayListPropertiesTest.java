package de.adorsys.example.generic;

import net.jqwik.api.*;
import net.jqwik.api.stateful.ActionSequence;

class MyArrayListPropertiesTest {

    @Property
    void test(@ForAll("sequences") ActionSequence<MyArrayList<String>> sequence) {
        sequence.run(new MyArrayList<>());
    }

    @Provide
    Arbitrary<ActionSequence<MyArrayList<String>>> sequences() {
        return Arbitraries.sequences(MyArrayListActions.actions());
    }
}
