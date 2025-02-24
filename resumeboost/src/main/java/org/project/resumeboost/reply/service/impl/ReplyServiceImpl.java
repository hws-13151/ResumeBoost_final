package org.project.resumeboost.reply.service.impl;

import org.project.resumeboost.board.repository.BoardRepository;
import org.project.resumeboost.member.repository.MemberRepository;
import org.project.resumeboost.reply.dto.ReplyDto;
import org.project.resumeboost.reply.entity.ReplyEntity;
import org.project.resumeboost.reply.repository.ReplyRepository;
import org.project.resumeboost.reply.service.ReplyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
  private final ReplyRepository replyRepository;
  private final MemberRepository memberRepository;
  private final BoardRepository boardRepository;

  public void replyCountFn(Long boardId) {
    boardRepository.replyCountFn(boardId);
  }

  public void replyCountDeleteFn(Long boardId) {
    boardRepository.replyCountDeleteFn(boardId);
  }

  public void myReplyCount(Long memberId) {
    memberRepository.myReplyCount(memberId);
  }

  @Override
  public Page<ReplyDto> boardReply(Long id, Pageable pageable) {
    Page<ReplyEntity> replyEntities = null;

    replyEntities = replyRepository.findAllByBoardEntity_Id(id, pageable);

    return replyEntities.map(ReplyDto::toReplyDto);
  }

  @Override
  public void replyInsert(ReplyDto replyDto) {
    Long memberId = memberRepository.findById(replyDto.getMemberId())
        .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다.")).getId();
    myReplyCount(memberId);
    Long boardId = boardRepository.findById(replyDto.getBoardId())
        .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다.")).getId();
    replyCountFn(boardId);

    replyRepository.save(ReplyEntity.toInsertReplyEntity(replyDto));
  }

  public void myReplyCountDelete(Long memberId) {
    memberRepository.myReplyCountDelete(memberId);
  }

  public void boardReplyCountDelete(Long boardId) {
    boardRepository.replyCountDeleteFn(boardId);
  }

  @Override
  public void replyDelete(Long replyId) {

    ReplyEntity reply = replyRepository.findById(replyId)
        .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

    Long memberId = reply.getMemberEntity().getId();
    Long boardId = reply.getBoardEntity().getId();

    myReplyCountDelete(memberId);
    boardReplyCountDelete(boardId);

    replyRepository.deleteById(replyId);
  }
}
