import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/slc")
public class ServletLifeCycle extends HttpServlet {
    @Override
    public void init(){
        System.out.println("Servlet Life Cycle init");
    }
    @Override
    protected void doGet(HttpServletRequest req , HttpServletResponse resp){
        System.out.println("Servlet Life Cycle doGet");
    }
    public void destroy(){
        System.out.println("Servlet Life Cycle destroy");
    }

}
