package com.dyg.work.utils.inject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;

/**
 * 
          
        * 描述:  注解工具类
        *  
        * @author wzy
        * @version 1.0  
        * @since 2015年10月16日 下午5:12:43
 */
public class ViewInjectUtils {
	private static final String METHOD_SET_CONTENTVIEW = "setContentView";
	private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";  
	
	/**
	 * 
	        * 描述:  注解布局文件 格式:@ContentView(value = R.layout.XXX)  
	        * @param activity  
	        * @author wzy 2015年10月16日 下午4:56:46
	 */
	private static void injectContentView(Activity activity)  
    {  
        Class<? extends Activity> clazz = activity.getClass();  
        // 查询类上是否存在ContentView注解  
        ContentView contentView = clazz.getAnnotation(ContentView.class);  
        if (contentView != null)// 存在  
        {  
            int contentViewLayoutId = contentView.value();  
            try  
            {  
                Method method = clazz.getMethod(METHOD_SET_CONTENTVIEW,  
                        int.class);  
                method.setAccessible(true);  
                method.invoke(activity, contentViewLayoutId);  
            } catch (Exception e)  
            {  
                e.printStackTrace();  
            }  
        }  
    } 
	
	/**
	 * 
	        * 描述:  注解控件 格式：@ViewInject(R.id.XXX)  
	        * @param activity  
	        * @author wzy 2015年10月16日 下午4:58:41
	 */
	private static void injectViews(Activity activity)  
    {  
        Class<? extends Activity> clazz = activity.getClass();  
        Field[] fields = clazz.getDeclaredFields();  
        // 遍历所有成员变量  
        for (Field field : fields)  
        {            
            ViewInject viewInjectAnnotation = field  
                    .getAnnotation(ViewInject.class);  
            if (viewInjectAnnotation != null)  
            {  
                int viewId = viewInjectAnnotation.value();  
                if (viewId != -1)  
                {  
                    // 初始化View  
                    try  
                    {  
                        Method method = clazz.getMethod(METHOD_FIND_VIEW_BY_ID,  
                                int.class);  
                        Object resView = method.invoke(activity, viewId);  
                        field.setAccessible(true);  
                        field.set(activity, resView);  
                    } catch (Exception e)  
                    {  
                        e.printStackTrace();  
                    }  
                }  
            }   
        }   
    }  
	
	/**
	 * 
	        * 描述:  onCreate中调用
	        * @param activity  
	        * @author wzy 2015年10月16日 下午5:00:15
	 */
	public static void inject(Activity activity)  
    {         
        injectContentView(activity);  
        injectViews(activity);        
    }  
}
