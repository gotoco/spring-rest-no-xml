package org.finance.app.core.domain.common;

import junit.framework.Assert;
import org.finance.app.annotations.UnitTest;
import org.finance.app.sharedcore.objects.Money;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.math.BigDecimal;
import java.util.Currency;

@Category(UnitTest.class)
public class MoneyTest {

    @Test
    public void multiplyTest(){
        //Given
        Money money = new Money(new BigDecimal(10));

        //When
        Money multiplicationResult = money.multiplyBy(new BigDecimal(10));

        //Then
        Assert.assertTrue(multiplicationResult.equals(new Money(100)));
    }

    @Test
    public void addTest(){
        //Given
        Money money = new Money(new BigDecimal(10));

        //When
        Money addResult = money.add(new Money(10));

        //Then
        Assert.assertTrue(addResult.equals(new Money(20)));
    }

    @Test
    public void subtractTest(){
        //Given
        Money money = new Money(new BigDecimal(10));

        //When
        Money subtractResult = money.subtract(new Money(10));

        //Then
        Assert.assertTrue(subtractResult.equals(new Money(0)));
    }

    @Test
    public void shouldThrowExceptionDuringOperationWithDifferentCurrency(){
        //Given
        Money money = new Money(new BigDecimal(10), Currency.getInstance("EUR"));
        Money subtractResult = null;

        //When
        try {
            subtractResult = money.subtract(new Money(10, Currency.getInstance("USA")));

            Assert.fail();
        } catch (IllegalArgumentException ex){

        //Then
        Assert.assertNull(subtractResult);
        }

    }
}
