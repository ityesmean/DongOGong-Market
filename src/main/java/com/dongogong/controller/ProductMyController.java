package com.dongogong.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.dongogong.domain.Post;
import com.dongogong.service.PostFacade;
@Controller
@RequestMapping("/apply/productMy.do")
public class ProductMyController {
	@Autowired(required=false)
	   private PostFacade postFacade;
		public void setPostFacade(PostFacade postFacade) {
			this.postFacade = postFacade;
		}
		@PostMapping
		public  ModelAndView productMy(HttpServletRequest request, Model model) throws Exception {
			UserSession userSession =
	                (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			
			String SpostIdx = request.getParameter("postIdx");
			int postIdx = Integer.valueOf(SpostIdx);
			
			
			model.addAttribute("userSession", userSession);
			model.addAttribute("productMy", productMy(postIdx));
			model.addAttribute("userId", userSession.getUserInfo().getUserId());
			
			return new ModelAndView("product_my");
		}
	public Post productMy(int postId) {
		return postFacade.selectPost(postId);
	}
}