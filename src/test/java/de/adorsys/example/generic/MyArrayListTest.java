package de.adorsys.example.generic;

import com.sun.xml.internal.fastinfoset.util.StringArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;

public class MyArrayListTest {

    private MyArrayList<String> list;

    @Before
    public void setup() throws Exception {
        list = new MyArrayList<>();
    }

    @Test
    public void shouldHaveZeroSizeAfterInitialization() throws Exception {
        assertThat(list.size(), is(0));
    }

    @Test
    public void shouldNotContainElementsAfterInitialization() throws Exception {
        assertThat(list.contains("does not exist"), is(equalTo(false)));
    }

    @Test
    public void shouldNotContainElementAfterInitializationAtIndex() throws Exception {
        assertThat(list.indexOf("does not exist"), is(equalTo(-1)));
    }

    @Test
    public void shouldNotContainElementAfterInitializationAtLastIndex() throws Exception {
        assertThat(list.lastIndexOf("does not exist"), is(equalTo(-1)));
    }

    @Test
    public void shouldBeEmptyAfterInitialization() throws Exception {
        assertThat(list.isEmpty(), is(equalTo(true)));
    }

    @Test
    public void shouldAddOneString() throws Exception {
        list.add("abc");
        String string = list.get(0);

        assertThat(string, is(equalTo("abc")));
        assertThat(list.size(), is(1));
        assertThat(list.indexOf("abc"), is(equalTo(0)));
    }

    @Test
    public void shouldNotSetFirstStringIfIndexIsNegative() throws Exception {
        try {
            list.set(-1, "abc");
        } catch(IndexOutOfBoundsException e){
            return;
        }

        Assert.fail("no IndexOutOfBoundsException thrown");
    }

    @Test
    public void shouldNotSetFirstStringIfListIsEmpty() throws Exception {
        try {
            list.set(0, "abc");
        } catch(IndexOutOfBoundsException e){
            return;
        }

        Assert.fail("no IndexOutOfBoundsException thrown");
    }

    @Test
    public void shouldNotSetSecondStringIfListContainsOneElement() throws Exception {
        list.add("abc");

        try {
            list.set(1, "xyz");
        } catch(IndexOutOfBoundsException e){
            return;
        }

        Assert.fail("no IndexOutOfBoundsException thrown");
    }

    @Test
    public void shouldSetSecondString() throws Exception {
        list.add("xyz");
        String previous = list.set(0, "abc");
        String string = list.get(0);

        assertThat(previous, is(equalTo("xyz")));
        assertThat(string, is(equalTo("abc")));
        assertThat(list.indexOf("abc"), is(equalTo(0)));
        assertThat(list.indexOf("xyz"), is(equalTo(-1)));
    }

    @Test
    public void shouldContainElementAtLastIndexAfterAddingOneElement() throws Exception {
        list.add("abc");
        assertThat(list.lastIndexOf("abc"), is(equalTo(0)));
    }

    @Test
    public void shouldContainFirstElementAfterAddingOneElement() throws Exception {
        list.add("abc");
        assertThat(list.contains("abc"), is(equalTo(true)));
    }

    @Test
    public void shouldContainFirstElementAfterAddingOneElementWhichIsntTheSame() throws Exception {
        list.add("ab".concat("c"));
        assertThat(list.contains("abc"), is(equalTo(true)));
    }

    @Test
    public void shouldNotContainNullElement() throws Exception {
        assertThat(list.contains(null), is(equalTo(false)));
    }

    @Test
    public void shouldNotContainNullElementAtIndex() throws Exception {
        assertThat(list.indexOf(null), is(equalTo(-1)));
    }
    @Test
    public void shouldNotContainNullElementAtLastIndex() throws Exception {
        assertThat(list.lastIndexOf(null), is(equalTo(-1)));
    }

    @Test
    public void shouldContainNullElementIfAdded() throws Exception {
        list.add(null);
        assertThat(list.contains(null), is(equalTo(true)));
    }

    @Test
    public void shouldContainNullElementIfAddedAtIndex() throws Exception {
        list.add(null);
        assertThat(list.indexOf(null), is(equalTo(0)));
    }

    @Test
    public void shouldContainNullElementIfAddedAtLastIndex() throws Exception {
        list.add(null);
        assertThat(list.lastIndexOf(null), is(equalTo(0)));
    }

    @Test
    public void shouldNotBeEmptyAfterAddingOneElement() throws Exception {
        list.add("abc");
        assertThat(list.isEmpty(), is(equalTo(false)));
    }

