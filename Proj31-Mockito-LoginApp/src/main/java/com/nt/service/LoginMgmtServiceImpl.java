package com.nt.service;

import com.nt.dao.ILoginDAO;

public class LoginMgmtServiceImpl implements ILoginMgmtService{
	
	private ILoginDAO loginDAO;
	
	public LoginMgmtServiceImpl(ILoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	public boolean login(String username, String pwd) {
		if(username.equalsIgnoreCase("") || pwd.equalsIgnoreCase(""))
			throw new IllegalArgumentException("Invalid credentials");
		//use DAO
		int count = loginDAO.autheticate(username, pwd);
		System.out.println("count "+count);
		if(count==1)
			return true;		
		return false;
	}

	public String registerUser(String user, String role) {
		if(!role.equalsIgnoreCase("") && !role.equalsIgnoreCase("visitor")) {
			loginDAO.addUser(user, role);
			return "User Added";
		}
		else {
			return "User not Added";
		}
	}
}
