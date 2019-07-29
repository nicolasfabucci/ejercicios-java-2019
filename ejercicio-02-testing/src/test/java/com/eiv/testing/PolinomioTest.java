package com.eiv.testing;

import java.math.BigDecimal;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PolinomioGrado2Test.class, PolinomioGrado3Test.class, PolinomioGrado13Test.class })
public class PolinomioTest {


    public static class BigDecimalEquals extends TypeSafeMatcher<BigDecimal> {

        private BigDecimal value;
        
        public BigDecimalEquals(BigDecimal value) {
            super();
            this.value = value;
        }

        @Override
        public void describeTo(Description description) {
            // TODO Auto-generated method stub
            
        }

        @Override
        protected boolean matchesSafely(BigDecimal item) {
            if (item == null) {
                return false;
            }
            return item.compareTo(value) == 0;
        }
        
        public static Matcher<BigDecimal> areEquals(BigDecimal value) {
            return new BigDecimalEquals(value);
        }
    }
}
