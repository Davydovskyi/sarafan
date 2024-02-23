package edu.jcourse.sarafan.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false, exclude = {"subscribers", "subscriptions"})
@ToString(exclude = {"subscribers", "subscriptions"})
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends AuditingEntity<String> {
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

    @Builder.Default
    @OneToMany(mappedBy = "subscriber")
    private List<UserSubscription> subscribers = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "subscription")
    private List<UserSubscription> subscriptions = new ArrayList<>();
}