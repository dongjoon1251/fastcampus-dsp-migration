package fastcampus.ad.migration.application.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import fastcampus.ad.migration.domain.migration.user.MigrationUser;
import fastcampus.ad.migration.domain.migration.user.MigrationUserRepository;
import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MigrationUserServiceTest {

  @Mock
  MigrationUserRepository repository;

  @InjectMocks
  MigrationUserService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void agree() {
    when(repository.findById(1L)).thenReturn(Optional.empty());
    when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    MigrationUserResult result = service.agree(1L);

    assertAll(() -> assertThat(result.id()).isEqualTo(1L),
        () -> assertThat(result.status()).isEqualTo(MigrationUserStatus.AGREED),
        () -> assertThat(result.agreedDate()).isNotNull(),
        () -> assertThat(result.updateDate()).isNotNull());
  }

  @Test
  void 이미_마이그레이션에_동의했으면_에러() {
    when(repository.findById(1L)).thenReturn(Optional.of(MigrationUser.agreed(1L)));

    assertThatThrownBy(() -> service.agree(1L))
        .isInstanceOf(AlreadyAgreedException.class);
  }

  @Test
  void 존재하지_않으면_에러() {
    when(repository.findById(1L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.findById(1L))
        .isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  void 동의하지_않았으면_true() {
    when(repository.findById(1L)).thenReturn(Optional.empty());

    boolean result = service.isDisagreed(1L);

    assertThat(result).isTrue();
  }

  @Test
  void 동의했으면_false() {
    when(repository.findById(1L)).thenReturn(Optional.of(MigrationUser.agreed(1L)));

    boolean result = service.isDisagreed(1L);

    assertThat(result).isFalse();
  }
}