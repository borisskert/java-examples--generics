package de.adorsys.example.generic;

import io.vavr.Tuple2;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import net.jqwik.api.stateful.Action;

import java.util.Collection;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;

class MyArrayListActions {

    static Arbitrary<Action<MyArrayList<String>>> actions() {
        return Arbitraries.oneOf(add(), addAll(), set(), remove());
    }

    static Arbitrary<Action<MyArrayList<String>>> add() {
        return Arbitraries.strings().alpha().numeric().map(AddAction::new);
    }

    static Arbitrary<Action<MyArrayList<String>>> addAll() {
        return Arbitraries.strings().alpha().numeric().list().map(AddAllAction::new);
    }

    static Arbitrary<Action<MyArrayList<String>>> set() {
        Arbitrary<Integer> integers = Arbitraries.integers();
        Arbitrary<String> strings = Arbitraries.strings().alpha().numeric();

        return Combinators.combine(integers, strings)
                .as(Tuple2::new)
                .map(SetAction::new)
        ;
    }

    static Arbitrary<Action<MyArrayList<String>>> remove() {
        return Arbitraries.strings().alpha().numeric().map(RemoveAction::new);
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

            return "add(" +
                    elementAsText +
                    ')';
        }
    }

    private static class AddAllAction<E> implements Action<MyArrayList<E>> {

        private final Collection<E> elements;

        private AddAllAction(Collection<E> elements) {
            this.elements = elements;
        }

        @Override
        public MyArrayList<E> run(MyArrayList<E> model) {
            int sizeBefore = model.size();

            model.addAll(elements);

            int sizeAfter = model.size();

            assertThat(sizeAfter).isEqualTo(sizeBefore + elements.size());

            int index = sizeBefore;
            for(E element : elements) {
                assertThat(model.get(index)).isEqualTo(element);
                index++;
            }

            return model;
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

            return "set(" +
                    index +
                    ',' + elementAsText +
                    ')';
        }
    }

    private static class RemoveAction<E> implements Action<MyArrayList<E>> {

        private final E element;

        private RemoveAction(E element) {
            this.element = element;
        }

        @Override
        public MyArrayList<E> run(MyArrayList<E> model) {
            boolean containsElement = model.contains(element);
            int sizeBefore = model.size();

            boolean gotRemoved = model.remove(element);

            int sizeAfter = model.size();

            assertThat(gotRemoved).isEqualTo(containsElement);
            assertThat(sizeAfter).isEqualTo(sizeBefore - (containsElement ? 1 : 0));
            assertThat(model.contains(element)).isEqualTo(false);
            assertThat(model.indexOf(element)).isEqualTo(-1);

            return model;
        }

        @Override
        public String toString() {
            String elementAsText = element == null ? "null" : element.toString();

            return "remove(" +
                    elementAsText +
                    ')';
        }
    }
}
