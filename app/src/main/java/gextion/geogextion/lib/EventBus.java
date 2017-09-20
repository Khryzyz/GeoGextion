package gextion.geogextion.lib;

/**
 * Interface para el manejo de eventos
 */
public interface EventBus {
    void register(Object Subscriber);
    void unregister(Object Subscriber);
    void post(Object Event);
}
