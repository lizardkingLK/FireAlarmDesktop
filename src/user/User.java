package user;

public class User {
	private static User userInstance;
	private String email,password,name,phone,address,bio,img;

	private User() {}

	public static User getInstance() {
		if(userInstance == null) {
			synchronized (User.class) {
				userInstance = new User();
			}
		}

		return userInstance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + ", name=" + name + ", phone=" + phone + ", address="
				+ address + ", bio=" + bio + ", img=" + img + "]";
	}

}
