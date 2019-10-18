package com.ww.springbootcommunity.service;


import com.ww.springbootcommunity.dto.CommentDTO;
import com.ww.springbootcommunity.entity.Comment;
import com.ww.springbootcommunity.entity.Notification;
import com.ww.springbootcommunity.entity.Question;
import com.ww.springbootcommunity.entity.User;
import com.ww.springbootcommunity.enums.CommentTypeEnum;
import com.ww.springbootcommunity.enums.NotificationStatusEnum;
import com.ww.springbootcommunity.enums.NotificationTypeEnum;
import com.ww.springbootcommunity.mapper.CommentMapper;
import com.ww.springbootcommunity.mapper.NotificationMapper;
import com.ww.springbootcommunity.mapper.QuestionMapper;
import com.ww.springbootcommunity.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    NotificationMapper notificationMapper;

    @Transactional
    public void insertComment(Comment comment, User commentator) {
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()) {


            //回复评论
            commentMapper.insertComment(comment);

            Question question = questionMapper.queryQuestionById(comment.getParentId());

            //增加评论数
            Comment Ccomment = new Comment();
            Ccomment.setId(comment.getParentId());
            Ccomment.setCommentCount(1);
            System.out.println(Ccomment);
            commentMapper.incCommentCount(Ccomment);

            //查出评论的对象
            Comment dbcomment = commentMapper.queryCommentById(comment.getParentId());

            //创建通知
            createNotify(comment, dbcomment.getCommentator(),commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());


        }else {
            //回复问题
            commentMapper.insertComment(comment);
            //得到这个id=comment.getParentId()的对象
            Question question = questionMapper.queryQuestionById(comment.getParentId());
            //然后再加上评论数，，把question传进去，先CommentCount+1，
            question.setCommentCount(1);
            questionMapper.incCommentCount(question);

            //创建通知
            createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());

        }
    }
    //创建通知方法
    private void createNotify(Comment comment, Integer receiver, String notificationName, String outerTitle, NotificationTypeEnum notificationType, Integer outerId) {
        Notification notification = new Notification();
        notification.setNotifier(comment.getCommentator());//通知者就是当前的评论人
        notification.setReceiver(receiver);
        notification.setOuterId(outerId);
        notification.setType(notificationType.getType());
        notification.setCreateTime(System.currentTimeMillis());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setNotifierName(notificationName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insertNotification(notification);
    }

    public List<CommentDTO> listQuestionById(Integer id) {


        //传进来的是一个Question的id，要根据这个comment的ParentId=id去查有多少条数据
        Comment comment = new Comment();
        comment.setParentId(id);
        //要确定是评论
        comment.setType(CommentTypeEnum.QUSETION.getType());
        List<Comment> comments = commentMapper.queryCommentList(comment);

        //根据comment里面的Commentator去查用户信息
        if(comments.size() == 0){
            return new ArrayList<>();
        }

        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment c : comments) {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(c,commentDTO);
            commentDTO.setUser(userMapper.findById(c.getCommentator()));
            commentDTOS.add(commentDTO);
        }

        return commentDTOS;
    }

    public List<CommentDTO> queryCommentList(Comment comment) {
        List<Comment> comments = commentMapper.queryCommentList(comment);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment c : comments) {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(c,commentDTO);
            commentDTO.setUser(userMapper.findById(c.getCommentator()));
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }
}
