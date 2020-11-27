//usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS io.quarkus:quarkus-resteasy:1.9.2.Final, io.quarkus:quarkus-mailer:1.9.2.Final, io.quarkus:quarkus-arc:1.9.2.Final
//JAVAC_OPTIONS -parameters
//JAVA_OPTIONS -Djava.util.logging.manager=org.jboss.logmanager.LogManager

//Q:CONFIG quarkus.mailer.from=probinso@redhat.com
//Q:CONFIG quarkus.mailer.host=smtp.corp.redhat.com
//Q:CONFIG quarkus.mailer.port=465
//Q:CONFIG quarkus.mailer.ssl=true
//Q:CONFIG quarkus.mailer.username=
//Q:CONFIG quarkus.mailer.password=
//Q:CONFIG quarkus.mailer.mock=false


//import io.quarkus.mailer.Mail;
//import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.runtime.Quarkus;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/hello")
@ApplicationScoped
public class quarkusapp {

    @Inject
    Mailer mailer;

    @GET
    public String sayHello() {
        return "hello";
    }

    public static void main(String[] args) {
        Quarkus.run(args);
    }

    @Inject
    GreetingService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    public String greeting(@PathParam String name) {
        System.out.println("Sending email...");
        mailer.send(Mail.withText("probinso@redhat.com", "A simple email from quarkus", "This is my body."));
        return service.greeting(name);
    }

    @ApplicationScoped
    static public class GreetingService {

        public String greeting(String name) {
            return "hello " + name;
        }
    }
}