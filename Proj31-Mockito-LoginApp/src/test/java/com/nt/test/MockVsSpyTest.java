package com.nt.test;

import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class MockVsSpyTest {
	
	@Test
	public void testList() {
		List<String> mockList = Mockito.mock(ArrayList.class); //mock
		List<String> spyList = Mockito.spy(new ArrayList<>()); //spy
		mockList.add("table");
		Mockito.when(mockList.size()).thenReturn(10); //stub on mock
		spyList.add("table");
		Mockito.when(spyList.size()).thenReturn(10); //stub on spy
		System.out.println(mockList.size()+" "+spyList.size()); //o/p - 10 10 but if stubbing is not done the o/p will be 0 1.
	}
}
