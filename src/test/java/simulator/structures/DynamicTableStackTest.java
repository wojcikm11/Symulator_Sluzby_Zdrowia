package simulator.structures;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DynamicTableStackTest {

    private StackImplementation<Integer> stack;
    @Before
    public void setUp() {
        stack = new DynamicTableStack<>();
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_NullPointerException_while_popping_from_empty_stack() {
        //when
        stack.pop();

        //then
        assert false;
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_NullPointerException_while_lookingUp_the_top_of_empty_stack() {
        //when
        stack.lookUpTop();

        //then
        assert false;
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_NullPointerException_while_lookingUp_not_existing_element_under_the_top() {
        //given
        stack.push(5);

        //when
        stack.lookUpUnderTheTop();

        //then
        assert false;
    }

    @Test
    public void should_return_expected_elements_when_looked_up() {
        //given
        int e1 = 1, e2 = 2;

        //when
        stack.push(1);
        stack.push(2);

        //then
        assertEquals(e2, (int) stack.lookUpTop());
        assertEquals(e1, (int) stack.lookUpUnderTheTop());
    }
    @Test
    public void should_return_expected_elements_if_pushed() {
        //given
        int e1 = 1, e2 = 2;

        //when
        stack.push(1);
        stack.push(2);

        //then
        assertEquals(e2,(int) stack.pop());
        assertEquals(e1,(int) stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    public  void should_be_empty_before_and_not_empty_after_element_pushed() {
        assertTrue(stack.isEmpty());
        stack.push(9);
        assertFalse(stack.isEmpty());
    }
}