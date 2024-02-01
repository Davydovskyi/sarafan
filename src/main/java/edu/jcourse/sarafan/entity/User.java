package edu.jcourse.sarafan.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends AuditingEntity<String>{
    @Id
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "user_pic")
    private String userPic;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "locale")
    private String locale;
    @Column(name = "last_visit")
    private LocalDateTime lastVisit;
}