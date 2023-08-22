package com.book.member.vo;

import org.springframework.stereotype.Component;
@Component("memberVO")
public class MemberVO {
	private String member_id;
	private String member_pw;

	private String member_name;

	public String getBookshop_money() {
		return bookshop_money;
	}

	public void setBookshop_money(String bookshop_money) {
		this.bookshop_money = bookshop_money;
	}

	private String hp1;
	private String subAddress;
	private String bookshop_money;
	private String joinDate;
	private String del_yn;
	private String member_address;
	public String getMember_address() {
		return member_address;
	}



	public void setMember_address(String member_address) {
		this.member_address = member_address;
	}

	private String zipcode;
    private String address;
    
    
    public String getMember_name() {
    	return member_name;
    }
    
    
    
    public void setMember_name(String member_name) {
    	this.member_name = member_name;
    }
    
    
    
    public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getSubAddress() {
		return subAddress;
	}



	public void setSubAddress(String subAddress) {
		this.subAddress = subAddress;
	}



	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_pw() {
		return member_pw;
	}

	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}


	public String getHp1() {
		return hp1;
	}

	public void setHp1(String hp1) {
		this.hp1 = hp1;
	}



	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}



	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getDel_yn() {
		return del_yn;
	}

	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}

}