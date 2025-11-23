import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    List<Customer> customerList=new ArrayList<Customer>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id=req.getParameter("id");
        String name=req.getParameter("name");
        String address=req.getParameter("address");
        System.out.println(id+name+address);
        customerList.add(new Customer(id,name,address));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        for(Customer c:customerList){
            resp.getWriter().println("<tr>" +
                    "<td>"+c.getId()+"</td>" +
                    "<td>"+c.getName()+"</td>" +
                    "<td>"+c.getAddress()+"</td>" +
                    "</tr>");
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        if (id == null || id.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                address == null || address.trim().isEmpty()) {

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"message\":\"Missing required parameters\"}");
            return;
        }

        Customer toUpdate = null;

        for (Customer c : customerList) {
            if (c.getId().equals(id)) {
                toUpdate = c;
                break;
            }
        }

        if (toUpdate == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.write("{\"message\":\"Customer not found\"}");
            return;
        }

        // Update fields
        toUpdate.setName(name);
        toUpdate.setAddress(address);

        resp.setStatus(HttpServletResponse.SC_OK);
        out.write("{\"message\":\"Customer updated successfully\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("DELETE request received...");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String id = req.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"message\":\"Missing required parameters\"}");
            return;
        }

        Customer toRemove = null;

        for (Customer c : customerList) {
            if (c.getId().equals(id)) {
                toRemove = c;
                break;
            }
        }

        if (toRemove == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.write("{\"message\":\"Customer not found\"}");
            return;
        }

        customerList.remove(toRemove);

        resp.setStatus(HttpServletResponse.SC_OK);
        out.write("{\"message\":\"Customer deleted successfully\"}");
    }
}