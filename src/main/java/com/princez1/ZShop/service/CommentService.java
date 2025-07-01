package com.princez1.ZShop.service;

import com.princez1.ZShop.dtos.CommentDTO;
import com.princez1.ZShop.entities.Comment;
import com.princez1.ZShop.exceptions.DataNotFoundException;
import com.princez1.ZShop.responses.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    Comment insertComment(CommentDTO comment);

    void deleteComment(Long commentId);
    void updateComment(Long id, CommentDTO commentDTO) throws DataNotFoundException;

    List<CommentResponse> getCommentsByUserAndProduct(Long userId, Long productId);
    List<CommentResponse> getCommentsByProduct(Long productId);
}
