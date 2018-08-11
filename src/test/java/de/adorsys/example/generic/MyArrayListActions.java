package de.adorsys.example.generic;

import io.vavr.Tuple2;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import net.jqwik.api.stateful.Action;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Java6Assertions.assertThat;

class MyArrayListActions {

    static Arbitrary<Action<MyArrayList<String>>> actions() {
        return Arbitraries.oneOf(add(), set());
    }

    static Arbitrary<Action<MyArrayList<String>>> add() {
        return Arbitraries.strings().alpha().numeric().map(AddAction::new);
    }

    static Arbitrary<Action<MyArrayList<String>>> set() {
        Arbitrary<Integer> integers = Arbitraries.integers();
        Arbitrary<String> strings = Arbitraries.strings().alpha().numeric();

        return Combinators.combine(integers, strings)
                .as(Tuple2::new)
                .map(SetAction::new)
        ;
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

            assertThat(isAdded).isTrue();
            assertThat(sizeAfter).isEqualTo(sizeBefore + 1);
            assertThat(model.get(sizeBefore)).isEqualTo(element);
            assertThat(model.size()).isEqualTo(sizeBefore + 1);

            return model;
        }

        @Override
        public String toString() {
            String elementAsText = element == null ? "null" : element.toString();

            return "AddAction{" +
                    "element=" + elementAsText +
                    '}';
        }
    }

    private static class SetAction<E> implements Action<MyArrayList<E>> {

        private final Integer index;
        private final E element;

        SetAction(Tuple2<Integer, E> tuple) {
            this.index = tuple._1();
            this.element = tuple._2();
        }

        SetAction(int index, E element) {
            this.index = index;
            this.element = element;
        }

        @Override
        public MyArrayList<E> run(MyArrayList<E> model) {
            if(index < 0) {
                testWithInvalidIndex(model);
            } else if(index >= model.size()) {
                testWithInvalidIndex(model);
            } else {
                testSet(model);
            }

            return model;
        }

        private void testSet(MyArrayList<E> model) {
            E previousElementBefore = model.get(index);
            int sizeBefore = model.size();

            E previousElement = model.set(index, element);

            int sizeAfter = model.size();
            E elementAfter = model.get(index);

            assertThat(previousElement).isEqualTo(previousElementBefore);
            assertThat(elementAfter).isEqualTo(element);
            assertThat(sizeAfter).isEqualTo(sizeBefore);
        }

        private void testWithInvalidIndex(MyArrayList<E> model) {
            catchException(model).set(index, element);
            assertThat((Throwable) caughtException()).isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Override
        public String toString() {
            String elementAsText = element == null ? "null" : element.toString();

            return "SetAction{" +
                    "index=" + index +
                    ", element=" + elementAsText +
                    '}';
        }
    }
}
