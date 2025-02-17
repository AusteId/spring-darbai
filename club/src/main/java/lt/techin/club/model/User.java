package lt.techin.club.model;

import jakarta.persistence.*;
import lt.techin.club.model.Registration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user")
  private List<Registration> registrations;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles;

  public User() {
  }

  public User(String username, String password, List<Role> roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Registration> getRegistrations() {
    return registrations;
  }

  public void setRegistrations(List<Registration> registrations) {
    this.registrations = registrations;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }
}

