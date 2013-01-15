package sa12.group9.server.view;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class RegisterBean{
  String name;
  String fname;
  String mname;
  String caddress;
  String paddress;
  String sex;
  Date dob;
  Long phoneno;
  Long mobileno;

  public RegisterBean(){}

  public String getName(){
  return name;
  }

  public void setName(String name){
  this.name = name;
  }

  public String getFname(){
  return fname;
  }

  public void setFname(String fname){
  this.fname = fname;
  }

  public String getMname(){
  return mname;
  }

  public void setMname(String mname){
  this.mname = mname;
  }

  public String getCaddress(){
  return caddress;
  }

  public void setCaddress(String caddress){
  this.caddress = caddress;
  }

  public String getPaddress(){
  return paddress;
  }

  public void setPaddress(String paddress){
  this.paddress = paddress;
  }

  public String getSex(){
  return sex;  
  }

  public void setSex(String sex){
  this.sex = sex;
  }

  public Date getDob(){
  return dob;
  }

  public void setDob(Date dob){
  this.dob = dob;
  }

  public Long getPhoneno(){
  return phoneno;
  }

  public void setPhoneno(Long phoneno){
  this.phoneno = phoneno;
  }

  public Long getMobileno(){
  return mobileno;
  }

  public void setMobileno(Long mobileno){
  this.mobileno = mobileno;
  }

}
