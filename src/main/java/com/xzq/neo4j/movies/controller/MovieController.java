package com.xzq.neo4j.movies.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzq.neo4j.movies.service.MovieService;

@Controller
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@RequestMapping("/")
	public String main() {
		return "index";
	}

	@RequestMapping("/index")
	public String index(Model model) {
		return "index";
	}
	
	@RequestMapping(value="/list")
	public Object list(Model model){
		Map<String, Object> map = movieService.graph(1);
		model.addAttribute("datas", map.get("data"));
		model.addAttribute("links", map.get("link"));
		return "page/graph";
	}
	
	@RequestMapping(value="/graph1",method=RequestMethod.POST)
	@ResponseBody
	public Object add(){
		return ResponseModel.getModel("ok", "success", null);
	}
}
