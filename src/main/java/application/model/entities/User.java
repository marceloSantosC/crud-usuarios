package application.model.entities;

public class User {
	private Integer iduser;
	private String name;
	private String username;
	private String email;
	
	public User() {}
	
	public User(String name, String username, String email) {
		this.name = name;
		this.username = username;
		this.email = email;
	}
	
	public User(Integer iduser, String name, String username, String email) {
		this.iduser = iduser;
		this.name = name;
		this.username = username;
		this.email = email;
	}

	public Integer getIduser() {
		return iduser;
	}
	
	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iduser == null) ? 0 : iduser.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (iduser == null) {
			if (other.iduser != null)
				return false;
		} else if (!iduser.equals(other.iduser))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "{iduser=" + iduser + ", name=" + name + ", username=" + username + ", password=" + ", email=" + email + "}";
	}	
}
