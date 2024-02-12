package edu.jcourse.sarafan.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "message")
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
}