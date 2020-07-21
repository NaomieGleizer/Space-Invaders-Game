package animations;

/**
 * Menu interface.
 *
 * @param <T>
 */
public interface Menu<T> extends Animation {

    /**
     * Generics for menu.
     *
     * @param key       key
     * @param message   message
     * @param returnVal return value
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return status.
     */
    T getStatus();

    /**
     * add a sub menu.
     *
     * @param key     key
     * @param message level type
     * @param subMenu sub menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
