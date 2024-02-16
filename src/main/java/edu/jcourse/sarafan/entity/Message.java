package edu.jcourse.sarafan.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false, exclude = {"comments", "user"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "message")
@ToString(exclude = {"comments", "user"})
public class Message extends AuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "text")
    private String text;
    @Column(name = "link")
    private String link;
    @Column(name = "link_title")
    private String linkTitle;
    @Column(name = "link_description")
    private String linkDescription;
    @Column(name = "link_cover")
    private String linkCover;
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;
    @Builder.Default
    @OneToMany(mappedBy = "message", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}