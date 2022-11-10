package com.nt.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.nt.dao.ILoginDAO;
import com.nt.service.ILoginMgmtService;
import com.nt.service.LoginMgmtServiceImpl;

public class LoginMgmtServiceTestAnno {

	@InjectMocks //to inject mock or spy object to service class
	private  LoginMgmtServiceImpl loginService;
	@Mock
	private ILoginDAO loginDAOMock;
//	@Spy
//	private ILoginDAO loginDAOSpy;
	public LoginMgmtServiceTestAnno() {
		MockitoAnnotations.openMocks(this); // call this method in @Before or constructor to initialize annotations
	}
	
	@Test
	public void testLoginWithValidCredentials() {
		//provide stub(temporary functionality) for DAO's authenticate method
		//Mockito.when(loginDAOMock.autheticate("tom", "jerry")).thenReturn(1);
		BDDMockito.given(loginDAOMock.autheticate("tom", "jerry")).willReturn(1);
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
