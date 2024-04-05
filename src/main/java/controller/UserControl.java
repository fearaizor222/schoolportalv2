package controller;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bean.User;

@Transactional
@Controller
@RequestMapping("user")
public class UserControl {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping("index")
	public String index(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql="FROM User";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<User> list = query.list();
		model.addAttribute("users", list);
		return "user/index";
	}
	@RequestMapping(value="delete/{username}")
	public String delete(ModelMap model, @PathVariable("username") String username) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			User needToDelete = (User) session.get(User.class, username);
			session.delete(needToDelete);
			t.commit();
		}
		catch(Exception e) {
			t.rollback();
		}
		finally {
			session.close();
		}
		return "redirect:/user/index.htm";
	}
	
	@RequestMapping(value="insert", method=RequestMethod.GET)
	public String insert(ModelMap model) {
		model.addAttribute("user", new User());
		return "user/insert";
	}
	
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(ModelMap model, @ModelAttribute("user") User user) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(user);
			t.commit();
			model.addAttribute("message", "thêm thành công");
		}
		catch(Exception e) {
			t.rollback();
			model.addAttribute("message", "thêm thất bại");
		}
		finally {
			session.close();
		}
		return "user/insert";
	}
	
	@RequestMapping(value="update", method=RequestMethod.GET)
	public String update(ModelMap model, @RequestParam String userId) {
		Session session = factory.openSession();
		User needToUpdate = (User) session.get(User.class, userId);
		model.addAttribute("user", needToUpdate);
		session.close();
		return "user/update";
	}
	
	@RequestMapping(value="update", method=RequestMethod.POST)
	public String update(ModelMap model, @ModelAttribute("user") User user) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(user);
			t.commit();
			model.addAttribute("message", "cập nhật thành công");
		}
		catch(Exception e) {
			t.rollback();
			model.addAttribute("message", "cập nhật thất bại");
		}
		finally {
			session.close();
		}
		return "user/update";
	}
	
	@RequestMapping(value="update",params="return")
	public String update() {
		return "redirect:/user/index.htm";
	}
}
