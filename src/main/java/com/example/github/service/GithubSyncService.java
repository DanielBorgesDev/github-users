package com.example.github.service;

import com.example.github.domain.User;
import com.example.github.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubSyncService implements CommandLineRunner {

  private final UserRepository userRepository;
  private final RestTemplate restTemplate = new RestTemplate();

  public GithubSyncService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  record GithubUserDto(String login, String url) {}

  @Override
  public void run(String... args) {
    String endpoint = "https://api.github.com/users?per_page=30";
    ResponseEntity<GithubUserDto[]> resp =
        restTemplate.getForEntity(endpoint, GithubUserDto[].class);

    if (resp.getBody() != null) {
      for (GithubUserDto dto : resp.getBody()) {
        if (!userRepository.existsByLogin(dto.login())) {
          User u = new User();
          u.setLogin(dto.login());
          u.setUrl(dto.url());
          userRepository.save(u);
        }
      }
    }
  }
}
