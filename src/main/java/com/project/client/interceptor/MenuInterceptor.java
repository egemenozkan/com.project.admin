package com.project.client.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.project.api.data.enums.Language;
import com.project.api.data.enums.MainType;
import com.project.api.data.enums.PlaceType;

public class MenuInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(MenuInterceptor.class);
	private final static MainType[] PLACE_MAIN_TYPES = MainType.values();
	private final static PlaceType[] PLACE_TYPES = PlaceType.values();
	private final static Language[] LANGUAGES = Language.values();

	/**
	 * Executed before actual handler is executed
	 **/
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

		return true;
	}

	/**
	 * Executed before after handler is executed
	 **/
	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final ModelAndView modelAndView) throws Exception {
		if (modelAndView == null || (modelAndView.getStatus() != null && modelAndView.getStatus().is3xxRedirection())) {
			return;
		}

		modelAndView.getModelMap().addAttribute("languages", LANGUAGES);
		modelAndView.getModelMap().addAttribute("placeMainTypes", PLACE_MAIN_TYPES);
		modelAndView.getModelMap().addAttribute("placeTypes", PLACE_TYPES);

		String path = request.getRequestURI();
		if (path == null || path.isEmpty())
			return;

		String attr = path.replace('/', '_').replace("-", "");
		int ind = 0;

		do {
			request.setAttribute("active" + attr, "open active");
			ind = attr.lastIndexOf('_');
			if (ind <= 0) {
				break;
			}

			attr = attr.substring(0, ind);

		} while (true);

	}

	/**
	 * Executed after complete request is finished
	 **/
	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final Exception ex) throws Exception {

	}

}
