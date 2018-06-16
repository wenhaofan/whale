package com.wenhaofan.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.Ret;
import com.wenhaofan.common.exception.MsgException;

public class ExceptionInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		try {
			inv.invoke();
		} catch (MsgException e) {
			inv.getController().renderJson(Ret.fail("msg", e.getMessage()));
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			inv.getController().renderJson(Ret.fail("msg", "出bug了！"));
		}
	}

}
