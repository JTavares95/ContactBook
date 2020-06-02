package org.example;


import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.example.resources.ContactResource;
import org.example.resources.HelloWorldResource;

@ApplicationPath("/rest")
public class ContactsBookApplication extends Application {

    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    public ContactsBookApplication() {
        classes.add(HelloWorldResource.class);
        classes.add(ContactResource.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}
