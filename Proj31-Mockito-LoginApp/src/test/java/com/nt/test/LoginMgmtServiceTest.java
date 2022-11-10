package com.nt.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.nt.dao.ILoginDAO;
import com.nt.service.ILoginMgmtService;
import com.nt.service.LoginMgmtServiceImpl;

public class LoginMgmtServiceTest {

	private static ILoginMgmtService loginService;
	private static ILoginDAO loginDAOMock;
	
	@BeforeAll
	public static void setUpOnce() {
		//create mock object
		loginDAOMock = Mockito.mock(ILoginDAO.class); //mock() generates InMemory class implementing ILoginDAO having 
													//method definition for authenticate method
		System.out.println(loginDAOMock.getClass()+" "+Arrays.toString(loginDAOMock.getClass().getInterfaces()));
		//create service class object
		loginService = new LoginMgmtServiceImpl(loginDAOMock);
	}
	
	@AfterAll
	public static void clearOnce() {
		loginDAOMock=null;
		loginService=null;
	}
	
	@Test
	public void testLoginWithValidCredentials() {
		//provide stub(temporary functionality) for DAO's authenticate method
		Mockito.when(loginDAOMock.autheticate("tom", "jerry")).thenReturn(1);
		assertTrue(loginService.login("tom", "jerry"));
	}
	
	@Test
	public void testLoginWithInValidCredentials() {
		//provide stub(temporary functionality) for DAO's authenticate method
		Mockito.when(loginDAOMock.autheticate("tom", "jerry1")).thenReturn(0);
		assertFalse(loginService.login("tom", "jerry1"));
	}
	
	@Test
	public void testLoginWithNoCredentials() {
		assertThrows(IllegalArgumentException.class, ()->loginService.login("", ""));
	}
	
	@Test
	public void testRegisterWithSpy() {
		ILoginDAO loginDAOSpy = Mockito.spy(ILoginDAO.class);
		ILoginMgmtService loginService = new LoginMgmtServiceImpl(loginDAOSpy);
		loginService.registerUser("Swati", "admin");
		loginService.registerUser("Tom", "visitor");
		loginService.registerUser("Ravi", "");
		
		Mockito.verify(loginDAOSpy, Mockito.times(1)).addUser("Swati", "admin");
		Mockito.verify(loginDAOSpy, Mockito.times(0)).addUser("Tom", "visitor");
		Mockito.verify(loginDAOSpy, Mockito.never()).addUser("Ravi", "");
	}
}
