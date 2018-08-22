package de.adorsys.example.generic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class MyArrayListIteratorTest {

    private MyArrayList<String> list;

    @Before
    public void setup() throws Exception {
        list = new MyArrayList<>();
    }

    @Test
    public void shouldNotHaveNextElement() throws Exception {
        Iterator<String> iterator = list.iterator();
        assertThat(iterator.hasNext(), is(false));

        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            return;
        }

        Assert.fail("No NoSuchElementException thrown");
    }

    @Test
    public void shouldHaveNextElement() throws Exception {
        list.add("abc");

        Iterator<String> iterator = list.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(equalTo("abc")));
        assertThat(iterator.hasNext(), is(false));

        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            return;
        }

        Assert.fail("No NoSuchElementException thrown");
    }

    @Test
    public void shouldHaveNextElementForTwoElements() throws Exception {
        list.add("abc");
        list.add("def");

        Iterator<String> iterator = list.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(equalTo("abc")));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(equalTo("def")));
        assertThat(iterator.hasNext(), is(false));

        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            return;
        }

        Assert.fail("No NoSuchElementException thrown");
    }
}
