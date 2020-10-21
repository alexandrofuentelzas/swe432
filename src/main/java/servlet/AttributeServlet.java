// From "Professional Java Server Programming", Patzer et al.,

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

// Import Java Libraries
import java.io.*;
import java.util.Enumeration;

@WebServlet(name = "attributeServlet", urlPatterns = {"/attribute"})
public class AttributeServlet extends HttpServlet
{
public void doGet (HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException
{
    

   String url = response.encodeURL("attribute");
   String action = request.getParameter("action");

   if( action != null && action.equals("invalidate")){
       // Called from the invalidate button, kill the session.
      // Get session object
      HttpSession session = request.getSession();
      session.invalidate();

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();

      out.println("<html>");
      out.println("<head>");
      out.println(" <title>Session Invalidation Page</title>");
      out.println("</head>");
      out.println("");
      out.println("<body>");

      out.println("<p>Your session has been invalidated.</P>");

      // Create a link so the user can create a new session.
      // The link will have a parameter builtin
      out.println("<a href=\"" + url + "?action=newSession\">");
      out.println("Create new session</A>");

      out.println("</body>");
      out.println("</html>");
      out.close();
   }else{

   // Get session object
   HttpSession session = request.getSession();

   String name   = request.getParameter("attrib_name");
   String value  = request.getParameter("attrib_value");
   String gender = request.getParameter("attrib_gender");
   String gvalue  = request.getParameter("attrib_gvalue");
   String remove = request.getParameter("attrib_remove");

   if (remove != null && remove.equals("on"))
   {
      session.removeAttribute(name);
      session.removeAttribute(gender);
   }
   else
   {
      if ((name != null && name.length() > 0) && (value != null && value.length() > 0) &&  
          (gender != null && gender.length() > 0) && (gvalue != null && gvalue.length() > 0) )
      {
         session.setAttribute(name, value);
         session.setAttribute(gender, gvalue);
      }

   }

   response.setContentType("text/html");
   PrintWriter out = response.getWriter();

   out.println("<html>");
   // no-cache lets the page reload by clicking on the reload link
   out.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">");
   out.println("<head>");
   out.println(" <title>Session lifecycle</title>");
   out.println("</head>");
   out.println("");

   out.println("<body>");
   out.println("<h1><center>Session attributes</center></h1>");

   out.println("Enter name, age, and value of an attribute");


   out.println("<form action=\"" + url + "\" method=\"GET\">");
   out.println(" Name: ");
   out.println(" <input type=\"text\" size=\"10\" name=\"attrib_name\">");

   out.println(" Value: ");
   out.println(" <input type=\"text\" size=\"10\" name=\"attrib_value\">");
   
   out.println(" Gender: ");
   out.println(" <input type=\"text\" size=\"3\" name=\"attrib_gender\">");

   out.println(" Value: ");
   out.println(" <input type=\"text\" size=\"10\" name=\"attrib_gvalue\">");
   
   out.println(" <br><input type=\"checkbox\" name=\"attrib_remove\">Remove");
   out.println(" <input type=\"submit\" name=\"update\" value=\"Update\">");
   out.println("</form>");
   out.println("<hr>");

   out.println("Attributes in this session:");
   Enumeration e = session.getAttributeNames();
   int count = 0;
   while (e.hasMoreElements())
   {
      String att_name  = (String) e.nextElement();
      String att_value = (String) session.getAttribute(att_name);
      
      if(count == 0){
      out.print  ("<br><b>Name:</b> ");
      out.println(att_name);
      out.print  ("<br><b>Value:</b> ");
      out.println(att_value);
      count++;
      }
      else{
      out.print  ("<br><b>Gender:</b> ");
      out.println(att_name);
      out.print  ("<br><b>Value:</b> ");
      out.println(att_value);
      }
   } //end while

   out.print  ("<br><br><a href=\"" + url + "?action=invalidate\">");
   out.println("Invalidate the session</a>");

   out.println("</body>");
   out.println("</html>");
   out.close();
   }
} // End doGet
} //End  SessionLifeCycle
