package cn.edu.bjtu.weibo.controller;





import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.bjtu.weibo.model.Comment;

/**
 * 
 * @author a
 *
 */

@RestController
@RequestMapping("/c")
public class CommentController {
	@RequestMapping(value = "/comment")
	public Result getComment(){
	 
		//Result result = new Result();
		
		/**
		 * private String content;
		 * private int like;private 
		 * String date;	
		 * private int commentNumber;	
		 * private String userId;
		 * 
		 * 
		 * private List<String> atUserIdList;
		 * private List<String> topicIdList;
		 * 
		 * 
		 * private String commentOrWeiboId
		 */
		
		//Comment comment = new Comment();		
		//result.setData(comment);

		
		
		return "Œ“ « comment controller!!";
		
	}
}
