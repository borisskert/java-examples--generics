package de.adorsys.example.generic;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class ObjectsTest {

    private List<String> list;

    @Before
    public void setup() throws Exception {
        list = new Objects<String>();
    }

    @Test
    public void shouldBeEmptyAfterInitialization() throws Exception {
        assertThat(list.isEmpty(), is(equalTo(true)));
    }

    @Test
    public void shouldStoreOneString() throws Exception {
        list.add("abc");
        String string = list.get(0);

        assertThat(string, is(equalTo("abc")));
    }

    @Test
    public void shouldNotBeEmptyAfterAddingOneElement() throws Exception {
        list.add("abc");
        assertThat(list.isEmpty(), is(equalTo(false)));
    }

    @Test
    public void shouldStoreTwoStrings() throws Exception {
        list.add("abc");
        list.add("xyz");

        String string = list.get(0);
        assertThat(string, is(equalTo("abc")));

        string = list.get(1);
        assertThat(string, is(equalTo("xyz")));
    }


    @Test
    public void shouldNotBeEmptyAfterAddingTwoElements() throws Exception {
        list.add("abc");
        list.add("xyz");
        assertThat(list.isEmpty(), is(equalTo(false)));
    }

    @Test
    public void shouldStoreThreeStrings() throws Exception {
        list.add("abc");
        list.add("xyz");
        list.add("mno");

        String string = list.get(0);
        assertThat(string, is(equalTo("abc")));

        string = list.get(1);
        assertThat(string, is(equalTo("xyz")));

        string = list.get(2);
        assertThat(string, is(equalTo("mno")));
    }


    @Test
    public void shouldNotBeEmptyAfterAddingThreeElements() throws Exception {
        list.add("abc");
        list.add("mno");
        list.add("xyz");
        assertThat(list.isEmpty(), is(equalTo(false)));
    }

    @Test
    public void shouldAddThousandStrings() throws Exception {
        for(Integer counter = 0; counter < 1000; counter++) {
            list.add(counter.toString());
        }
    }

    @Test
    public void shouldAddTenThousandStrings() throws Exception {
        for(Integer counter = 0; counter < 10000; counter++) {
            list.add(counter.toString());
        }
    }

    @Test
    public void shouldAddHundredThousandStrings() throws Exception {
        for(Integer counter = 0; counter < 100000; counter++) {
            list.add(counter.toString());
        }
    }

    @Test
    public void shouldAddOneMillionStrings() throws Exception {
        for(Integer counter = 0; counter < 1000000; counter++) {
            list.add(counter.toString());
        }
    }

    @Test
    @Ignore
    public void shouldAddTenMillionStrings() throws Exception {
        for(Integer counter = 0; counter < 10000000; counter++) {
            list.add(counter.toString());
        }
    }

    @Test
    @Ignore
    public void shouldAddOneBillionStrings() throws Exception {
        for(Integer counter = 0; counter < 1000000000; counter++) {
            list.add(counter.toString());
        }
    }
}
