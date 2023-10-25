package com.javateam.controller;

import com.javateam.model.*;
import com.javateam.service.CommentService;
import com.javateam.service.PostService;
import com.javateam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CommentController {
    private PostService postService;
    private CommentService commentService;
    private UserService userService;
    @Autowired
    public CommentController(PostService postService, CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/addComment/{postId}")
    public String addComment(
            @PathVariable Integer postId,
            @RequestParam String content
    ){
        Post post = postService.findById(postId);
        Comment comment = new Comment();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);

        commentService.saveComment(comment);
        return "redirect:/"+post.getPostId();
    }
    @PostMapping("/replyOnComment/{commentId}")
    public String saveReplyComment(@PathVariable Integer commentId, @RequestParam String content) {
        Comment parentComment = commentService.getCommentById(commentId);

        if (parentComment.getParentComment() != null) {
            Comment newReplyComment = new Comment();
            newReplyComment.setContent(content);
            newReplyComment.setParentComment(parentComment);
            newReplyComment.setPost(parentComment.getPost());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByEmail(authentication.getName());
            newReplyComment.setUser(user);

            commentService.saveComment(newReplyComment);

            return "redirect:/viewReplies/"+parentComment.getParentComment().getCommentId();
        } else {
            Comment newReplyComment = new Comment();
            newReplyComment.setContent(content);
            newReplyComment.setParentComment(parentComment);
            newReplyComment.setPost(parentComment.getPost());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByEmail(authentication.getName());
            newReplyComment.setUser(user);

            commentService.saveComment(newReplyComment);
        }

        return "redirect:/" + parentComment.getPost().getPostId();
    }

    @GetMapping("/viewReplies/{commentId}")
    public String viewReplies(@PathVariable Integer commentId,
                              Model model
    ){
        Comment comment = commentService.getCommentById(commentId);
        List<Comment> replies = comment.getReplies();
        Post post = comment.getPost();

        model.addAttribute("comment",comment);
        model.addAttribute("post",post);
        model.addAttribute("replies",replies);

        return "view-replies";
    }

    @GetMapping("/editComment/{commentId}")
    public String editComment(@PathVariable Integer commentId,
                              Model model) {
        Comment comment = commentService.getCommentById(commentId);
        model.addAttribute("comment", comment);

        return "comment_edit";
    }

    @PostMapping("/editComment/{commentId}")
    public String saveEditedComment(@PathVariable Integer commentId,
                                    @RequestParam(value = "content")String content,
                                    @RequestParam(value = "page", required = false)String page) {
        Comment existingComment = commentService.getCommentById(commentId);
        existingComment.setContent(content);
        commentService.saveComment(existingComment);

        if(page != null && page.equals("profile")) {
            return "redirect:/profile/comment";
        }

        return "redirect:/" + existingComment.getPost().getPostId();
    }

    @GetMapping("/cancelEdit/{commentId}")
    public String cancelEdit(@PathVariable Integer commentId) {
        Comment comment = commentService.getCommentById(commentId);

        return "redirect:/" + comment.getPost().getPostId();
    }

    @GetMapping("/profile/cancelEdit/{commentId}")
    public String cancelProfileEdit(@PathVariable Integer commentId) {
        Comment comment = commentService.getCommentById(commentId);

        return "redirect:/profile/comment";
    }

    @GetMapping("/deleteComment/{commentId}")
    public String deleteComment(@PathVariable Integer commentId) {
        Comment comment = commentService.getCommentById(commentId);
        commentService.deleteCommentById(commentId);

        return "redirect:/" + comment.getPost().getPostId();
    }
    @GetMapping("/profile/deleteComment/{commentId}")
    public String deleteProfileComment(@PathVariable Integer commentId) {
        Comment comment = commentService.getCommentById(commentId);
        commentService.deleteCommentById(commentId);

        return "redirect:/profile/comment";
    }
    @GetMapping("/comments/upvote/{commentId}/{voteType}")
    public String upvote(@PathVariable Integer commentId, @PathVariable VoteType voteType, Model model) {
        Comment comment = commentService.getCommentById(commentId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Vote existingVote = commentService.getVoteByComment(user, comment);

        if (existingVote != null) {
            if (existingVote.getVoteType() != voteType) {
                existingVote.setVoteType(voteType);
                commentService.updateVote(existingVote);
                Integer voteCount = comment.getVoteCount();
                comment.setVoteCount(voteCount + 1);
            }
        } else {
            Vote vote = new Vote();
            vote.setComment(comment);
            vote.setUser(user);
            vote.setVoteType(voteType);
            commentService.createVote(vote);

            Integer voteCount = comment.getVoteCount();
            comment.setVoteCount(voteCount + 1);
        }

        model.addAttribute("voteType", voteType);

        commentService.saveComment(comment);

        if(comment.getParentComment() != null) {
            return "redirect:/viewReplies/"+comment.getParentComment().getCommentId();
        }

        return "redirect:/"+comment.getPost().getPostId();
    }

    @GetMapping("/comments/downvote/{commentId}/{voteType}")
    public String downvote(@PathVariable Integer commentId, @PathVariable VoteType voteType, Model model) {
        Comment comment = commentService.getCommentById(commentId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Vote existingVote = commentService.getVoteByComment(user, comment);

        if (existingVote != null) {
            if (existingVote.getVoteType() != voteType) {
                existingVote.setVoteType(voteType);
                commentService.updateVote(existingVote);
                Integer voteCount = comment.getVoteCount();
                comment.setVoteCount(voteCount - 1);
            }
        } else {
            Vote vote = new Vote();
            vote.setComment(comment);
            vote.setUser(user);
            vote.setVoteType(voteType);
            commentService.createVote(vote);

            Integer voteCount = comment.getVoteCount();
            comment.setVoteCount(voteCount - 1);
        }

        model.addAttribute("voteType", voteType);

        commentService.saveComment(comment);

        if(comment.getParentComment() != null) {
            return "redirect:/viewReplies/"+comment.getParentComment().getCommentId();
        }

        return "redirect:/"+comment.getPost().getPostId();
    }

}
