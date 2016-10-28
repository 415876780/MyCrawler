package ccnu.computer.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CoutLineListener implements HttpSessionListener {

	 
	public void sessionCreated(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("����session......");   
        ServletContext context=event.getSession().getServletContext();   
        Integer count=(Integer)context.getAttribute("count");   
        if(count==null){   
            count=new Integer(1);   
        }else{   
            int co = count.intValue( );   
            count= new Integer(co+1);   
        }   
        System.out.println("��ǰ�û�����"+count);   
        context.setAttribute("count", count);//��������
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		 System.out.println("���session......");   
         ServletContext context=event.getSession().getServletContext();   
         Integer count=(Integer)context.getAttribute("count");   
         int co=count.intValue();   
         count=new Integer(co-1);   
         context.setAttribute("count", count);   
         System.out.println("��ǰ�û�����"+count);  
	}

}
