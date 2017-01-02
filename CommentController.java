package cn.edu.bjtu.weibo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.bjtu.weibo.model.BaseContent;
import cn.edu.bjtu.weibo.model.Comment;
import cn.edu.bjtu.weibo.model.Result;
import cn.edu.bjtu.weibo.service.CommentMeService;
import cn.edu.bjtu.weibo.service.CommentMessageService;

/**
 * Commentcontroller 
 * 
 * 总共实现两个方法：
 * 
 * 1.实现返回和改用户有关的所有评论
 * 调用第一个方法的url http://ip:port/c/comment1/
 * 
 * 2.当用户操作了，进行评论，将用户的评论传入数据库
 * 调用第二个方法的url http://ip:port/c/comment2/
 * 
 * @author a
 *
 */


@RestController
@RequestMapping("/c")
public class CommentController {
    
	@Autowired
	private CommentMeService commentMeService;
	
	@Autowired
	private CommentMessageService commentMessageService;
    

	
	/**
	 * 获取当前这个用户的评论
	 * 
	 * 将获取和这个用户相关的所有评论返回给前端
	 * 
	 * 前端解析后，直接展示即可
	 * 
	 * 成功后的tipMsg:get comments successfully"
	 * 
	 * @param comment
	 * @return 返回一个Result 给前端
	 */
	
	@RequestMapping(value = "/comment1",method=RequestMethod.POST)
	public Result getCommentInfo(@RequestBody Comment comment) {
		
		Result result = new Result();
		List<BaseContent> token= commentMeService.getCommentMeContentList(comment.getUserId(), 1, 1);
		if(token == null || token.isEmpty())
		{
			result.setStatus(Result.FAILED);
			result.setTipCode("401");
			result.setTipMsg("falied");
			return result;
		}
	   
		result.setStatus(Result.SUCCESS);
		result.setTipCode("200");
		result.setTipMsg("get comments successfully");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);	
		result.setData(map);
		
		return result;
	}
	
	
	
	

	
	/**
	 * 前端post方法	
	 * 
	 * 提交评论数据到后端
	 * 
	 * 后端写入数据库
	 * 
	 * 成功后的tipMsg:comment success
	 * 
	 * @param comment 被评论的评论
	 * @param comment2  此次评论的评论
	 * @return 返回一个Result给前端
	 */
	
	@RequestMapping(value = "/comment2",method=RequestMethod.POST)
	public Result setComment(@RequestBody Comment comment,Comment comment2){
		
		Result result = new Result();
		
        boolean isCommentToCommentSuccess = commentMessageService.CommentToWeibo(comment2.getUserId(),comment.getCommentOrWeiboId(),comment2);
		
		if(!isCommentToCommentSuccess)
		{
			result.setStatus(Result.FAILED);
			result.setTipCode("400");
			result.setTipMsg(" falied");
			return result;
		}
		
		result.setStatus(Result.SUCCESS);
		result.setTipCode("200");
		result.setTipMsg("comment success");
        
		return result;
	}
	

	
}
