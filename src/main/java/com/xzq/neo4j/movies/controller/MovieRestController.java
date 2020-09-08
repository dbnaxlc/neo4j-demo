package com.xzq.neo4j.movies.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xzq.neo4j.movies.service.MovieService;

@RestController
@RequestMapping("/movierest")
public class MovieRestController {

	@Autowired
	private MovieService movieService;
	
	@GetMapping("/index")
    public ModelAndView require() {
        return new ModelAndView("page/graph");
    }
	
    @GetMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		return movieService.graph(limit == null ? 100 : limit);
	}
}
