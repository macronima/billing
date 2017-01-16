package billing.test;

import billing.test.dao.DBConnection;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Servlet implementation class MyServlet
 */
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /* PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>demolet</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
        out.close();*/
       process(request,response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    public void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        PrintWriter out = res.getWriter();
        res.setContentType("text/html;charset=UTF-8");
       // Connection con = null;
        try(Connection con = DBConnection.getDBConnection()) {

            StructDescriptor structDesc = StructDescriptor.createDescriptor("TYPE_OBJ", con);
            ArrayList arrayList = new ArrayList();
            Object[] record = new Object[3];
            //Определение значений и создание объекта STRUCT./*
            record[0] = Integer.valueOf(req.getParameter("id"));
            record[1] = req.getParameter("code");
            record[2] = req.getParameter("text");
            arrayList.add(new STRUCT(structDesc, con, record));
            HttpSession session = req.getSession();
            arrayList = Main.s4(con,arrayList);
            out.println("<html>");
            out.println("<head><title>demolet</title></head>");
            out.println("<body>");
            out.println("<p>");
            for (Object obj : arrayList) {
                record = ((STRUCT) obj).getAttributes();
                out.println(record[0] + " | " + record[1] + " | " + record[2]);
                System.out.println("asdasd");
            }
            out.println("</p>");
            out.println("</body></html>");

        } catch (Exception e) {

        } finally {
             RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/index.html");
            reqDispatcher.forward(req,res);

        }

    }
}