package feefo.note.web.app.ws.infrastructure.user.controller;

public class UserResponseDto {

  private Long id;

  private String username;

  public UserResponseDto() {

  }

  public UserResponseDto(Long id, String username) {
    this.id = id;
    this.username = username;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
