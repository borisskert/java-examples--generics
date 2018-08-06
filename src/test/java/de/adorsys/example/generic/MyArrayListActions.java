package de.adorsys.example.generic;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.stateful.Action;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class MyArrayListActions {

    static Arbitrary<Action<MyArrayList<String>>> actions() {
        return Arbitraries.oneOf(add());
    }

    static Arbitrary<Action<MyArrayList<String>>> add() {
        return Arbitraries.strings().alpha().numeric().ascii().map(AddAction::new);
    }

    private static class AddAction<E> implements Action<MyArrayList<E>> {

        private final E element;

        private AddAction(E element) {
            this.element = element;
        }

        @Override
        public MyArrayList<E> run(MyArrayList<E> model) {
            int sizeBefore = model.size();

            boolean isAdded = model.add(element);
            int sizeAfter = model.size();

            assertThat(isAdded, is(equalTo(true)));
            assertThat(sizeAfter, is(equalTo(sizeBefore + 1)));
            assertThat(model.get(sizeBefore), is(equalTo(element)));

            return model;
        }

        @Override
        public String toString() {
            return "AddAction{" +
                    "element=" + element +
                    '}';
        }
    }
}
