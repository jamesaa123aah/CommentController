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
 * �ܹ�ʵ������������
 * 
 * 1.ʵ�ַ��غ͸��û��йص���������
 * ���õ�һ��������url http://ip:port/c/comment1/
 * 
 * 2.���û������ˣ��������ۣ����û������۴������ݿ�
 * ���õڶ���������url http://ip:port/c/comment2/
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
	 * ��ȡ��ǰ����û�������
	 * 
	 * ����ȡ������û���ص��������۷��ظ�ǰ��
	 * 
	 * ǰ�˽�����ֱ��չʾ����
	 * 
	 * �ɹ����tipMsg:get comments successfully"
	 * 
	 * @param comment
	 * @return ����һ��Result ��ǰ��
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
	 * ǰ��post����	
	 * 
	 * �ύ�������ݵ����
	 * 
	 * ���д�����ݿ�
	 * 
	 * �ɹ����tipMsg:comment success
	 * 
	 * @param comment �����۵�����
	 * @param comment2  �˴����۵�����
	 * @return ����һ��Result��ǰ��
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
