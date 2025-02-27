package org.project.resumeboost.member.entity;

import java.util.List;

import org.project.resumeboost.basic.common.BasicTime;
import org.project.resumeboost.basic.common.Role;
import org.project.resumeboost.board.entity.BoardEntity;
import org.project.resumeboost.item.entity.ItemEntity;
import org.project.resumeboost.member.dto.MemberDto;
import org.project.resumeboost.pay.entity.PayEntity;
import org.project.resumeboost.reply.entity.ReplyEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@ToString(exclude = { "boardEntities", "replyEntities", "itemEntities", "payEntities", "memberImgEntities",
    "memberPtEntities" })
@Table(name = "member_tb")
public class MemberEntity extends BasicTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(nullable = false, unique = true)
  private String userEmail;

  @Column(nullable = false)
  private String userPw;

  // ??΄
  @Column(nullable = false)
  private int age;

  // μ§??­
  @Column(nullable = false)
  private String address;

  // λ³λͺ
  @Column(nullable = false, unique = true)
  private String nickName;

  // ?€?  ?΄λ¦?
  @Column(nullable = false)
  private String userName;

  // ??Έ?€λͺ? ? λͺ?
  private String detailTitle;

  // ??Έ?€λͺ?
  @Column(length = 5000)
  private String detail;

  // λ¦¬λ·°?
  @Column(nullable = true, columnDefinition = "int default 0")
  private int replyCount;

  @Column(nullable = true, columnDefinition = "int default 0")
  private int viewCount;

  // ?΄κ°? ??±? ?κΈ? ?
  @Column(nullable = true, columnDefinition = "int default 0")
  private int myReplyCount;

  // ?΄κ°? ??±? κ²μκΈ? ?
  @Column(nullable = true, columnDefinition = "int default 0")
  private int myPostCount;

  // κ²½λ ₯
  private String career;

  // κΆν MEMBER(?Όλ°ν?), MENTOR(λ©ν ??), ADMIN
  @Enumerated(EnumType.STRING)
  private Role role;

  // ?λ‘ν ?¬μ§? ? λ¬?
  @Column(nullable = false)
  private int attachFile;

  // ?¬?Έ?΄λ¦¬μ€ ? λ¬?
  @Column(nullable = false)
  private int portfolioFile;

  @Column(nullable = false)
  private String phone;

  // ??λ‘κ·Έ?Έ
  @Column(columnDefinition = "boolean default false")
  private Boolean social;

  // κ²μ?
  @JsonIgnore
  @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<BoardEntity> boardEntities;

  // λ¦¬λ·°
  @JsonIgnore
  @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<ReplyEntity> replyEntities;

  // ??
  @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<ItemEntity> itemEntities;

  // κ²°μ 
  @JsonIgnore
  @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<PayEntity> payEntities;

  // ?λ‘ν?¬μ§? ??Ό
  @JsonIgnore
  @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<MemberImgEntity> memberImgEntities;

  // ?΄?Έ?΄λ¦¬μ€ ??Ό
  @JsonIgnore
  @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<MemberPtEntity> memberPtEntities;
}
