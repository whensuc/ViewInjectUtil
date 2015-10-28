package com.dyg.work.utils.inject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;

/**
 * 
          
        * ����:  ע�⹤����
        *  
        * @author wzy
        * @version 1.0  
        * @since 2015��10��16�� ����5:12:43
 */
public class ViewInjectUtils {
	private static final String METHOD_SET_CONTENTVIEW = "setContentView";
	private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";  
	
	/**
	 * 
	        * ����:  ע�Ⲽ���ļ� ��ʽ:@ContentView(value = R.layout.XXX)  
	        * @param activity  
	        * @author wzy 2015��10��16�� ����4:56:46
	 */
	private static void injectContentView(Activity activity)  
    {  
        Class<? extends Activity> clazz = activity.getClass();  
        // ��ѯ�����Ƿ����ContentViewע��  
        ContentView contentView = clazz.getAnnotation(ContentView.class);  
        if (contentView != null)// ����  
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
	        * ����:  ע��ؼ� ��ʽ��@ViewInject(R.id.XXX)  
	        * @param activity  
	        * @author wzy 2015��10��16�� ����4:58:41
	 */
	private static void injectViews(Activity activity)  
    {  
        Class<? extends Activity> clazz = activity.getClass();  
        Field[] fields = clazz.getDeclaredFields();  
        // �������г�Ա����  
        for (Field field : fields)  
        {            
            ViewInject viewInjectAnnotation = field  
                    .getAnnotation(ViewInject.class);  
            if (viewInjectAnnotation != null)  
            {  
                int viewId = viewInjectAnnotation.value();  
                if (viewId != -1)  
                {  
                    // ��ʼ��View  
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
	        * ����:  onCreate�е���
	        * @param activity  
	        * @author wzy 2015��10��16�� ����5:00:15
	 */
	public static void inject(Activity activity)  
    {         
        injectContentView(activity);  
        injectViews(activity);        
    }  
}