    @Test
    public void shouldContainElementAtIndexAfterAddingTwoElements() throws Exception {
        list.add("abc");
        list.add("xyz");
        assertThat(list.indexOf("xyz"), is(equalTo(1)));
    }

    @Test
    public void shouldContainElementAtLastIndexAfterAddingTwoElements() throws Exception {
        list.add("abc");
        list.add("xyz");
        assertThat(list.lastIndexOf("xyz"), is(equalTo(1)));
    }

    @Test
    public void shouldContainElementAtLastIndexAfterAddingTwoSameElements() throws Exception {
        list.add("xyz");
        list.add("abc");
        list.add("xyz");
        assertThat(list.lastIndexOf("xyz"), is(equalTo(2)));
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
    public void shouldHaveSizeOfTwoAfterAddingTwoElements() throws Exception {
        list.add("abc");
        list.add("xyz");
        assertThat(list.size(), is(2));
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
    public void shouldHaveSizeOfThreeAfterAddingThreeElements() throws Exception {
        list.add("abc");
        list.add("mno");
        list.add("xyz");
        assertThat(list.size(), is(3));
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

    @Test
    public void shouldContainAllElementsWhenNoElementExists() throws Exception {
        List<String> otherList = new ArrayList<>();
        assertThat(list.containsAll(otherList), is(equalTo(true)));

    }

    @Test
    public void shouldNotContainAllElementsWhenElementsDiffer() throws Exception {
        List<String> otherList = new ArrayList<>();
        otherList.add("abc");
        assertThat(list.containsAll(otherList), is(equalTo(false)));
    }

    @Test
    public void shouldContainAllElementsWhenListsContainingSameElement() throws Exception {
        list.add("abc");

        List<String> otherList = new ArrayList<>();
        otherList.add("abc");
        assertThat(list.containsAll(otherList), is(equalTo(true)));
    }

    @Test
    public void shouldContainAllElementsWhenListsContainingOneElement() throws Exception {
        list.add("abc");
        list.add("xyz");

        List<String> otherList = new ArrayList<>();
        otherList.add("abc");
        assertThat(list.containsAll(otherList), is(equalTo(true)));
    }

    @Test
    public void shouldContainAllElementsWhenListsContainingSameElements() throws Exception {
        list.add("abc");
        list.add("xyz");

        List<String> otherList = new ArrayList<>();
        otherList.add("abc");
        otherList.add("xyz");
        assertThat(list.containsAll(otherList), is(equalTo(true)));
    }

    @Test
    public void shouldAddAllElementFromCollection() throws Exception {
        List<String> otherList = new ArrayList<>();
        otherList.add("abc");
        list.addAll(otherList);
        assertThat(list.addAll(otherList), is(equalTo(true)));
    }

    @Test
    public void shouldAddAllThreeElementsFromCollection() throws Exception {
        List<String> otherList = new ArrayList<>();
        otherList.add("abc");
        otherList.add("def");
        otherList.add("xyz");
        list.addAll(otherList);
        assertThat(list.addAll(otherList), is(equalTo(true)));
    }

    @Test
    public void shouldAddAllElementFromCollectionAtIndex() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");
        List<String> otherList = new ArrayList<>();
        otherList.add("abc");
        list.addAll(2, otherList);
        assertThat(list.addAll(otherList), is(equalTo(true)));
    }

    @Test
    public void shouldAddAllElementsFromCollectionAtIndexZero() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");

        List<String> otherList = new ArrayList<>();
        otherList.add("xyz");
        list.addAll(0, otherList);

        assertThat(list.indexOf("xyz"), is(equalTo(0)));
        assertThat(list.indexOf("abc"), is(equalTo(1)));
        assertThat(list.indexOf("def"), is(equalTo(2)));
        assertThat(list.indexOf("ghi"), is(equalTo(3)));
    }

    @Test
    public void shouldAddAllTwoElementsFromCollectionAtIndexZero() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");

        List<String> otherList = new ArrayList<>();
        otherList.add("xyz");
        otherList.add("123");
        list.addAll(0, otherList);

        assertThat(list.indexOf("xyz"), is(equalTo(0)));
        assertThat(list.indexOf("123"), is(equalTo(1)));
        assertThat(list.indexOf("abc"), is(equalTo(2)));
        assertThat(list.indexOf("def"), is(equalTo(3)));
        assertThat(list.indexOf("ghi"), is(equalTo(4)));
    }

    @Test
    public void shouldAddAllElementsFromCollectionAtIndexTwo() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");

