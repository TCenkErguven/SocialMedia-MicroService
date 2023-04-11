package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.entity.enums.ERole;
import com.bilgeadam.repository.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder //extend'lendiği bir classı olduğu için Builder yerine konulur ata classa'da bu anotasyon koyulur bu durumda
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblauth")
public class Auth extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private String activationCode;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ERole role = ERole.USER;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EStatus status = EStatus.PENDING;
}
