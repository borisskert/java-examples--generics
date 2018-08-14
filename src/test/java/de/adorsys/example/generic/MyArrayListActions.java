package de.adorsys.example.generic;

import io.vavr.Tuple2;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import net.jqwik.api.stateful.Action;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;

class MyArrayListActions {

    static Arbitrary<Action<MyArrayList<String>>> actions() {
        return Arbitraries.oneOf(add(), addAll(), set(), remove(), removeByIndex(), clear());
    }

    static Arbitrary<Action<MyArrayList<String>>> add() {
        return Arbitraries.strings().alpha().numeric().map(AddAction::new);
    }

    static Arbitrary<Action<MyArrayList<String>>> addAll() {
        return Arbitraries.strings().alpha().numeric().list().map(AddAllAction::new);
    }

    static Arbitrary<Action<MyArrayList<String>>> allAllByIndex() {
        Arbitrary<Integer> integers = Arbitraries.integers();
        Arbitrary<List<String>> strings = Arbitraries.strings().alpha().numeric().list();

        return Combinators.combine(integers, strings)
                .as(Tuple2::new)
                .map(AddAllByIndexAction::new)
        ;
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

    static Arbitrary<Action<MyArrayList<String>>> removeByIndex() {
        return Arbitraries.integers().map(RemoveByIndex::new);
    }

    static Arbitrary<Action<MyArrayList<String>>> clear() {
        return Arbitraries.constant(new ClearAction<>());
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

            return model;
        }

        @Override
        public String toString() {
            String elementAsText = element == null ? "null" : "\"" + element.toString() + "\"";

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

        @Override
        public String toString() {
            StringJoiner joiner = new StringJoiner(",");
            elements.forEach(s -> joiner.add("\"" + s.toString() + "\""));

            return "addAll([" +
                    joiner.toString() +
                    "])";
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
            String elementAsText = element == null ? "null" : "\"" + element.toString() + "\"";

            return "set(" +
                    index +
                    ',' + elementAsText +
                    ')';
        }
    }

    private static class AddAllByIndexAction<E> implements Action<MyArrayList<E>> {

        private final Integer index;
        private final Collection<E> elements;

        private AddAllByIndexAction(Integer index, Collection<E> elements) {
            this.index = index;
            this.elements = elements;
        }

        AddAllByIndexAction(Tuple2<Integer, List<E>> tuple) {
            this.index = tuple._1();
            this.elements = tuple._2();
        }

        @Override
        public MyArrayList<E> run(MyArrayList<E> model) {
            int sizeBefore = model.size();

            if (index < 0) {
                testWithInvalidIndex(model);
            } else if (index > sizeBefore) {
                testWithInvalidIndex(model);
            } else {
                testSuccessful(model, sizeBefore);
            }

            return model;
        }

        private void testSuccessful(MyArrayList<E> model, int sizeBefore) {
            model.addAll(index, elements);

            int sizeAfter = model.size();

            assertThat(sizeAfter).isEqualTo(sizeBefore + elements.size());

            int currentIndex = index;
            for(E element : elements) {
                assertThat(model.get(currentIndex)).isEqualTo(element);
                currentIndex++;
            }
        }

        private void testWithInvalidIndex(MyArrayList<E> model) {
            catchException(model).addAll(index, elements);
            assertThat((Throwable) caughtException()).isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Override
        public String toString() {
            StringJoiner joiner = new StringJoiner(",");
            elements.forEach(s -> joiner.add("\"" + s.toString() + "\""));

            return "addAll(" +
                    index +
                    ", [" +
                    joiner.toString() +
                    "])";
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

            return model;
        }

        @Override
        public String toString() {
            String elementAsText = element == null ? "null" : "\"" + element.toString() + "\"";

            return "remove(" +
                    elementAsText +
                    ')';
        }
    }

    private static class RemoveByIndex<E> implements Action<MyArrayList<E>> {

        private final Integer index;

        private RemoveByIndex(Integer index) {
            this.index = index;
        }

        @Override
        public MyArrayList<E> run(MyArrayList<E> model) {
            if(index < 0) {
                testFail(model);
            } else if(index >= model.size()) {
                testFail(model);
            } else {
                testSuccess(model);
            }

            return model;
        }

        private void testFail(MyArrayList<E> model) {
            catchException(model).remove(index.intValue());
            assertThat((Throwable) caughtException()).isInstanceOf(IndexOutOfBoundsException.class);
        }

        private void testSuccess(MyArrayList<E> model) {
            int sizeBefore = model.size();
            E elementToBeRemoved = model.get(index);

            E removedElement = model.remove(index.intValue());

            int sizeAfter = model.size();

            assertThat(removedElement).isEqualTo(elementToBeRemoved);
            assertThat(sizeAfter).isEqualTo(sizeBefore - 1);
        }

        @Override
        public String toString() {
            return "remove(" +
                    index +
                    ')';
        }
    }

    private static class ClearAction<E> implements Action<MyArrayList<E>> {

        @Override
        public MyArrayList<E> run(MyArrayList<E> model) {
            model.clear();

            assertThat(model.size()).isEqualTo(0);
            assertThat(model.isEmpty()).isEqualTo(true);

            return model;
        }

        @Override
        public String toString() {
            return "clear()";
        }
    }
}
