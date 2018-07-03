package com.vmall;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );

    }
    private static    int count =0;
    private class Person{
        @Override
        public boolean equals(Object obj) {
            return true;
        }
        @Override
        public int hashCode() {
            return 111;
        }
    }
    @Test
    public  void testSet(){
        Set set = new HashSet<>();
        Person p1 = new Person();
        Person p2 = new Person();
        System.out.println(new Integer(1)==new Integer(1));
        set.add(p1);
        set.add(p2);
        System.out.println(set);
    }
}
