package Admin;
//import Entities.Employee;


import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANIL on 13/01/2018.
 */
@Path("/Hello")
@WebService()
public class HelloWorld {
  /*@WebMethod
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String sayHelloWorldFrom() {
    return "<h1>Hello Anna...!</h1>";
  }*/
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String sayHelloWorldFrom() {
    return "<h1>Hi Anna</h1>";
  }
/*
  @GET
  @Path("all")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Employee> all()
  {
    List<Employee> result=new ArrayList<Employee>();
    result.add(new Employee(1,"Anil"));
    result.add(new Employee(2,"Ajay"));
    result.add(new Employee(3,"Nadeem"));
    result.add(new Employee(4,"Mahesh"));
    result.add(new Employee(5,"Samadhan"));
    return result;
  }*/
  public static void main(String[] argv) {
    // Object implementor = new HelloWorld ();
    // String address = "http://localhost:9000/HelloWorld";
    // Endpoint.publish(address, implementor);
  }
}
