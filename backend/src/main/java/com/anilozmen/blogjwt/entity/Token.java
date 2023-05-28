package com.anilozmen.blogjwt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @SequenceGenerator(name = "token_id_seq", sequenceName = "token_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_id_seq")
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "token_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean expired;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
