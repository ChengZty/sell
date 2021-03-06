package com.buss.kuaidi.callBack;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.buss.kuaidi.pojo.NoticeRequest;
import com.buss.kuaidi.pojo.NoticeResponse;
import com.buss.kuaidi.pojo.Result;
import com.buss.kuaidi.postOrder.JacksonHelper;

public class Callback extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Callback() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		NoticeResponse resp = new NoticeResponse();
		resp.setResult(false);
		resp.setReturnCode("500");
		resp.setMessage("保存失败");
		try {
			String param = request.getParameter("param");
			NoticeRequest nReq = JacksonHelper.fromJSON(param,
					NoticeRequest.class);

			Result result = nReq.getLastResult();
			// 处理快递结果
			//request.setAttribute("data", result);
			resp.setResult(true);
			resp.setReturnCode("200");
			response.getWriter().print(JacksonHelper.toJSON(resp)); //这里必须返回，否则认为失败，过30分钟又会重复推送。
		} catch (Exception e) {
			resp.setMessage("保存失败" + e.getMessage());
			response.getWriter().print(JacksonHelper.toJSON(resp));//保存失败，服务端等30分钟会重复推送。
		}
	}
	 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