        List<String> otherList = new ArrayList<>();
        otherList.add("xyz");
        list.addAll(2, otherList);

        assertThat(list.indexOf("abc"), is(equalTo(0)));
        assertThat(list.indexOf("def"), is(equalTo(1)));
        assertThat(list.indexOf("xyz"), is(equalTo(2)));
        assertThat(list.indexOf("ghi"), is(equalTo(3)));
    }

    @Test
    public void shouldAddAllElementsFromCollectionAtIndexThree() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");

        List<String> otherList = new ArrayList<>();
        otherList.add("xyz");
        list.addAll(3, otherList);

        assertThat(list.indexOf("abc"), is(equalTo(0)));
        assertThat(list.indexOf("def"), is(equalTo(1)));
        assertThat(list.indexOf("ghi"), is(equalTo(2)));
        assertThat(list.indexOf("xyz"), is(equalTo(3)));
    }

    @Test
    public void shouldAddAllByIndexWithHighAmountOfElements() throws Exception {
        list.add("a");
        list.add("b");

        final int count = 63;
        List<String> otherList = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            otherList.add(String.valueOf(i));
        }

        list.addAll(1, otherList);

        assertThat(list.indexOf("a"), is(equalTo(0)));
        for(int i = 0; i < count; i++) {
            assertThat(list.indexOf(String.valueOf(i)), is(equalTo(i + 1)));
        }
        assertThat(list.indexOf("b"), is(equalTo(count + 1)));
    }

    @Test
    public void shouldNotAddAllElementsFromCollectionWithNegativeIndex() throws Exception {
        List<String> otherList = new ArrayList<>();
        otherList.add("xyz");

        try {
            list.addAll(-1, otherList);
        } catch(IndexOutOfBoundsException e) {
            return;
        }

        Assert.fail("no IndexOutOfBoundsException thrown");
    }

    @Test
    public void shouldNotAddAllElementsFromCollectionWithIndexAboveRange() throws Exception {
        List<String> otherList = new ArrayList<>();
        otherList.add("xyz");

        try {
            list.addAll(1, otherList);
        } catch(IndexOutOfBoundsException e) {
            return;
        }

        Assert.fail("no IndexOutOfBoundsException thrown");
    }

    @Test
    public void shouldNotRemoveElementFromCollection() throws Exception {
        list.add("abc");
        boolean gotRemoved = list.remove("xyz");
        assertThat(gotRemoved, is(equalTo(false)));
    }

    @Test
    public void shouldNotRemoveElementTwice() throws Exception {
        list.add("abc");

        list.remove("abc");
        boolean gotRemoved = list.remove("abc");

        assertThat(gotRemoved, is(equalTo(false)));
        assertThat(list.size(), is(equalTo(0)));
    }

    @Test
    public void shouldRemoveElementFromCollection() throws Exception {
        list.add("abc");
        boolean gotRemoved = list.remove("abc");
        assertThat(gotRemoved, is(equalTo(true)));
        assertThat(list.size(), is(equalTo(0)));
    }

    @Test
    public void shouldRemoveLastElementFromLongerCollection() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("xyz");
        list.add("abc");
        list.add("def");

        boolean gotRemoved = list.remove("def");

        assertThat(gotRemoved, is(equalTo(true)));
        assertThat(list.size(), is(equalTo(4)));
        assertThat(list.get(0), is(equalTo("abc")));
        assertThat(list.get(1), is(equalTo("xyz")));
        assertThat(list.get(2), is(equalTo("abc")));
        assertThat(list.get(3), is(equalTo("def")));
    }

    @Test
    public void shouldRemoveFirstElementFromLongerCollection() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("xyz");
        list.add("abc");
        list.add("def");

        boolean gotRemoved = list.remove("abc");

        assertThat(gotRemoved, is(equalTo(true)));
        assertThat(list.size(), is(equalTo(4)));
        assertThat(list.get(0), is(equalTo("def")));
        assertThat(list.get(1), is(equalTo("xyz")));
        assertThat(list.get(2), is(equalTo("abc")));
        assertThat(list.get(3), is(equalTo("def")));
    }

    @Test
    public void shouldRemoveAllElementsFromCollection() throws Exception {
        list.add("abc");
        List<String> otherList = new ArrayList<>();
        otherList.add("abc");
        list.removeAll(otherList);
        assertThat(list.removeAll(otherList), is(equalTo(true)));
    }

    @Test
    public void shouldRemoveAllElementsFromLongerCollection() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");
        list.add("abc");
        list.add("xyz");
        List<String> otherList = new ArrayList<>();
        otherList.add("abc");
        otherList.add("xyz");
        list.removeAll(otherList);
        assertThat(list.removeAll(otherList), is(equalTo(true)));
    }

    @Test
    public void shouldRetainAllElementsFromCollection() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");
        list.add("abc");
        list.add("xyz");

        List<String> otherList = new ArrayList<>();
        otherList.add("abc");

        assertThat(list.retainAll(otherList), is(equalTo(true)));
        assertThat(list.size(), is(equalTo(2)));
        assertThat(list.indexOf("abc"), is(equalTo(0)));
        assertThat(list.lastIndexOf("abc"), is(equalTo(1)));
    }

    @Test
    public void shouldNotChangeRetainAllElementsFromCollection() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");
        list.add("xyz");
        List<String> otherList = new ArrayList<>();
        otherList.add("abc");
        otherList.add("def");
        otherList.add("ghi");
        otherList.add("xyz");

        boolean hasChanged = list.retainAll(otherList);

        assertThat(hasChanged, is(equalTo(false)));
        assertThat(list.size(), is(equalTo(4)));
        assertThat(list.indexOf("abc"), is(equalTo(0)));
        assertThat(list.indexOf("def"), is(equalTo(1)));
        assertThat(list.indexOf("ghi"), is(equalTo(2)));
        assertThat(list.indexOf("xyz"), is(equalTo(3)));
    }

    @Test
    public void shouldDeleteWithRetainAllElementsWithEmptyCollection() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");
        list.add("xyz");
        List<String> otherList = new ArrayList<>();

        boolean hasChanged = list.retainAll(otherList);

        assertThat(hasChanged, is(equalTo(true)));
        assertThat(list.size(), is(equalTo(0)));
    }

    @Test
    public void shouldNotRemoveByNegativeIndex() throws Exception {
        try {
            list.remove(-1);
        } catch(IndexOutOfBoundsException e) {
            return;
        }

        Assert.fail("no IndexOutOfBoundsException thrown");
    }

    @Test
    public void shouldNotRemoveByIndexGreaterThanSize() throws Exception {
        try {
            list.remove(1);
        } catch(IndexOutOfBoundsException e) {
            return;
        }

        Assert.fail("no IndexOutOfBoundsException thrown");
    }

    @Test
    public void shouldRemoveFirstElement() throws Exception {
        list.add("abc");

        String removedElement = list.remove(0);
        assertThat(list.size(), is(equalTo(0)));
        assertThat(removedElement, is(equalTo("abc")));
    }

    @Test
    public void shouldRemoveFirstElementOfTwo() throws Exception {
        list.add("abc");
        list.add("def");

        String removedElement = list.remove(0);
        assertThat(list.size(), is(equalTo(1)));
        assertThat(removedElement, is(equalTo("abc")));
        assertThat(list.get(0), is(equalTo("def")));
    }

    @Test
    public void shouldRemoveSecondElementOfTwo() throws Exception {
        list.add("abc");
        list.add("def");

        String removedElement = list.remove(1);
        assertThat(list.size(), is(equalTo(1)));
        assertThat(removedElement, is(equalTo("def")));
        assertThat(list.get(0), is(equalTo("abc")));
    }

    @Test
    public void shouldBeEmptyAfterClear() throws Exception {
        list.add("abc");
        list.add("def");

        list.clear();

        assertThat(list.size(), is(0));
        assertThat(list.isEmpty(), is(true));
        assertThat(list.indexOf("abc"), is(equalTo(-1)));
        assertThat(list.indexOf("def"), is(equalTo(-1)));

    }

    @Test
    public void shouldAddElementAtIndex() throws Exception {
        list.add("abc");
        list.add(0, "def");

        assertThat(list.size(), is(2));
        assertThat(list.indexOf("def"), is(equalTo(0)));
        assertThat(list.indexOf("abc"), is(equalTo(1)));
    }
    @Test
    public void shouldAddElementAtLastIndex() throws Exception {
        list.add("abc");
        list.add("def");
        list.add(2, "xyz");

        assertThat(list.size(), is(3));
        assertThat(list.indexOf("abc"), is(equalTo(0)));
        assertThat(list.indexOf("def"), is(equalTo(1)));
        assertThat(list.indexOf("xyz"), is(equalTo(2)));
    }

    @Test
    public void shouldNotAddElementWithNegativeIndex() throws Exception {
        list.add("abc");
        list.add("def");

        try {
            list.add(-1, "xyz");
        }
        catch (IndexOutOfBoundsException e){
            return;
        }
        Assert.fail("no IndexOutOfBoundsException thrown");
    }

    @Test
    public void shouldNotAddElementByIndexGreaterSize() throws Exception {
        list.add("abc");
        list.add("def");

        try {
            list.add(3, "xyz");
        }
        catch (IndexOutOfBoundsException e){
            return;
        }
        Assert.fail("no IndexOutOfBoundsException thrown");
    }

    @Test
    public void shouldNotCreateSublistForFromIndexGreaterToIndex() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");

        try {
            list.subList(2, 1);
        }
        catch (IndexOutOfBoundsException e) {
            return;
        }
        Assert.fail("no IndexOutOfBoundsException thrown");
    }

    @Test
    public void shouldNotCreateSublistForNegativeIndex() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");

        try {
            list.subList(-1, 1);
        }
        catch (IndexOutOfBoundsException e) {
            return;
        }
        Assert.fail("no IndexOutOfBoundsException thrown");
    }

    @Test
    public void shouldNotCreateSublistForToIndexGreaterSize() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");

        try {
            list.subList(1, 5);
        }
        catch (IndexOutOfBoundsException e) {
            return;
        }
        Assert.fail("no IndexOutOfBoundsException thrown");
    }

    @Test
    public void shouldBeEmptyWithIdenticalFromAndToIndex() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");
        list.add("jkl");
        list.add("mno");
        list.add("pqr");


        List<String> otherList = list.subList(3, 3);

        assertThat(otherList.isEmpty(), is(equalTo(true)));

    }

    @Test
    public void shouldDisplaySublistWithOneElement() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");
        list.add("jkl");
        list.add("mno");
        list.add("pqr");

        List<String> otherList = list.subList(1, 2);

        assertThat(otherList.size(), is(equalTo(1)));
        assertThat(otherList.indexOf("def"), is(equalTo(0)));
    }

    @Test
    public void shouldDisplaySublistWithThreeElements() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");
        list.add("jkl");
        list.add("mno");
        list.add("pqr");

        List<String> otherList = list.subList(1, 4);

        assertThat(otherList.size(), is(equalTo(3)));
        assertThat(otherList.indexOf("def"), is(equalTo(0)));
        assertThat(otherList.indexOf("ghi"), is(equalTo(1)));
        assertThat(otherList.indexOf("jkl"), is(equalTo(2)));
    }


    @Test
    public void shouldConvertEmptyArrayListToArray() throws Exception {
        Object[] objectsAsArray = list.toArray();

        assertThat(objectsAsArray.length, is(equalTo(0)));
    }

    @Test
    public void shouldConvertFilledArrayListToArray() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");

        Object[] objectsAsArray = list.toArray();

        assertThat(objectsAsArray[0], is(equalTo("abc")));
        assertThat(objectsAsArray[1], is(equalTo("def")));
        assertThat(objectsAsArray[2], is(equalTo("ghi")));
        assertThat(objectsAsArray.length, is(equalTo(3)));
    }

    @Test
    public void shouldConvertEmptyArrayListToTypedArray() throws Exception {
        String[] newArray = new String[0];
        String[] objectsAsArray = list.toArray(newArray);

        assertThat(objectsAsArray.length, is(equalTo(0)));
    }

    @Test
    public void shouldConvertFilledArrayListToTypedArray() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");

        String[] newArray = new String[0];
        String[] objectsAsArray = list.toArray(newArray);

        assertThat(objectsAsArray[0], is(equalTo("abc")));
        assertThat(objectsAsArray[1], is(equalTo("def")));
        assertThat(objectsAsArray[2], is(equalTo("ghi")));
        assertThat(objectsAsArray.length, is(equalTo(3)));
    }

    @Test
    public void shouldConvertArrayListToTypedArray() throws Exception {
        list.add("abc");
        list.add("def");
        list.add("ghi");

        String[] newArray = new String[3];
        String[] objectsAsArray = list.toArray(newArray);

        assertThat(objectsAsArray[0], is(equalTo("abc")));
        assertThat(objectsAsArray[1], is(equalTo("def")));
        assertThat(objectsAsArray[2], is(equalTo("ghi")));
        assertThat(objectsAsArray.length, is(equalTo(3)));

        assertThat(newArray[0], is(equalTo("abc")));
        assertThat(newArray[1], is(equalTo("def")));
        assertThat(newArray[2], is(equalTo("ghi")));
        assertThat(newArray.length, is(equalTo(3)));
        assertThat(objectsAsArray, is(sameInstance(newArray)));
    }

}
