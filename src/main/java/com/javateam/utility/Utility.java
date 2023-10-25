package com.javateam.utility;

import com.javateam.model.User;
import com.javateam.service.CommentService;
import com.javateam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Utility {
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

   public Integer notifications(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       User user = userService.findByEmail(authentication.getName());
       Integer numberOfCommentNotification = commentService.findNotification(user.getUserId());
       Integer numberOfVotesNotification = commentService.findNumberVoteNotification(user.getUserId());
       Integer numberOfChildCommentNotification = commentService.countUnviewedChildCommentsByUserId(user.getUserId());
       System.out.println(numberOfCommentNotification);
       return numberOfCommentNotification+numberOfVotesNotification+numberOfChildCommentNotification;
   }
}
