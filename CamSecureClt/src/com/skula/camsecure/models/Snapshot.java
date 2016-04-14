package com.skula.camsecure.models;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Snapshot implements KvmSerializable {
	public String mailAddress;
	public String login;
	public String passWord;

	public Snapshot() {
	}

	public Snapshot(String mailAddress, String login, String passWord) {
		this.mailAddress = mailAddress;
		this.login = login;
		this.passWord = passWord;
	}

	public Object getProperty(int arg0) {

		switch (arg0) {
		case 0:
			return login;
		case 1:
			return mailAddress;
		case 2:
			return passWord;
		}

		return null;
	}

	public int getPropertyCount() {
		return 3;
	}

	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "login";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "mailAddress";
			break;
		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "passWord";
			break;
		default:
			break;
		}
	}

	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			login = value.toString();
			break;
		case 1:
			mailAddress = value.toString();
			break;
		case 2:
			passWord = value.toString();
			break;
		default:
			break;
		}
	}

}