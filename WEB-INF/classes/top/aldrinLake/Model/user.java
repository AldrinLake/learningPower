package top.aldrinLake.Model;

public class user {
	int iduser;
	String pwd;
	String name;
	int grades;
	String submit_time;
	String submit_state;
	int number_of_failed;
	int last_grades;
	String last_submit_time;
	int qualifiedOrNot;
	public int getQualifiedOrNot() {
		return qualifiedOrNot;
	}
	public void setQualifiedOrNot(int qualifiedOrNot) {
		this.qualifiedOrNot = qualifiedOrNot;
	}
	public int getLast_grades() {
		return last_grades;
	}
	public String getLast_submit_time() {
		return last_submit_time;
	}
	public void setLast_submit_time(String last_submit_time) {
		this.last_submit_time = last_submit_time;
	}
	public void setLast_grades(int last_grades) {
		this.last_grades = last_grades;
	}
	public int getNumber_of_failed() {
		return number_of_failed;
	}
	public void setNumber_of_failed(int number_of_failed) {
		this.number_of_failed = number_of_failed;
	}
	public user(int iduser) {
		this.iduser = iduser;
	}
	public user() {
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGrades() {
		return grades;
	}
	public void setGrades(int grades) {
		this.grades = grades;
	}
	public String getSubmit_time() {
		return submit_time;
	}
	public void setSubmit_time(String submit_time) {
		this.submit_time = submit_time;
	}
	public String getSubmit_state() {
		return submit_state;
	}
	public void setSubmit_state(String submit_state) {
		this.submit_state = submit_state;
	}
}
