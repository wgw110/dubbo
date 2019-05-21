//package tv.mixiong.saas.school.util;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//
//public class SessionUtil {
//
//    public static void setSession(Object key, Object value) {
//        Subject currentUser = SecurityUtils.getSubject();
//        if (null != currentUser) {
//            Session session = currentUser.getSession();
//            session.setAttribute(key, value);
//        }
//    }
//
//    public static Object getAttribute(Object key){
//        Session session = SecurityUtils.getSubject().getSession();
//        return session.getAttribute(key);
//    }
//
//}
